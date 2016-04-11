/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.mil.rrhh.mbeans;

import he1.seguridades.entities.nuevos.TipoRelacionLaboral;
import he1.seguridades.sessions.TipoRelacionLaboralFacade;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author christian_ruiz
 */
@FacesConverter("converterMenuModulo")
public class ConverterTipo implements Converter {

    @EJB
    private TipoRelacionLaboralFacade tipoRelacionLaboralFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("value getAsObject= " + value);
        TipoRelacionLaboral find = tipoRelacionLaboralFacade.find(new BigDecimal(value));
        if (find == null) {
            return null;
        }
        return find;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.out.println("value getAsString= " + value);
        BigDecimal id = (value instanceof TipoRelacionLaboral) ? ((TipoRelacionLaboral) value).getTrlIdtiporelacion() : null;
        return (id != null) ? String.valueOf(id) : "-20";

    }

}
