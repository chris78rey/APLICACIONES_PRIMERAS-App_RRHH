/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.mbeans;

import he1.seguridades.entities.SegModulos;
import he1.seguridades.entities.SegOrganizacion;
import he1.seguridades.entities.SegPerfil;
import he1.seguridades.sessions.SegModulosFacade;
import he1.seguridades.sessions.SegOrganizacionFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author christian_ruiz
 */
@ManagedBean(name = "modulos")
@SessionScoped
public class JSFManagedBeanModulos implements Serializable {

    @EJB
    private SegOrganizacionFacade segOrganizacionFacade;

    @EJB
    private SegModulosFacade segModulosFacade;
    private List<SegModulos> segModuloses = new ArrayList<>();
    private SegModulos segModulos = new SegModulos();
    private BigDecimal modId;
    SegOrganizacion segOrganizacion = new SegOrganizacion();
    SegModulos segModulos2;

    /**
     * Creates a new instance of JSFManagedBeanModulos
     */
    public JSFManagedBeanModulos() {
        List<SegModulos> segModuloses = new ArrayList<>();

    }

    /**
     * @return the segModuloses
     */
    public List<SegModulos> getSegModuloses() {
        segModuloses = segModulosFacade.findAll();
        return segModuloses;
    }

    public String accion(String p) {

        modId = new BigDecimal(p);

        return "";
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

    private boolean btnCrear = true;
    private boolean btnModificar = true;
    private boolean btnEliminar = true;

    public String buttonAction_nuevo(String tipo) {
        segModulos = new SegModulos();
        if (tipo.equalsIgnoreCase("Crear")) {
            btnCrear = true;
            btnModificar = false;
            btnEliminar = false;
        } else {

            btnCrear = false;
            btnModificar = true;
            btnEliminar = true;

        }

        return "";
    }

    public void buttonAction_nuevo() {
        System.out.println("this = " + this);
        segModulos = new SegModulos();
    }

    public void buttonAction_crear() {

        BigDecimal bd = new BigDecimal("1");
        SegOrganizacion find = segOrganizacionFacade.find(bd);
        SegModulos sm = new SegModulos();
        sm.setSegOrganizacion(find);
        sm.setModFechaCreacion(new Date());
        sm.setModNombreModulo(segModulos.getModNombreModulo());
        sm.setModUrl(segModulos.getModUrl());
        sm.setModUrlSiguiente(segModulos.getModUrlSiguiente());

        segModulosFacade.create(sm);
        segModuloses = segModulosFacade.findAll();
        segModulos = new SegModulos();
    }

    public String buttonAction_modificar() {

        segModulosFacade.edit(segModulos);
        addMessage("mofificar", "mofificar");
        return "";
    }

    public String buttonAction_eliminar() {
        segModuloses = segModulosFacade.findAll();
        segModulosFacade.remove(segModulos);

        addMessage("eliminar", "eliminar");
        return "";
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void listen1(AjaxBehaviorEvent event) {

    }

    public void listen2(AjaxBehaviorEvent event) {

    }

    public void listen3(AjaxBehaviorEvent event) {
    }

    /**
     * @return the btnCrear
     */
    public boolean isBtnCrear() {
        return btnCrear;
    }

    /**
     * @param btnCrear the btnCrear to set
     */
    public void setBtnCrear(boolean btnCrear) {
        this.btnCrear = btnCrear;
    }

    /**
     * @return the btnModificar
     */
    public boolean isBtnModificar() {
        return btnModificar;
    }

    /**
     * @param btnModificar the btnModificar to set
     */
    public void setBtnModificar(boolean btnModificar) {
        this.btnModificar = btnModificar;
    }

    /**
     * @return the btnEliminar
     */
    public boolean isBtnEliminar() {
        return btnEliminar;
    }

    /**
     * @param btnEliminar the btnEliminar to set
     */
    public void setBtnEliminar(boolean btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

}
