package com.seidor.inventario.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleCsvReportConfiguration;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jfree.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.Locales;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;

import com.seidor.inventario.exception.BusinessException;

public class ReportUtil {

	public static final String TASK_PDF = "pdf";
	public static final String TASK_DOCX = "docx";
	public static final String TASK_DOC = "doc";
	public static final String TASK_XLS = "xls";
	public static final String TASK_XLSX = "xlsx";
	public static final String TASK_CSV = "csv";
	
	@SuppressWarnings("rawtypes")
	public static Media generateReport(String jasperUri, String type, Map exportParams, String fileName) {
		Object nothing = null;
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(nothing);
		return generateReport(list, jasperUri, type, exportParams, fileName);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Media generateReport(List data, String jasperUri, String type, Map exportParams, String fileName) {
		String file = "report";
		if (fileName != null && fileName.length() > 0) {
			file = fileName;
		}
		
		try {
			// fill the report
			exportParams.put(JRParameter.REPORT_LOCALE, Locales.getCurrent());
			
			byte[] report = getReportBytes(data, jasperUri, type, exportParams);
			
			// export one type of report
			if (TASK_PDF.equals(type)) {
				return new AMedia(file + ".pdf", "pdf", "application/pdf", report);
			}
			else if (TASK_XLS.equals(type)) {
				return new AMedia(file + ".xls", "xls", "application/vnd.ms-excel", report);
			}
			else if (TASK_XLSX.equals(type)) {
				return new AMedia(file + ".xlsx", "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", report);
			}
			else if (TASK_CSV.equals(type)) {
				return new AMedia(file + ".csv", "csv", "text/csv", report);
			}
			else if (TASK_DOCX.equals(type)) {
				return new AMedia(file + ".docx", "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", report);
			}
			else if (TASK_DOC.equals(type)) {
				return new AMedia(file + ".doc", "doc", "application/msword", report);
			}
			else {
				throw new RuntimeException("Tipo no soportado: " + type);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static byte[] getReportBytes(String jasperUri, String type, Map exportParams) {
		Object nothing = null;
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(nothing);
		return getReportBytes(list, jasperUri, type, exportParams);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static byte[] getReportBytes(List data, String jasperUri, String type, Map exportParams) {
		if (data == null) data = new ArrayList();
		if (data.isEmpty()) data.add(null);
		
		InputStream is = new ReportUtil().getClass().getClassLoader().getResourceAsStream(jasperUri);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
		
		
		try {
			// fill the report
			exportParams.put(JRParameter.REPORT_LOCALE, Locales.getCurrent());
			JasperPrint jasperPrint = JasperFillManager.fillReport(is, exportParams, dataSource);
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);
			
			//Exporter
			Exporter exporter = null;
			
			//Output stream
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			
			// export one type of report
			if (TASK_PDF.equals(type)) {
				exporter = new JRPdfExporter();
				exporter.setConfiguration(new SimplePdfExporterConfiguration());
			}
			else if (TASK_XLS.equals(type)) {
				exporter = new JRXlsxExporter();
				SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
				configuration.setOnePagePerSheet(false);
				exporter.setConfiguration(configuration);
			}
			else if (TASK_CSV.equals(type)) {
				exporter = new JRCsvExporter();
				SimpleCsvReportConfiguration configuration = new SimpleCsvReportConfiguration();
				exporter.setConfiguration(configuration);
			} else if(TASK_DOCX.equals(type)) {
				exporter = new JRDocxExporter();
				SimpleDocxReportConfiguration conf = new SimpleDocxReportConfiguration();
				exporter.setConfiguration(conf);
			} else if(TASK_DOC.equals(type)) {
				exporter = new JRDocxExporter();
			}else {
				throw new RuntimeException("Tipo no soportado: " + type);
			}
			
			exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(arrayOutputStream));
			exporter.exportReport();
			
			arrayOutputStream.close();
			
			return arrayOutputStream.toByteArray();
			
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	
	//Generate Report from source = byte[];
	@SuppressWarnings("rawtypes")
	public static Media generateReport(byte[] source, String type, Map exportParams, String fileName) {
		Object nothing = null;
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(nothing);
		return generateReport(list, source, type, exportParams, fileName);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Media generateReport(List data, byte[] source, String type, Map exportParams, String fileName) {
		String file = "report";
		if (fileName != null && fileName.length() > 0) {
			file = fileName;
		}
		
		try {
			// fill the report
			exportParams.put(JRParameter.REPORT_LOCALE, Locales.getCurrent());
			
			byte[] report = getReportBytes(data, source, type, exportParams);
			
			// export one type of report
			if (TASK_PDF.equals(type)) {
				return new AMedia(file + ".pdf", "pdf", "application/pdf", report);
			}
			else if (TASK_XLS.equals(type)) {
				return new AMedia(file + ".xls", "xls", "application/vnd.ms-excel", report);
			}
			else if (TASK_XLSX.equals(type)) {
				return new AMedia(file + ".xlsx", "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", report);
			}
			else if (TASK_CSV.equals(type)) {
				return new AMedia(file + ".csv", "csv", "text/csv", report);
			}
			else if (TASK_DOCX.equals(type)) {
				return new AMedia(file + ".docx", "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", report);
			}
			else if (TASK_DOC.equals(type)) {
				return new AMedia(file + ".doc", "doc", "application/msword", report);
			}
			else {
				throw new RuntimeException("Tipo no soportado: " + type);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static byte[] getReportBytes(List data, byte[] source, String type, Map exportParams) {
		InputStream is = new ByteArrayInputStream(source);
		if (data.isEmpty()) data.add(null);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
		
		//Exporter
		Exporter exporter = null;
		
		//Output stream
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		
		try {
			// fill the report
			exportParams.put(JRParameter.REPORT_LOCALE, Locales.getCurrent());
			JasperPrint jasperPrint = JasperFillManager.fillReport(is, exportParams, dataSource);
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			jasperPrintList.add(jasperPrint);
			
			// export one type of report
			if (TASK_PDF.equals(type)) {
				exporter = new JRPdfExporter();
				exporter.setConfiguration(new SimplePdfExporterConfiguration());
			}
			else if (TASK_XLS.equals(type)) {
				exporter = new JRXlsxExporter();
				SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
				configuration.setOnePagePerSheet(false);
				exporter.setConfiguration(configuration);
			}
			else if (TASK_CSV.equals(type)) {
				exporter = new JRCsvExporter();
				SimpleCsvReportConfiguration configuration = new SimpleCsvReportConfiguration();
				exporter.setConfiguration(configuration);
			}
			else if (TASK_DOCX.equals(type) || TASK_DOC.equals(type)) {
				exporter = new JRDocxExporter();
				SimpleDocxReportConfiguration conf = new SimpleDocxReportConfiguration();
				exporter.setConfiguration(conf);
			}
			else if (TASK_DOC.equals(type)) {
				exporter = new JRDocxExporter();
			}
			else {
				throw new RuntimeException("Tipo no soportado: " + type);
			}
			
			exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(arrayOutputStream));
			exporter.exportReport();
			
			arrayOutputStream.close();
			
			return arrayOutputStream.toByteArray();
			
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	
	//Word String Replacement Area.
	
	public static Media generateReportDocx(String filename, byte[] source, String format, HashMap<String, Object> params){
		try {
			XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(source));
			
			for (XWPFParagraph p : doc.getParagraphs()) {
				for (XWPFRun r : p.getRuns()) {
					String text = r.getText(0);
					r.setText(StringUtil.replace(params, text), 0);
				}
			}
			for (XWPFTable tbl : doc.getTables()) {
				for (XWPFTableRow row : tbl.getRows()) {
					for (XWPFTableCell cell : row.getTableCells()) {
						for (XWPFParagraph p : cell.getParagraphs()) {
							for (XWPFRun r : p.getRuns()) {
								String text = r.getText(0);
								r.setText(StringUtil.replace(params, text), 0);
							}
						}
					}
				}
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			doc.write(baos);			
			baos.close();
						
			// export one type of report
			if (TASK_PDF.equals(format)) {
			    return new AMedia(filename + ".pdf", "pdf", "application/pdf", convertToPDF(baos.toByteArray(), "doc"));
			}
			else if (TASK_DOCX.equals(format)) {
				return new AMedia(filename + ".docx", "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", baos.toByteArray());
			}
			else {
				throw new RuntimeException("Tipo no soportado: " + format);
			}
		} catch(Exception ex){
			ex.printStackTrace();
			if (ex instanceof BusinessException) throw ((BusinessException)ex);
		}
		return null;
	}
	
	public static Media generateReportDoc(String filename, byte[] source, String format, HashMap<String, Object> params){
		try {
			InputStream is = new ByteArrayInputStream(source);
		    POIFSFileSystem fs = new POIFSFileSystem(is);
		
		    HWPFDocument doc = new HWPFDocument(fs);
		
		    Range range = doc.getRange();
		    
		    for (String k : params.keySet()) {
		    	Object value = params.get(k);
		    	if (value == null) value = "";
		    	if (value instanceof String) {
		    		range.replaceText(k, (String)value);
		    	}
			}   
		    
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    doc.write(baos);
		
		    is.close();
		    baos.close();		   
		    
			// export one type of report
			if (TASK_PDF.equals(format)) {
			    return new AMedia(filename + ".pdf", "pdf", "application/pdf", convertToPDF(baos.toByteArray(), "doc"));
			}
			else if (TASK_DOC.equals(format)) {
				return new AMedia(filename + ".doc", "doc", "application/msword", baos.toByteArray());
			}
			else {
				throw new RuntimeException("Tipo no soportado: " + format);
			}
		} catch(Exception ex){
			ex.printStackTrace();
			if (ex instanceof BusinessException) throw ((BusinessException)ex);
		}
		return null;
	}
	
	public static byte[] convertToPDF(byte[] data, String ext){
		
		try {			
			File tempPDF  = File.createTempFile("tempdf", ".pdf");
			File tempFo = File.createTempFile("tempFo", ".fo");
			InputStream is = new ByteArrayInputStream(data);
			
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(is);
			OutputStream out = new FileOutputStream(tempPDF);
			
			FOSettings foSettings = Docx4J.createFOSettings();
			foSettings.setFoDumpFile(tempFo);
			
			foSettings.setWmlPackage(wordMLPackage);
			
			Docx4J.toFO(foSettings, out, Docx4J.FLAG_EXPORT_PREFER_XSL);
			
			if (wordMLPackage.getMainDocumentPart().getFontTablePart() != null) {
				wordMLPackage.getMainDocumentPart().getFontTablePart().deleteEmbeddedFontTempFiles();
			} 
	        
			byte[] bFile = new byte[(int) tempPDF.length()];
			FileInputStream inputStreamR = new FileInputStream(tempPDF);
			inputStreamR.read(bFile);
			inputStreamR.close();
			
			tempPDF.delete();
			tempFo.delete();
			
			return bFile;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("No se ha podido crear el archivo.");
		}
		
	}
	
	
	
	public static List<HashMap<String, Object>> getListFromJsonGeneric(String json) {
		List<HashMap<String, Object>> array = new ArrayList<HashMap<String, Object>>();
		try {
			JSONArray jsonarray = new JSONArray(json);
			for (int i = 0; i < jsonarray.length(); i++) {
				String jsondata = jsonarray.get(i).toString();
				HashMap<String, Object> map = new HashMap<String, Object>();
				JSONObject jObject = new JSONObject(jsondata);
				Iterator<?> keys = jObject.keys();

				while (keys.hasNext()) {
					String key = (String) keys.next();
					String value = jObject.getString(key);
					map.put(key, value);
				}
				array.add(map);
			}
		} catch (Exception ex) {
//			ex.getCause();
		}
		return array;
	}
	
	
	

	
	

}
