package com.floreantpos.swing;

import java.util.List;

public interface PaginationSupport {
	public int getNumRows();

	public void setNumRows(int numRows);

	public int getCurrentRowIndex();

	public void setCurrentRowIndex(int currentRowIndex);

	public int getPageSize();

	public void setPageSize(int pageSize);

	public boolean hasNext();

	public boolean hasPrevious();

	public int getNextRowIndex();

	public int getPreviousRowIndex();
	
	public void setRows(List rows);
}
