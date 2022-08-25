package com.tuempresa.facturacion.validadores; // En el paquete 'validadores'
 
import org.openxava.util.*;
import org.openxava.validators.*;

import com.tuempresa.facturacion.modelo.*;

import lombok.*;
 
@Getter @Setter 
public class ValidadorEntregadoParaEstarEnFactura
    implements IValidator { // ha de implementar 'IValidator'
 
    private int anyo; // Propiedades a ser inyectadas desde Pedido
    private int numero;
    private boolean entregado;
    private Factura factura;
 
    public void validate(Messages errors)
        throws Exception { // La l�gica de validaci�n
        if (factura == null) return;
        if (!entregado) {
            errors.add( // Al a�adir mensajes a 'errors' la validaci�n fallar�
                "pedido_debe_estar_entregado", // Un id del archivo i18n
                anyo, numero); // Argumentos para el mensaje
        }
    }

}