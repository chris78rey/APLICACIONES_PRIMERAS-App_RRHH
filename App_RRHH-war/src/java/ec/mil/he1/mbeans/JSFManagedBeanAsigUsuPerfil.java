/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.mbeans;

import he1.seguridades.entities.SegModulos;
import he1.seguridades.entities.SegPerfil;
import he1.seguridades.entities.SegUsuarioPerfil;
import he1.seguridades.sessions.SegPerfilFacade;
import he1.seguridades.sessions.SegUsuarioPerfilFacade;
import he1.sis.entities.Personal;
import he1.utilities.SesionSeguridades;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author christian_ruiz
 */
@ManagedBean(name = "perfildeusuario")
@ViewScoped
public class JSFManagedBeanAsigUsuPerfil implements Serializable {
    
    private BigDecimal v;
    @EJB
    private SegPerfilFacade segPerfilFacade;
    
    @EJB
    private SegUsuarioPerfilFacade segUsuarioPerfilFacade;
    SegUsuarioPerfil up;
    @EJB
    private SesionSeguridades sesionSeguridades;
    
    private String criterioBusqueda = "";
    
    private SegUsuarioPerfil segUsuarioPerfil = new SegUsuarioPerfil();
    
    private Personal personal = new Personal();
    private SegPerfil segPerfil = new SegPerfil();
    
    private BigDecimal perid = new BigDecimal("0");

    /**
     * Creates a new instance of JSFManagedBeanAsigUsuPerfil
     */
    private List<Personal> findPersonalCriterios = new ArrayList<>();
    
    public JSFManagedBeanAsigUsuPerfil() {
        segPerfil = new SegPerfil();
    }

    /**
     * @return the findPersonalCriterios
     */
    public List<Personal> getFindPersonalCriterios() {
        criterioBusqueda = (criterioBusqueda == null) ? "-1522" : criterioBusqueda;
        findPersonalCriterios.clear();
        findPersonalCriterios = sesionSeguridades.findPersonalCriterios(criterioBusqueda);
        return findPersonalCriterios;
    }

    /**
     * @param findPersonalCriterios the findPersonalCriterios to set
     */
    public void setFindPersonalCriterios(List<Personal> findPersonalCriterios) {
        this.findPersonalCriterios = findPersonalCriterios;
    }

    /**
     * @return the criterioBusqueda
     */
    public String getCriterioBusqueda() {
        return criterioBusqueda;
    }

    /**
     * @param criterioBusqueda the criterioBusqueda to set
     */
    public void setCriterioBusqueda(String criterioBusqueda) {
        this.criterioBusqueda = criterioBusqueda;
    }
    
    public void listen1(AjaxBehaviorEvent event) {
        
        findPersonalCriterios = getFindPersonalCriterios();
        
    }

    /**
     * @return the personal
     */
    public Personal getPersonal() {
        return personal;
    }

    /**
     * @param personal the personal to set
     */
    public void setPersonal(Personal personal) {
        System.out.println("personal = " + personal.getApellidos());
        this.personal = personal;
    }
    
    private List<SegUsuarioPerfil> listSegUsuarioPerfil = new ArrayList<SegUsuarioPerfil>();

    /**
     * @return the listSegUsuarioPerfil
     */
    public List<SegUsuarioPerfil> getListSegUsuarioPerfil() {
        try {
            listSegUsuarioPerfil = sesionSeguridades.buscaPerfilesNQ(personal.getCodigo().toString());
        } catch (Exception e) {
        }
        
        return listSegUsuarioPerfil;
    }

    /**
     * @param listSegUsuarioPerfil the listSegUsuarioPerfil to set
     */
    public void setListSegUsuarioPerfil(List<SegUsuarioPerfil> listSegUsuarioPerfil) {
        this.listSegUsuarioPerfil = listSegUsuarioPerfil;
    }
    
    private List<SegPerfil> findPerfilesCriterios = new ArrayList<>();

    /**
     * @return the findPerfilesCriterios
     */
    public List<SegPerfil> getFindPerfilesCriterios() {
        try {
            findPerfilesCriterios = sesionSeguridades.findPerfilesCriterios("%%");
        } catch (Exception e) {
        }
        
        return findPerfilesCriterios;
    }

    /**
     * @param findPerfilesCriterios the findPerfilesCriterios to set
     */
    public void setFindPerfilesCriterios(List<SegPerfil> findPerfilesCriterios) {
        this.findPerfilesCriterios = findPerfilesCriterios;
    }

    /**
     * @return the listadePerfiles
     */
    private List<SelectItem> listadePerfiles = new ArrayList<>();
    
    public List<SelectItem> getListadePerfiles() {
        
        listadePerfiles.clear();
        List<SegModulos> findPerfiles = sesionSeguridades.findPerfiles();
        Iterator<SegModulos> iterator = findPerfiles.iterator();
        
        while (iterator.hasNext()) {
            SegModulos next = iterator.next();
            Iterator<SegPerfil> iterator1 = next.getSegPerfilList().iterator();
            SegPerfil next1;
            while (iterator1.hasNext()) {
                next1 = iterator1.next();
                SelectItem selectItem = new SelectItem(next1.getPerId(), next1.getSegModulos().getModNombreModulo() + "-" + next1.getPerDescripcion());
                
                listadePerfiles.add(selectItem);
            }
            
        }
        return listadePerfiles;
    }

    /**
     * @param listadePerfiles the listadePerfiles to set
     */
    public void setListadePerfiles(List<SelectItem> listadePerfiles) {
        
        this.listadePerfiles = listadePerfiles;
    }

    /**
     * @return the segPerfil
     */
    public SegPerfil getSegPerfil() {
        return segPerfil;
    }

    /**
     * @param segPerfil the segPerfil to set
     */
    public void setSegPerfil(SegPerfil segPerfil) {
        this.segPerfil = segPerfil;
    }
    
    public void listen1Per(AjaxBehaviorEvent event) {
        
    }
    
    public String accion() {


        segPerfil = segPerfilFacade.find(v);       

        SegUsuarioPerfil segUsuarioPerfil = new SegUsuarioPerfil();
        segUsuarioPerfil.setCodigoPersonal(personal.getCodigo());
        segUsuarioPerfil.setSegPerfil(segPerfil);
        segUsuarioPerfil.setUmoFechaCreacion(new Date());
        segUsuarioPerfil.setUmoActivo(new BigInteger("1"));
        segUsuarioPerfilFacade.create(segUsuarioPerfil);
        
        return null;
    }

    /**
     * @return the perid
     */
    public BigDecimal getPerid() {
        return perid;
    }

    /**
     * @param perid the perid to set
     */
    public void setPerid(BigDecimal perid) {
        this.perid = perid;
    }

    /**
     * @return the v
     */
    public BigDecimal getV() {
        
        return v;
    }

    /**
     * @param v the v to set
     */
    public void setV(BigDecimal v) {
        
        System.out.println("v = " + v);
        this.v = v;
    }
    
    public void buttonAction(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
    }
    
    public void buttonAction2(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
    }
    
    public void buttonActionQuitarPerfil(ActionEvent actionEvent) {
        System.out.println("actionEvent = " + segUsuarioPerfil.getCodigoPersonal());
        segUsuarioPerfilFacade.remove(segUsuarioPerfil);
    }
    
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * @return the segUsuarioPerfil
     */
    public SegUsuarioPerfil getSegUsuarioPerfil() {
        return segUsuarioPerfil;
    }

    /**
     * @param segUsuarioPerfil the segUsuarioPerfil to set
     */
    public void setSegUsuarioPerfil(SegUsuarioPerfil segUsuarioPerfil) {
        System.out.println("segUsuarioPerfil = " + segUsuarioPerfil.getSegPerfil().getPerDescripcion());
        this.segUsuarioPerfil = segUsuarioPerfil;
    }
}
