package com.github.cysong.client;

import com.github.cysong.BrokerConstants;
import com.github.cysong.entity.AutoKitchenTicket;
import com.github.cysong.entity.CoffeeMakerStatus;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author cysong
 * @date 2024/6/26 10:47
 **/
public class TicketsDispatcher {

    private static Logger logger = Logger.getLogger(TicketsDispatcher.class);

    private static MqttAsyncClient publisher;

    private static MqttAsyncClient subscriber;

    private static final Map<String, String> SUBSCRIBERS = new ConcurrentHashMap<>();

    private static final Map<String, com.github.cysong.CoffeeMakerStatus> CLIENT_STATUS = new ConcurrentHashMap<>();

    private static final Gson gson = new Gson();

    /***
     * start ticket publisher and status subscriber
     * @author cysong
     * @date 2024/6/26 14:01
     **/
    public static void start() {
        try {
            publisher = createClient("POS_TICKET_PUBLISHER", BrokerConstants.TEMP_DIR);
            publisher.connect().waitForCompletion(1_000);

            subscriber = createClient("POS_STATUS_SUBSCRIBER", BrokerConstants.TEMP_DIR);
            subscriber.connect().waitForCompletion(1_000);
        } catch (IOException | MqttException e) {
            throw new RuntimeException("Fail to connect to mqtt broker", e);
        }

        try {
            subscriber.subscribe(BrokerConstants.TOPIC_COFFEEMAKER_STATUS, BrokerConstants.DEFAULT_QOS, getStatusMessageListener());
        } catch (MqttException e) {
            throw new RuntimeException(String.format("Subscribe to subject %s error", BrokerConstants.TOPIC_COFFEEMAKER_STATUS), e);
        }
        try {
            subscriber.subscribe(BrokerConstants.TOPIC_TICKET_STATUS, BrokerConstants.DEFAULT_QOS, TicketStatusMessageListener.getInstance());
        } catch (MqttException e) {
            throw new RuntimeException(String.format("Subscribe to subject %s error", BrokerConstants.TOPIC_TICKET_STATUS), e);
        }
    }

    public static boolean publishTicket(String clientId) {
        assert StringUtils.isNotBlank(clientId);
        if (!SUBSCRIBERS.containsKey(clientId)) {
            logger.error(String.format("Client %s not subscribe ticket topic", clientId));
            return false;
        }
        AutoKitchenTicket ticket = AutoKitchenTicketsQueue.getTicket();
        if (ticket == null) {
            logger.info("No available ticket to publish to client " + clientId);
            return false;
        }
        TicketsDispatcher.publishTicket(clientId, ticket);
        return true;
    }

    /***
     * Publish ticket to client exclusive topic
     * @author cysong
     * @date 2024/6/26 14:03
     * @param clientId
     * @param ticket
     **/
    private static void publishTicket(String clientId, AutoKitchenTicket ticket) {
        String message = gson.toJson(ticket);
        try {
            publisher.publish(SUBSCRIBERS.get(clientId), message.getBytes(UTF_8), BrokerConstants.DEFAULT_QOS, false);
        } catch (MqttException e) {
            throw new RuntimeException(String.format("Publish ticket to client %s error", clientId), e);
        }
    }

    public static String addTicketSubscriber(String clientId, String topic) {
        assert StringUtils.isNotBlank(clientId);
        assert StringUtils.isNotBlank(topic);
        return SUBSCRIBERS.put(clientId, topic);
    }

    public static String removeTicketSubscriber(String clientId) {
        return SUBSCRIBERS.remove(clientId);
    }

    public static MqttAsyncClient createClient(String clientName, Path tempFolder) throws IOException, MqttException {
        final String dataPath = newFolder(tempFolder, clientName).getAbsolutePath();
        MqttClientPersistence clientDataStore = new MqttDefaultFilePersistence(dataPath);
        return new MqttAsyncClient(BrokerConstants.BROKER_URL, clientName, clientDataStore);
    }

    public static File newFolder(Path parent, String child) throws IOException {
        final Path newPath = parent.resolve(child);
        final File dir = newPath.toFile();
        if (!dir.mkdirs()) {
            throw new IOException("Can't create the new folder path: " + dir.getAbsolutePath());
        }
        return dir;
    }

    public static void saveClientStatus(String clientId, com.github.cysong.CoffeeMakerStatus status) {
        assert StringUtils.isNotBlank(clientId);
        assert status != null;
        CLIENT_STATUS.put(clientId, status);
    }

    private static IMqttMessageListener getStatusMessageListener() {
        return new IMqttMessageListener() {
            @Override
            public void messageArrived(String clientId, MqttMessage message) {
                logger.info(String.format("Got coffeemaker status message %s from client %s", message.getId(), clientId));
                final String content = new String(message.getPayload(), UTF_8);
                CoffeeMakerStatus entity = gson.fromJson(content, CoffeeMakerStatus.class);
                com.github.cysong.CoffeeMakerStatus statusEnum = com.github.cysong.CoffeeMakerStatus.getByName(entity.getStatus());
                if (statusEnum == null) {
                    saveClientStatus(clientId, com.github.cysong.CoffeeMakerStatus.UNKNOWN);
                    throw new RuntimeException("Unrecognized coffee maker status message:" + entity.getStatus());
                } else {
                    saveClientStatus(clientId, statusEnum);
                }
                switch (statusEnum) {
                    case BUSYING:
                        break;
                    case READY:
                        publishTicket(clientId);
                        break;
                    case OUT_OF_BEANS:
                    case OUT_OF_MILK:
                    case NEEDS_CLEANING:
                    case UNKNOWN:
                        logger.warn(String.format("Coffer maker error, status:%s, remark:%s", entity.getStatus(), entity.getRemark()));
                }
            }
        };
    }


}
