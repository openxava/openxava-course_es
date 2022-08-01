package com.tuempresa.facturacion.run;

import org.openxava.util.*;

/**
 * Ejecuta esta clase para arrancar la aplicación.
 *
 * Con OpenXava Studio/Eclipse: Botón derecho del ratón > Run As > Java Application
 */

public class facturacion {

	public static void main(String[] args) throws Exception {
		DBServer.start("facturacion-db"); // Para usar tu propia base de datos comenta esta línea y configura src/main/webapp/META-INF/context.xml
		AppServer.run("facturacion"); // Usa AppServer.run("") para funcionar en el contexto raíz
	}

}
