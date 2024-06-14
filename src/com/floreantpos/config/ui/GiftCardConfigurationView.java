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
package com.floreantpos.config.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.floreantpos.Messages;
import com.floreantpos.config.TerminalConfig;
import com.floreantpos.extension.ExtensionManager;
import com.floreantpos.extension.FloreantPlugin;
import com.floreantpos.extension.GiftCardPaymentPlugin;
import com.floreantpos.ui.dialog.POSMessageDialog;

import net.miginfocom.swing.MigLayout;

public class GiftCardConfigurationView extends ConfigurationView {

	private JComboBox cbPaymentGateway;

	private JPanel pluginConfigPanel = new JPanel(new BorderLayout());

	public GiftCardConfigurationView() {
		createUI();
	}

	private void createUI() {
		setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[]30[grow]", "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		JLabel lblMerchantGateway = new JLabel(Messages.getString("CardConfigurationView.2")); //$NON-NLS-1$
		contentPanel.add(lblMerchantGateway, ""); //$NON-NLS-1$

		cbPaymentGateway = new JComboBox();
		cbPaymentGateway.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					updatePluginConfigUI();
				} catch (Exception e1) {
					POSMessageDialog.showError(GiftCardConfigurationView.this, e1.getMessage(), e1);
				}
			}
		});
		contentPanel.add(cbPaymentGateway, "growx"); //$NON-NLS-1$
		contentPanel.add(pluginConfigPanel, "newline,span,wrap,growx"); //$NON-NLS-1$

		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setBorder(null);
		add(scrollPane);

	}

	private void initialMerchantGateways() {
		String givexGetwayId = TerminalConfig.getGivexGetwayId();
		DefaultComboBoxModel<GiftCardPaymentPlugin> model = new DefaultComboBoxModel<GiftCardPaymentPlugin>();
		List<FloreantPlugin> plugins = ExtensionManager.getPlugins(GiftCardPaymentPlugin.class);
		GiftCardPaymentPlugin selectedGivexPlugin = null;
		model.addElement(null);
		for (FloreantPlugin plugin : plugins) {
			if (plugin instanceof GiftCardPaymentPlugin) {
				GiftCardPaymentPlugin givexPlugin = (GiftCardPaymentPlugin) plugin;
				if (givexPlugin.getId().equals(givexGetwayId)) {
					selectedGivexPlugin = givexPlugin;
				}
				model.addElement((GiftCardPaymentPlugin) plugin);
			}
		}

		cbPaymentGateway.setModel(model);
		cbPaymentGateway.setSelectedItem(selectedGivexPlugin);
	}

	@Override
	public boolean save() throws Exception {
		Object plugin = cbPaymentGateway.getSelectedItem();
		if (plugin instanceof GiftCardPaymentPlugin) {
			GiftCardPaymentPlugin giftCardPlugin = (GiftCardPaymentPlugin) plugin;
			boolean isSave = giftCardPlugin.getConfigurationView().save();
			if (isSave) {
				TerminalConfig.setGivexGetwayId(giftCardPlugin.getId());
			}
			return isSave;
		}
		TerminalConfig.setGivexGetwayId(null);
		return true;

	}

	@Override
	public void initialize() throws Exception {
		initialMerchantGateways();
		updatePluginConfigUI();
		setInitialized(true);
	}

	private void updatePluginConfigUI() throws Exception {
		GiftCardPaymentPlugin plugin = (GiftCardPaymentPlugin) cbPaymentGateway.getSelectedItem();
		pluginConfigPanel.removeAll();
		if (plugin == null) {
			return;
		}
		ConfigurationView configurationPane = plugin.getConfigurationView();
		configurationPane.initialize();
		pluginConfigPanel.add(configurationPane);
		revalidate();
		repaint();
	}

	@Override
	public String getName() {
		return "Gift card";
	}

}
