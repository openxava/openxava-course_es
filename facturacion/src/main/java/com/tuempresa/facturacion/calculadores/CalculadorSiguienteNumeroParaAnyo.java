package com.tuempresa.facturacion.calculadores;
 
import javax.persistence.*;

import org.openxava.calculators.*;
import org.openxava.jpa.*;

import lombok.*;
 
public class CalculadorSiguienteNumeroParaAnyo
    implements ICalculator { // Un calculador tiene que implementar ICalculator
 
    @Getter @Setter     
    int anyo; // Este valor se inyectará antes de calcular
 
    public Object calculate() throws Exception { // Hace el cálculo
        Query query = XPersistence.getManager() // Una consulta JPA
        						  .createQuery("select max(f.numero) from DocumentoComercial f where f.anyo = :anyo"); // La consulta devuelve
                                                              // el número de factura máximo del año indicado
        query.setParameter("anyo", anyo); // Ponemos el año inyectado como parámetro de la consulta
        Integer ultimoNumero = (Integer) query.getSingleResult();
        return ultimoNumero == null ? 1 : ultimoNumero + 1; // Devuelve el último número
                                            // de factura del año + 1 o 1 si no hay último número
    }
 
}