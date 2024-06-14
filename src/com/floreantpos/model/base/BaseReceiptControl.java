package com.floreantpos.model.base;

import java.lang.Comparable;
import java.io.Serializable;


/**
 * This is an object that contains data related to the RECEIPT_CONTROL table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="RECEIPT_CONTROL"
 */

public abstract class BaseReceiptControl  implements Comparable, Serializable {

	public static String REF = "ReceiptControl"; //$NON-NLS-1$
	public static String PROP_VALID_FROM = "validFrom"; //$NON-NLS-1$
	public static String PROP_NEXT_AVAILABLE_SEQUENCE = "nextAvailableSequence"; //$NON-NLS-1$
	public static String PROP_VALID_TO = "validTo"; //$NON-NLS-1$
	public static String PROP_SEQUENCE_START = "sequenceStart"; //$NON-NLS-1$
	public static String PROP_SEQUENCE_END = "sequenceEnd"; //$NON-NLS-1$


	// constructors
	public BaseReceiptControl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseReceiptControl (
		java.lang.String storeNo,
		java.lang.String cashierNo,
		java.lang.String receiptPrinterId) {

		this.setStoreNo(storeNo);
		this.setCashierNo(cashierNo);
		this.setReceiptPrinterId(receiptPrinterId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key

	private java.lang.String storeNo;

	private java.lang.String cashierNo;

	private java.lang.String receiptPrinterId;

	// fields
		protected java.util.Date validFrom;
		protected java.util.Date validTo;
		protected java.lang.Long sequenceStart;
		protected java.lang.Long sequenceEnd;
		protected java.lang.Long nextAvailableSequence;



	/**
     * @hibernate.property
     *  column=STORE_NO
	 * not-null=true
	 */
	public java.lang.String getStoreNo () {
		return this.storeNo;
	}

	/**
	 * Set the value related to the column: STORE_NO
	 * @param storeNo the STORE_NO value
	 */
	public void setStoreNo (java.lang.String storeNo) {
		this.storeNo = storeNo;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
     * @hibernate.property
     *  column=CASHIER_NO
	 * not-null=true
	 */
	public java.lang.String getCashierNo () {
		return this.cashierNo;
	}

	/**
	 * Set the value related to the column: CASHIER_NO
	 * @param cashierNo the CASHIER_NO value
	 */
	public void setCashierNo (java.lang.String cashierNo) {
		this.cashierNo = cashierNo;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
     * @hibernate.property
     *  column=RECEIPT_PRINTER_ID
	 * not-null=true
	 */
	public java.lang.String getReceiptPrinterId () {
		return this.receiptPrinterId;
	}

	/**
	 * Set the value related to the column: RECEIPT_PRINTER_ID
	 * @param receiptPrinterId the RECEIPT_PRINTER_ID value
	 */
	public void setReceiptPrinterId (java.lang.String receiptPrinterId) {
		this.receiptPrinterId = receiptPrinterId;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: VALID_FROM
	 */
	public java.util.Date getValidFrom () {
					return validFrom;
			}

	/**
	 * Set the value related to the column: VALID_FROM
	 * @param validFrom the VALID_FROM value
	 */
	public void setValidFrom (java.util.Date validFrom) {
		this.validFrom = validFrom;
	}



	/**
	 * Return the value associated with the column: VALID_TO
	 */
	public java.util.Date getValidTo () {
					return validTo;
			}

	/**
	 * Set the value related to the column: VALID_TO
	 * @param validTo the VALID_TO value
	 */
	public void setValidTo (java.util.Date validTo) {
		this.validTo = validTo;
	}



	/**
	 * Return the value associated with the column: SEQUENCE_START
	 */
	public java.lang.Long getSequenceStart () {
					return sequenceStart;
			}

	/**
	 * Set the value related to the column: SEQUENCE_START
	 * @param sequenceStart the SEQUENCE_START value
	 */
	public void setSequenceStart (java.lang.Long sequenceStart) {
		this.sequenceStart = sequenceStart;
	}



	/**
	 * Return the value associated with the column: SEQUENCE_END
	 */
	public java.lang.Long getSequenceEnd () {
					return sequenceEnd;
			}

	/**
	 * Set the value related to the column: SEQUENCE_END
	 * @param sequenceEnd the SEQUENCE_END value
	 */
	public void setSequenceEnd (java.lang.Long sequenceEnd) {
		this.sequenceEnd = sequenceEnd;
	}



	/**
	 * Return the value associated with the column: NEXT_AVAILABLE_SEQUENCE
	 */
	public java.lang.Long getNextAvailableSequence () {
					return nextAvailableSequence;
			}

	/**
	 * Set the value related to the column: NEXT_AVAILABLE_SEQUENCE
	 * @param nextAvailableSequence the NEXT_AVAILABLE_SEQUENCE value
	 */
	public void setNextAvailableSequence (java.lang.Long nextAvailableSequence) {
		this.nextAvailableSequence = nextAvailableSequence;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.floreantpos.model.ReceiptControl)) return false;
		else {
			com.floreantpos.model.ReceiptControl receiptControl = (com.floreantpos.model.ReceiptControl) obj;
			if (null != this.getStoreNo() && null != receiptControl.getStoreNo()) {
				if (!this.getStoreNo().equals(receiptControl.getStoreNo())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getCashierNo() && null != receiptControl.getCashierNo()) {
				if (!this.getCashierNo().equals(receiptControl.getCashierNo())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getReceiptPrinterId() && null != receiptControl.getReceiptPrinterId()) {
				if (!this.getReceiptPrinterId().equals(receiptControl.getReceiptPrinterId())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getStoreNo()) {
				sb.append(this.getStoreNo().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getCashierNo()) {
				sb.append(this.getCashierNo().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getReceiptPrinterId()) {
				sb.append(this.getReceiptPrinterId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}

	public int compareTo (Object obj) {
		if (obj.hashCode() > hashCode()) return 1;
		else if (obj.hashCode() < hashCode()) return -1;
		else return 0;
	}

	public String toString () {
		return super.toString();
	}


}