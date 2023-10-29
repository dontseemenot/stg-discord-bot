package org.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class BotListeners extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            String messageSent = event.getMessage().getContentRaw().toLowerCase();
            if (messageSent.startsWith("/bot ")) {
                if (messageSent.equals("/bot ping")) {
                    event.getChannel().sendMessage("Pong!").queue();
                } else if (messageSent.startsWith("/bot say ")) {
                    String repeatMessage = messageSent.substring(9);
                    event.getChannel().sendMessage(repeatMessage).queue();
                }
                else {
                    event.getChannel().sendMessage("Commands:\n/bot ping\n/bot say <message>").queue();
                }

            }
        }

    }

}
