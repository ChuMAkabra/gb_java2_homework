package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;

public class Start {
    private static final Logger logger = Logger.getLogger("");
    private static Handler fileHandler;

    public static void main(String[] args) {
        try {
// TODO попробовать прикрутить log4j2 фреймворк. Dependency пока закомментированы
            LogManager manager = LogManager.getLogManager();
            manager.readConfiguration(new FileInputStream("logging.properties"));

            fileHandler = new FileHandler("critical_%g.log", 5 * 1024,
                    5, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.SEVERE);
            // удаляем вывод в консоль
            logger.removeHandler(Logger.getLogger("").getHandlers()[0]);
            // добавляем вывод в файл
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Server();
    }
}
