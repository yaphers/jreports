package local.dev.jreports;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class MyViewModel {
	
	ReportType reportType = null;
	private ReportConfig reportConfig = null;
		
	private ListModelList<ReportType> reportTypesModel = new ListModelList<ReportType>(
			Arrays.asList(
					new ReportType("PDF", "pdf"), 
					new ReportType("HTML", "html"), 
					new ReportType("Word (RTF)", "rtf"), 
					new ReportType("Excel", "xls"), 
					new ReportType("Excel (JXL)", "jxl"), 
					new ReportType("CSV", "csv"), 
					new ReportType("OpenOffice (ODT)", "odt")));


	@Command("showReport")
	@NotifyChange("reportConfig")
	public void showReport() throws SQLException, IOException {
		reportConfig = new ReportConfig();
		reportConfig.setType(reportType);
		
		reportConfig.setOutput(File.createTempFile("output", ".pdf"));
		
		Connection conn = reportConfig.getDataConnection();
		try {			
			JasperReport jasperReport = JasperCompileManager.compileReport(reportConfig.getSourceXML());
			JasperPrint nRet = JasperFillManager.fillReport(jasperReport, reportConfig.getParameters(), conn);
			
			JasperExportManager.exportReportToPdfFile(nRet, reportConfig.getOutput().getAbsolutePath());
		    
		} catch(JRException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && conn.isClosed( ) == false) {
					conn.close( );
					conn = null;
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ListModelList<ReportType> getReportTypesModel() {
		return reportTypesModel;
	}

	public ReportConfig getReportConfig() {
		return reportConfig;
	}
	
	public ReportType getReportType() {
		return reportType;
	}

	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}
	
}
