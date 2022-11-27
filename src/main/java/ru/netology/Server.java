package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        String city = " ";
        int points = 0;

        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            System.out.println("Game is started");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ) {
                    // Отправляем клиенту "???" или последнее слово
                    out.println(String.format((city == " ") ? "???" : "Last city was: " + city));
                    // Принимаем от клиента в качестве ответа название города
                    String getAnswer = in.readLine();
                    // Выводим для наглядности принятое название города
                    System.out.println(getAnswer);
                    // Проверяем введено ли первое слово
                    // или
                    // начинается ли введенное слово на последнюю букву текущего слова
                    if(city == " " || getAnswer.toLowerCase().charAt(0) == city.toLowerCase().charAt(city.length() - 1)) {
                        city = getAnswer;
                        points++;
                        out.println("OK");
                    } else {
                        points = 0;
                        out.println("NOT OK");
                    }
                    out.println("Number of points is " + points);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}