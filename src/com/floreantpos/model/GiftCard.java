package com.floreantpos.model;

import com.floreantpos.model.base.BaseGiftCard;



public class GiftCard extends BaseGiftCard {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public GiftCard () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public GiftCard (java.lang.String cardNumber) {
		super(cardNumber);
	}

/*[CONSTRUCTOR MARKER END]*/


}