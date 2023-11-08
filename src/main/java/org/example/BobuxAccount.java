package org.example;

import java.util.List;

public class BobuxAccount {
    public BobuxAccount(Long discordID, String username, int amount) {
        DiscordID = discordID;
        Username = username;
        Amount = amount;
    }
    public BobuxAccount(Long discordID, String username) {
        DiscordID = discordID;
        Username = username;
        Amount = 0;
    }
    public Long DiscordID;
    public String Username;
    public int Amount;
}
