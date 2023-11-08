package org.example;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

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
        if (!dbHandler.CheckIfExistsBobuxAccounts(acc)) {
            System.out.println("Entry with discord id + " + acc.DiscordID + "did not exist, adding now.");
            dbHandler.AddEntryBobuxAccounts(acc);
        }
        if (!dbHandler.UpdateEntryBobuxAccounts(acc, amount)) {
            return "There was an error in executing the /work command";
        }
        return String.format("%1$s earned %2$d Bobux!", username, amount);
    }

    public String Steal(User currentUser, User targetUser) {
        // Check if target user exists in DB. If not, return fail.
        BobuxAccount targetAcc = new BobuxAccount(targetUser.getIdLong(), targetUser.getName());
        if (!dbHandler.CheckIfExistsBobuxAccounts(targetAcc)) {
            return "Error: Cannot steal from a user that has not worked!";
        }

        // Check if possible to steal from target user (target user must have more than amount deducted). If not, return fail.
        int amountToSteal = rand.nextInt(100);
        int targetUserAmount = dbHandler.GetAmountRobuxAccounts(targetAcc);
        if (amountToSteal > targetUserAmount) {
            return String.format("Amount to steal (%1$d) exceeds %2$s current balance (%3$d)!", amountToSteal, targetUser.getName(), targetUserAmount);
        }

        BobuxAccount curAcc = new BobuxAccount(currentUser.getIdLong(), currentUser.getName());
        dbHandler.UpdateEntryBobuxAccounts(curAcc, amountToSteal);
        dbHandler.UpdateEntryBobuxAccounts(targetAcc, -1 * amountToSteal);
        return String.format("%1$s stole %2$d Bobux from %3$s!", currentUser.getName(), amountToSteal, targetUser.getName());
    }

    public BobuxAccountLeaderboard Leaderboard() {
        List<BobuxAccount> bobuxAccounts = dbHandler.GetTableBobuxAccounts();
        BobuxAccountLeaderboard leaderboard = new BobuxAccountLeaderboard(bobuxAccounts);
        return leaderboard;
    }


}
