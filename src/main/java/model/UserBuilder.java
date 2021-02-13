package model;

import persistence.TransactionHandler;
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
        var userHandler = new UserHandler();
        var transactionHandler = new TransactionHandler();

        var personUser = userBuilder.createPersonUser("From", 20502304, 10000.,"Mike", "Rasmussen");
        var companyUser = userBuilder.createCompanyUser("To",20202, 5000., "Google", "USA", 23232);

        userHandler.addUser(personUser);
        userHandler.addUser(companyUser);



        transactionHandler.transferMoneyFromUserToUser(companyUser, personUser, 5000);

        userHandler.deleteUser(companyUser);
    }
}
