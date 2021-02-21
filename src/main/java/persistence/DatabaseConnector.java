package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum DatabaseConnector {

    CONNECTOR;

    Connection connection;

    public void connect() {

        this.connection = null;

        try {
            // db parameters
            String url = "jdbc:sqlite:db/database.sqlite";
            // create a connection to the database
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void beginTransaction() throws SQLException {

        String begin = "BEGIN TRANSACTION";
        PreparedStatement preparedStatement = connection.prepareStatement(begin);
        preparedStatement.executeUpdate();
    }

    void commit() throws SQLException {

        String commit = "COMMIT";
        PreparedStatement preparedStatement = connection.prepareStatement(commit);
        preparedStatement.executeUpdate();
    }


    void closeConnection() throws SQLException {

        assert connection != null;
        connection.close();
        connection = null;
    }

    public Connection getConnection() {

        return connection;
    }
}