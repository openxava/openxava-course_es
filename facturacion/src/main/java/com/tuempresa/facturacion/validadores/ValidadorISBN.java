package com.tuempresa.facturacion.validadores; // En el paquete 'validadores'
 
import javax.validation.*;

import org.openxava.util.*;

import com.tuempresa.facturacion.anotaciones.*;
 
public class ValidadorISBN implements ConstraintValidator<ISBN, Object> {
 
    private static org.apache.commons.validator.routines.ISBNValidator
        validador = // De 'Commons Validator'
            new org.apache.commons.validator.routines.ISBNValidator();
 
    public void initialize(ISBN isbn) {
 
    }
    
    // Contiene la lógica de validación
    public boolean isValid(Object valor, ConstraintValidatorContext contexto) { 
        if (Is.empty(valor)) return true;
        return validador.isValid(valor.toString()); // Usa 'Commons Validator'
    }
}