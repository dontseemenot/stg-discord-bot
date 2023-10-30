package org.example;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Properties;

public class BotListeners extends ListenerAdapter {

    private ChatGPTAPI chatGPTAPI;
    public BotListeners() {
        Properties properties = PropertiesHandler.GetProperties();
        String chatGPTApiKey = properties.getProperty("CHATGPT_APIKEY");
        chatGPTAPI = new ChatGPTAPI(chatGPTApiKey);
        System.out.println("Botlisteners setup");
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "ping":
                long time = System.currentTimeMillis();
                // Ephermal: only the user can see the command
                event.reply("Pong!").setEphemeral(true) // reply or acknowledge
                        .flatMap(v ->
                                event.getHook().editOriginalFormat("Pong! %d ms", System.currentTimeMillis() - time) // then edit original
                        ).queue(); // Queue both reply and edit
                break;
            case "say":
                event.reply(event.getOption("message").getAsString()).queue(); // reply immediately
                break;
            case "gpt":
//                String prompt = event.getOption("message").getAsString();
//                String response = chatGPTAPI.sendPrompt(prompt);
//                event.getChannel().sendMessage(response).queue();
                event.getChannel().sendMessage("Sorry, this feature is still in development.").queue();
                break;
            default:
                event.getChannel().sendMessage("Commands:\n/ping\n/say <message>").queue();
                break;
        }
    }
//    @Override
//    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
//        if (!event.getAuthor().isBot()) {
//            String messageSent = event.getMessage().getContentRaw().toLowerCase();
//            if (messageSent.startsWith("/ ")) {
//                if (messageSent.equals("/ping")) {
//                    event.getChannel().sendMessage("Pong!").queue();
//                } else if (messageSent.startsWith("/say ")) {
//                    String repeatMessage = messageSent.substring(5);
//                    event.getChannel().sendMessage(repeatMessage).queue();
//                }
//                else {
//                    event.getChannel().sendMessage("Commands:\n/ping\n/say <message>").queue();
//                }
//
//            }
//        }
//
//    }

}
