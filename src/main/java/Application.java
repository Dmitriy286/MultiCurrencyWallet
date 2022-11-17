import models.Currency;
import models.Wallet;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Application {
    Wallet wallet;
    Scanner scanner;

    public Application(Scanner scanner) throws InterruptedException {
        this.scanner = scanner;
        System.out.println("==============================");

        System.out.println("Welcome to Multi-currency Wallet");
        System.out.println("If you have a wallet already enter your username. " + "\n" +
                "Else enter new username to create a new wallet:");
        String userName = scanner.nextLine();
        this.createNewWallet(userName);
        this.chooseUserStep();
    }




    private void chooseUserStep() throws InterruptedException {
        System.out.println("==============================");

        System.out.println("Choose your action, enter the appropriate number: ");
        System.out.println("If you want to add new currency, enter 1");
        System.out.println("If you want to add new deposit, enter 2");
        System.out.println("If you want to withdraw some amount, enter 3");
        System.out.println("If you want to set rate for two currencies, enter 4");
        System.out.println("If you want to convert any amount of any currency to other currency, enter 5");
        System.out.println("To view the whole balance of all currencies, enter 6");
        System.out.println("To view total amount in any currency, enter 7");

        int choiceNumber = scanInt();

        switch (choiceNumber) {
            case (1):
                System.out.println("Enter currency name");
                scanner.nextLine();
                String currencyName = scanner.nextLine();
                this.addNewCurrency(wallet, currencyName);
                break;

            case (2):
                String depositCurrencyName = chooseCurrency("added to");
                System.out.println("Enter the amount");
                double depositAmount = scanDouble();
                if (!Objects.equals(depositCurrencyName, "")) {
                    Currency currencyByName = wallet.findCurrencyByName(depositCurrencyName);
                    wallet.deposit(depositAmount, currencyByName);
                } else {
                    wallet.deposit(depositAmount);
                }
                break;

            case (3):
                String withdrawCurrencyName = chooseCurrency("subtracted from");
                System.out.println("Enter the amount");
                double withdrawAmount = scanDouble();
                if (!Objects.equals(withdrawCurrencyName, "")) {
                    Currency currencyByName = wallet.findCurrencyByName(withdrawCurrencyName);
                    wallet.withdraw(withdrawAmount, currencyByName);
                } else {
                    wallet.withdraw(withdrawAmount);
                }
                break;

            case (4):
                System.out.println("Enter first currency name:");
                scanner.nextLine();
                String firstCurrencyName = scanner.nextLine();
                System.out.println("Enter second currency name:");
                //scanner.nextLine();//todo может быть лишний? или наоборот не хватает
                String secondCurrencyName = scanner.nextLine();
                System.out.println("firstCurrencyName:");
                System.out.println(firstCurrencyName);
                System.out.println("secondCurrencyName:");
                System.out.println(secondCurrencyName);
                System.out.println("Enter the amount of how much ruble is worth in dollar:");
                double rate = scanDouble();
                setRate(wallet, firstCurrencyName, secondCurrencyName, rate);//todo wallet лишний
                break;

            case (5):
                System.out.println("Enter first currency name:");
                scanner.nextLine();
                String firstCurrencyNameForConvert = scanner.nextLine();
                System.out.println("Enter second currency name:");
                //scanner.nextLine();//todo может быть лишний? или наоборот не хватает
                String secondCurrencyNameForConvert = scanner.nextLine();
                System.out.println("firstCurrencyNameForConvert:");
                System.out.println(firstCurrencyNameForConvert);
                System.out.println("secondCurrencyNameForConvert:");
                System.out.println(secondCurrencyNameForConvert);
                System.out.println("Enter the amount to convert:");
                double convertAmount = scanDouble();
                Currency currencyOne = wallet.findCurrencyByName(firstCurrencyNameForConvert);
                Currency currencyTwo = wallet.findCurrencyByName(secondCurrencyNameForConvert);;
                wallet.convert(currencyOne, currencyTwo, convertAmount);
                break;

            case (6):
                wallet.showBalance();
                break;

            case (7):

                break;

            default:

                break;
        }
        System.out.println("==============================");
        System.out.println("If you want to quit, enter 0");
        System.out.println("If you want to go to main menu, enter any other number");
        System.out.println("==============================");



        int lastChoiceNumber = scanner.nextInt();

        if (lastChoiceNumber == 0) {
            Main.terminate();
        } else {
            this.chooseUserStep();
        }

    }

    /**
     * Defines currency which will be passed to Wallet class methods.
     * @param chosenAction String for description of method action: "added to" or "subtracted from".
     * @return name of defined currency
     */
    private String chooseCurrency(String chosenAction) {
        boolean flag = false;
        String depositCurrencyName = "";
        int count = 0;
        while (!flag) {
            count += 1;
            System.out.println("Enter currency name. If you will not enter the name, " + "\n" +
                    "the amount will be " + chosenAction + " the first currency in your wallet. In this case just press Enter");
            if (count == 1) {
                scanner.nextLine();
            }
            depositCurrencyName = scanner.nextLine();
            if (!Objects.equals(depositCurrencyName, "")) {
                if (wallet.getCurrenciesAmountMap().keySet()
                        .stream()
                        .map(e -> e.getName())
                        .toList()
                        .contains(depositCurrencyName)) {
                    flag = true;
                } else {
                    System.out.println("There is no such currency in the wallet");
                    System.out.println("==============================");
                    continue;
                }
            }
            flag = true;
        }
        return depositCurrencyName;
    }

    public int scanInt() {
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

    public double scanDouble() {
        double result;
        while (true) {
            try {
                result = scanner.nextDouble();
                break;
            } catch (InputMismatchException exception) {
                System.out.println("You have to enter a number, please retry:");
                scanner.nextLine();
            }
        }
        return result;
    }

    private String scanString() {
        String result = "";
        return result;
    }


    public Currency addNewCurrency(Wallet wallet, String name) {
        Currency currency = new Currency(name);
        wallet.addCurrency(currency);
        System.out.println("Currency " + currency + " has been added to your wallet");
        System.out.println(wallet);
        return currency;
    }

    /**
     * Creates instance of a new Wallet.
     * @param userName
     */
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

    /**
     * Inits set rate between two currencies. Automatically inits the same process for the second currency
     * in order to provide consistency.
     * @param wallet
     * @param firstCurrency
     * @param secondCurrency
     * @param rate
     */
    public void setRate(Wallet wallet, String firstCurrency, String secondCurrency, double rate) {
        Currency currencyOne = wallet.findCurrencyByName(firstCurrency);
        Currency currencyTwo = wallet.findCurrencyByName(secondCurrency);
        currencyOne.setRates(currencyTwo, rate);
        currencyTwo.setRates(currencyOne, 1 / rate);
    }

}
