package com.tuempresa.facturacion.calculadores;
 
import javax.persistence.*;

import org.openxava.calculators.*;
import org.openxava.jpa.*;

import lombok.*;
 
public class CalculadorSiguienteNumeroParaAnyo
    implements ICalculator { // Un calculador tiene que implementar ICalculator
 
    @Getter @Setter     
    int anyo; // Este valor se inyectar� antes de calcular
 
    public Object calculate() throws Exception { // Hace el c�lculo
        Query query = XPersistence.getManager() // Una consulta JPA
        						  .createQuery("select max(f.numero) from DocumentoComercial f where f.anyo = :anyo"); // La consulta devuelve
                                                              // el n�mero de factura m�ximo del a�o indicado
        query.setParameter("anyo", anyo); // Ponemos el a�o inyectado como par�metro de la consulta
        Integer ultimoNumero = (Integer) query.getSingleResult();
        return ultimoNumero == null ? 1 : ultimoNumero + 1; // Devuelve el �ltimo n�mero
                                            // de factura del a�o + 1 o 1 si no hay �ltimo n�mero
    }
 
}