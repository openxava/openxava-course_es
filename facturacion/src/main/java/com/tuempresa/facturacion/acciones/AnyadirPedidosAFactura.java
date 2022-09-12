package com.tuempresa.facturacion.acciones; // En el paquete 'acciones'

import java.rmi.*;
import java.util.*;

import javax.ejb.*;

import org.openxava.actions.*; // Para usar AddElementsToCollectionAction
import org.openxava.model.*;
import org.openxava.util.*;
import org.openxava.validators.*;

import com.tuempresa.facturacion.modelo.*;

public class AnyadirPedidosAFactura
    extends AddElementsToCollectionAction { // L�gica est�ndar para a�adir
                                            // elementos a la colecci�n
    public void execute() throws Exception {
        super.execute(); // Usamos la l�gica est�ndar "tal cual"
        getView().refresh(); // Para visualizar datos frescos, incluyendo los importes
    }                        // recalculados, que dependen de las l�neas de detalle

    protected void associateEntity(Map clave) // El m�todo llamado para asociar
        throws ValidationException, // cada entidad a la principal, en este caso para
            XavaException, ObjectNotFoundException,// asociar cada pedido a la factura
            FinderException, RemoteException
    {
        super.associateEntity(clave); // Ejecuta la l�gica est�ndar (1)
        Pedido pedido = (Pedido) MapFacade.findEntity("Pedido", clave); // (2)
        pedido.copiarDetallesAFactura(); // Delega el trabajo principal en la entidad (3)
    }
}