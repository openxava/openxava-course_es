package com.tuempresa.facturacion.modelo;
 
import java.util.*;

import javax.persistence.*;

import lombok.*;
 
@Entity @Getter @Setter
public class Factura extends DocumentoComercial{
 
	@OneToMany(mappedBy="factura")
    Collection<Pedido> pedidos; // Colecci�n de entidades Pedido a�adida
	
}