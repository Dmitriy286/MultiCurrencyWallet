import models.Currency;
import models.Wallet;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Application {
    Wallet wallet;
    Scanner scanner;

    public Application(Scanner scanner) {
        this.scanner = scanner;
        System.out.println("Welcome to Multi-currency Wallet");
        System.out.println("If you have a wallet already enter your username. " + "\n" +
                "Else enter new username to create a new wallet");
        String userName = scanner.nextLine();
        this.createNewWallet(userName);
        this.chooseUserStep();
    }




    private void chooseUserStep() {
        System.out.println("Choose your action, enter the appropriate number: ");
        System.out.println("If you want to add new currency, enter 1");
        System.out.println("If you want to add new deposit, enter 2");
        System.out.println("If you want to withdraw some amount, enter 3");
        System.out.println("If you want to set rate for two currencies, enter 4");
        System.out.println("If you want to convert any amount of any currency to other currency, enter 5");
        System.out.println("To look the whole balance of all currencies, enter 6");
        System.out.println("To look total amount in any currency, enter 7");

        int choiceNumber = scan();

        switch (choiceNumber) {
            case (1):
                System.out.println("Enter currency name");
                scanner.nextLine();
                String currencyName = scanner.nextLine();
                this.addNewCurrency(wallet, currencyName);
                break;

            case (2):
                boolean flag = false;
                //todo вынести в отдельный метод
                while (!flag) {
                System.out.println("Enter currency name. If you will not enter the name, " + "\n" +
                        "the amount will be added to the first currency in your wallet. In this case just press Enter");
                scanner.nextLine();
                String depositCurrencyName = scanner.nextLine();
                if (depositCurrencyName != "") {
                    if (wallet.getCurrenciesAmountMap().keySet()
                            .stream()
                            .map(e -> e.getName())
                            .toList()
                            .contains(depositCurrencyName)) {
                        flag = true;
                    } else {
                        System.out.println("There is no such currency in the wallet");
                        continue;
                    }
                }

                System.out.println("Enter the amount");
                double depositAmount = scanner.nextDouble();
                if (!Objects.equals(depositCurrencyName, "")) {
                    Currency currencyByName = wallet.findCurrencyByName(depositCurrencyName);
                    wallet.deposit(depositAmount, currencyByName);
                } else {
                    wallet.deposit(depositAmount);
                }
                flag = true;
                }
                break;

            case (3):

                break;

            case (4):

                break;

            case (5):

                break;

            case (6):
                wallet.showBalance();
                break;

            case (7):

                break;

            default:

                break;
        }

        System.out.println("If you want to quit, enter 0");
        System.out.println("If you want to go to main menu, enter any other number");


        int lastChoiceNumber = scanner.nextInt();

        if (lastChoiceNumber == 0) {
            Main.terminate();
        } else {
            this.chooseUserStep();
        }

    }

    public int scan() {
        int result;
        while (true) {
            try {
                result = scanner.nextInt();
                break;
            } catch (InputMismatchException exception) {
                System.out.println("You have to enter a number, please retry:");
                scanner.nextLine();
            }
        }
        return result;
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
                        .filter(e -> Objects.equals(e.getUserName(), userName))
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
