package org.thootau.waasqtelegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.thootau.waasqtelegrambot.mqtt.MqttClient;

import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class WaasqBot extends AbilityBot {
    private final ResponseHandler responseHandler;
    private final MqttClient.MqttGateWay mqttGateWay;

    @Autowired
    public WaasqBot(Environment env, MqttClient.MqttGateWay mqttGateWay) {
        super(env.getProperty("BOT_TOKEN"), env.getProperty("BOT_NAME"));
        this.mqttGateWay = mqttGateWay;
        responseHandler = new ResponseHandler(silent, db);
    }
    @Override
    public long creatorId() {
        return 1L;
    }

    public Ability startBot() {
        return Ability
                .builder()
                .name("start")
                .info("start")
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> responseHandler.replyToStart(ctx.chatId()))
                .build();
    }

    public Ability feedBot() {
        return Ability
                .builder()
                .name("feed")
                .info("feed")
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> {
                    try {
                        responseHandler.replyToFeed(ctx, this.mqttGateWay);
                    } catch (Exception ex) {
                        System.out.printf("%s", ex);
                    }
                })
                .build();
    }

    public static class ResponseHandler {
        private final SilentSender sender;
        public ResponseHandler(SilentSender sender, DBContext ignore) {
            this.sender = sender;
        }

        public void replyToStart(long chatId) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("START_TEXT");
            sender.execute(message);
        }

        public void replyToFeed(MessageContext ctx, MqttClient.MqttGateWay mqttGateWay) {
            long chatId = ctx.chatId();
            String hostIp = ctx.firstArg();
            String  count = ctx.secondArg();
            mqttGateWay.sendToMqtt(count, String.format("%s/feed", hostIp));

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(String.format("%s FEED OK", hostIp));
            sender.execute( message );
        }
    }
}