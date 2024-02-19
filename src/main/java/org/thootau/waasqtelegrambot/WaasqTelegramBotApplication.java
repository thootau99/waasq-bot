package org.thootau.waasqtelegrambot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class WaasqTelegramBotApplication {
    public static void main(String[] args) throws TelegramApiException {
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(WaasqTelegramBotApplication.class)
                        .run(args);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(context.getBean(WaasqBot.class));
    }
}
