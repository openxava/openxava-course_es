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
        throws Exception { // La lógica de validación
        if (factura == null) return;
        if (!entregado) {
            errors.add( // Al añadir mensajes a 'errors' la validación fallará
                "pedido_debe_estar_entregado", // Un id del archivo i18n
                anyo, numero); // Argumentos para el mensaje
        }
    }

}