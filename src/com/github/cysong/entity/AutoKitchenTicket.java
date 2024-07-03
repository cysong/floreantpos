package com.github.cysong.entity;

import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.TicketItemModifier;
import com.github.cysong.BrokerConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cysong
 * @date 2024/6/19 15:09
 **/
public class AutoKitchenTicket {
    private Integer ticketId;
    private Integer ticketItemId;
    private Integer itemId;
    private Long created;
    private Integer temperature;
    private Integer itemCount;
    private Character beanType;
    private Character milkType;
    private Character beverageType;
    private Character cupType;
    private Integer cupSize;


    public static List<AutoKitchenTicket> fromTicket(Ticket ticket) {
        List<TicketItem> items = ticket.getTicketItems();
        List<AutoKitchenTicket> result = new ArrayList<>();
        if (items == null || items.size() == 0) {
            return result;
        }
        for (TicketItem item : items) {
            if (isValid(item)) {
                result.add(fromTicketItem(item));
            }
        }
        return result;
    }

    public static AutoKitchenTicket fromTicketItem(TicketItem ticketItem) {
        AutoKitchenTicket akTicket = new AutoKitchenTicket();
        Ticket ticket = ticketItem.getTicket();
        akTicket.setTicketId(ticket.getId());
        akTicket.setCreated(ticket.getActiveDate().getTime() / 1000);
        akTicket.setTicketItemId(ticketItem.getId());
        akTicket.setItemId(ticketItem.getItemId());
        akTicket.setItemCount(ticketItem.getItemCount());
        //TODO:parse modifiers
        List<TicketItemModifier> modifiers = ticketItem.getTicketItemModifiers();
        if (modifiers != null && modifiers.size() > 0) {
            for (TicketItemModifier modifier : modifiers) {
                //parse beanType, milkType, cupType, cupSize from modifier
            }
        }
        return akTicket;
    }

    /***
     * 判断菜单是否可以由自动化咖啡机加工,并且没有ready
     * @author cysong
     * @date 2024/6/26 10:30
     * @param ticketItem
     * @return boolean
     **/
    public static boolean isValid(TicketItem ticketItem) {
        return ticketItem != null &&
                StringUtils.equalsIgnoreCase(BrokerConstants.COFFEE_GROUP_NAME, ticketItem.getGroupName())
                && !Ticket.STATUS_READY.equals(ticketItem.getStatus());
    }


    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getTicketItemId() {
        return ticketItemId;
    }

    public void setTicketItemId(Integer ticketItemId) {
        this.ticketItemId = ticketItemId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Character getBeanType() {
        return beanType;
    }

    public void setBeanType(Character beanType) {
        this.beanType = beanType;
    }

    public Character getMilkType() {
        return milkType;
    }

    public void setMilkType(Character milkType) {
        this.milkType = milkType;
    }

    public Character getBeverageType() {
        return beverageType;
    }

    public void setBeverageType(Character beverageType) {
        this.beverageType = beverageType;
    }

    public Character getCupType() {
        return cupType;
    }

    public void setCupType(Character cupType) {
        this.cupType = cupType;
    }

    public Integer getCupSize() {
        return cupSize;
    }

    public void setCupSize(Integer cupSize) {
        this.cupSize = cupSize;
    }
}
