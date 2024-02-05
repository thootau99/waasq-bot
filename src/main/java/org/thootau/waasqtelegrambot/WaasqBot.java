package org.thootau.waasqtelegrambot;

import org.apache.http.client.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class WaasqBot extends AbilityBot {
    private final ResponseHandler responseHandler;
    @Autowired
    public WaasqBot(Environment env) {
        super(env.getProperty("BOT_TOKEN"), env.getProperty("BOT_NAME"));
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
                .action(ctx -> responseHandler.replyToFeed(ctx.chatId()))
                .build();
    }

    public static class ResponseHandler {
        private final SilentSender sender;
        public ResponseHandler(SilentSender sender, DBContext db) {
            this.sender = sender;
        }

        public void replyToStart(long chatId) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("START_TEXT");
            sender.execute(message);
        }

        public void replyToFeed(long chatId) {}
    }
}