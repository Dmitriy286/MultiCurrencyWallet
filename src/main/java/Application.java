import models.Currency;
import models.Wallet;

import java.util.List;
import java.util.stream.Collectors;

public class Application {
    Wallet wallet;

    public Application() {
        System.out.println("Welcome to Multi-currency Wallet");
        System.out.println("If you have a wallet already enter your username. " + "\n" +
                "Else enter new username to create a new wallet");
    }




    public Currency addNewCurrency(Wallet wallet, String name) {
        Currency currency = new Currency(name);
        wallet.addCurrency(currency);
        System.out.println("Currency " + currency + " has been added to your wallet");
        System.out.println(wallet);
        return currency;
    }

    public void createNewWallet(String userName) {
        if (Wallet.getWalletList() != null) {
            List<String> userList = Wallet.getWalletList()
                    .stream()
                    .map(Wallet::getUserName)
                    .toList();

            if (userList.contains(userName)) {
                wallet = Wallet.getWalletList()
                        .stream()
                        .filter(e -> e.getUserName() == userName)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Wallet with such username does not exist"));
            } else {
                wallet = new Wallet(userName);
                System.out.println("User " + userName + " has created a new wallet");
            }
        } else {
                wallet = new Wallet(userName);
                System.out.println("User " + userName + " has created a new wallet");
            }
        }

    public void setRate(Wallet wallet, String firstCurrency, Currency secondCurrency, double rate) {
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
