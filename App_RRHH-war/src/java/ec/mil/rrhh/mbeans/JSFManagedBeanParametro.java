/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.rrhh.mbeans;

import he1.seguridades.entities.nuevos.SegMinisterioFinanzas;
import he1.seguridades.entities.nuevos.TipoRelacionLaboral;
import he1.seguridades.sessions.SegMinisterioFinanzasFacade;
import he1.seguridades.sessions.TipoRelacionLaboralFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author christian_ruiz
 */
@ManagedBean(name = "parametro")
@ViewScoped
public class JSFManagedBeanParametro implements Serializable {

    public JSFManagedBeanParametro() {
    }
    @EJB
    private TipoRelacionLaboralFacade tipoRelacionLaboralFacade;

    @EJB
    private SegMinisterioFinanzasFacade segMinisterioFinanzasFacade;

    private SegMinisterioFinanzas segMinisterioFinanzas = new SegMinisterioFinanzas();
    private List<SegMinisterioFinanzas> segMinisterioFinanzases = new ArrayList<>();
    private List<TipoRelacionLaboral> tipoRelacionLaboral = new ArrayList<>();

    private TipoRelacionLaboral tipoRelacionLaboral1 = new TipoRelacionLaboral();

    @PostConstruct
    private void init() {
        recuperaListas();
    }

    public SegMinisterioFinanzas getSegMinisterioFinanzas() {
        return segMinisterioFinanzas;
    }

    /**
     * @param segMinisterioFinanzas the segMinisterioFinanzas to set
     */
    public void setSegMinisterioFinanzas(SegMinisterioFinanzas segMinisterioFinanzas) {
        this.segMinisterioFinanzas = segMinisterioFinanzas;
    }

    /**
     * @return the segMinisterioFinanzases
     */
    public List<SegMinisterioFinanzas> getSegMinisterioFinanzases() {
        return segMinisterioFinanzases;
    }

    private void recuperaListas() {

        segMinisterioFinanzases = segMinisterioFinanzasFacade.findOrdenados();
        tipoRelacionLaboral = getTipoRelacionLaboralFacade().findAll();
    }

    /**
     * @return the tipoRelacionLaboral
     */
    public List<TipoRelacionLaboral> getTipoRelacionLaboral() {
        return tipoRelacionLaboral;
    }

    /**
     * @return the tipoRelacionLaboralFacade
     */
    public TipoRelacionLaboralFacade getTipoRelacionLaboralFacade() {
        return tipoRelacionLaboralFacade;
    }

    /**
     * @param tipoRelacionLaboralFacade the tipoRelacionLaboralFacade to set
     */
    public void setTipoRelacionLaboralFacade(TipoRelacionLaboralFacade tipoRelacionLaboralFacade) {
        this.tipoRelacionLaboralFacade = tipoRelacionLaboralFacade;
    }

    /**
     * @return the tipoRelacionLaboral1
     */
    public TipoRelacionLaboral getTipoRelacionLaboral1() {
        return tipoRelacionLaboral1;
    }

    /**
     * @param tipoRelacionLaboral1 the tipoRelacionLaboral1 to set
     */
    public void setTipoRelacionLaboral1(TipoRelacionLaboral tipoRelacionLaboral1) {
        System.out.println("tipoRelacionLaboral1 = " + tipoRelacionLaboral1.getTrlIdtiporelacion());
        this.tipoRelacionLaboral1 = tipoRelacionLaboral1;
    }

    /**
     * @param event
     * @return the tipoRelacionLaboral
     */
    public void onRowEdit(RowEditEvent event) {
        segMinisterioFinanzas = ((SegMinisterioFinanzas) event.getObject());
        segMinisterioFinanzas.setMfiDesc(segMinisterioFinanzas.getMfiAnio() + "-" + segMinisterioFinanzas.getMfiMes() + "-" + segMinisterioFinanzas.getTipoRelacionLaboral().getTrlTiporelacion());
        segMinisterioFinanzasFacade.edit(segMinisterioFinanzas);
        recuperaListas();
        FacesMessage msg = new FacesMessage("Registro Editado", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        segMinisterioFinanzas = ((SegMinisterioFinanzas) event.getObject());
        segMinisterioFinanzasFacade.remove(segMinisterioFinanzas);
        recuperaListas();
        FacesMessage msg = new FacesMessage("Edit Cancelled", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void listen1(AjaxBehaviorEvent event) {
    }

    public String crear() {
        segMinisterioFinanzas.setId(BigDecimal.ONE);
        segMinisterioFinanzas.setMfiDesc(segMinisterioFinanzas.getMfiAnio() + "-" + segMinisterioFinanzas.getMfiMes() + "-" + segMinisterioFinanzas.getTipoRelacionLaboral().getTrlTiporelacion());
        segMinisterioFinanzasFacade.create(segMinisterioFinanzas);
        segMinisterioFinanzas = new SegMinisterioFinanzas();
        recuperaListas();
        return null;

    }

    public String buttonActionEliminar() {
        segMinisterioFinanzasFacade.remove(segMinisterioFinanzas);
        recuperaListas();
        FacesMessage msg = new FacesMessage("Registro eliminado", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return null;
    }
}
