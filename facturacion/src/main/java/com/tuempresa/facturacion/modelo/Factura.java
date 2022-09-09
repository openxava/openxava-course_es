package com.tuempresa.facturacion.modelo;
 
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;
 
@Entity @Getter @Setter
@View( extendsView="super.DEFAULT", // La vista por defecto
members="pedidos { pedidos } "
)
@View( name="SinClienteNiPedidos", // Una vista llamada SinClienteNiPedidos
members=                       // que no incluye cliente ni pedidos
    "anyo, numero, fecha;" +   // Ideal para usarse desde Pedido
    "detalles;" +
    "observaciones"
)
@Tab(baseCondition = "eliminado = false") // Tab sin nombre, es el de por defecto
@Tab(name="Eliminado", baseCondition = "eliminado = true") // Tab con nombre
public class Factura extends DocumentoComercial{
 
    @OneToMany(mappedBy="factura")
    @CollectionView("SinClienteNiFactura") // Esta vista se usa para visualizar pedidos
    Collection<Pedido> pedidos; // Colecci�n de entidades Pedido a�adida
	
    
    public static Factura crearDesdePedidos(Collection<Pedido> pedidos)
            throws CrearFacturaException
        {
            Factura factura = null;
            for (Pedido pedido: pedidos) {
                if (factura == null) { // El primero pedido
                    pedido.crearFactura(); // Reutilizamos la l�gica para crear una
                                           // factura desde un pedido
                    factura = pedido.getFactura(); // y usamos la factura creada
                }
                else { // Para el resto de los pedidos la factura ya est� creada
                    pedido.setFactura(factura); // Asigna la factura
                    pedido.copiarDetallesAFactura(); // Un m�todo de Pedido para copiar las lineas
                } 
            } 
            if (factura == null) { // Si no hay pedidos
                throw new CrearFacturaException("pedidos_no_especificados");
            }
            return factura;
        }
}