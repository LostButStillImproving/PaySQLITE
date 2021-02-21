package persistence;

import java.util.ArrayList;

public class UserQueryDataClass {

    ArrayList<String> userNames = new ArrayList<>();
    ArrayList<Double> balances = new ArrayList<>();

    public void appendUserName(String userName) {
        this.userNames.add(userName);
    }

    public void appendBalance(Double balance) {
        this.balances.add(balance);
    }

    public String toString() {

        int numberOfUsers = userNames.size();

        StringBuilder toString = new StringBuilder();
        toString.append(String.format("%-15s %15s %n", "USERNAME", "BALANCE"));
        toString.append("-------------------------------\n");
        for (int i = 0; i < numberOfUsers; i++) {
            toString.append(String.format("%-15s %15s %n",
                    userNames.get(i),
                    balances.get(i)));
        }
        toString.append("-------------------------------");

        return toString.toString();
    }
}
