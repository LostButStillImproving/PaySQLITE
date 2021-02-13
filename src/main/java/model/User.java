package model;

public class User {

    private final String username;
    private final int phonenumber;
    private final double balance;

    public User(String username, int phonenumber, double balance) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", phonenumber=" + phonenumber +
                ", balance=" + balance +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public double getBalance() {
        return balance;
    }
}
