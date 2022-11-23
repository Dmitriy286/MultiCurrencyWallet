import models.Currency;
import models.Wallet;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;


/**
 * Application class with the main program's logic.
 */
public class Application {
    protected Wallet wallet;
    protected Scanner scanner;

    /**
     * Empty constructor for testing purposes.
     */
    public Application() {

    }

    public Application(Scanner scanner) throws InterruptedException {
        this.scanner = scanner;
        System.out.println("==============================");

        System.out.println("Welcome to Multi-currency Wallet");
        System.out.println("Enter your username. If there is a wallet with such username, " +
                        "your wallet will be provided. " +
                         "\n" + "In other case new wallet will be created");
        String userName = this.scanner.nextLine();
        this.wallet = Application.createNewWallet(userName);
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
        System.out.println("To close your wallet, enter 8");
        System.out.println("To quit program, enter 0");

        System.out.println("==============================");

        int choiceNumber = this.scanInt();

        switch (choiceNumber) {
            case (1) -> {
                boolean isCreated = false;
                int caseCount = 0;
                while (!isCreated) {
                    caseCount += 1;
                    System.out.println("Enter currency name:");
                    if (caseCount == 1) {
                        this.scanner.nextLine();
                    }
                    String currencyName = this.scanner.nextLine();
                    if (this.checkIfCurrencyExistsByName(currencyName)) {
                        System.out.println("Such currency has already been added to your wallet. Try other currency name");
                    } else if (Objects.equals(currencyName, "")){
                        System.out.println("Currency name can't be empty string. Try again");
                    } else {
                        isCreated = true;
                    }
                }
            }
            case (2) -> {
                String depositCurrencyName = this.chooseCurrency("added to");
                System.out.println("Enter the amount:");
                double depositAmount = this.scanDouble();
                if (!Objects.equals(depositCurrencyName, "")) {
                    Currency currencyByName = wallet.findCurrencyByName(depositCurrencyName);
                    this.wallet.deposit(currencyByName, depositAmount);
                } else {
                    this.wallet.deposit(depositAmount);
                }
            }
            case (3) -> {
                String withdrawCurrencyName = this.chooseCurrency("subtracted from");
                System.out.println("Enter the amount:");
                double withdrawAmount = this.scanDouble();
                if (!Objects.equals(withdrawCurrencyName, "")) {
                    Currency currencyByName = this.wallet.findCurrencyByName(withdrawCurrencyName);
                    this.wallet.withdraw(currencyByName, withdrawAmount);
                } else {
                    this.wallet.withdraw(withdrawAmount);
                }
            }
            case (4) -> this.setRate();
            case (5) -> this.convert();
            case (6) -> wallet.showBalance();
            case (7) -> {
                String currencyForTotalBalance = this.chooseCurrency("shown in");
                if (!Objects.equals(currencyForTotalBalance, "")) {
                    Currency currencyByName = this.wallet.findCurrencyByName(currencyForTotalBalance);
                    this.wallet.showTotal(currencyByName);
                } else {
                    this.wallet.showTotal();
                }
            }
            case (8) -> Main.terminate();
            case (0) -> {
                Main.terminateAndClose();
                return;
            }
            default -> this.chooseUserStep();
        }
        System.out.println("==============================");
        System.out.println("Your wallet:");
        System.out.println(wallet);
        System.out.println("==============================");
        System.out.println("If you want to close wallet, enter 8");
        System.out.println("If you want to quit program, enter 0");
        System.out.println("If you want to go to main menu, enter any other number");
        System.out.println("==============================");

        int lastChoiceNumber = scanInt();

        if (lastChoiceNumber == 8) {
            Main.terminate();
        } else if (lastChoiceNumber == 0) {
            Main.terminateAndClose();
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
                this.scanner.nextLine();
            }
            depositCurrencyName = this.scanner.nextLine();
            if (!Objects.equals(depositCurrencyName, "")) {
                if (this.checkIfCurrencyExistsByName(depositCurrencyName)) {
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
                result = this.scanner.nextInt();
                break;
            } catch (InputMismatchException exception) {
                System.out.println("You have to enter a number, please retry:");
                this.scanner.nextLine();
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
                result = this.scanner.nextDouble();
                break;
            } catch (InputMismatchException exception) {
                System.out.println("You have to enter a number, please retry:");
                this.scanner.nextLine();
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
        this.scanner.nextLine();
        String firstString = this.stringScannerCycle();
        System.out.println("Enter second currency name:");
        String secondString = this.stringScannerCycle();
        stringArray[0] = firstString;
        stringArray[1] = secondString;
        return stringArray;
    }

    /**
     * Scans input from keyboard until user will enter the existing currency name (according to current wallet)
     * @return String currency name
     */
    private String stringScannerCycle() {
        String scannedString;
        while (true) {
            scannedString = this.scanner.nextLine();
            if (this.checkIfCurrencyExistsByName(scannedString)) {
                break;
            } else {
                System.out.println("There is no such currency in the wallet. Try again:");
            }
        }
        return scannedString;
    }

    /**
     * Checks if currency with such username has been already added to the current wallet.
     * @param currencyName String name of currency, which user enters from the keyboard
     * @return boolean value, true if such currency has been already added to the current wallet
     */
    private boolean checkIfCurrencyExistsByName(String currencyName) {
        boolean contains = this.wallet.getCurrenciesAmountMap().keySet()
                .stream()
                .map(Currency::getName)
                .toList()
                .contains(currencyName);

        return contains;
    }

    /**
     * Creates instance of a new Wallet. If such userName is already registered assigns its wallet instance
     * to "wallet" variable.
     * @param userName String username. Algorithm checks if such username already exists in the system.
     */
    public static Wallet createNewWallet(String userName) {
        Wallet wallet;
        if (Wallet.getWalletList() != null && Wallet.checkIfWalletUserNameExists(userName)) {
            wallet = Wallet.getWalletList()
                        .stream()
                        .filter(e -> Objects.equals(e.getUserName(), userName))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Wallet with such username does not exist"));
        } else {
            wallet = new Wallet(userName);
            System.out.println("User " + userName + " has created a new wallet");
        }

        return wallet;
    }

    /**
     * Inits set rate method of the wallet instance between two currencies.
     * Automatically inits the same process for the second currency
     * in order to provide consistency.
     */
    public void setRate() {
        boolean flag = false;
        String[] currenciesNameArray = this.scanStrings();
        String firstCurrency = currenciesNameArray[0];
        String secondCurrency = currenciesNameArray[1];
        while (!flag) {
            System.out.println("Enter the amount, how much " + firstCurrency + " is worth in " + secondCurrency + ":");
            double rate = this.scanDouble();

            Currency currencyOne = wallet.findCurrencyByName(firstCurrency);
            Currency currencyTwo = wallet.findCurrencyByName(secondCurrency);

            if (currencyOne.setRates(currencyTwo, rate)) {
                currencyTwo.setRates(currencyOne, 1.0 / rate);
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

        Currency currencyOne = this.wallet.findCurrencyByName(firstCurrency);
        Currency currencyTwo = this.wallet.findCurrencyByName(secondCurrency);
        this.wallet.convert(currencyOne, currencyTwo, convertAmount);
    }

}
