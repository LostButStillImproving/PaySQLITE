package model;


public class UserBuilder {

    public Company createCompanyUser(String username, int phonenumber, double balance,
                                     String name, String country, int CVR, int cardNumber) {

        return new Company(username, phonenumber, balance, name, country, CVR, cardNumber);
    }

    public Person createPersonUser(String username, int phonenumber, double balance,
                                    String firstName, String lastName, int cardNumber) {

        return new Person(username, phonenumber, balance, firstName, lastName, cardNumber);
    }
}
