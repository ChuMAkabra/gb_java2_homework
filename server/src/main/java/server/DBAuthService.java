package server;

import org.sqlite.JDBC;

import java.sql.*;

public class DBAuthService implements AuthService {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ResultSet rs;

    public DBAuthService() {
        try {
            connect();
            System.out.println("Подключились к базе!");
        } catch (Exception e) {
            e.printStackTrace();
            disconnect();
//        } finally {
//
        }

    }

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(JDBC.PREFIX + "main.db"); //jdbc.sqlite:main.db
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        statement.executeUpdate("INSERT INTO students (name, score) VALUES ('guy2', '70')");
    }

    private void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        try {
            if (recordFound(login, password))
                return rs.getString("nick");
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname){
        try {
            if (recordFound(login, password))
                return false;
            else
                preparedStatement = connection.prepareStatement("INSERT INTO users (login, password, nick) VALUES (?, ?, ?)");
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, nickname);
                preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean recordFound(String login, String password) throws SQLException {
        connect();
        preparedStatement = connection.prepareStatement("SELECT nick FROM users WHERE login = ? AND password = ?");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        rs = preparedStatement.executeQuery();
        return rs.next();
    }
}
