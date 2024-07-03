package com.github.cysong.client;

import com.floreantpos.model.TicketItem;
import com.github.cysong.AutoKitchenManager;
import com.github.cysong.entity.AutoKitchenTicket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * @author cysong
 * @date 2024/6/26 10:34
 **/
public class AutoKitchenTicketsQueue {

    private static Logger logger = Logger.getLogger(AutoKitchenManager.class);

    private static BlockingQueue<AutoKitchenTicket> queue = new LinkedBlockingQueue<>();

    public static AutoKitchenTicket getTicket() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
        }
        return null;
    }

    public static void addTicketsToQueue(List<AutoKitchenTicket> tickets) {
        if (tickets == null || tickets.size() == 0) {
            return;
        }
        for (AutoKitchenTicket ticket : tickets) {
            queue.offer(ticket);
        }
        logger.info(String.format("Successfully add %d auto kitchen ticket(s) to queue", tickets.size()));
    }

    /***
     *  遍历队列，根据TicketItemId查找匹配的订单，并按照itemCount数量调减，
     *  如果调减到0，则从队列中删除，继续遍历队列，直到到达队列末尾，
     *  还没有匹配的TicketItem则认为已经发送到咖啡机，程序不再做处理
     * @author cysong
     * @date 2024/6/20 14:58
     * @param ticketItems
     **/
    public static void removeTicketsFromQueue(List<TicketItem> ticketItems) {
        if (ticketItems == null || ticketItems.size() == 0) {
            return;
        }
        Iterator<AutoKitchenTicket> it = queue.iterator();
        while (!it.hasNext()) {
            AutoKitchenTicket ticket = it.next();
            Iterator<TicketItem> it2 = ticketItems.iterator();
            while (it2.hasNext()) {
                TicketItem item = it2.next();
                if (item.getId().equals(ticket.getTicketItemId())) {
                    if (item.getItemCount().equals(ticket.getItemCount())) {
                        it.remove();
                        it2.remove();
                    } else if (item.getItemCount() > ticket.getItemCount()) {
                        it.remove();
                        item.setItemCount(item.getItemCount() - ticket.getItemCount());
                    } else {
                        it2.remove();
                        ticket.setItemCount(ticket.getItemCount() - item.getItemCount());
                    }
                    break;
                }
            }
        }
        if (ticketItems.size() > 0) {
            String ids = ticketItems.stream().map(i -> i.getId().toString()).collect(Collectors.joining(","));
            logger.warn(String.format("Items can not be remove(probably have been sent to kitchen): %s", ids));
        }
    }

}
