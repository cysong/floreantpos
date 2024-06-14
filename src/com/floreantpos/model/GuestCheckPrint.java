package com.floreantpos.model;

import java.util.Date;

import com.floreantpos.model.base.BaseGuestCheckPrint;
import com.floreantpos.model.util.DateUtil;



public class GuestCheckPrint extends BaseGuestCheckPrint {
	private static final long serialVersionUID = 1L;
	private String diffInBillPrint;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public GuestCheckPrint () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public GuestCheckPrint (java.lang.Integer id) {
		super(id);
	}

	public String getDiffInBillPrint() {
		return DateUtil.getElapsedTime(getPrintTime(), new Date());
	}

	public void setDiffInBillPrint(String diffInBillPrint) {
		this.diffInBillPrint = diffInBillPrint;
	}

/*[CONSTRUCTOR MARKER END]*/


}