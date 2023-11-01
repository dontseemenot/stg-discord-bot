package org.example;

import java.util.Random;

public class BobuxHandler {

    private DBHandler dbHandler;
    private Random rand;
    public BobuxHandler(DBHandler dbHandler) {
        this.dbHandler = dbHandler;
        this.rand = new Random();
    }

    public String Work(String username) {
        StringBuilder sb = new StringBuilder();
        int n = rand.nextInt(100);
        sb.append(username);
        sb.append(" worked and earned $");
        sb.append(n);
        sb.append(" bobux!");
        return sb.toString();
    }
}
