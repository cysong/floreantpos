package com.floreantpos.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.floreantpos.model.dao.RestaurantDAO;

public class IntegerIdGenerator implements IdentifierGenerator {
	private final static RestaurantDAO restaurantDAO = RestaurantDAO.getInstance();
	private static int lastGeneratedId;
	AtomicInteger at = new AtomicInteger();

	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		System.out.println("generating id for " + object.getClass());
		Integer generatedId = null;
		try {
			Class<? extends Object> clazz = object.getClass();
			Method method = clazz.getMethod("getId", (Class<?>[]) null);
			if (method != null) {
				Object id = method.invoke(object, (Object[]) null);
				if (id != null) {
					generatedId = (Integer) id;
				}
			}
			else {
				method = clazz.getMethod("getAutoId", (Class<?>[]) null);
				if (method != null) {
					Object id = method.invoke(object, (Object[]) null);
					if (id != null) {
						generatedId = (Integer) id;
					}
				}
			}

		} catch (Exception e) {
		}
		if (generatedId == null) {
			Timestamp timestamp = restaurantDAO.geTimestamp();
			long time = timestamp.getTime();
			generatedId = (int) ((time / 1000L) % Integer.MAX_VALUE);
			//generatedId = at.incrementAndGet();
		}
		if (generatedId == lastGeneratedId) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			return generate(session, object);
		}
		lastGeneratedId = generatedId;
		return generatedId;
	}
}
