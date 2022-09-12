package com.tuempresa.facturacion.acciones; // En el paquete 'acciones'

import java.util.*;

import org.openxava.actions.*; // Para usar OnChangeSearchAction
import org.openxava.model.*;
import org.openxava.view.*;

import com.tuempresa.facturacion.modelo.*;

public class BuscarAlCambiarFactura  
    extends OnChangeSearchAction { // L�gica est�ndar para buscar una referencia cuando
                                   // los valores clave cambian en la interfaz de usuario (1)
    public void execute() throws Exception {
        super.execute(); // Ejecuta la l�gica est�ndar (2)
        Map clave = getView() // getView() aqu� es la de la referencia, no la principal(3)
            .getKeyValuesWithValue();
        if (clave.isEmpty()) return;  // Si la clave est� vac�a no se ejecuta m�s l�gica
        Factura factura = (Factura) // Buscamos la factura usando la clave tecleada (4)
            MapFacade.findEntity(getView().getModelName(), clave);
        View vistaCliente = getView().getRoot().getSubview("cliente"); // (5)
        int numeroCliente = vistaCliente.getValueInt("numero");
        if (numeroCliente == 0) { // Si no hay cliente lo llenamos (6)
            vistaCliente.setValue("numero", factura.getCliente().getNumero());
            vistaCliente.refresh();
        } 
        else { // Si ya hay un cliente verificamos que coincida con el cliente de la factura (7)
            if (numeroCliente != factura.getCliente().getNumero()) {
                addError("cliente_factura_no_coincide", 
                    factura.getCliente().getNumero(), factura, numeroCliente);
                getView().clear();
            }
        }
    }
}	