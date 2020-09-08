package client;

import java.io.*;

public class History {
    private static FileOutputStream output;
    private static BufferedReader input;

    public static String getLast100HistoryLines(String login) {
        // определяем путь к файлу для текущего пользователя
        File historyFile = new File(getHistoryFilePath(login));
        // если файл существует, приступаем к чтению
        if (historyFile.exists()) {
            // StringBuilder позволит легко добавить строки и вернуть результат с помощью toString
            StringBuilder stringBuilder = new StringBuilder();
            try {
                // создаем входной (символьный) поток для чтения из файла
                input = new BufferedReader(new FileReader(historyFile));
                // записываем в списочный массив до 100 строк из файла
                for (int i = 0; i < 100; i++) {
                    String newline;
                    if ((newline = input.readLine()) != null)
                        // добавляем строку + разделитель строк
                        stringBuilder.append(newline).append(System.lineSeparator());
                    else
                        break;
                }
                // возвращаем историю чата одним строковым значением (сохранящим разделители строк)
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // если файл не существует или произошла ошибка, возвращаем пустую строку
        return "";
    }

    public static String getHistoryFilePath(String login) {
//        return "src\\main\\java\\client\\history\\history_" + login + ".txt";
        return "history/history_" + login + ".txt";
    }

    public static void start(String login) {
        try {
            // определяем путь к файлу для текущего пользователя
            File historyFile = new File(getHistoryFilePath(login));
            // если файла не существует, создаем его
            if(!historyFile.exists()) historyFile.createNewFile();
            // создаем новый выходной (для разнообразия - битовый) поток, в который будем писать
            // с помощью метода writeLine()
            output = new FileOutputStream(historyFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        // закрываем потоки, если они были открыты
        try {
            if (output != null) output.close();
            if (input != null) input.close();
        } catch (IOException e) {
            System.out.println("Какой-то из потоков не был открыт");
        }
    }

    public static void writeLine(String line) {
        try {
            output.write(line.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
