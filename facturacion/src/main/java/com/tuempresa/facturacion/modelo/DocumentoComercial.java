package com.tuempresa.facturacion.modelo;

import java.math.*;
import java.time.*;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;
import org.openxava.calculators.*;
import org.openxava.jpa.*;

import com.tuempresa.facturacion.calculadores.*;

import lombok.*;

@Entity @Getter @Setter
@View(members=
"anyo, numero, fecha," + // Los miembros para la cabecera en una l�nea
"datos {" + // Una pesta�a 'datos' para los datos principales del documento
    "cliente;" +
    "detalles;" +
    "observaciones" +
"}")
abstract public class DocumentoComercial extends Eliminable{

	@Column(length=4)
    @DefaultValueCalculator(CurrentYearCalculator.class) // A�o actual
    int anyo;
 
	@Column(length = 6)
	//  @DefaultValueCalculator(value=CalculadorSiguienteNumeroParaAnyo.class, // Quita esto
	//      properties=@PropertyValue(name="anyo")
	//  )
	@ReadOnly // El usuario no puede modificar el valor
	int numero;
 
    @Required
    @DefaultValueCalculator(CurrentLocalDateCalculator.class) // Fecha actual
    LocalDate fecha;
 
    @ManyToOne(fetch=FetchType.LAZY, optional=false) // El cliente es obligatorio
    @ReferenceView("Simple") // La vista llamada 'Simple' se usar� para visualizar esta referencia
    Cliente cliente;
    
    @ElementCollection
    @ListProperties(
            "producto.numero, producto.descripcion, cantidad, precioPorUnidad, " +
            "importe+[" +
            	"documentoComercial.porcentajeIVA," +
            	"documentoComercial.iva," +
            	"documentoComercial.importeTotal" +
            "]" 
        )	
    Collection<Detalle> detalles;
    
    @TextArea
    String observaciones;
    
    @Digits(integer=2, fraction=0) // Para indicar su tama�o
    @DefaultValueCalculator(CalculadorPorcentajeIVA.class)
    BigDecimal porcentajeIVA;
    
    @ReadOnly
    @Money
    @Calculation("sum(detalles.importe) * porcentajeIVA / 100")
    BigDecimal iva;
  
    @org.hibernate.annotations.Formula("IMPORTETOTAL * 0.10") // El c�lculo usando SQL
    @Setter(AccessLevel.NONE) // El setter no se genera, s�lo necesitamos el getter
    @Money
    BigDecimal beneficioEstimado; // Un campo, como con una propiedad persistente
    
    @ReadOnly
    @Money
    @Calculation("sum(detalles.importe) + iva")    
    BigDecimal importeTotal;    

    @PrePersist // Ejecutado justo antes de grabar el objeto por primera vez
    private void calcularNumero() {
        Query query = XPersistence.getManager().createQuery(
            "select max(f.numero) from " +
            getClass().getSimpleName() + // De esta forma es v�lido para Factura y Pedido
            " f where f.anyo = :anyo");
        query.setParameter("anyo", anyo);
        Integer ultimoNumero = (Integer) query.getSingleResult();
        this.numero = ultimoNumero == null ? 1 : ultimoNumero + 1;
    }
}
