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
    private static Application application;
    private static Scanner scanner;
    static Wallet dimasWallet;
    static Wallet ilyasWallet;
    public static void main(String[] args) {
        init();
        run();
    }

    public static void init() {
        application = new Application();
        scanner = new Scanner(System.in);

    }

    public static void run() {
        application.createNewWallet("Dima");
        application.createNewWallet("Ilya");
        dimasWallet = application.wallet;
        Currency dollar = application.addNewCurrency(dimasWallet, "dollar");
        Currency ruble = application.addNewCurrency(dimasWallet, "ruble");
        dimasWallet.deposit(1);
        System.out.println(dimasWallet);
        dimasWallet.deposit(100, ruble);
        System.out.println(dimasWallet);

        System.out.println("==================");
        application.setRate(dimasWallet, "dollar", ruble, 60);
        System.out.println(dollar.getRates());
        System.out.println(ruble.getRates());
        System.out.println("==================");
        dimasWallet.convert(ruble, dollar, 80);
        System.out.println(dimasWallet);
        dimasWallet.withdraw(2.1, dollar);
        System.out.println(dimasWallet);
        dimasWallet.showBalance();
        System.out.println("=========================");
        dimasWallet.showTotal();
        System.out.println("=========================");
        dimasWallet.showTotal(ruble);
        System.out.println("=========================");

    }

    public static void terminate() {
        application = null;
    }
}
