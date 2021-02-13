package persistence;

import model.User;

import java.sql.*;

import static persistence.DatabaseConnector.*;

public class TransactionHandler {


    public void transferMoneyFromUserToUser(User userFrom, User userTo, double amount) throws SQLException {

        CONNECTOR.connect();
        var userFromName = userFrom.getUsername();
        var userToName = userTo.getUsername();

        var userFromId = getID(userFromName);
        var userToId = getID(userToName);

        var userFromBalance = getBalanceFromDB(userFromName);
        var userToBalance = getBalanceFromDB(userToName);

        var userFromBalanceAfter = userFromBalance - amount;
        var userToBalanceAfter = userToBalance + amount;


        CONNECTOR.beginTransaction();
        updateBalances(userFromBalanceAfter, userToBalanceAfter, userFromName, userToName);
        insertIntoTransactionTable(userFromId, userToId, amount);
        CONNECTOR.commit();
        CONNECTOR.closeConnection();
    }

    private double getBalanceFromDB(String username) {
        var getBalanceQuery = "SELECT Balance FROM Users WHERE username = "+ "'" + username + "'";
        var balance = 0.;

        try (
                Statement stmt  = CONNECTOR.getConnection().createStatement();
                ResultSet rs    = stmt.executeQuery(getBalanceQuery)){

            while (rs.next()) {
                balance =  rs.getDouble("Balance");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return balance;
    }

    private void updateBalances(Double balanceFrom, Double balanceTO, String usernameFrom, String usernameTo) throws SQLException {

        var updateFromBalance = "UPDATE Users SET Balance = " + balanceFrom + " WHERE username = " + "'" + usernameFrom + "'";
        var updateToBalance = "UPDATE Users SET Balance = " + balanceTO + " WHERE username = " + "'" + usernameTo + "'";

        var preparedStatement = CONNECTOR.getConnection().prepareStatement(updateFromBalance);
        preparedStatement.executeUpdate();

        preparedStatement = CONNECTOR.getConnection().prepareStatement(updateToBalance);
        preparedStatement.executeUpdate();
    }

    private void insertIntoTransactionTable(int userFromId, int userToId, double amount) throws SQLException {

        var insertStatement = "INSERT INTO Transactions(from_user_id, to_user_id, amount) VALUES(?, ?, ?)";

        PreparedStatement preparedStatement = CONNECTOR.getConnection().prepareStatement(insertStatement);
        preparedStatement.setInt(1,userFromId);
        preparedStatement.setInt(2, userToId);
        preparedStatement.setDouble(3, amount);
        preparedStatement.executeUpdate();

    }

    private int getID(String username) {

        var selectStatement = "SELECT id FROM Users WHERE username = " + "'" + username + "'";
        var id = 0;

        try (
                var statement  = CONNECTOR.getConnection().createStatement();
                var resultSet    = statement.executeQuery(selectStatement)){

            while (resultSet.next()) {
                id =  resultSet.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
}
