package com.github.cysong.entity;

/**
 * @author cysong
 * @date 2024/6/26 15:28
 **/
public class AutoKitchenTicketStatus {

    private Integer ticketId;
    private Integer ticketItemId;
    private boolean finished;
    private String remark;

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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
