package model;

public class Person extends User{

    private final String firstName;
    private final String lastName;

    public Person(String username, int phonenumber, double balance,
                  String firstName, String lastName, int cardNumber) {
        super(username, phonenumber, balance, cardNumber);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
