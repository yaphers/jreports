package local.dev.jreports;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Iframe;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class MyViewModel {
	
	@Init
	public void init(@BindingParam("frame") Iframe frame) throws IOException {
		File f =  File.createTempFile("output", ".pdf");		
		Context ctx = null;
		Connection conn = null;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ERPAppPool");			
			conn = ds.getConnection();
			
			JasperReport jasperReport = JasperCompileManager.compileReport("c:\\Java\\workspace\\jreports\\src\\main\\webapp\\erpapp.jrxml");
			JasperPrint nRet = JasperFillManager.fillReport(jasperReport, new HashMap<String, Object>(), conn);			
			JasperExportManager.exportReportToPdfFile(nRet, f.getAbsolutePath());
			
			AMedia m = new AMedia("output", "pdf", "application/pdf", f, true);			

			frame.setContent(m);	
			
		} catch(Exception e) {
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
	
}
