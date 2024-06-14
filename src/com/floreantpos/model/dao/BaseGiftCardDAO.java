package com.floreantpos.model.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import com.floreantpos.model.dao.GiftCardDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseGiftCardDAO extends com.floreantpos.model.dao._RootDAO {

	// query name references


	public static GiftCardDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static GiftCardDAO getInstance () {
		if (null == instance) instance = new GiftCardDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.floreantpos.model.GiftCard.class;
	}

    public Order getDefaultOrder () {
		return null;
    }

	/**
	 * Cast the object as a com.floreantpos.model.GiftCard
	 */
	public com.floreantpos.model.GiftCard cast (Object object) {
		return (com.floreantpos.model.GiftCard) object;
	}

	public com.floreantpos.model.GiftCard get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (com.floreantpos.model.GiftCard) get(getReferenceClass(), key);
	}

	public com.floreantpos.model.GiftCard get(java.lang.String key, Session s)
		throws org.hibernate.HibernateException {
		return (com.floreantpos.model.GiftCard) get(getReferenceClass(), key, s);
	}

	public com.floreantpos.model.GiftCard load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (com.floreantpos.model.GiftCard) load(getReferenceClass(), key);
	}

	public com.floreantpos.model.GiftCard load(java.lang.String key, Session s)
		throws org.hibernate.HibernateException {
		return (com.floreantpos.model.GiftCard) load(getReferenceClass(), key, s);
	}

	public com.floreantpos.model.GiftCard loadInitialize(java.lang.String key, Session s) 
			throws org.hibernate.HibernateException { 
		com.floreantpos.model.GiftCard obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

/* Generic methods */

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<com.floreantpos.model.GiftCard> findAll () {
		return super.findAll();
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<com.floreantpos.model.GiftCard> findAll (Order defaultOrder) {
		return super.findAll(defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List<com.floreantpos.model.GiftCard> findAll (Session s, Order defaultOrder) {
		return super.findAll(s, defaultOrder);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param giftCard a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(com.floreantpos.model.GiftCard giftCard)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(giftCard);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param giftCard a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.String save(com.floreantpos.model.GiftCard giftCard, Session s)
		throws org.hibernate.HibernateException {
		return (java.lang.String) save((Object) giftCard, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param giftCard a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.floreantpos.model.GiftCard giftCard)
		throws org.hibernate.HibernateException {
		saveOrUpdate((Object) giftCard);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param giftCard a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(com.floreantpos.model.GiftCard giftCard, Session s)
		throws org.hibernate.HibernateException {
		saveOrUpdate((Object) giftCard, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param giftCard a transient instance containing updated state
	 */
	public void update(com.floreantpos.model.GiftCard giftCard) 
		throws org.hibernate.HibernateException {
		update((Object) giftCard);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param giftCard a transient instance containing updated state
	 * @param the Session
	 */
	public void update(com.floreantpos.model.GiftCard giftCard, Session s)
		throws org.hibernate.HibernateException {
		update((Object) giftCard, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException {
		delete((Object) load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param id the instance ID to be removed
	 * @param s the Session
	 */
	public void delete(java.lang.String id, Session s)
		throws org.hibernate.HibernateException {
		delete((Object) load(id, s), s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param giftCard the instance to be removed
	 */
	public void delete(com.floreantpos.model.GiftCard giftCard)
		throws org.hibernate.HibernateException {
		delete((Object) giftCard);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param giftCard the instance to be removed
	 * @param s the Session
	 */
	public void delete(com.floreantpos.model.GiftCard giftCard, Session s)
		throws org.hibernate.HibernateException {
		delete((Object) giftCard, s);
	}
	
	/**
	 * Re-read the state of the given instance from the underlying database. It is inadvisable to use this to implement
	 * long-running sessions that span many business tasks. This method is, however, useful in certain special circumstances.
	 * For example 
	 * <ul> 
	 * <li>where a database trigger alters the object state upon insert or update</li>
	 * <li>after executing direct SQL (eg. a mass update) in the same session</li>
	 * <li>after inserting a Blob or Clob</li>
	 * </ul>
	 */
	public void refresh (com.floreantpos.model.GiftCard giftCard, Session s)
		throws org.hibernate.HibernateException {
		refresh((Object) giftCard, s);
	}


}