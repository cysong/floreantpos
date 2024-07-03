package com.github.cysong;

import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.dao.KitchenTicketDAO;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.swing.PaginatedListModel;
import com.github.cysong.client.AutoKitchenTicketsQueue;
import com.github.cysong.entity.AutoKitchenTicket;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author cysong
 * @date 2024/6/19 15:49
 **/
public class AutoKitchenManager {

    private static Logger logger = Logger.getLogger(AutoKitchenManager.class);

    /***
     * 前台下单或者修改订单，筛选订单中满足条件的菜品（一般是咖啡）生成ticket并加入到队列等待分发到咖啡机
     * 如果是修改订单需要比较菜单的变化，删除的菜品从队列中删除，增加的菜品新建新的ticket加入到队列
     * @author cysong
     * @date 2024/6/26 9:49
     * @param oldTicket
     * @param newTicket
     **/
    public static void receiveTicket(Ticket oldTicket, Ticket newTicket) {
        if (newTicket == null) {
            return;
        }
        List<AutoKitchenTicket> akTickets = new ArrayList<>();
        if (oldTicket == null) {
            akTickets = AutoKitchenTicket.fromTicket(newTicket);
        } else {
            List<TicketItem> addedItems = getAddedItems(oldTicket, newTicket);
            List<TicketItem> deletedItems = getDeletedItems(oldTicket, newTicket);
            AutoKitchenTicketsQueue.removeTicketsFromQueue(deletedItems);
            for (TicketItem item : addedItems) {
                if (AutoKitchenTicket.isValid(item)) {
                    akTickets.add(AutoKitchenTicket.fromTicketItem(item));
                }
            }
        }
        AutoKitchenTicketsQueue.addTicketsToQueue(akTickets);
    }

    public static void initUnfinishedKitchenTickets() {
        PaginatedListModel dataModel = new PaginatedListModel(Integer.MAX_VALUE);
        dataModel.setCurrentRowIndex(0);
        KitchenTicketDAO.getInstance().loadKitchenTickets(null, null, dataModel);
        List<Ticket> tickets = TicketDAO.getInstance().findOpenTickets();
        tickets.stream().map(t -> AutoKitchenTicket.fromTicket(t)).forEach(AutoKitchenTicketsQueue::addTicketsToQueue);
    }


    /***
     * 比较新旧菜单，生成增加的item清单
     * @author cysong
     * @date 2024/6/26 10:28
     * @param oldTicket
     * @return newTicket
     **/
    private static List<TicketItem> getAddedItems(Ticket oldTicket, Ticket newTicket) {
        List<TicketItem> newItems = newTicket.getTicketItems()
                .stream()
                .filter(AutoKitchenTicket::isValid)
                .collect(Collectors.toList());
        List<TicketItem> addedItems = new ArrayList<>();
        for (TicketItem newItem : newItems) {
            TicketItem oldItem = oldTicket.getTicketItems()
                    .stream()
                    .filter(item -> item.getId().equals(newItem.getId()))
                    .findFirst()
                    .orElse(null);
            if (oldItem == null) {
                addedItems.add(newItem);
            } else if (newItem.getItemCount() > oldItem.getItemCount()) {
                TicketItem clone = newItem.clone(newItem);
                clone.setItemCount(newItem.getItemCount() - oldItem.getItemCount());
                addedItems.add(clone);
            }
        }
        return addedItems;
    }

    /***
     * 比较新旧菜单，得出删除的item清单
     * @author cysong
     * @date 2024/6/26 10:29
     * @param oldTicket
     * @param newTicket
     * @return java.util.List<com.floreantpos.model.TicketItem>
     **/
    private static List<TicketItem> getDeletedItems(Ticket oldTicket, Ticket newTicket) {
        List<TicketItem> oldItems = oldTicket.getTicketItems()
                .stream()
                .filter(AutoKitchenTicket::isValid)
                .collect(Collectors.toList());
        List<TicketItem> deletedItems = new ArrayList<>();
        for (TicketItem oldItem : oldItems) {
            TicketItem newItem = newTicket.getTicketItems()
                    .stream()
                    .filter(item -> item.getId().equals(oldItem.getId()))
                    .findFirst()
                    .orElse(null);
            if (newItem == null) {
                deletedItems.add(oldItem);
            } else if (newItem.getItemCount() < oldItem.getItemCount()) {
                TicketItem clone = newItem.clone(newItem);
                clone.setItemCount(oldItem.getItemCount() - newItem.getItemCount());
                deletedItems.add(clone);
            }
        }
        return deletedItems;
    }

}
