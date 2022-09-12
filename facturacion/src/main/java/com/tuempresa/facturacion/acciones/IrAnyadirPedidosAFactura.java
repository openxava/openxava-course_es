package com.tuempresa.facturacion.acciones; // En el paquete 'acciones'

import org.openxava.actions.*; // Para usar GoAddElementsToCollectionAction

public class IrAnyadirPedidosAFactura
    extends GoAddElementsToCollectionAction { // L�gica est�ndar para ir a la lista que
                                              // permite a�adir elementos a la colecci�n
    public void execute() throws Exception {
        super.execute(); // Ejecuta la l�gica est�ndar, la cual muestra un di�logo
        int numeroCliente =
            getPreviousView() // getPreviousView() es la vista principal (estamos en un di�logo)
                .getValueInt("cliente.numero"); // Lee el n�mero de cliente de la
                                                // factura actual de la vista
        getTab().setBaseCondition( // La condici�n de la lista de pedidos a a�adir
            "${cliente.numero} = " + numeroCliente +
            " and ${entregado} = true and ${factura} is null"
        );
    }
    
    public String getNextController() { // A�adimos este m�todo
        return "AnyadirPedidosAFactura"; // El controlador con las acciones disponibles
    }                                    // en la lista de pedidos a a�adir
}