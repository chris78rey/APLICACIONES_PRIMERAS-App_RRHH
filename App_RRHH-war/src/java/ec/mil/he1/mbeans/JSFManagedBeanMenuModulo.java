/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.mbeans;

import he1.seguridades.entities.SegMenu;
import he1.seguridades.entities.SegModulos;
import he1.seguridades.sessions.SegMenuFacade;
import he1.seguridades.sessions.SegModulosFacade;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author christian_ruiz
 */
@ManagedBean(name = "menu")
@ViewScoped
public class JSFManagedBeanMenuModulo implements Serializable {

    @EJB
    private SegMenuFacade segMenuFacade;

    @EJB
    private SegModulosFacade segModulosFacade;
    SegModulos modulos = new SegModulos();
    private List<SegMenu> segMenus = new ArrayList<>();
    private List<SegMenu> segMenusNivel2 = new ArrayList<>();
    private SegMenu segMenu = new SegMenu();
    private SegMenu segMenuDialog = new SegMenu();
    private SegModulos segModulos = new SegModulos();

    /**
     * Creates a new instance of JSFManagedBeanMenuModulo
     */
    public JSFManagedBeanMenuModulo() {
    }

    private List<SegModulos> findModulossOrdenados = new ArrayList<>();

    private List<SegModulos> segModuloses = new ArrayList<>();

    /**
     * @return the segModuloses
     */
    public List<SegModulos> getSegModuloses() {
        segModuloses.clear();
        findModulossOrdenados = segModulosFacade.findModulossOrdenados();
        for (SegModulos findModulossOrdenado : findModulossOrdenados) {
            segModuloses.add(findModulossOrdenado);
        }

        return segModuloses;
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
        recuperaMenu();
        recuperaMenuNivel2();
    }

    public void listen1(AjaxBehaviorEvent event) {

        recuperaMenu();
        recuperaMenuNivel2();
    }

    public void listen2(AjaxBehaviorEvent event) {

    }

    /**
     * @return the segMenus
     */
    public void recuperaMenu() {
        segMenus.clear();
        segMenus = segModulosFacade.findMenuBaseModulo(segModulos);
    }

    public void recuperaMenuNivel2() {
        setSegMenusNivel2(segModulosFacade.findMenuBaseModuloNivel2(segModulos));
    }

    public List<SegMenu> getSegMenus() {
        return segMenus;
    }

    /**
     * @return the segMenu
     */
    public SegMenu getSegMenu() {
        return segMenu;
    }

    /**
     * @param segMenu the segMenu to set
     */
    public void setSegMenu(SegMenu segMenu) {

        this.segMenu = segMenu;
    }

    private List<SegMenu> findMenuBaseModuloNivel1 = new ArrayList<>();

    /**
     * @return the findMenuBaseModuloNivel1
     */
    public List<SegMenu> getFindMenuBaseModuloNivel1() {
        findMenuBaseModuloNivel1.clear();
        findMenuBaseModuloNivel1 = segModulosFacade.findMenuBaseModuloNivel1(segModulos);
        return findMenuBaseModuloNivel1;
    }

    /**
     * @return the segMenuDialog
     */
    public SegMenu getSegMenuDialog() {
        return segMenuDialog;
    }

    /**
     * @param segMenuDialog the segMenuDialog to set
     */
    public void setSegMenuDialog(SegMenu segMenuDialog) {
 
        this.segMenuDialog = segMenuDialog;
    }

    public void buttonAction(ActionEvent actionEvent) {
        try {

            segMenu.setMenNivel(BigInteger.ONE);
            segMenu.setSegModulos(segModulos);
            segMenuFacade.create(segMenu);
            recuperaMenu();
            recuperaMenuNivel2();
            segMenu = new SegMenu();

        } catch (Exception e) {
            System.out.println("e = " + e.getLocalizedMessage());
        }

    }

    public void buttonAction2(ActionEvent actionEvent) {
        try {
            segMenu.setMenNivel(new BigInteger("2"));
            segMenu.setSegModulos(segModulos);
            segMenu.setSegMenu(segMenuDialog);
            segMenuFacade.create(segMenu);
            segMenu = new SegMenu();
            segMenuDialog = new SegMenu();
            recuperaMenuNivel2();
        } catch (Exception e) {
            System.out.println("e = " + e.getLocalizedMessage());
        }

    }

    private String menUrl;

    public void onRowEdit(RowEditEvent event) {
        SegMenu menu = (SegMenu) event.getObject();
        segMenu = menu;
        segMenuFacade.edit(menu);
        FacesMessage msg = new FacesMessage("Menú actualizado", menu.getMenUrl());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        recuperaMenu();
        recuperaMenuNivel2();
    }

    public void onRowEdit2(RowEditEvent event) {
        SegMenu menu = (SegMenu) event.getObject();
        segMenu = menu;
        segMenuFacade.edit(menu);
        FacesMessage msg = new FacesMessage("Menú actualizado", menu.getMenUrl());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        recuperaMenu();
        recuperaMenuNivel2();
    }

    public void onRowCancel(RowEditEvent event) {
        SegMenu menu = (SegMenu) event.getObject();
        recuperaMenu();
        recuperaMenuNivel2();
        FacesMessage msg = new FacesMessage("Cancelada la Edición", "Cancelada la Edición");
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void onRowCancel2(RowEditEvent event) {
        SegMenu menu = (SegMenu) event.getObject();
        recuperaMenu();
        recuperaMenuNivel2();
        FacesMessage msg = new FacesMessage("Cancelada la Edición", "Cancelada la Edición");
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * @return the menUrl
     */
    public String getMenUrl() {
        return menUrl;
    }

    /**
     * @param menUrl the menUrl to set
     */
    public void setMenUrl(String menUrl) {
        this.menUrl = menUrl;
    }

    @PostConstruct
    public void init() {
        recuperaMenu();
        recuperaMenuNivel2();
    }

    /**
     * @return the segMenusNivel2
     */
    public List<SegMenu> getSegMenusNivel2() {
        return segMenusNivel2;
    }

    /**
     * @param segMenusNivel2 the segMenusNivel2 to set
     */
    public void setSegMenusNivel2(List<SegMenu> segMenusNivel2) {
        this.segMenusNivel2 = segMenusNivel2;
    }

    public String buttonActionEliminar() {
        segMenuFacade.remove(segMenu);
        segMenu = new SegMenu();
        recuperaMenu();
        recuperaMenuNivel2();
        addMessage("El registro ha sido eliminado");
        return null;
    }

    public String buttonActionEliminar2() {
        boolean contains = segMenusNivel2.contains(segMenu);
        if (contains) {
            segMenusNivel2.remove(segMenu);
        }
        segMenuFacade.remove(segMenu);
        segMenu = new SegMenu();
        recuperaMenu();
        recuperaMenuNivel2();
        addMessage("El registro ha sido eliminado");
        return null;
    }

    public String destroyWorld() {

        return null;
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
