package persistence;

import model.User;

import java.sql.*;

import static persistence.DatabaseConnector.*;

public class TransactionHandler {


    public void transferMoneyFromUserToUser(User userFrom, User userTo, double amount) throws SQLException {

        CONNECTOR.connect();

        var userFromBalance = getBalanceFromDB(userFrom.getUsername());
        var userToBalance = getBalanceFromDB(userTo.getUsername());

        var userFromBalanceAfter = userFromBalance - amount;
        var userToBalanceAfter = userToBalance + amount;


        CONNECTOR.beginTransaction();
        updateBalances(userFromBalanceAfter, userToBalanceAfter, userFrom.getUsername(), userTo.getUsername());
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
}
