package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static persistence.DatabaseConnector.CONNECTOR;

public class TransactionHandler {


    public void transferMoneyFromUserToUser(String userNameFrom, String userNameTo, double amount) throws SQLException {

        CONNECTOR.connect();

        if (!userNameExists(userNameFrom) || !userNameExists(userNameTo)) return;

        var userFromId = getID(userNameFrom);
        var userToId = getID(userNameTo);

        var userFromBalance = getBalanceFromDB(userNameFrom);
        var userToBalance = getBalanceFromDB(userNameTo);

        var userFromBalanceAfter = userFromBalance - amount;
        var userToBalanceAfter = userToBalance + amount;


        CONNECTOR.beginTransaction();
        updateBalances(userFromBalanceAfter, userToBalanceAfter, userNameFrom, userNameTo);
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

    private boolean userNameExists(String userName) {

        return getID(userName) != 0;
    }
}
