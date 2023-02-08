package com.seidor.inventario.util;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.exporter.Interceptor;
import org.zkoss.exporter.excel.ExcelExporter;
import org.zkoss.exporter.excel.ExcelExporter.ExportContext;
import org.zkoss.poi.ss.usermodel.Cell;
import org.zkoss.poi.xssf.usermodel.XSSFSheet;
import org.zkoss.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.exporter.pdf.PdfExporter;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;

import com.seidor.inventario.exception.BusinessException;


public class ExporterUtil {
	
	
	@Command
	public void exportGrid(@BindingParam("ref") Grid grid) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		PdfExporter exporter = new PdfExporter();
		exporter.export(grid, out);
		
		AMedia amedia = new AMedia("FirstReport.pdf", "pdf", "application/pdf", out.toByteArray());
		Filedownload.save(amedia);		
		out.close();
	}
	
	@Command
	public static void exportListboxToExcel(@BindingParam("ref") Listbox listbox) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		ExcelExporter exporter = new ExcelExporter();
		exporter.export(listbox, out);
		
		AMedia amedia = new AMedia("FirstReport.xlsx", "xls", "application/file", out.toByteArray());
		Filedownload.save(amedia);
		out.close();
	}
	

	public static void excel(Listbox lb, Boolean render) {
		if (render != null && render) {
			excelByRenderer(lb);
		} else {
			excel(lb);
		}
	}

	public static void excel(Listbox lb) {
		
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			lb.renderAll();
			ExcelExporter exporter = new ExcelExporter();
			exporter.export(lb, out);

			AMedia amedia = new AMedia("Reporte.xlsx", "xlsx", "application/file", out.toByteArray());
			Filedownload.save(amedia);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Hubo un problema al generar el reporte.");
		}
	}

	public static void pdf(Listbox lb) {
		lb.renderAll();		
	
		if(lb.getItems()==null || lb.getItems().isEmpty()) {
			throw new BusinessException("Hubo un problema al generar el reporte, quizás esta intentando exportar un reporte vacío.");
		}

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			PdfExporter exporter = new PdfExporter();
			exporter.export(lb, out);

			AMedia amedia = new AMedia("Reporte.pdf", "pdf", "application/pdf", out.toByteArray());
			Filedownload.save(amedia);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Hubo un problema al generar el reporte.");
		}
	}

	public static <T> void excelByRenderer(Listbox lb) {
		final ExcelExporter exporter = new ExcelExporter();
		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			Listhead lh = lb.getListhead();
			Collection<Component> headersLh = lh.getChildren();
			final Map<String, String> headers = new LinkedHashMap<String, String>();

			for (Component component : headersLh) {
				Listheader header = (Listheader) component;
				if (header.getAttribute("name") != null) {
					headers.put((String) header.getAttribute("name"), header.getLabel());
				}
			}

			Object modelListbox = lb.getListModel();
			ArrayList<T> model = new ArrayList<T>();
			if (modelListbox instanceof ListModelList) {
				model = new ArrayList<T>((ListModelList) modelListbox);
			}

			exporter.setInterceptor(new Interceptor<XSSFWorkbook>() {
				@Override
				public void beforeRendering(XSSFWorkbook arg0) {
					ExportContext context = exporter.getExportContext();
					for (Map.Entry<String, String> entry : headers.entrySet()) {
						String header = entry.getValue();
						String aux = header;
						Cell cell = exporter.getOrCreateCell(context.moveToNextCell(), context.getSheet());
						cell.setCellValue(aux.toUpperCase());
					}
				}

				@Override
				public void afterRendering(XSSFWorkbook arg0) {
				}
			});

			exporter.export(headers.size(), model,
					new org.zkoss.exporter.RowRenderer<org.zkoss.poi.ss.usermodel.Row, T>() {
						@Override
						public void render(org.zkoss.poi.ss.usermodel.Row arg0, T arg1, boolean arg2) {
							ExportContext context = exporter.getExportContext();
							XSSFSheet sheet = context.getSheet();
							Method getMethod = null;

							for (Map.Entry<String, String> entry : headers.entrySet()) {
								String attribute = entry.getKey();
								String result =  ReflectUtil.getValueGetter(arg1, attribute);
								exporter.getOrCreateCell(context.moveToNextCell(), sheet).setCellValue(result);
							}
						}
					}, out);

			AMedia amedia = new AMedia("Reporte.xlsx", "xlsx", "application/xlsx", out.toByteArray());
			Filedownload.save(amedia);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Hubo un problema al generar el reporte.");
		}
	}
	
	public static <T> void excelByMap(HashMap<String, ArrayList<String>> map, String header) {
		final ExcelExporter exporter = new ExcelExporter();
		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			ArrayList<T> model = new ArrayList<T>();
			for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
				model.add((T)entry.getKey());
				model.addAll((ArrayList<T>)entry.getValue());
			}

			exporter.setInterceptor(new Interceptor<XSSFWorkbook>() {
				@Override
				public void beforeRendering(XSSFWorkbook arg0) {
					if(header!=null && !header.isEmpty()) {
						ExportContext context = exporter.getExportContext();
						Cell cell = exporter.getOrCreateCell(context.moveToNextCell(), context.getSheet());
						cell.setCellValue(header);
					}
				}

				@Override
				public void afterRendering(XSSFWorkbook arg0) {
				}
			});

			exporter.export(1, model,
					new org.zkoss.exporter.RowRenderer<org.zkoss.poi.ss.usermodel.Row, T>() {
						@Override
						public void render(org.zkoss.poi.ss.usermodel.Row arg0, T arg1, boolean arg2) {
							ExportContext context = exporter.getExportContext();
							XSSFSheet sheet = context.getSheet();
							exporter.getOrCreateCell(context.moveToNextCell(), sheet).setCellValue((String)arg1);
						}
					}, out);

			AMedia amedia = new AMedia(header + ".xlsx", "xlsx", "application/xlsx", out.toByteArray());
			Filedownload.save(amedia);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("Hubo un problema al generar el reporte.");
		}
	}

}