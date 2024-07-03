package com.github.cysong;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author cysong
 * @date 2024/6/21 10:28
 **/
public class BrokerConstants {

    public static final int PORT = 1883;

    public static final String BROKER_URL = "tcp://localhost:" + PORT;

    public static Path TEMP_DIR;

    public static final int DEFAULT_QOS = 2;

    public static final String MQTT_CLIENT_NAME = "pos";

    public static final String TOPIC_COFFEEMAKER_STATUS = "COFFEEMAKER_STATUS";

    public static final String TOPIC_TICKET_STATUS = "TICKET_STATUS";

    public static final String PREFIX_COFFEE_MAKER_CLIENT = "COFFEE_MAKER_";

    public static final String COFFEE_GROUP_NAME = "coffee";




    static {
        try {
            TEMP_DIR = Files.createTempDirectory("MQTT").toAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Fail to create temp dir", e);
        }
    }

}
