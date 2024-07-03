package com.github.cysong.broker;

import com.github.cysong.BrokerConstants;
import com.github.cysong.client.TicketsDispatcher;
import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.messages.*;
import org.apache.log4j.Logger;

/**
 * @author cysong
 * @date 2024/6/26 9:43
 **/
public class DefaultInterceptHandler extends AbstractInterceptHandler {

    private static Logger logger = Logger.getLogger(DefaultInterceptHandler.class);

    @Override
    public String getID() {
        return DefaultInterceptHandler.class.getSimpleName();
    }

    @Override
    public void onConnect(InterceptConnectMessage msg) {
        logger.info("Client connected: " + msg.getClientID());
    }

    @Override
    public void onDisconnect(InterceptDisconnectMessage msg) {
        logger.info("Client disconnected: " + msg.getClientID());
    }

    @Override
    public void onConnectionLost(InterceptConnectionLostMessage msg) {
        logger.warn("Connection lost: " + msg.getClientID());
    }

    @Override
    public void onPublish(InterceptPublishMessage msg) {
        logger.info("Message published from client " + msg.getClientID() + " to topic " + msg.getTopicName());
        msg.getPayload().release();
    }

    @Override
    public void onSubscribe(InterceptSubscribeMessage msg) {
        logger.info("Client " + msg.getClientID() + " subscribed to topic " + msg.getTopicFilter());
        if (msg.getClientID().startsWith(BrokerConstants.PREFIX_COFFEE_MAKER_CLIENT)) {
            TicketsDispatcher.addTicketSubscriber(msg.getClientID(), msg.getTopicFilter());
        }
    }

    @Override
    public void onUnsubscribe(InterceptUnsubscribeMessage msg) {
        logger.info("Client " + msg.getClientID() + " unsubscribed from topic " + msg.getTopicFilter());
        if (msg.getClientID().startsWith(BrokerConstants.PREFIX_COFFEE_MAKER_CLIENT)) {
            TicketsDispatcher.removeTicketSubscriber(msg.getClientID());
        }
    }

    @Override
    public void onMessageAcknowledged(InterceptAcknowledgedMessage msg) {
        logger.info(String.format("Message acknowledged from topic %s", msg.getTopic()));
    }
}
