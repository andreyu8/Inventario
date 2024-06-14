package com.seidor.inventario.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.seidor.inventario.model.Producto;

public class ExportExcelFiles {
	
	
	public static void exportFileExcel (ArrayList<Producto> products) throws IOException {
		
		   String[] columns = {"Codigo", "Nombre", "Almacen", "Categoria","Unidad de medida","Cantidad","Precio de compra"};
		   
			// Create a Workbook
	        Workbook workbook = new XSSFWorkbook();     // new HSSFWorkbook() for generating `.xls` file

	        /* CreationHelper helps us create instances for various things like DataFormat,
	           Hyperlink, RichTextString etc in a format (HSSF, XSSF) independent way */
	        CreationHelper createHelper = workbook.getCreationHelper();

	        // Create a Sheet
	        Sheet sheet = workbook.createSheet("Productos");

	        // Create a Font for styling header cells
	        Font headerFont = workbook.createFont();
	        //headerFont.setBoldweight(true);
	        headerFont.setFontHeightInPoints((short) 14);
	        headerFont.setColor(IndexedColors.BLUE.getIndex());

	        // Create a CellStyle with the font
	        CellStyle headerCellStyle = workbook.createCellStyle();
	        headerCellStyle.setFont(headerFont);

	        // Create a Row
	        Row headerRow = sheet.createRow(0);

	        // Creating cells
	        for(int i = 0; i < columns.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(columns[i]);
	            cell.setCellStyle(headerCellStyle);
	        }

	        // Cell Style for formatting Date
	        CellStyle dateCellStyle = workbook.createCellStyle();
	        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

	        // Create Other rows and cells with employees data
	        int rowNum = 1;
	        for(Producto p: products) {
	            Row row = sheet.createRow(rowNum++);

	            row.createCell(0)
	                    .setCellValue(p.getCodigo());

	            row.createCell(1)
	                    .setCellValue(p.getNombre());
	            
	            row.createCell(2)
	            		.setCellValue(p.getAlmacen().getAlmacen());
	            
	            row.createCell(3)
	            		.setCellValue(p.getCategoria().getCategoria());
	            
	            row.createCell(4)
	            		.setCellValue(p.getUnidadMedida().getDescripcion());
	            
	            row.createCell(5)
	            		.setCellValue(p.getCantidad());
	            
	            row.createCell(6)
	    				.setCellValue(NumberFormatUtil.format(p.getPrecioCompra(),2));

	            
	        }

	        // Resize all columns to fit the content size
	        for(int i = 0; i < columns.length; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        // Write the output to a file
	        FileOutputStream fileOut = new FileOutputStream("productos.xlsx");
	        workbook.write(fileOut);
	        fileOut.close();
			
	    
			//crear reporte
		  /* ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		    workbook.write(outByteStream);
		    byte[] outArray = outByteStream.toByteArray();
		    
		    AMedia amedia = new AMedia("ReporteProductos.xlsx", "xls", "application/file", outArray);
			Filedownload.save(amedia);
		    
			outByteStream.close(); */
			
		}

}
