package com.github.cysong.broker;

import com.github.cysong.BrokerConstants;
import io.moquette.broker.Server;
import io.moquette.broker.config.MemoryConfig;
import io.moquette.interception.InterceptHandler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Properties;

import static io.moquette.BrokerConstants.DEFAULT_MOQUETTE_STORE_H2_DB_FILENAME;

/**
 * @author cysong
 * @date 2024/6/21 10:13
 **/
public class MQTTBroker {

    private static Server broker;

    public static Properties prepareTestProperties(String dbPath) {
        Properties testProperties = new Properties();
        testProperties.put("data_path", dbPath);
        testProperties.put("persistence_enabled", "false");
        testProperties.put("port", String.valueOf(BrokerConstants.PORT));
        testProperties.put("telemetry_enabled", "false");
        testProperties.put("persistent_queue_type", "segmented");
        return testProperties;
    }

    public static void startServer(Path dbPath) throws IOException {
        broker = new Server();
        final Properties configProps = prepareTestProperties(dbPath.toString());
        InterceptHandler handler = new DefaultInterceptHandler();
        broker.startServer(new MemoryConfig(configProps), Arrays.asList(handler));
    }

    public static void start() throws IOException {
        Path dbPath = BrokerConstants.TEMP_DIR.resolve(DEFAULT_MOQUETTE_STORE_H2_DB_FILENAME).toAbsolutePath();
        startServer(dbPath);
    }


}
