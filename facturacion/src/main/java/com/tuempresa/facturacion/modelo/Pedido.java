package com.tuempresa.facturacion.modelo;

import javax.persistence.*;

import lombok.*;

@Entity @Getter @Setter
public class Pedido extends DocumentoComercial{

	@ManyToOne
    Factura factura;  // Referencia a factura añadida
	
}
