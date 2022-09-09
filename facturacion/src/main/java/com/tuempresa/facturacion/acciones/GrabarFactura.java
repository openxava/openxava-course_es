package com.tuempresa.facturacion.acciones;

import org.openxava.actions.*;

public class GrabarFactura
    extends SaveAction { // Acci�n est�ndar de OpenXava para 
                         // grabar el contenido de la vista	             
    public void execute() throws Exception {
        super.execute(); // La l�gica est�ndar de grabaci�n (1)
        closeDialog(); // (2)
    }
}