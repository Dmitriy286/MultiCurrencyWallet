//-- Мультивалютный кошелек - выглядит просто --
//Реализовать консольное приложение
//
//Вход: операция из списка, где <> - обязательные параметры, [] - опциональные
//[currency] - если не указано в запросе использовать первую добавленную в кошелёк валюту
//Операции:
//add currency <currency>
//deposit <amount> [currency]
//withdraw <amount> [currency]
//set rate <currency 1> <currency 2>
//convert <amount> <currency 1> to <currency 2>
//show balance
//show total [in currency]
//
//Пример:
//add currency ruble
//deposit 100
//add currency dollar
//set rate dollar ruble 1:60
//deposit 1 dollar
//convert 80 ruble to dollar
//withdraw 2.1 dollar
//
//show balance
//20 ruble
//0.23 dollar
//
//show total
//34 ruble
//
//show total in dollar
//0.66 dollar

import models.Currency;
import models.Wallet;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    protected static Application application;
    private static Scanner scanner;

    public static void main(String[] args) {
        init();
        run();
    }

    public static void init() {
        scanner = new Scanner(System.in);
        application = new Application(scanner);
    }

    public static void run() {

    }

    public static void terminate() {
        application = null;
        init();
        run();
    }
}
