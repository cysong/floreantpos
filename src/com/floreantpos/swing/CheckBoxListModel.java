package com.floreantpos.swing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CheckBoxListModel<E> extends AbstractTableModel implements PaginationSupport {
	List<CheckBoxList.Entry<E>> items;
	private int numRows;
	private int currentRowIndex;
	private int pageSize = 3;
	private String[] columnNames;

	public CheckBoxListModel() {

	}

	public CheckBoxListModel(String[] names) {
		columnNames = names;
	}

	public CheckBoxListModel(String[] names, List<E> rows) {
		items = new ArrayList<CheckBoxList.Entry<E>>(rows.size());
		for (E e : rows) {
			items.add(createEntry(e));
		}
		columnNames = names;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	protected CheckBoxListModel(List<E> _items) {
		items = new ArrayList<CheckBoxList.Entry<E>>(_items.size());
		for (int i = 0; i < _items.size(); i++) {
			items.add(createEntry(_items.get(i)));
		}
	}

	public CheckBoxListModel(E[] _items) {
		items = new ArrayList<CheckBoxList.Entry<E>>(_items.length);
		for (int i = 0; i < _items.length; i++) {
			items.add(createEntry(_items[i]));
		}
	}
	public CheckBoxListModel(E[] _items , String[] columnNames) {
		items = new ArrayList<CheckBoxList.Entry<E>>(_items.length);
		for (int i = 0; i < _items.length; i++) {
			items.add(createEntry(_items[i]));
		}
		this.columnNames = columnNames;
	}

	protected CheckBoxList.Entry createEntry(E obj) {
		if (obj instanceof CheckBoxList.Entry)
			return (CheckBoxList.Entry) obj;
		else
			return new CheckBoxList.Entry(false, obj);
	}

	public int getRowCount() {
		if (items == null) {
			return 0;
		}
		return items.size();
	}

	public E getRow(int index) {
		return (E) getItems().get(index);
	}

	public int getColumnCount() {
		if (columnNames != null)
			return columnNames.length;
		return currentRowIndex;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	public Object getValueAt(int row, int col) {
		CheckBoxList.Entry entry = items.get(row);
		switch (col) {
			case 0:
				return Boolean.valueOf(entry.checked);

			case 1:

				return entry.value;
			default:
				throw new InternalError();
		}
	}

	public Class getColumnClass(int col) {
		switch (col) {
			case 0:
				return Boolean.class;
			case 1:
				return String.class;
			default:
				throw new InternalError();
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return col == 0;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		if (col == 0) {
			CheckBoxList.Entry entry = items.get(row);
			entry.checked = (value.equals(Boolean.TRUE));

			fireTableRowsUpdated(row, row);
		}
	}

	public List<CheckBoxList.Entry<E>> getItems() {

		return items;
	}

	public void setItems(List<CheckBoxList.Entry<E>> items) {
		this.items = items;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getCurrentRowIndex() {
		return currentRowIndex;
	}

	public void setCurrentRowIndex(int currentRowIndex) {
		this.currentRowIndex = currentRowIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean hasNext() {
		return (currentRowIndex + pageSize) < numRows;
	}

	public boolean hasPrevious() {
		return currentRowIndex > 0;
	}

	public int getNextRowIndex() {
		if (numRows == 0) {
			return 0;
		}

		return getCurrentRowIndex() + getPageSize();
	}

	public int getPreviousRowIndex() {
		int i = getCurrentRowIndex() - getPageSize();
		if (i < 0) {
			i = 0;
		}

		return i;
	}

	@Override
	public void setRows(List rows) {
		items = new ArrayList<CheckBoxList.Entry<E>>(rows.size());
		for (Iterator iterator = rows.iterator(); iterator.hasNext();) {
			E e = (E) iterator.next();
			items.add(createEntry(e));
		}
		fireTableDataChanged();
	}

}