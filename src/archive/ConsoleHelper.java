package archive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper { //класс для работы с консолью
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){ //вывести сообщение на консоль
        System.out.println(message);
    }

    public static String readString() throws IOException { //считать строку из консоли
        return reader.readLine();
    }

    public static int readInt() throws IOException { //считать число из консоли
        return Integer.parseInt(reader.readLine());
    }
}
