/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.mbeans;

import he1.seguridades.entities.SegMenuHabilita;
import he1.seguridades.entities.SegModulos;
import he1.seguridades.entities.SegPerfil;
import he1.seguridades.sessions.SegMenuHabilitaFacade;
import he1.seguridades.sessions.SegModulosFacade;
import he1.seguridades.sessions.SegPerfilFacade;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author christian_ruiz
 */
@ManagedBean(name = "habilitaPerfil")
@ViewScoped
public class JSFManagedBeanHabilPerdil implements Serializable {

    @EJB
    private SegMenuHabilitaFacade segMenuHabilitaFacade;

    @EJB
    private SegPerfilFacade segPerfilFacade;

    @EJB
    private SegModulosFacade segModulosFacade;

    private SegMenuHabilita segMenuHabilita = new SegMenuHabilita();
    private List<Boolean> bolean = new ArrayList<>();

    public static final String PROP_FINDMODULOSSORDENADOS = "PROP_FINDMODULOSSORDENADOS";
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    private final transient VetoableChangeSupport vetoableChangeSupport = new java.beans.VetoableChangeSupport(this);
    private String idperfil = "";
    private boolean value2;

    /**
     * Creates a new instance of JSFManagedBeanHabilPerdil
     */
    public JSFManagedBeanHabilPerdil() {

        this.findMenuhabilita = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        recuperaModulossOrdenados();
        recuperaTabla();
    }
    private List<SegPerfil> findModulossOrdenados = new ArrayList<>();
    private List<SegMenuHabilita> findMenuhabilita;

    /**
     * @return the findModulossOrdenados
     */
    public List<SegPerfil> getFindModulossOrdenados() {
        return findModulossOrdenados;
    }

    public void recuperaModulossOrdenados() {
        findModulossOrdenados.clear();
        List<SegModulos> list = new ArrayList<>();
        list = segModulosFacade.findModulossOrdenados();
        Iterator<SegModulos> iterator = list.iterator();
        while (iterator.hasNext()) {
            SegModulos next = iterator.next();
            List<SegPerfil> segPerfilList = next.getSegPerfilList();
            Iterator<SegPerfil> iterator1 = segPerfilList.iterator();
            while (iterator1.hasNext()) {
                SegPerfil next1 = iterator1.next();
                next1.setPerDescripcion(next.getModNombreModulo() + "-" + next1.getPerDescripcion());
                findModulossOrdenados.add(next1);

            }
        }

    }

    public void recuperaTabla() {
        try {
            SegPerfil segPerfil = segPerfilFacade.find(new BigDecimal(idperfil));
            findMenuhabilita = segMenuHabilitaFacade.findHabilitados(segPerfil);

            bolean.clear();
            Iterator<SegMenuHabilita> iterator = findMenuhabilita.iterator();
            while (iterator.hasNext()) {
                SegMenuHabilita next = iterator.next();
                Boolean b = (next.getMhaHabilita() == new BigInteger("1")) ? true : false;
//                next.setMhaHabilita();
            }
        } catch (Exception e) {
            System.out.println("e = " + e.getLocalizedMessage());
        }

    }
    private boolean estado = false;

    SegPerfil segPerfil = new SegPerfil();

    public void listen(AjaxBehaviorEvent event) {

        recuperaTabla();

    }

    /**
     * @return the idperfil
     */
    public String getIdperfil() {
        return idperfil;
    }

    /**
     * @param idperfil the idperfil to set
     */
    public void setIdperfil(String idperfil) {
        this.idperfil = idperfil;
    }

    /**
     * @return the findMenuhabilita
     */
    public List<SegMenuHabilita> getFindMenuhabilita() {
        return findMenuhabilita;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * @return the value2
     */
    public boolean isValue2() {
        return value2;
    }

    /**
     * @param value2 the value2 to set
     */
    public void setValue2(boolean value2) {
        this.value2 = value2;
    }

    /**
     * @return the segMenuHabilita
     */
    public SegMenuHabilita getSegMenuHabilita() {
        return segMenuHabilita;
    }

    /**
     * @param segMenuHabilita the segMenuHabilita to set
     */
    public void setSegMenuHabilita(SegMenuHabilita segMenuHabilita) {
        this.segMenuHabilita = segMenuHabilita;
    }

    public void addMessage() {
        String summary = value2 ? "Checked" : "Unchecked";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    /**
     * @return the bolean
     */
    public List<Boolean> getBolean() {
        return bolean;
    }

    /**
     * @param bolean the bolean to set
     */
    public void setBolean(List<Boolean> bolean) {
        this.bolean = bolean;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {

        this.estado = estado;
    }



    public String action() {
        segMenuHabilitaFacade.edit(segMenuHabilita);
        return null;
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
