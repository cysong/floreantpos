/**
 * ************************************************************************
 * * The contents of this file are subject to the MRPL 1.2
 * * (the  "License"),  being   the  Mozilla   Public  License
 * * Version 1.1  with a permitted attribution clause; you may not  use this
 * * file except in compliance with the License. You  may  obtain  a copy of
 * * the License at http://www.floreantpos.org/license.html
 * * Software distributed under the License  is  distributed  on  an "AS IS"
 * * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * * License for the specific  language  governing  rights  and  limitations
 * * under the License.
 * * The Original Code is FLOREANT POS.
 * * The Initial Developer of the Original Code is OROCUBE LLC
 * * All portions are Copyright (C) 2015 OROCUBE LLC
 * * All Rights Reserved.
 * ************************************************************************
 */
package com.floreantpos.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * A list from which multiple items can be selected.
 */
public class CheckBoxList<E> extends JTable {

	public CheckBoxList() {
		resizeColWidth();
	}

	public CheckBoxList(E[] items) {
		setModel(items);
	}

	public CheckBoxList(List<E> items) {
		setModel(items);
	}

	public void setModel(E[] items) {
		setModel(items, new String[] {"",""});
	}

	public void setModel(List<E> items) {
		setModel(items, new String[] {"",""});
	}

	public void setModel(List<E> items, String[] header) {
		CheckBoxListModel<E> model = new CheckBoxListModel<E>(header, items);
		setModel(model);
		resizeColWidth();
	}
	public void setModel(E[] items, String[] header) {
		CheckBoxListModel<E> model = new CheckBoxListModel<E>(items, header);
		setModel(model);
		resizeColWidth();
	}

	public List<E> getCheckedValues() {
		List values = new ArrayList();
		CheckBoxListModel model = (CheckBoxListModel) getModel();
		if (model.items == null) {
			return values;
		}
		for (int i = 0; i < model.items.size(); i++) {
			CheckBoxList.Entry<E> entry = (Entry<E>) model.items.get(i);
			if (entry.checked) {
				values.add(entry.value);
			}
		}
		return values;
	}
	
	public List<E> getUnCheckedValues() {
		List values = new ArrayList();
		CheckBoxListModel model = (CheckBoxListModel) getModel();
		if (model.items == null) {
			return values;
		}
		for (int i = 0; i < model.items.size(); i++) {
			CheckBoxList.Entry<E> entry = (Entry<E>) model.items.get(i);
			if (!entry.checked) {
				values.add(entry.value);
			}
		}
		return values;
	}

	public void setSelected(Object type) {
		CheckBoxListModel model = (CheckBoxListModel) getModel();

		if (type != null) {
			for (int i = 0; i < model.items.size(); i++) {
				Entry entry = (Entry) model.items.get(i);
				if (type.equals(entry.value)) {
					entry.checked = true;
					break;
				}
			}
			model.fireTableRowsUpdated(0, model.getRowCount());
		}

	}

	//{{{ selectAll() method
	@Override
	public void selectAll() {
		CheckBoxListModel model = (CheckBoxListModel) getModel();
		for (int i = 0; i < model.items.size(); i++) {
			Entry entry = (Entry) model.items.get(i);
			entry.checked = true;

		}

		model.fireTableRowsUpdated(0, model.getRowCount());
	}

	public void selectItems(List types) {
		CheckBoxListModel model = (CheckBoxListModel) getModel();

		if (types != null && model.items != null) {
			for (int i = 0; i < model.items.size(); i++) {
				Entry entry = (Entry) model.items.get(i);

				for (int j = 0; j < types.size(); j++) {

					Object type = types.get(j);

					if (type.equals(entry.value)) {
						entry.checked = true;
						break;

					}

				}
			}

			model.fireTableRowsUpdated(0, model.getRowCount());

		}
	}

	public void unCheckAll() {
		CheckBoxListModel model = (CheckBoxListModel) getModel();
		for (int i = 0; i < model.items.size(); i++) {
			Entry entry = (Entry) model.items.get(i);
			entry.checked = false;

		}

		model.fireTableRowsUpdated(0, model.getRowCount());
	}

	public Entry[] getValues() {
		CheckBoxListModel model = (CheckBoxListModel) getModel();
		return (Entry[]) model.items.toArray();
	}

	public Object getSelectedValue() {
		int row = getSelectedRow();
		if (row == -1) {
			return null;
		}
		else {
			return getModel().getValueAt(row, 1);
		}
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {

		TableCellRenderer cellRenderer = super.getCellRenderer(row, column);

		if (cellRenderer instanceof JCheckBox) {
			((JCheckBox) cellRenderer).setEnabled(isEnabled());
		}
		
		return cellRenderer;
	}

	public void resizeColWidth() {
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setShowGrid(false);
		setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		if (getColumnModel() != null && getColumnCount() > 0) {
			TableColumn column = getColumnModel().getColumn(0);
			int checkBoxWidth = new JCheckBox().getPreferredSize().width;
			column.setPreferredWidth(checkBoxWidth);
			column.setMinWidth(checkBoxWidth);
			column.setWidth(checkBoxWidth);
			column.setMaxWidth(checkBoxWidth);
			column.setResizable(false);
		}
	}

	public void setTableHeaderHide(boolean hideHeader) {
		if (hideHeader)
			setTableHeader(null);
	}

	/**
	 * A check box list entry.
	 */
	public static class Entry<E> {
		public boolean checked;
		public E value;

		public Entry(boolean checked, E value) {
			this.checked = checked;
			this.value = value;
		}

		public boolean isChecked() {

			return checked;
		}

		public Object getValue() {

			return value;
		}

		public void setChecked(boolean checked) {

			this.checked = checked;
		}

		public void setValue(E value) {

			this.value = value;
		}
	}

}