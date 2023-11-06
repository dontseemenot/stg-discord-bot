package org.example;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

public class BobuxHandler {

    private DBHandler dbHandler;
    private Random rand;
    public BobuxHandler(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
        this.rand = new Random();
    }

    public String Work(String strDiscordID, String username) {
        Long longDiscordID = Long.parseLong(strDiscordID);
        int amount = rand.nextInt(100);
        BobuxAccount acc = new BobuxAccount(longDiscordID, username);
        if (!dbHandler.UpdateEntryBobuxAccounts(acc, amount)) {
            return "There was an error in executing the /work command";
        }
        return String.format("%1$s earned %2$d bobux!", username, amount);
    }
    public BobuxAccountLeaderboard Leaderboard() {
        List<BobuxAccount> bobuxAccounts = dbHandler.GetTableBobuxAccounts();
        BobuxAccountLeaderboard leaderboard = new BobuxAccountLeaderboard(bobuxAccounts);
        return leaderboard;
    }


}
