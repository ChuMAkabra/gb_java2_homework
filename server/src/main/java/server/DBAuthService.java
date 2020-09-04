package server;

import org.sqlite.JDBC;

import java.sql.*;

public class DBAuthService implements AuthService {
    private static Connection connection;
    private static ResultSet rs;

    public DBAuthService() {
        try {
            connect();
            System.out.println("Подключились к базе!");
        } catch (Exception e) {
            e.printStackTrace();
            disconnect();
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
            Statement statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            PreparedStatement psRegistration;
            if (recordFound(login, password))
                return false;
            else
                psRegistration = connection.prepareStatement("INSERT INTO users (login, password, nick) VALUES (?, ?, ?)");
                psRegistration.setString(1, login);
                psRegistration.setString(2, password);
                psRegistration.setString(3, nickname);
                psRegistration.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return true;
    }

    private boolean recordFound(String login, String password) throws SQLException {
        connect();
        PreparedStatement psRecordFound = connection.prepareStatement("SELECT nick FROM users WHERE login = ? AND password = ?");
        psRecordFound.setString(1, login);
        psRecordFound.setString(2, password);
        rs = psRecordFound.executeQuery();
        return rs.next();
    }

    @Override
    public boolean changeNickname(String oldNickname, String newNickname) {
        connect();
        try {
            PreparedStatement psChangeNickname = connection.prepareStatement("UPDATE users SET nick = ? where nick = ?");
            psChangeNickname.setString(1, newNickname);
            psChangeNickname.setString(2, oldNickname);
            psChangeNickname.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return false;
    }
}
