/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.mbeans;

import he1.seguridades.entities.SegUrls;
import he1.seguridades.entities.nuevos.VUsuariosClasif;
import he1.seguridades.sessions.SegUrlsFacade;
import he1.seguridades.sessions.SegUsuarioFacade;
import he1.seguridades.sessions.VUsuariosClasifFacade;
import he1.utilities.SesionSeguridades;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author christian_ruiz
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginJSFManagedBean implements Serializable {

    @EJB
    private SegUrlsFacade segUrlsFacade;

    private String existeFoto = "0";

    @EJB
    private VUsuariosClasifFacade vUsuariosClasifFacade;
    private VUsuariosClasif findByCedulaLogin = new VUsuariosClasif();

    @EJB
    private SegUsuarioFacade segUsuarioFacade;

    @EJB
    private SesionSeguridades encryptFacade;

    private String usuario = "";
    private String password1 = "";
    private String usuarioEncriptado = "";

    public String getUsuarioEncriptado() {
        return usuarioEncriptado;
    }

    @PostConstruct
    private void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        findByCedulaLogin = (VUsuariosClasif) session.getAttribute("vUsuariosClasif");
    }

    public void setUsuarioEncriptado(String usuarioEncriptado) {
        this.usuarioEncriptado = usuarioEncriptado;
    }

    String urlSiguiente = "";

    public String getUrlSiguiente() {
        return urlSiguiente;
    }

    public void setUrlSiguiente(String urlSiguiente) {
        this.urlSiguiente = urlSiguiente;
    }

    /**
     * Creates a new instance of LoginJSFManagedBean
     */
    public LoginJSFManagedBean() {

    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the password1
     */
    public String getPassword1() {
        return password1;
    }

    /**
     * @param password1 the password1 to set
     */
    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    /**
     * @return the findByCedulaLogin
     */
    public VUsuariosClasif getFindByCedulaLogin() {
        return findByCedulaLogin;
    }

    /**
     * @param findByCedulaLogin the findByCedulaLogin to set
     */
    public void setFindByCedulaLogin(VUsuariosClasif findByCedulaLogin) {
        this.findByCedulaLogin = findByCedulaLogin;
    }

    public String logout() throws IOException {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
            Enumeration<String> attributeNames = session.getAttributeNames();
            String nextElement = "";
            while (attributeNames.hasMoreElements()) {
                try {
                    nextElement = attributeNames.nextElement();
                    session.removeAttribute(nextElement);
                } catch (Exception e) {
                }
            }
            session = null;
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

            SegUrls findBySalir = segUrlsFacade.findBySalir("salirnp");
            ec.redirect(findBySalir.getUrl());
        } catch (Exception e) {
            System.out.println("e = " + e.getLocalizedMessage());
        }
        return "";
    }

    public String regresaMenuPortal() throws IOException {
        String usuId = encryptFacade.encyrpDinamico(findByCedulaLogin.getCedulaLogin());
        SegUrls findURL = segUrlsFacade.findURL("menu del portal");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(findURL.getUrl()+usuId);
        return null;
    }

    /**
     * @return the existeFoto
     */
    public String getExisteFoto() {
        return existeFoto;
    }

    /**
     * @param existeFoto the existeFoto to set
     */
    public void setExisteFoto(String existeFoto) {
        this.existeFoto = existeFoto;
    }
}
