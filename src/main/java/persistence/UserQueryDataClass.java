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

    public ArrayList<String> getUserNames() {
        return userNames;
    }

    public ArrayList<Double> getBalances() {
        return balances;
    }
}
