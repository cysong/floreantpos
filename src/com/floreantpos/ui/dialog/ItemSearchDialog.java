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
package com.floreantpos.ui.dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import net.miginfocom.swing.MigLayout;

import com.floreantpos.Messages;
import com.floreantpos.POSConstants;
import com.floreantpos.main.Application;
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.dao.MenuItemDAO;
import com.floreantpos.swing.BeanTableModel;
import com.floreantpos.swing.PosButton;
import com.floreantpos.swing.PosScrollPane;
import com.floreantpos.swing.PosUIManager;
import com.floreantpos.swing.QwertyKeyPad;
import com.floreantpos.util.CurrencyUtil;
import com.floreantpos.util.NumberUtil;
import com.floreantpos.util.POSUtil;

public class ItemSearchDialog extends OkCancelOptionDialog {

	private JTextField tfSearchInput;
	private JTable table;
	private BeanTableModel<MenuItem> tableModel;
	private MenuItem selectedItem;

	public ItemSearchDialog() {
		super(POSUtil.getFocusedWindow(), POSConstants.SEARCH_ITEM_BUTTON_TEXT);
		init();
	}

	public ItemSearchDialog(Frame parent) {
		super(parent, POSConstants.SEARCH_ITEM_BUTTON_TEXT);
		init();
	}

	private void init() {
		JPanel contentPane = getContentPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

		MigLayout layout = new MigLayout("inset 0,fill"); //$NON-NLS-1$ 
		contentPane.setLayout(layout);

		tfSearchInput = new JTextField();
		tfSearchInput.setFont(tfSearchInput.getFont().deriveFont(Font.BOLD, PosUIManager.getNumberFieldFontSize()));
		tfSearchInput.setFocusable(true);
		tfSearchInput.requestFocus();
		tfSearchInput.setBackground(Color.WHITE);

		ActionListener searchListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doSearchItem(false);
			}
		};
		tfSearchInput.addActionListener(searchListener);
		KeyListener keyListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				String searchString = tfSearchInput.getText();
				if (searchString.length() > 2)
					doSearchItem(true);
				else if (searchString.isEmpty())
					tableModel.removeAll();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		};
		tfSearchInput.addKeyListener(keyListener);

		PosButton btnSearch = new PosButton(POSConstants.SEARCH);
		btnSearch.addActionListener(searchListener);

		contentPane.add(tfSearchInput, "spanx,split 2, grow"); //$NON-NLS-1$
		contentPane.add(btnSearch, "w 90!"); //$NON-NLS-1$

		PosScrollPane scrollPane = new PosScrollPane();
		table = new JTable();
		table.setRowHeight(35);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setPreferredSize(PosUIManager.getSize(0, 30));
		table.setMinimumSize(PosUIManager.getSize(0, 400));
		scrollPane.setViewportView(table);

		tableModel = new BeanTableModel<MenuItem>(MenuItem.class) {
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				int index = table.convertRowIndexToModel(rowIndex);
				MenuItem menuItem = tableModel.getRow(index);
				if (columnIndex == 2) {
					return CurrencyUtil.getCurrencySymbol() + NumberUtil.formatNumber(menuItem.getPrice());
				}
				return super.getValueAt(rowIndex, columnIndex);
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 2)
					return String.class;
				return super.getColumnClass(columnIndex);
			}
		};
		tableModel.addColumn("SKU", MenuItem.PROP_BARCODE);
		tableModel.addColumn(POSConstants.NAME.toUpperCase(), MenuItem.PROP_NAME);
		tableModel.addColumn(POSConstants.PRICE.toUpperCase() + " (" + CurrencyUtil.getCurrencySymbol() + ")", "price"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		tableModel.addColumn("AVL " + Messages.getString("MenuItemExplorer.16"), "stockAmount"); //$NON-NLS-1$ //$NON-NLS-2$

		table.setModel(tableModel);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int index = table.getSelectedRow();
				if (index < 0)
					return;
				index = table.convertRowIndexToModel(index);
				selectedItem = tableModel.getRow(index);
			}
		});
		table.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setHorizontalAlignment(SwingConstants.RIGHT);
				return c;
			}
		});
		resizeTableColumns();
		QwertyKeyPad qwertyKeyPad = new QwertyKeyPad();
		qwertyKeyPad.setKeyPadListener(keyListener);

		contentPane.add(scrollPane, "spanx,grow"); //$NON-NLS-1$
		contentPane.add(qwertyKeyPad, "spanx ,grow"); //$NON-NLS-1$
	}

	private void doSearchItem(boolean searchByNameOnly) {
		String searchString = tfSearchInput.getText();
		if (searchString.equals("0") || searchString.equals("")) { //$NON-NLS-1$ //$NON-NLS-2$
			POSMessageDialog.showError(Application.getPosWindow(), Messages.getString("ItemSearchDialog.2")); //$NON-NLS-1$
			return;
		}
		List<MenuItem> menuItems = new ArrayList<>();
		if (!searchByNameOnly) {
			MenuItem menuItem = MenuItemDAO.getInstance().getMenuItemByBarcode(searchString);
			if (menuItem == null) {
				try {
					Integer id = Integer.valueOf(searchString);
					menuItem = MenuItemDAO.getInstance().get(id);
				} catch (Exception e2) {
				}
			}
			if (menuItem != null) {
				menuItems.add(menuItem);
			}
		}
		if (menuItems.isEmpty()) {
			menuItems = MenuItemDAO.getInstance().getMenuItemByName(searchString);
		}
		if (!searchByNameOnly && menuItems != null && menuItems.size() == 1) {
			selectedItem = menuItems.get(0);
			doOk();
		}
		else {
			tableModel.setRows(menuItems);
		}
	}

	private void resizeTableColumns() {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		setColumnWidth(0, PosUIManager.getSize(120));
		setColumnWidth(2, PosUIManager.getSize(90));
		setColumnWidth(3, PosUIManager.getSize(90));
	}

	private void setColumnWidth(int columnNumber, int width) {
		TableColumn column = table.getColumnModel().getColumn(columnNumber);
		column.setPreferredWidth(width);
		column.setMaxWidth(width);
		column.setMinWidth(width);
	}

	@Override
	public void doOk() {
		if (selectedItem == null) {
			POSMessageDialog.showMessage(Application.getPosWindow(), Messages.getString("ItemSearchDialog.4")); //$NON-NLS-1$
			return;
		}
		setCanceled(false);
		dispose();
	}

	public MenuItem getSelectedItem() {
		return selectedItem;
	}
}
