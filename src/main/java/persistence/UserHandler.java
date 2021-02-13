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
            addPersontoPersonTable(personUser);
            int personID = getNewestPersonID();
            addPersonToUserTable(personUser, personID);
        } catch (Exception e) {
            System.out.println("Couldnt connect");
        }

        assert connection != null;
        connection.close();
        connection = null;

    }public void addCompanyUser(Company companyUser) throws SQLException {
        try {
            this.connection = Connect.connect();
        } catch (Exception e) {
            System.out.println("Couldnt connect");
        }
        if (connection != null) {
            addCompanytoCompanyTable(companyUser);
            int companyID = getNewestCompanyID();
            addCompanyToUserTable(companyUser, companyID);

        }

        assert connection != null;
        connection.close();
        connection = null;
    }

    private void addCompanyToUserTable(Company companyUser, int companyID) throws SQLException {
        String sql = "INSERT INTO Users(username, phonenumber, Balance,company_id,person_id) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,companyUser.getUsername());
        preparedStatement.setInt(2, companyUser.getPhonenumber());
        preparedStatement.setDouble(3, companyUser.getBalance());
        preparedStatement.setInt(4, companyID);
        preparedStatement.executeUpdate();
    }

    private void addCompanytoCompanyTable(Company companyUser) throws SQLException {
        String sql = "INSERT INTO Companies(companyName, country, CVR ) VALUES(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,companyUser.getCompanyName());
        preparedStatement.setString(2, companyUser.getCountry());
        preparedStatement.setInt(3, companyUser.getCVR());
        preparedStatement.executeUpdate();
    }

    private void addPersonToUserTable(Person personUser, int PersonID) throws SQLException {


       String sql = "INSERT INTO Users(username, phonenumber, Balance,company_id,person_id) VALUES(?, ?, ?, ?, ?)";
       PreparedStatement preparedStatement = connection.prepareStatement(sql);
       preparedStatement.setString(1,personUser.getUsername());
       preparedStatement.setInt(2, personUser.getPhonenumber());
       preparedStatement.setDouble(3, personUser.getBalance());
       preparedStatement.setInt(5, PersonID);
       preparedStatement.executeUpdate();
   }

    private void addPersontoPersonTable(Person personUser) throws SQLException {
       String sql = "INSERT INTO Persons(firstname, lastname) VALUES(?, ?)";
       PreparedStatement preparedStatement = connection.prepareStatement(sql);
       preparedStatement.setString(1,personUser.getFirstName());
       preparedStatement.setString(2, personUser.getLastName());
       preparedStatement.executeUpdate();
   }

    private int getNewestPersonID() {
        String sql = "SELECT id FROM Persons ORDER BY id DESC LIMIT 1";
        return getID(sql);
    }

    private int getNewestCompanyID() {
        String sql = "SELECT id FROM Companies ORDER BY id DESC LIMIT 1";
        return getID(sql);
    }

    private int getID(String sql) {
        int personID = 0;
        try (
                Statement stmt  = this.connection.createStatement();
                ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                personID =  rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return personID;
    }

}
