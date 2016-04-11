/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.mbeans;

import he1.seguridades.entities.SegUsuario;
import he1.utilities.SesionSeguridades;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author christian_ruiz
 */
@ManagedBean(name = "listacumulacion")
@ViewScoped
public class JSFManagedBeanAcumulacion {

    @EJB
    private SesionSeguridades sesionSeguridades;

    /**
     * Creates a new instance of JSFManagedBeanAcumulacion
     */
    public JSFManagedBeanAcumulacion() {
    }

    @PostConstruct
    private void init() {
        try {
            SegUsuario segusuario = new SegUsuario();
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
            segusuario = (SegUsuario) session.getAttribute("mbsegusuario");
        } catch (Exception e) {
            System.out.println("e = " + e.toString() );
            Throwable cause = e.getCause();
        }

    }

    private List<Map> listD13D14 = new ArrayList<>();


    public List<Map> getListD13D14() {
        List data = new ArrayList<HashMap>();
        data = sesionSeguridades.listD13D14("2015");
        listD13D14 = data;
        return listD13D14;
    }

}
