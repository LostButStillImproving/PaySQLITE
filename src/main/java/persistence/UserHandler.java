package persistence;

import model.Company;
import model.Person;
import model.User;

import java.sql.*;

public class UserHandler {

    Connection connection;

    public void addUser(User user) throws SQLException {

        if (user instanceof Company) {
            addCompany((Company) user);
        }
        if (user instanceof Person) {
            addPerson((Person) user);
        }
    }

    private void addPerson(Person personUser) throws SQLException {

        try {
            connectToDB(DatabaseConnector.connect());
            beginTransaction();
            insertIntoPersonsTable(personUser);
            insertIntoUsersTable(personUser);
            commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        closeConnection();
    }

    private void addCompany(Company companyUser) throws SQLException {

        try {
            connectToDB(DatabaseConnector.connect());
            beginTransaction();
            insertIntoCompanyTable(companyUser);
            insertIntoUsersTable(companyUser);
            commit();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        closeConnection();
    }

    private void insertIntoCompanyTable(Company companyUser) throws SQLException {

        String insertIntoCompanyTable = "INSERT INTO Companies(companyName, country, CVR ) VALUES(?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(insertIntoCompanyTable);
        preparedStatement.setString(1,companyUser.getCompanyName());
        preparedStatement.setString(2, companyUser.getCountry());
        preparedStatement.setInt(3, companyUser.getCVR());
        preparedStatement.executeUpdate();
    }

    private void insertIntoPersonsTable(Person personUser) throws SQLException {

        String insertIntoPersonsTable = "INSERT INTO Persons(firstname, lastname) VALUES(?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertIntoPersonsTable);
        preparedStatement.setString(1,personUser.getFirstName());
        preparedStatement.setString(2, personUser.getLastName());
        preparedStatement.executeUpdate();
    }

    private void insertIntoUsersTable(User user) throws SQLException {

        String insertIntoUsersTable = "INSERT INTO Users(username, phonenumber, Balance,company_id,person_id) VALUES(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(insertIntoUsersTable);
        preparedStatement.setString(1,user.getUsername());
        preparedStatement.setInt(2, user.getPhonenumber());
        preparedStatement.setDouble(3, user.getBalance());

        if (user instanceof Company) {
            int companyID = getNewestCompanyID();
            preparedStatement.setInt(4, companyID);
        }

        if (user instanceof Person) {
            int personID = getNewestPersonID();
            preparedStatement.setInt(5, personID);
        }

        preparedStatement.executeUpdate();
    }

    private int getNewestCompanyID() {

        String sql = "SELECT id FROM Companies ORDER BY id DESC LIMIT 1";

        return getID(sql);

    }private int getNewestPersonID() {

        String sql = "SELECT id FROM Persons ORDER BY id DESC LIMIT 1";

        return getID(sql);
    }

    private int getID(String sql) {

        int id = 0;
        try (
                Statement stmt  = this.connection.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                id =  rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    private void beginTransaction() throws SQLException {

        String begin = "BEGIN TRANSACTION";
        PreparedStatement preparedStatement = connection.prepareStatement(begin);
        preparedStatement.executeUpdate();
    }

    private void commit() throws SQLException {

        String end = "COMMIT";
        PreparedStatement preparedStatement = connection.prepareStatement(end);
        preparedStatement.executeUpdate();
    }

    private void connectToDB(Connection connect) {

        this.connection = connect;
    }

    private void closeConnection() throws SQLException {

        assert connection != null;
        connection.close();
        connectToDB(null);
    }
}
