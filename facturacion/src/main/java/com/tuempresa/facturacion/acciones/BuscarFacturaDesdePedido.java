package com.tuempresa.facturacion.acciones; // En el paquete 'acciones'

import org.openxava.actions.*; // Para usar ReferenceSearchAction

public class BuscarFacturaDesdePedido
    extends ReferenceSearchAction { // L�gica est�ndar para buscar una referencia

    public void execute() throws Exception {
        int numeroCliente =
            getView().getValueInt("cliente.numero"); // Lee de la vista el n�mero
                                                  // de cliente del pedido actual
        super.execute(); // Ejecuta la l�gica est�ndar, la cual muestra un di�logo
        if (numeroCliente > 0) { // Si hay cliente los usamos para filtrar
            getTab().setBaseCondition("${cliente.numero} = " + numeroCliente);
        }
    }
}