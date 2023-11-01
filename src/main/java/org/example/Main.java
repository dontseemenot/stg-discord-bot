package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.Properties;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

public class Main {
    public static void main(String[] args) throws LoginException {
        // Setup and inject dependencies
        PropertiesHandler propertiesHandler = new PropertiesHandler();
        DBHandler dbHandler = new DBHandler(propertiesHandler);
        ChatGPTHandler chatGPTHandler = new ChatGPTHandler(propertiesHandler);

        BobuxHandler bobuxHandler = new BobuxHandler(dbHandler);
        BotListeners botListeners = new BotListeners(
                propertiesHandler,
                dbHandler,
                chatGPTHandler,
                bobuxHandler
        );

        JDA bot = JDABuilder.createDefault(propertiesHandler.GetProperty("BOT_TOKEN"))
                .setActivity(Activity.playing(Constants.SetActivity))
                .addEventListeners(botListeners)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        bot.updateCommands().addCommands(
                Commands.slash("ping", "Calculate ping of the bot"),
                Commands.slash("say", "Bot will repeat the message")
                        .addOption(OptionType.STRING, "message", "Message to repeat", true),
                Commands.slash("gpt", "Send a prompt to ChatGPT")
                        .addOption(OptionType.STRING, "message", "ChatGPT prompt", true),
                Commands.slash(Constants.CmdWork, Constants.CmdWorkDesc)
        ).queue();
    }




}