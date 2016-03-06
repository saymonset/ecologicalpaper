package com.ecological.reportes;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import java.net.URL;
import java.util.HashMap;

public class GenerarReporte {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JasperReport jasperReport;
		try {
			URL url = Reportes.class.getResource("helloJasperReportXML.jrxml");
			System.out.println(url.getPath());

			jasperReport = JasperCompileManager.compileReport(url.getPath());
			url = Reportes.class.getResource("helloJasperReportXML.xml");
			System.out.println(url.getPath());
			JRXmlDataSource xmlDataSource = new JRXmlDataSource(url.getPath(),
					"users//user");
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, new HashMap(), xmlDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					"report_from_xml.pdf");

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
