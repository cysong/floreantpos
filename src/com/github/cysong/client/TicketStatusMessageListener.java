package com.github.cysong.client;

import com.floreantpos.model.KitchenTicket;
import com.floreantpos.model.KitchenTicketItem;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.dao.KitchenTicketDAO;
import com.floreantpos.model.dao.KitchenTicketItemDAO;
import com.floreantpos.model.dao.TicketItemDAO;
import com.github.cysong.entity.AutoKitchenTicketStatus;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author cysong
 * @date 2024/6/26 15:23
 **/
public class TicketStatusMessageListener implements IMqttMessageListener {

    private static Logger logger = Logger.getLogger(TicketStatusMessageListener.class);

    private static final Gson gson = new Gson();

    private static TicketStatusMessageListener instance;

    public synchronized static TicketStatusMessageListener getInstance() {
        if (instance == null) {
            instance = new TicketStatusMessageListener();
        }
        return instance;
    }

    @Override
    public void messageArrived(String clientId, MqttMessage message) throws Exception {
        logger.info(String.format("Got ticket status message %s from client %s", message.getId(), clientId));
        final String content = new String(message.getPayload(), UTF_8);
        AutoKitchenTicketStatus entity = gson.fromJson(content, AutoKitchenTicketStatus.class);
        if (entity == null) {
            return;
        }
        if (!entity.isFinished()) {
            logger.warn(String.format("Status of Ticket %s from client %s error", entity.getTicketItemId(), clientId));
            return;
        }

        TicketItem item = TicketItemDAO.getInstance().get(entity.getTicketItemId());

        KitchenTicketItem kItem = KitchenTicketItemDAO.getInstance().getByTicketItemId(item.getId());
        item.setStatus(Ticket.STATUS_READY);

        Ticket ticket = item.getTicket();
        List<KitchenTicket> kTickets = KitchenTicketDAO.getInstance().findByParentId(ticket.getId());
        KitchenTicket kTicket = null;
        for (KitchenTicket kt : kTickets) {
            //如果KitchenTicket只有这一个item，则一起关闭
            List<KitchenTicketItem> kItems = kt.getTicketItems();
            KitchenTicketItem target = kItems.stream().filter(i -> i.getId().equals(kItem.getId())).findAny().orElse(null);
            if (target != null) {
                //TicketItemId of modifier is 0
                if (!kItems.stream().filter(i -> (!i.getId().equals(kItem.getId()) && i.getTicketItemId() != 0)).findAny().isPresent()) {
                    kTicket = kt;
                    target.setStatus(KitchenTicket.KitchenTicketStatus.DONE.name());
                    break;
                }
            }
        }

        Session session = null;
        Transaction tx = null;
        try {
            session = KitchenTicketItemDAO.getInstance().createNewSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(item);
            if (kTicket != null) {
                kTicket.setStatus(KitchenTicket.KitchenTicketStatus.DONE.name());
                session.saveOrUpdate(kTicket);
            } else {
                kItem.setStatus(KitchenTicket.KitchenTicketStatus.DONE.name());
                session.saveOrUpdate(kItem);
            }
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        } finally {
            session.close();
        }
    }
}
