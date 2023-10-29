package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.Properties;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws LoginException {
        Properties properties = PropertiesHandler.GetProperties();
        String botToken = properties.getProperty("BOT_TOKEN");
        JDA bot = JDABuilder.createDefault(botToken)
                .setActivity(Activity.playing("Playing around"))
                .addEventListeners(new BotListeners())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
    }

}