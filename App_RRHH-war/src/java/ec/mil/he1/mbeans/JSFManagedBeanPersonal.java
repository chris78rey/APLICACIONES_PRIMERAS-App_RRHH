/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.mbeans;

import he1.utilities.SesionSeguridades;
import he1.sis.entities.Personal;
import he1.sis.sessions.PersonalFacade;
import java.io.IOException;
import java.util.Enumeration;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author christian_ruiz
 */
@ManagedBean(name = "mbpersonal")
@RequestScoped
public class JSFManagedBeanPersonal {

    @EJB
    private SesionSeguridades sesionSeguridades;

    @EJB
    private PersonalFacade personalFacade;

    public Personal getPersonal() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        String usuarioDB = (String) session.getAttribute("usuarioDB");
        personal = personalFacade.find(usuarioDB);
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    Personal personal;

    /**
     * Creates a new instance of JSFManagedBeanPersonal
     */
    public JSFManagedBeanPersonal() {
        personal = new Personal();
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
            ec.redirect(sesionSeguridades.getPresionarSalir());
        } catch (Exception e) {
        }
        return "";
    }
}
