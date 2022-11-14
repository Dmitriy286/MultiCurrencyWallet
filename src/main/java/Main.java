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

public class Main {
    public static void main(String[] args) {
        Wallet dimasWallet = createNewWallet("Dima");
        Wallet ilyasWallet = createNewWallet("Ilya");
        //todo enum для валют
        addNewCurrency(dimasWallet, "dollar");
        Currency ruble = addNewCurrency(dimasWallet, "ruble");
        dimasWallet.deposit(100);
        System.out.println(dimasWallet);
        dimasWallet.deposit(200, ruble);
        System.out.println(dimasWallet);
    }

    public static Currency addNewCurrency(Wallet wallet, String name) {
        Currency currency = new Currency(name);
        wallet.addCurrency(currency);
        System.out.println("Currency " + currency + " has been added to your wallet");
        System.out.println(wallet);
        return currency;
    }

    public static Wallet createNewWallet(String userName) {
        Wallet wallet = new Wallet(userName);
        System.out.println("User " + userName + " has created a new wallet");
        System.out.println(wallet);
        System.out.println(Wallet.getWalletList());

        return wallet;
    }
}
