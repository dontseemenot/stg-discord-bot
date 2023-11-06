package org.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;

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
                StringBuilder colUser = new StringBuilder();
                StringBuilder colBobux = new StringBuilder();
                for (BobuxAccount acc : leaderboard.BobuxAccountList) {
                    colUser.append(acc.Username).append("\n");
                    colBobux.append(acc.Amount).append("\n");
                }
                embed.addField("Username", colUser.toString(), true);
                embed.addField(Constants.EmojiDollarBanknote + " Bobux", colBobux.toString(), true);

                event.getHook().sendMessageEmbeds(embed.build()).queue();
                break;
            default:
                event.getChannel().sendMessage("Type \"/\" to see all commands").queue();
                break;
        }
    }

}
