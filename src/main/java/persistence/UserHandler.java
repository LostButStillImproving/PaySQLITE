package persistence;

import model.Company;
import model.Person;
import model.User;

import java.sql.*;

import static persistence.DatabaseConnector.*;

public class UserHandler {

    public void addUser(User user) throws SQLException {

        if (user instanceof Company) {
            addCompany((Company) user);
        }
        if (user instanceof Person) {
            addPerson((Person) user);
        }
    }

    public void deleteUser(User user) throws SQLException {

        try {
            CONNECTOR.connect();
            CONNECTOR.beginTransaction();
            deleteUserFromTable(user.getUsername());
            CONNECTOR.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        CONNECTOR.closeConnection();
    }

    private void deleteUserFromTable(String username) throws SQLException {

        var deleteUserFromTable = "DELETE FROM Users WHERE username = " + "'" + username + "'";

        var preparedStatement = CONNECTOR.
                getConnection().prepareStatement(deleteUserFromTable);
        preparedStatement.executeUpdate();
    }

    private void addPerson(Person personUser) throws SQLException {

        try {
            CONNECTOR.connect();
            CONNECTOR.beginTransaction();
            insertIntoPersonsTable(personUser);
            insertIntoUsersTable(personUser);
            CONNECTOR.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        CONNECTOR.closeConnection();
    }

    private void addCompany(Company companyUser) throws SQLException {

        try {
            CONNECTOR.connect();
            CONNECTOR.beginTransaction();
            insertIntoCompanyTable(companyUser);
            insertIntoUsersTable(companyUser);
            CONNECTOR.commit();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        CONNECTOR.closeConnection();
    }

    private void insertIntoCompanyTable(Company companyUser) throws SQLException {

        var insertIntoCompanyTable = "INSERT INTO Companies(companyName, country, CVR ) VALUES(?, ?, ?)";
        var preparedStatement = CONNECTOR.getConnection().
                prepareStatement(insertIntoCompanyTable);

        preparedStatement.setString(1,companyUser.getCompanyName());
        preparedStatement.setString(2, companyUser.getCountry());
        preparedStatement.setInt(3, companyUser.getCVR());
        preparedStatement.executeUpdate();
    }

    private void insertIntoPersonsTable(Person personUser) throws SQLException {

        var insertIntoPersonsTable = "INSERT INTO Persons(firstname, lastname) VALUES(?, ?)";

        PreparedStatement preparedStatement = CONNECTOR.getConnection().prepareStatement(insertIntoPersonsTable);
        preparedStatement.setString(1,personUser.getFirstName());
        preparedStatement.setString(2, personUser.getLastName());
        preparedStatement.executeUpdate();
    }

    private void insertIntoUsersTable(User user) throws SQLException {

        var insertIntoUsersTable = "INSERT INTO Users(username, phonenumber, Balance,company_id,person_id, cardnumber) VALUES(?, ?, ?, ?, ?,?)";

        PreparedStatement preparedStatement = CONNECTOR.getConnection().prepareStatement(insertIntoUsersTable);
        preparedStatement.setString(1,user.getUsername());
        preparedStatement.setInt(2, user.getPhonenumber());
        preparedStatement.setDouble(3, user.getBalance());
        preparedStatement.setInt(6, user.getCardnumber());

        if (user instanceof Company) {
            var companyID = getNewestCompanyID();
            preparedStatement.setInt(4, companyID);
        }

        if (user instanceof Person) {
            var personID = getNewestPersonID();
            preparedStatement.setInt(5, personID);
        }

        preparedStatement.executeUpdate();
    }

    public UserQueryDataClass getAllUsers() {
        CONNECTOR.connect();
        var users = new UserQueryDataClass();

        var selectStatement = "SELECT username, Balance FROM Users";
        try (
                var statement  = CONNECTOR.getConnection().createStatement();
                var resultSet    = statement.executeQuery(selectStatement)){

            while (resultSet.next()) {
                 users.appendUserName(resultSet.getString("username"));
                 users.appendBalance(resultSet.getDouble("Balance"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    private int getNewestCompanyID() {

        var sql = "SELECT id FROM Companies ORDER BY id DESC LIMIT 1";

        return getID(sql);

    }private int getNewestPersonID() {

        var sql = "SELECT id FROM Persons ORDER BY id DESC LIMIT 1";

        return getID(sql);
    }

    private int getID(String sql) {

        var id = 0;
        try (
                var statement  = CONNECTOR.getConnection().createStatement();
                var resultSet    = statement.executeQuery(sql)){

            while (resultSet.next()) {
                id =  resultSet.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

}
