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
public class Factura extends DocumentoComercial{
 
    @OneToMany(mappedBy="factura")
    @CollectionView("SinClienteNiFactura") // Esta vista se usa para visualizar pedidos
    Collection<Pedido> pedidos; // Colección de entidades Pedido añadida
	
}