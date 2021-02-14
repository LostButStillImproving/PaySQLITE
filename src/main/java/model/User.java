package model;

public class User {

    private final String username;
    private final int phonenumber;
    private final double balance;
    private final int cardnumber;

    public User(String username, int phonenumber, double balance, int cardnumber) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.balance = balance;
        this.cardnumber = cardnumber;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", phonenumber=" + phonenumber +
                ", balance=" + balance +
                ", cardnumber=" + cardnumber +
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

    public int getCardnumber() { return cardnumber;}
}
