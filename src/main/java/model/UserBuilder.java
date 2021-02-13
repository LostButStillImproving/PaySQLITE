package model;

import persistence.UserHandler;

import java.sql.SQLException;

public class UserBuilder {

    public Company createCompanyUser(String username, int phonenumber, double balance,
                                     String name, String country, int CVR) {

        return new Company(username, phonenumber, balance, name, country, CVR);
    }

    public Person createPersonUser(String username, int phonenumber, double balance,
                                    String firstName, String lastName) {

        return new Person(username, phonenumber, balance, firstName, lastName);
    }

    public static void main(String[] args) throws SQLException {
        var userBuilder = new UserBuilder();
        var personUser = userBuilder.createPersonUser("MrAwesume", 20502304, 200.,"Mike", "Rasmussen");

        var companyUser = userBuilder.createCompanyUser("asusvenus",20202, 10000., "Google", "USA", 23232);
        var companyUser1 = userBuilder.createCompanyUser("asusvenus1",20202, 10000., "Google", "USA", 23232);
        var userHandler = new UserHandler();

        userHandler.addUser(companyUser);
        userHandler.addUser(personUser);
        userHandler.addUser(companyUser1);
    }
}
