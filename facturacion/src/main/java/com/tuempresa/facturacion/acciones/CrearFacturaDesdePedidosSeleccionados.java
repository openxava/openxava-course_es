package com.tuempresa.facturacion.acciones;

import java.util.*;

import javax.ejb.*;

import org.openxava.actions.*;
import org.openxava.model.*;

import com.tuempresa.facturacion.modelo.*;

public class CrearFacturaDesdePedidosSeleccionados
    extends TabBaseAction { // Tipico de acciones de lista. Permite usar getTab() (1)

    public void execute() throws Exception {
        Collection<Pedido> pedidos = getPedidosSeleccionados(); // (2)
        Factura factura = Factura.crearDesdePedidos(pedidos); // (3)
        addMessage("factura_creada_desde_pedidos", factura, pedidos); // (4)
        
        showDialog(); // (1)
        // A partir de ahora getView() es el diálogo
        getView().setModel(factura); // Visualiza la factura en el diálogo (2)
        getView().setKeyEditable(false); // Para indicar que el objeto ya existe (3)
        setControllers("EdicionFactura"); // Las acciones del diálogo (4)
        
    }

    private Collection<Pedido> getPedidosSeleccionados() // (5)
        throws FinderException
    {
        Collection<Pedido> pedidos = new ArrayList<>();
        for (Map key: getTab().getSelectedKeys()) { // (6)
            Pedido pedido = (Pedido) MapFacade.findEntity("Pedido", key); // (7)
            pedidos.add(pedido);
        }
        return pedidos;
    }
}