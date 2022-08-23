package com.tuempresa.facturacion.calculadores; // En el paquete 'calculadores'
 
import org.openxava.calculators.*; // Para usar 'ICalculator'

import com.tuempresa.facturacion.util.*; // Para usar 'PreferenciasFacturacion'
 
public class CalculadorPorcentajeIVA implements ICalculator {
 
    public Object calculate() throws Exception {
        return PreferenciasFacturacion.getPorcentajeIVADefecto();
    }
}