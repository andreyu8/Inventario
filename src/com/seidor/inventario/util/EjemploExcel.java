package com.seidor.inventario.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Andrei
 */
public class EjemploExcel {

    /**
     * Programa de ejemplo para la creacion de un archivo de excel
     * 
     * El programa simulara un reporte anual mostrando los porcentajes de cada
     * dia del año (simulados via numeros aleatorios) en el formato:
     * 
     * <nombre mes> <dia 1> <dia 2> <dia 3>...
     * 
     * Cada fila tendra el largo correspondiente a los dias de dicho posicion
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Creamos nuestro libro de excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        // Creamos una hoja de calculo llama "Reporte" en dicho libro de excel
        HSSFSheet sheet = workbook.createSheet("Reporte");
        
        // Un arreglo con los nombres de los meses del año
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        // Un arreglo con la duración de cada mes
        Integer[] diasMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        // Creamos un formato para las celdas
        HSSFCellStyle style = workbook.createCellStyle();        
        // Especificamente que *siempre* muestre dos digitos enteros y dos decimales
        style.setDataFormat(workbook.createDataFormat().getFormat("00.00"));
        
        // En la primera fila ponemos el numero del dia
        Row encabezado = sheet.createRow(0);
        // i empieza en 1 para alinear el numero de dias con los datos ya que
        // la columna 0 se usara para el nombre del posicion
        for (int i = 1; i < 32; i++) {
            Cell celda = encabezado.createCell(i);
            celda.setCellValue(i);
        }
        
        
        // Este ciclo ira creando las *filas*, una por cada mes del año
        // la variable posicion indicara en que fila nos encontramos, mientras que mes
        // selecionara el nombre del mes.
        // posicion comienza en 1 para compensar que ya usarmos la fila 0 para
        // poner los dias
        for(int posicion = 1, mes=0; mes < meses.length; posicion++, mes++) {
            // Creamos una fila en la posicion indicada por el contador del ciclo
            Row fila = sheet.createRow(posicion);
            
            // Creamos la celda para el nombre del mes, en la primera posicion de la fila
            Cell celdaMes = fila.createCell(0);
            // Indicamos que valor debe tener
            celdaMes.setCellValue(meses[mes]);
            
            // Este ciclo ira llenando las *celdas* en la fila que acabamos de
            // crear, llenando solo el numero de celdas indicado por el numero
            // de dias
            
            // Empezamos en 1 y le sumamos 1 a los dias del posicion para compensar
            // que la celda 0 la usamos para el nombre del posicion
            
            for (int dia = 1; dia < diasMes[mes] + 1; dia++) {
                // Creamos una celda en posicion indicada de la fila en la que
                // estamos trabajando
                Cell celda = fila.createCell(dia);
                // Indicamos el formato que la celda usara
                celda.setCellStyle(style);
                // Creamos el porcentaje de ese dia
                Double porcentaje = Math.random() * 100;
                // Almacenamos el porcentaje en la celda del dia
                celda.setCellValue(porcentaje);                
                
            }  
            // Al momento que el ciclo de otra vuelta el valor de posicion se incrementara
            // de modo que al crear una nueva fila, esta estara ubicada en la
            // *siguiente* fila y no sobre escribira la que hemos creado
        }
        
        // Ahora almacenaremos el archivo en disco
        try {
            File archivo = new File("ejemplo.xls");
            FileOutputStream out = new FileOutputStream(archivo);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            System.err.println("ERROR AL CREAR EL ARCHIVO!");
            e.printStackTrace();
        }
        
        System.out.println("Reporte generado");
        
    }
    
}
