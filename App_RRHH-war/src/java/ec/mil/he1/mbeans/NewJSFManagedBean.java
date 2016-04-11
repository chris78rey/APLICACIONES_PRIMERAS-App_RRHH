/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.he1.mbeans;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeSupport;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author christian_ruiz
 */
@ManagedBean
@RequestScoped
public class NewJSFManagedBean {

    public static final String PROP_STRING = "PROP_STRING";
    private String string = "";
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    private final transient VetoableChangeSupport vetoableChangeSupport = new java.beans.VetoableChangeSupport(this);

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public NewJSFManagedBean() {
    }

    /**
     * @return the string
     */
    public String getString() {
        return string;
    }

    /**
     * @param string the string to set
     */
    public void setString(String string) throws PropertyVetoException {
        java.lang.String oldString = this.string;
        vetoableChangeSupport.fireVetoableChange(PROP_STRING, oldString, string);
        this.string = string;
        propertyChangeSupport.firePropertyChange(PROP_STRING, oldString, string);
    }

    public void listen(AjaxBehaviorEvent event) {
        System.out.println("event = " + string);
    }

    public void buttonAction(ActionEvent actionEvent) {
        System.out.println("actionEvent = " + string);
        addMessage("Welcome to Primefaces!!");
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String accion() {
        System.out.println("accion = " + string);
        return null;
    }
}
