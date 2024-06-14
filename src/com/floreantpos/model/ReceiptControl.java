package com.floreantpos.model;

import com.floreantpos.model.base.BaseReceiptControl;



public class ReceiptControl extends BaseReceiptControl {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ReceiptControl () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ReceiptControl (
		java.lang.String storeNo,
		java.lang.String cashierNo,
		java.lang.String receiptPrinterId) {

		super (
			storeNo,
			cashierNo,
			receiptPrinterId);
	}

/*[CONSTRUCTOR MARKER END]*/


}