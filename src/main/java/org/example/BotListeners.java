package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class BotListeners extends ListenerAdapter {

    private PropertiesHandler propertiesHandler;
    private DBHandler dbHandler;
    private ChatGPTHandler chatGPTHandler;
    private BobuxHandler bobuxHandler;

    public BotListeners(PropertiesHandler propertiesHandler, DBHandler dbHandler, ChatGPTHandler chatGPTHandler, BobuxHandler bobuxHandler) {
        this.propertiesHandler = propertiesHandler;
        this.dbHandler = dbHandler;
        this.chatGPTHandler = chatGPTHandler;
        this.bobuxHandler = bobuxHandler;

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
            case "work":
                String username = event.getUser().getName();
                String discordID = event.getUser().getId();
                String response = bobuxHandler.Work(discordID,username);
                event.reply(response).queue();
                break;
            case "leaderboard":
                event.deferReply().queue();
                BobuxAccountLeaderboard leaderboard = bobuxHandler.Leaderboard();
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Bobux Leaderboard");
                embed.setColor(Color.GREEN);
                for(BobuxAccount acc : leaderboard.BobuxAccountList) {
                    embed.addField("User", acc.Username, true);
                    embed.addField("Bobux", Integer.toString(acc.Amount), true);
                }

                event.getHook().sendMessageEmbeds(embed.build()).queue();
                break;
            default:
                event.getChannel().sendMessage("Type \"/\" to see all commands").queue();
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
