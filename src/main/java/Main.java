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

public class Main {
    static Wallet dimasWallet;
    static Wallet ilyasWallet;
    public static void main(String[] args) {
        dimasWallet = createNewWallet("Dima");
        ilyasWallet = createNewWallet("Ilya");
        //todo enum для валют
        Currency dollar = addNewCurrency(dimasWallet, "dollar");
        Currency ruble = addNewCurrency(dimasWallet, "ruble");
        dimasWallet.deposit(1);
        System.out.println(dimasWallet);
        dimasWallet.deposit(100, ruble);
        System.out.println(dimasWallet);
//        dimasWallet.withdraw(50);
//        System.out.println(dimasWallet);
//        dimasWallet.withdraw(100, ruble);
//        System.out.println(dimasWallet);
//        addNewCurrency(ilyasWallet, "dollar");
//        System.out.println(ilyasWallet);
//        ilyasWallet.withdraw(100);
//        System.out.println(ilyasWallet);
//        ilyasWallet.withdraw(100, ruble);

        System.out.println("==================");
        setRate(dimasWallet, "dollar", ruble, 60);
        System.out.println(dollar.getRates());
        System.out.println(ruble.getRates());
        System.out.println("==================");
        dimasWallet.convert(ruble, dollar, 80);
        System.out.println(dimasWallet);
        dimasWallet.withdraw(2.1, dollar);
        System.out.println(dimasWallet);
        dimasWallet.showBalance();
        System.out.println(ilyasWallet);
        System.out.println("=========================");
        dimasWallet.showTotal();
        System.out.println("=========================");
        dimasWallet.showTotal(ruble);
        System.out.println("=========================");

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

    public static void setRate(Wallet wallet, String firstCurrency, Currency secondCurrency, double rate) {
        Currency currencyByName = wallet.findCurrencyByName(firstCurrency);
        currencyByName.setRates(secondCurrency, rate);
        secondCurrency.setRates(currencyByName, 1 / rate);
    }

//    public static Currency findCurrencyByName() {
//        return ;
//    }
//
//    public static Wallet findWalletByUserName() {
//
//    }
}
