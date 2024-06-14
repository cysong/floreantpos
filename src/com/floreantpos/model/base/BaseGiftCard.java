package com.floreantpos.model.base;

import java.lang.Comparable;
import java.io.Serializable;


/**
 * This is an object that contains data related to the GIFT_CARD table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="GIFT_CARD"
 */

public abstract class BaseGiftCard  implements Comparable, Serializable {

	public static String REF = "GiftCard"; //$NON-NLS-1$
	public static String PROP_DURATION_TYPE = "durationType"; //$NON-NLS-1$
	public static String PROP_CARD_NUMBER = "cardNumber"; //$NON-NLS-1$
	public static String PROP_OWNER_NAME = "ownerName"; //$NON-NLS-1$
	public static String PROP_EXPIRY_DATE = "expiryDate"; //$NON-NLS-1$
	public static String PROP_EMAIL = "email"; //$NON-NLS-1$
	public static String PROP_ISSUE_DATE = "issueDate"; //$NON-NLS-1$
	public static String PROP_BATCH_NO = "batchNo"; //$NON-NLS-1$
	public static String PROP_DE_ACTIVATION_DATE = "deActivationDate"; //$NON-NLS-1$
	public static String PROP_DURATION = "duration"; //$NON-NLS-1$
	public static String PROP_POINT = "point"; //$NON-NLS-1$
	public static String PROP_ACTIVE = "active"; //$NON-NLS-1$
	public static String PROP_TYPE = "type"; //$NON-NLS-1$
	public static String PROP_PIN_NUMBER = "pinNumber"; //$NON-NLS-1$
	public static String PROP_ACTIVATION_DATE = "activationDate"; //$NON-NLS-1$
	public static String PROP_BALANCE = "balance"; //$NON-NLS-1$
	public static String PROP_DISABLE = "disable"; //$NON-NLS-1$


	// constructors
	public BaseGiftCard () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseGiftCard (java.lang.String cardNumber) {
		this.setCardNumber(cardNumber);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String cardNumber;

	 long version;

	// fields
		protected java.lang.String ownerName;
		protected java.lang.Double balance;
		protected java.util.Date issueDate;
		protected java.util.Date activationDate;
		protected java.util.Date deActivationDate;
		protected java.util.Date expiryDate;
		protected java.lang.Boolean active;
		protected java.lang.Boolean disable;
		protected java.lang.String durationType;
		protected java.lang.Integer duration;
		protected java.lang.String pinNumber;
		protected java.lang.Integer point;
		protected java.lang.String batchNo;
		protected java.lang.String email;
		protected java.lang.String type;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="CARD_NO"
     */
	public java.lang.String getCardNumber () {
		return cardNumber;
	}

	/**
	 * Set the unique identifier of this class
	 * @param cardNumber the new ID
	 */
	public void setCardNumber (java.lang.String cardNumber) {
		this.cardNumber = cardNumber;
		this.hashCode = Integer.MIN_VALUE;
	}



	/**
	 * Return the value associated with the column: VERSION_NO
	 */
	public long getVersion () {
					return version;
			}

	/**
	 * Set the value related to the column: VERSION_NO
	 * @param version the VERSION_NO value
	 */
	public void setVersion (long version) {
		this.version = version;
	}




	/**
	 * Return the value associated with the column: OWNER_NAME
	 */
	public java.lang.String getOwnerName () {
					return ownerName;
			}

	/**
	 * Set the value related to the column: OWNER_NAME
	 * @param ownerName the OWNER_NAME value
	 */
	public void setOwnerName (java.lang.String ownerName) {
		this.ownerName = ownerName;
	}



	/**
	 * Return the value associated with the column: BALANCE
	 */
	public java.lang.Double getBalance () {
									return balance == null ? Double.valueOf(0) : balance;
					}

	/**
	 * Set the value related to the column: BALANCE
	 * @param balance the BALANCE value
	 */
	public void setBalance (java.lang.Double balance) {
		this.balance = balance;
	}



	/**
	 * Return the value associated with the column: ISSUE_DATE
	 */
	public java.util.Date getIssueDate () {
					return issueDate;
			}

	/**
	 * Set the value related to the column: ISSUE_DATE
	 * @param issueDate the ISSUE_DATE value
	 */
	public void setIssueDate (java.util.Date issueDate) {
		this.issueDate = issueDate;
	}



	/**
	 * Return the value associated with the column: ACTIVATION_DATE
	 */
	public java.util.Date getActivationDate () {
					return activationDate;
			}

	/**
	 * Set the value related to the column: ACTIVATION_DATE
	 * @param activationDate the ACTIVATION_DATE value
	 */
	public void setActivationDate (java.util.Date activationDate) {
		this.activationDate = activationDate;
	}



	/**
	 * Return the value associated with the column: DEACTIVATION_DATE
	 */
	public java.util.Date getDeActivationDate () {
					return deActivationDate;
			}

	/**
	 * Set the value related to the column: DEACTIVATION_DATE
	 * @param deActivationDate the DEACTIVATION_DATE value
	 */
	public void setDeActivationDate (java.util.Date deActivationDate) {
		this.deActivationDate = deActivationDate;
	}



	/**
	 * Return the value associated with the column: EXPIRY_DATE
	 */
	public java.util.Date getExpiryDate () {
					return expiryDate;
			}

	/**
	 * Set the value related to the column: EXPIRY_DATE
	 * @param expiryDate the EXPIRY_DATE value
	 */
	public void setExpiryDate (java.util.Date expiryDate) {
		this.expiryDate = expiryDate;
	}



	/**
	 * Return the value associated with the column: ACTIVE
	 */
	public java.lang.Boolean isActive () {
								return active == null ? Boolean.FALSE : active;
					}

	/**
	 * Set the value related to the column: ACTIVE
	 * @param active the ACTIVE value
	 */
	public void setActive (java.lang.Boolean active) {
		this.active = active;
	}



	/**
	 * Return the value associated with the column: DISABLE
	 */
	public java.lang.Boolean isDisable () {
								return disable == null ? Boolean.FALSE : disable;
					}

	/**
	 * Set the value related to the column: DISABLE
	 * @param disable the DISABLE value
	 */
	public void setDisable (java.lang.Boolean disable) {
		this.disable = disable;
	}



	/**
	 * Return the value associated with the column: DURATION_TYPE
	 */
	public java.lang.String getDurationType () {
					return durationType;
			}

	/**
	 * Set the value related to the column: DURATION_TYPE
	 * @param durationType the DURATION_TYPE value
	 */
	public void setDurationType (java.lang.String durationType) {
		this.durationType = durationType;
	}



	/**
	 * Return the value associated with the column: DURATION
	 */
	public java.lang.Integer getDuration () {
									return duration == null ? Integer.valueOf(0) : duration;
					}

	/**
	 * Set the value related to the column: DURATION
	 * @param duration the DURATION value
	 */
	public void setDuration (java.lang.Integer duration) {
		this.duration = duration;
	}



	/**
	 * Return the value associated with the column: PIN
	 */
	public java.lang.String getPinNumber () {
					return pinNumber;
			}

	/**
	 * Set the value related to the column: PIN
	 * @param pinNumber the PIN value
	 */
	public void setPinNumber (java.lang.String pinNumber) {
		this.pinNumber = pinNumber;
	}



	/**
	 * Return the value associated with the column: POINT
	 */
	public java.lang.Integer getPoint () {
									return point == null ? Integer.valueOf(0) : point;
					}

	/**
	 * Set the value related to the column: POINT
	 * @param point the POINT value
	 */
	public void setPoint (java.lang.Integer point) {
		this.point = point;
	}



	/**
	 * Return the value associated with the column: BATCH_NO
	 */
	public java.lang.String getBatchNo () {
					return batchNo;
			}

	/**
	 * Set the value related to the column: BATCH_NO
	 * @param batchNo the BATCH_NO value
	 */
	public void setBatchNo (java.lang.String batchNo) {
		this.batchNo = batchNo;
	}



	/**
	 * Return the value associated with the column: EMAIL
	 */
	public java.lang.String getEmail () {
					return email;
			}

	/**
	 * Set the value related to the column: EMAIL
	 * @param email the EMAIL value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
	}



	/**
	 * Return the value associated with the column: TYPE
	 */
	public java.lang.String getType () {
					return type;
			}

	/**
	 * Set the value related to the column: TYPE
	 * @param type the TYPE value
	 */
	public void setType (java.lang.String type) {
		this.type = type;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.floreantpos.model.GiftCard)) return false;
		else {
			com.floreantpos.model.GiftCard giftCard = (com.floreantpos.model.GiftCard) obj;
			if (null == this.getCardNumber() || null == giftCard.getCardNumber()) return false;
			else return (this.getCardNumber().equals(giftCard.getCardNumber()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getCardNumber()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getCardNumber().hashCode();
				this.hashCode = hashStr.hashCode();
			}
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