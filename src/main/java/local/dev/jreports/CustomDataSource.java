package local.dev.jreports;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class CustomDataSource implements JRDataSource {

	private Object[][] data;

	private int index = -1;

	public CustomDataSource() {		
	}

	public boolean next() throws JRException {
		index++;
		return (index < data.length);
	}

	public Object getFieldValue(JRField field) throws JRException {
		Object value = null;
		String fieldName = field.getName();
		
		if ("id".equals(fieldName)) {
			value = data[index][0];
		} else if ("name".equals(fieldName)) {
			value = data[index][1];
		} else if ("age".equals(fieldName)) {
			value = data[index][2];
		}
		
		return value;
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}
	
	
}