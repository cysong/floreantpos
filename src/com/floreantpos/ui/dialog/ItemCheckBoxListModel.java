package com.floreantpos.ui.dialog;

import com.floreantpos.model.MenuItem;
import com.floreantpos.swing.CheckBoxList;
import com.floreantpos.swing.CheckBoxListModel;
import com.floreantpos.util.NumberUtil;

public class ItemCheckBoxListModel extends CheckBoxListModel<CheckBoxList.Entry> {
	public ItemCheckBoxListModel(String[] names) {
		super(names);
	}

	@Override
	public Object getValueAt(int row, int col) {
		CheckBoxList.Entry entry = getItems().get(row);
		switch (col) {
			case 0:
				return Boolean.valueOf(entry.checked);

			case 1:
				if (entry.value instanceof MenuItem) {
					return ((MenuItem) entry.value).getName();
				}
				return entry.value;

			case 2:
				return NumberUtil.formatNumber(((MenuItem) entry.value).getPrice());

			default:
				throw new InternalError();
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		if (col == 0) {
			CheckBoxList.Entry entry = getItems().get(row);
			entry.checked = (value.equals(Boolean.TRUE));

			fireTableRowsUpdated(row, row);
		}
	}

	@Override
	public Class getColumnClass(int col) {
		switch (col) {
			case 0:
				return Boolean.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			default:
				throw new InternalError();
		}
	}

}