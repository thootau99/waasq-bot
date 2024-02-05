package org.thootau.waasqtelegrambot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WaasqTelegramBotApplication {
    @Value("${mqtt.host}")
    String MqttHost;
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(WaasqTelegramBotApplication.class)
                        .run(args);
    }
}
