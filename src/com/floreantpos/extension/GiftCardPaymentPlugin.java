package com.floreantpos.extension;

import com.floreantpos.config.ui.ConfigurationView;
import com.floreantpos.ui.views.payment.GiftCardProcessor;

public abstract class GiftCardPaymentPlugin extends AbstractFloreantPlugin {
	public abstract String getId();

	public abstract boolean shouldShowCardInputProcessor();

	public abstract ConfigurationView getConfigurationView() throws Exception;

	public abstract GiftCardProcessor getProcessor();

}
