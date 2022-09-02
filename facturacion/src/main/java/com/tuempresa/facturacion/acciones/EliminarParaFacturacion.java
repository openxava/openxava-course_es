package com.tuempresa.facturacion.acciones;  // En el paquete 'acciones'
 

import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;
 
public class EliminarParaFacturacion
    extends ViewBaseAction { // ViewBaseAction tiene getView(), addMessage(), etc
 
    public void execute() throws Exception {
        if (!getView().getMetaModel().containsMetaProperty("eliminado")) {
            executeAction("CRUD.delete"); // LLamamos a la acci�n est�ndar
            return;                       //   de OpenXava para borrar
        }
        
     // Cuando "eliminado" existe usamos nuestra propia l�gica de borrado
        Map<String, Object> valores =
            new HashMap<>(); // Los valores a modificar en la entidad
        valores.put("eliminado", true); // Asignamos true a la propiedad 'eliminado'
        MapFacade.setValues( // Modifica los valores de la entidad indicada
            getModelName(), // Un m�todo de ViewBaseAction
            getView().getKeyValues(), // La clave de la entidad a modificar
            valores); // Los valores a cambiar
        resetDescriptionsCache(); // Reinicia los caches para los combos
        addMessage("object_deleted", getModelName());
        getView().clear();
        getView().setKeyEditable(true);
        getView().setEditable(false); // Dejamos la vista como no editable
    }
    
}