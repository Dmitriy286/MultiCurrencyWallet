import models.Currency;
import models.Wallet;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


/**
 * Application class with the main program's logic.
 */
public class Application {
    Wallet wallet;
    Scanner scanner;

    public Application(Scanner scanner) throws InterruptedException {
        this.scanner = scanner;
        System.out.println("==============================");

        System.out.println("Welcome to Multi-currency Wallet");
        System.out.println("Enter your username. If there is a wallet with such username, " +
                        "your wallet will be provided. " +
                         "\n" + "In other case new wallet will be created");
        String userName = scanner.nextLine();
        this.createNewWallet(userName);
        this.chooseUserStep();
    }


    /**
     * Provides main menu in the console. Consists of switch-case conditions to choose next step.
     * @throws InterruptedException because of Time.sleep() method for app loading process imitation
     */
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
        System.out.println("To quit, enter 0");

        System.out.println("==============================");

        int choiceNumber = scanInt();

        switch (choiceNumber) {
            case (1) -> {
                boolean isCreated = false;
                int caseCount = 0;
                while (!isCreated) {
                    caseCount += 1;
                    System.out.println("Enter currency name:");
                    if (caseCount == 1) {
                        scanner.nextLine();
                    }
                    String currencyName = scanner.nextLine();
                    if (!wallet.addCurrency(currencyName)) {
                        System.out.println("Such currency has already been added to your wallet. Try other currency");
                    } else {
                        isCreated = true;
                    }
                }
            }
            case (2) -> {
                String depositCurrencyName = chooseCurrency("added to");
                System.out.println("Enter the amount:");
                double depositAmount = scanDouble();
                if (!Objects.equals(depositCurrencyName, "")) {
                    Currency currencyByName = wallet.findCurrencyByName(depositCurrencyName);
                    wallet.deposit(currencyByName, depositAmount);
                } else {
                    wallet.deposit(depositAmount);
                }
            }
            case (3) -> {
                String withdrawCurrencyName = chooseCurrency("subtracted from");
                System.out.println("Enter the amount:");
                double withdrawAmount = scanDouble();
                if (!Objects.equals(withdrawCurrencyName, "")) {
                    Currency currencyByName = wallet.findCurrencyByName(withdrawCurrencyName);
                    wallet.withdraw(currencyByName, withdrawAmount);
                } else {
                    wallet.withdraw(withdrawAmount);
                }
            }
            case (4) -> setRate();
            case (5) -> convert();
            case (6) -> wallet.showBalance();
            case (7) -> {
                String currencyForTotalBalance = chooseCurrency("shown in");
                if (!Objects.equals(currencyForTotalBalance, "")) {
                    Currency currencyByName = wallet.findCurrencyByName(currencyForTotalBalance);
                    wallet.showTotal(currencyByName);
                } else {
                    wallet.showTotal();
                }
            }
            case (0) -> Main.terminate();
            default -> this.chooseUserStep();
        }
        System.out.println("==============================");
        System.out.println("Your wallet:");
        System.out.println(wallet);
        System.out.println("==============================");
        System.out.println("If you want to quit, enter 0");
        System.out.println("If you want to go to main menu, enter any other number");
        System.out.println("==============================");

        int lastChoiceNumber = scanInt();

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
                        .map(Currency::getName)
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

    /**
     * Parses int number from scanner.
     * @return int number
     */
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

    /**
     * Parses double number from scanner.
     * @return double number
     */
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

    /**
     * Parses Strings from scanner.
     * @return array of two Strings
     */
    private String[] scanStrings() {
        String[] stringArray = new String[2];
        System.out.println("Enter first currency name:");
        scanner.nextLine();
        String firstString = scanner.nextLine();
        System.out.println("Enter second currency name:");
        String secondString = scanner.nextLine();
        stringArray[0] = firstString;
        stringArray[1] = secondString;
        return stringArray;
    }

    /**
     * Creates instance of a new Wallet. If such userName is already registered assigns its wallet instance
     * to "wallet" variable.
     * @param userName String username. Algorithm checks if such username already exists in the system.
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
     * Inits set rate method of the wallet instance between two currencies.
     * Automatically inits the same process for the second currency
     * in order to provide consistency.
     */
    public void setRate() {
        boolean flag = false;
        String[] currenciesNameArray = scanStrings();
        String firstCurrency = currenciesNameArray[0];
        String secondCurrency = currenciesNameArray[1];
        while (!flag) {
            System.out.println("Enter the amount, how much ruble is worth in dollar:");
            double rate = scanDouble();

            Currency currencyOne = wallet.findCurrencyByName(firstCurrency);
            Currency currencyTwo = wallet.findCurrencyByName(secondCurrency);

            if (currencyOne.setRates(currencyTwo, rate)) {
                currencyTwo.setRates(currencyOne, 1 / rate);
                flag = true;
            } else {
                System.out.println("Amount has to be more than zero. Try again");
            }
        }

    }

    /**
     * Inits convert method of the wallet instance between two currencies.
     */
    private void convert() {
        String[] currenciesNameArray = scanStrings();
        String firstCurrency = currenciesNameArray[0];
        String secondCurrency = currenciesNameArray[1];
        System.out.println("Enter the amount to convert:");
        double convertAmount = scanDouble();

        Currency currencyOne = wallet.findCurrencyByName(firstCurrency);
        Currency currencyTwo = wallet.findCurrencyByName(secondCurrency);
        wallet.convert(currencyOne, currencyTwo, convertAmount);
    }

}
