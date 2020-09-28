package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.util.logging.*;

public class Server {
    private List<ClientHandler> clients;
    private AuthService authService;
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private static final Handler handler = new ConsoleHandler();

    public AuthService getAuthService() {
        return authService;
    }

    public Server() {
        handler.setLevel(Level.CONFIG);
        logger.addHandler(handler);

        clients = new Vector<>();
        authService = new DBAuthService(); // replaces SimpleAuthService();

        ServerSocket server = null;
        Socket socket;

        final int PORT = 8189;


        try {
            server = new ServerSocket(PORT);
            logger.config("Сервер запущен!");
            logger.fine("Тестовое сообщение, не попадающее никуда");
            logger.severe("Тестовое сообщение для записи в файл");

            while (true) {
                socket = server.accept();
                // записываем в лог двумя разными методами
                logger.log(Level.CONFIG, "Local Socket Address: " +
                        socket.getLocalSocketAddress());
                logger.config("Remote Socket Address: " +
                        socket.getRemoteSocketAddress());
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // передаем объект Throwable в лог
            logger.log(Level.SEVERE, "Ошибка подключения", e);
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.log(Level.SEVERE, "Ошибка при закрытии соединения", e);
            }
        }
    }

    void broadcastMsg(ClientHandler sender, String msg) {
        String message = String.format("%s : %s", sender.getNick(), msg);

        for (ClientHandler client : clients) {
            client.sendMsg(message);
        }
    }

    void privateMsg(ClientHandler sender, String receiver, String msg) {
        String message = String.format("[%s] private [%s] : %s", sender.getNick(), receiver, msg);

        for (ClientHandler c : clients) {
            if(c.getNick().equals(receiver)){
                c.sendMsg(message);
                sender.sendMsg(message);
                return;
            }
        }
        sender.sendMsg(String.format("Client %s not found", receiver));
    }


    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }

    public boolean isLoginAuthorized(String login){
        for (ClientHandler c : clients) {
            if(c.getLogin().equals(login)){
                return true;
            }
        }
        return false;
    }

    void broadcastClientList() {
        StringBuilder sb = new StringBuilder("/clientlist ");

        for (ClientHandler c : clients) {
            sb.append(c.getNick()).append(" ");
        }

        String msg = sb.toString();

        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }

}
