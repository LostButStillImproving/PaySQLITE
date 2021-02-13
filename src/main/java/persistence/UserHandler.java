package persistence;

import model.Company;
import model.Person;
import model.User;

import java.sql.*;

public class UserHandler {

    Connection connection;
    public void addUser(User user) throws SQLException {

        if (user instanceof Company) {
            addCompanyUser((Company) user);
        }
        if (user instanceof Person) {
            addPersonUser((Person) user);
        }
    }
    public void addPersonUser(Person personUser) throws SQLException {
        try {
            this.connection = Connect.connect();
            String begin = "BEGIN TRANSACTION";
            String insertIntoPersonsTable = "INSERT INTO Persons(firstname, lastname) VALUES(?, ?)";
            String insertIntoUsersTable = "INSERT INTO Users(username, phonenumber, Balance,company_id,person_id) VALUES(?, ?, ?, ?, ?)";
            String end = "COMMIT";

            PreparedStatement preparedStatement = connection.prepareStatement(begin);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(insertIntoPersonsTable);
            preparedStatement.setString(1,personUser.getFirstName());
            preparedStatement.setString(2, personUser.getLastName());
            preparedStatement.executeUpdate();

            int personID = getNewestPersonID();

            preparedStatement = connection.prepareStatement(insertIntoUsersTable);
            preparedStatement.setString(1,personUser.getUsername());
            preparedStatement.setInt(2, personUser.getPhonenumber());
            preparedStatement.setDouble(3, personUser.getBalance());
            preparedStatement.setInt(5, personID);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(end);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        closeConnection();
    }


    public void addCompanyUser(Company companyUser) throws SQLException {
        try {
            this.connection = Connect.connect();
            String begin = "BEGIN TRANSACTION";
            String insertIntoCompanyTable = "INSERT INTO Companies(companyName, country, CVR ) VALUES(?, ?, ?)";
            String insertIntoUsersTable = "INSERT INTO Users(username, phonenumber, Balance,company_id,person_id) VALUES(?, ?, ?, ?, ?)";
            String end = "COMMIT";

            PreparedStatement preparedStatement = connection.prepareStatement(begin);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(insertIntoCompanyTable);
            preparedStatement.setString(1,companyUser.getCompanyName());
            preparedStatement.setString(2, companyUser.getCountry());
            preparedStatement.setInt(3, companyUser.getCVR());
            preparedStatement.executeUpdate();

            int companyID = getNewestCompanyID();

            preparedStatement = connection.prepareStatement(insertIntoUsersTable);
            preparedStatement.setString(1,companyUser.getUsername());
            preparedStatement.setInt(2, companyUser.getPhonenumber());
            preparedStatement.setDouble(3, companyUser.getBalance());
            preparedStatement.setInt(4, companyID);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(end);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        closeConnection();
    }

    private void closeConnection() throws SQLException {
        assert connection != null;
        connection.close();
        connection = null;
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
}
