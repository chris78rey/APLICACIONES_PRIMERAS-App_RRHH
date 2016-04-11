/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.mbeans;

import he1.seguridades.entities.SegModulos;
import he1.seguridades.entities.SegPerfil;
import he1.seguridades.sessions.SegModulosFacade;
import he1.seguridades.sessions.SegPerfilFacade;
import he1.utilities.SesionSeguridades;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author christian_ruiz
 */
@ManagedBean(name = "perfil")
@ViewScoped
public class JSFManagedBeanPerfil implements Serializable {
    
    @EJB
    private SesionSeguridades sesionSeguridades;
    
    @EJB
    private SegPerfilFacade segPerfilFacade;
    @EJB
    private SegModulosFacade segModulosFacade;
    
    private List<SegPerfil> listSegPerfil = new ArrayList<>();
    private SegModulos segModulos = new SegModulos();
    SegPerfil segPerfil = new SegPerfil();
    private SegPerfil segPerfilModificar = new SegPerfil();
    
    private String txt1;
    private String idModulo;
    
    private List<SegModulos> listSegModulos = new ArrayList<>();

    /**
     * Creates a new instance of JSFManagedBeanPerfil
     */
    public JSFManagedBeanPerfil() {
    }

    /**
     * @return the listSegPerfil
     */
    public List<SegPerfil> getListSegPerfil() {
        txt1 = (txt1 == null) ? "%" : txt1;
        List<SegModulos> findModuloPorNombre = sesionSeguridades.findModuloPorNombre(txt1);
//        

        Iterator<SegModulos> iterator = findModuloPorNombre.iterator();
        while (iterator.hasNext()) {
            setSegModulos(iterator.next());
            
        }
        listSegPerfil.clear();
        listSegPerfil = sesionSeguridades.findPerfil(getSegModulos());
        return listSegPerfil;
    }

    /**
     * @param listSegPerfil the listSegPerfil to set
     */
    public void setListSegPerfil(List<SegPerfil> listSegPerfil) {
        this.listSegPerfil = listSegPerfil;
    }

    /**
     * @return the segModulos
     */
    public SegModulos getSegModulos() {
        return segModulos;
    }

    /**
     * @param segModulos the segModulos to set
     */
    public void setSegModulos(SegModulos segModulos) {
        this.segModulos = segModulos;
    }

    /**
     * @return the listSegModulos
     */
    public List<SegModulos> getListSegModulos() {
        listSegModulos = segModulosFacade.findAll();
        //cities

        return listSegModulos;
    }

    /**
     * @param listSegModulos the listSegModulos to set
     */
    public void setListSegModulos(List<SegModulos> listSegModulos) {
        this.listSegModulos = listSegModulos;
    }

    /**
     * @return the modulos
     */
    private Map<String, String> modulos = new HashMap<String, String>();
    
    public Map<String, String> getModulos() {
        
        modulos = new HashMap<String, String>();
        
        Iterator<SegModulos> iterator = segModulosFacade.findAll().iterator();
        while (iterator.hasNext()) {
            SegModulos next = iterator.next();
            modulos.put(next.getModNombreModulo(), next.getModId().toString());
        }
        
        return modulos;
    }

    /**
     * @param modulos the modulos to set
     */
    public void setModulos(Map<String, String> modulos) {
        this.modulos = modulos;
    }

    /**
     * @return the txt1
     */
    public String getTxt1() {
        return txt1;
    }

    /**
     * @param txt1 the txt1 to set
     */
    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    /**
     * @return the cities
     */
    public List<String> completeText(String query) {
        List<String> RetornaAutocompleteModulo = sesionSeguridades.RetornaAutocompleteModulo(query);
        return RetornaAutocompleteModulo;
    }
    
    public void buttonAction(ActionEvent actionEvent) {
        System.out.println("txt1 = " + txt1);
        listSegPerfil.clear();
        listSegPerfil = getListSegPerfil();
        
    }

    /**
     * @return the segPerfilModificar
     */
    public SegPerfil getSegPerfilModificar() {
        return segPerfilModificar;
    }

    /**
     * @param segPerfilModificar the segPerfilModificar to set
     */
    public void setSegPerfilModificar(SegPerfil segPerfilModificar) {
        
        System.out.println("segPerfilModificar = " + segPerfilModificar.getPerDescripcion());
        this.segPerfilModificar = segPerfilModificar;
    }
    
    public void buttonAction_crear() {
        
        try {
            SegPerfil perfil = new SegPerfil();
            perfil.setPerDescripcion(segPerfilModificar.getPerDescripcion());
            perfil.setSegModulos(getSegModulos());
            perfil.setPerFechaCreacion(new Date());
            perfil.setPerDefault(new BigInteger("1"));
            perfil.setPerOrdenPresentacion(new BigInteger("10"));
            segPerfilFacade.create(perfil);
        } catch (Exception e) {
        }
        
    }
    private static final Logger LOG = Logger.getLogger(JSFManagedBeanPerfil.class.getName());
    
    public void buttonAction_nuevo() {
        segPerfilModificar = new SegPerfil();
        
    }
    
    public void buttonAction_modificar() {
        try {
            segPerfilFacade.edit(segPerfilModificar);
        } catch (Exception e) {
        }
    }
    
    public void buttonAction_eliminar() {
        try {
            segPerfilFacade.remove(segPerfilModificar);
        } catch (Exception e) {
        }
        
    }

    /**
     * @return the idModulo
     */
    public String getIdModulo() {
        return idModulo;
    }

    /**
     * @param idModulo the idModulo to set
     */
    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }
    
    public void listen(AjaxBehaviorEvent event) {
    }
    
    public void listen1(AjaxBehaviorEvent event) {
    }
}
