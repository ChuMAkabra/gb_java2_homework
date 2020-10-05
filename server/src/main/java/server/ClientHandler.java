package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler {
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());
    private static final Handler handler = new ConsoleHandler();

    Server server;
    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;

    private String nick;
    private String login;

    public ClientHandler(Server server, Socket socket) {
        try {
            handler.setLevel(Level.INFO);
            logger.addHandler(handler);
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());


            new Thread(() -> {
                try {
                    socket.setSoTimeout(120000);

                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/auth ")) {
                            String[] token = str.split("\\s");
                            if (token.length < 3) {
                                continue;
                            }
                            String newNick = server
                                    .getAuthService()
                                    .getNicknameByLoginAndPassword(token[1], token[2]);
                            login = token[1];
                            if (newNick != null) {
                                if (!server.isLoginAuthorized(login)) {
                                    sendMsg("/authok " + newNick);
                                    nick = newNick;
                                    server.subscribe(this);
                                    logger.info("Клиент " + nick + " подключился");
                                    socket.setSoTimeout(0);
                                    break;
                                } else {
                                    sendMsg("С этим логином уже авторизовались");
                                    logger.info("С этим логином уже авторизовались");
                                }
                            } else {
                                sendMsg("Неверный логин / пароль");
                                logger.info("Неверный логин / пароль");
                            }
                        }

                        if (str.startsWith("/reg ")) {
                            String[] token = str.split("\\s");
                            if (token.length < 4) {
                                continue;
                            }
                            boolean b = server.getAuthService()
                                    .registration(token[1], token[2], token[3]);
                            if (b) {
                                sendMsg("/regresult ok");
                                logger.info("Регистрация прошла успешно");
                            } else {
                                sendMsg("/regresult failed");
                                logger.info("Ошибка при регистрации");
                            }
                        }
                    }


                    //цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/")) {
                            if (str.equals("/end")) {
                                out.writeUTF("/end");
                                break;
                            }

                            if (str.startsWith("/w ")) {
                                String[] token = str.split("\\s", 3);
                                if (token.length < 3) continue;

                                server.privateMsg(this, token[1], token[2]);
                            }

                            if (str.startsWith("/chnick ")) {
                                String[] token = str.split("\\s", 2);
                                if (token[1].equals("")) {
                                    sendMsg("Ник не введен");
                                    logger.info("Ник не введен");
                                    continue;
                                }
                                if (token[1].contains(" ")) {
                                    sendMsg("Ник не может содержать пробелов");
                                    logger.info("Ник не может содержать пробелов");
                                    continue;
                                }
                                if (server.getAuthService().changeNickname(this.nick, token[1])) {
                                    sendMsg("/newnick " + token[1]);
                                    sendMsg("Ник успешно заменен на " + token[1]);
                                    logger.info("Ник успешно заменен на " + token[1]);
                                    this.nick = token[1];
                                    server.broadcastClientList();

                                } else {
                                    sendMsg("Что-то пошло не так...");
                                    logger.warning("Что-то пошло не так..." + token[1]);
                                }
                            }

                        } else {
                            server.broadcastMsg(this, str);
                        }
                    }
                } catch (SocketTimeoutException e) {
                    sendMsg("/end");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    logger.info("Клиент отключился");
                    server.unsubscribe(this);
                    try {
                        in.close();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void sendMsg(String str) {
        try {
            out.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }

    public String getLogin() {
        return login;
    }
}
