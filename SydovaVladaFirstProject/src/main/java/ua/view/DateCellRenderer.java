package ua.view;

import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableCellRenderer;

public class DateCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	private SimpleDateFormat sdfNewValue = new SimpleDateFormat("dd.MM.YYYY HH:mm");

	@Override
	public void setValue(Object value) {
		try {
			if (value != null)
				value = sdfNewValue.format(value);
		} catch (IllegalArgumentException e) {
		}

		super.setValue(value);
	}

}
