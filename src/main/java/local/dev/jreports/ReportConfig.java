package local.dev.jreports;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

public class ReportConfig {

	private File output;
	private String sourceXML = "c:\\Java\\workspace\\jreports\\src\\main\\webapp\\erpapp.jrxml";
	private String source = "/erpapp.jasper";
	private Map<String, Object> parameters;
	private JRDataSource dataSource;
	private Connection dataConnection;

	private ReportType type;

	public ReportConfig() {
		parameters = new HashMap<String, Object>();
		
		String userName = "sa";
		String password = "okok1234";
		String url = "jdbc:sqlserver://localhost;databaseName=ERPAPP";
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dataConnection = conn;
	}
	
	public ReportType getType() {
		return type;
	}

	public void setType(ReportType selectedReportType) {
		this.type = selectedReportType;
	}

	public String getSource() {
		return source;
	}
	
	public String getSourceXML() {
		return sourceXML;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public JRDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(JRDataSource reportDataSource) {
		this.dataSource = reportDataSource;
	}

	public Connection getDataConnection() {
		return dataConnection;
	}

	public void setDataConnection(Connection dataConnection) {
		this.dataConnection = dataConnection;
	}

	public File getOutput() {
		return output;
	}

	public void setOutput(File output) {
		this.output = output;
	}	
	
}
