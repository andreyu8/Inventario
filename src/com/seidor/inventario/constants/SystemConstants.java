package com.seidor.inventario.constants;

public class SystemConstants {
	
	
	//tipos movimiento
	//estatus entradas   
	public static final Integer ENTRADA_POR_COMPRA = 1;
	public static final Integer ENTRADA_POR_REASIGNACION = 2;
	public static final Integer DEVOLUCION_POR_REASIGNACION = 3;
	 
	
	//estatus salida
	public static final Integer SALIDA_POR_VALE = 4;
	public static final Integer SALIDA_POR_REASIGNACION = 5;
	
	//estatus stock
	public static final Integer TIPO_ENTRADA = 6;
	public static final Integer TIPO_SALIDA = 7;
	
	
	//eststus tablas
	//stock 
	public static final Integer ESTATUS_STOCK_ACTIVO = 1;
	public static final Integer ESTATUS_STOCK_INACTIVO = 2;

	//proyectos
	public static final Integer PROYECTO_ABIERTO = 1;
	public static final Integer PROYECTO_CERRADO = 2;
	
	//factura
	public static final Integer FACTURA_ACTIVA = 1;
	public static final Integer FACTURA_INACTIVA = 2;
	
	//proveedor
	public static final Integer PROVEEDOR_ACTIVA = 1;
	public static final Integer PROVEEDOR_INACTIVA = 2;
	
	//datos bancarios
	public static final Integer DATOSBANCARIOS_ACTIVA = 1;
	public static final Integer DATOSBANCARIOS_INACTIVA = 2;
	
	
	// movimientos almacen
	public static final Integer ENTRADA_COMPRA= 1;
	public static final Integer ENTRADA_REASIGNACIÓN= 2;
	public static final Integer DEVOLUCION_REASIGNACION= 3;
	public static final Integer SALIDA_VALE= 4;
	public static final Integer SALIDA_REASIGNACION= 5;
	public static final Integer ENTRADA_STOCK= 6;
	public static final Integer SALIDA_STOCK= 7;

	
	//status movimientos 
	public static final Integer ESTATUS_MOVIMIENTO_ACTIVO = 1;
	public static final Integer ESTATUS_MOVIMIENTO_INACTIVO = 2;
	
	
	//status orden compra 
	public static final Integer ESTATUS_OC_COMPLETA = 1;
	public static final Integer ESTATUS_OC_INCOMPLETA = 2;
	public static final Integer ESTATUS_OC_PARCIAL = 3;
	
	
	
}
