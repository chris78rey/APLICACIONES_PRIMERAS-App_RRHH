/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.rrhh.mbeans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "booleanToYNConverter")
public class BooleanToYNConverter implements Converter {

    public BooleanToYNConverter() {
    }

    @Override
    public Object getAsObject(FacesContext facesContext,
            UIComponent uiComponent,
            String param) {
        Boolean result = Boolean.parseBoolean(param);
        try {
            if (param.equalsIgnoreCase("1")) {
                result = Boolean.TRUE;
            } else {
                result = Boolean.FALSE;
            }
            return result;
        } catch (Exception exception) {
            throw new ConverterException(exception);
        }
    }

    @Override
    public String getAsString(FacesContext facesContext,
            UIComponent uiComponent,
            Object obj) {
        try {
            if ((obj != null) && ((Boolean) obj)) {
                System.out.println("#BooleanToYNConverter.getAsString returns Y");
                return "Y";
            } else {
                System.out.println("#BooleanToYNConverter.getAsString returns N");
                return "N";
            }
        } catch (Exception exception) {
            throw new ConverterException(exception);
        }
    }
}
