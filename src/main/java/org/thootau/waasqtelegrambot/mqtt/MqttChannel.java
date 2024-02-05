package org.thootau.waasqtelegrambot.mqtt;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Repository;

@Repository
public class MqttChannel {
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }
}
