package models;

import java.text.DecimalFormat;
import java.util.*;


/**
 * Wallet class. Contains methods to work with wallet and currencies in it.
 */
public class Wallet {
    private static int idCount = 1;
    /**
     * List with all wallets, created in runtime.
     */
    private static List<Wallet> walletList;
    private final int walletId;
    private final String userName;
    private Map<Currency, Double> currenciesAmountMap;

    public Wallet(String userName) {
        this.walletId = idCount;
        idCount++;
        if (walletList == null) {
            walletList = new ArrayList<>();
        }
        walletList.add(this);
        if (!Wallet.checkIfWalletUserNameExists(userName)) {
            this.userName = userName;
        } else {
            throw new IllegalArgumentException("Wallet with such username already exists");
        }
        this.currenciesAmountMap = new LinkedHashMap<>();
    }

    public static List<Wallet> getWalletList() {
        return walletList;
    }

    public int getWalletId() {
        return walletId;
    }

    public String getUserName() {
        return userName;
    }

    public Map<Currency, Double> getCurrenciesAmountMap() {
        return currenciesAmountMap;
    }

    /**
     * Checks if wallet with such username already exists.
     * @param userName String username, which user enters from the keyboard
     * @return boolean value, true if such username has been already used
     */
    public static boolean checkIfWalletUserNameExists(String userName) {
        boolean contains = Wallet.getWalletList()
                .stream()
                .map(Wallet::getUserName)
                .toList()
                .contains(userName);

        return contains;
    }

    /**
     * Adds new currency to the wallet.
     * @param currencyName String name of new currency
     */
    public boolean addCurrency(String currencyName) {
        boolean isCreated = false;
        Currency currency = new Currency(currencyName);
        if (!this.getCurrenciesAmountMap().containsKey(currency)) {
            this.getCurrenciesAmountMap().put(currency, 0.0);
            System.out.println("Currency " + currency + " has been added to your wallet");
            isCreated = true;
        }
        return isCreated;
    }

    /**
     * Looks for first currency added to the wallet.
     * @return instance of Currency
     */
    private Currency lookForDefaultCurrency() {
        Currency defaultCurrency = this.getCurrenciesAmountMap()
                .keySet()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There are no currencies in the current wallet"));

        return defaultCurrency;
    }

    /**
     * Increase amount of the first currency added to wallet.
     * @param amount double amount, which is being added to the wallet balance.
     */
    public void deposit(double amount) {
        Currency defaultCurrency = lookForDefaultCurrency();
        double currentSum = this.getCurrenciesAmountMap().get(defaultCurrency);
        this.getCurrenciesAmountMap().put(defaultCurrency, currentSum + amount);
    }

    /**
     *Increase amount of the currency passed to this method.
     * @param currency Currency instance
     * @param amount double amount, which is being added to the wallet balance
     */
    public void deposit(Currency currency, double amount) {
        if (this.getCurrenciesAmountMap().containsKey(currency)) {
            double currentSum = this.getCurrenciesAmountMap().get(currency);
            this.getCurrenciesAmountMap().put(currency, currentSum + amount);
        } else {
            this.getCurrenciesAmountMap().put(currency, amount);
        }
    }

    /**
     * Decrease amount of the first currency added to wallet.
     * @param amount double amount, which is being subtracted from the wallet balance.
     */
    public void withdraw(double amount) {
        Currency defaultCurrency = lookForDefaultCurrency();
        Double currentSum = this.getCurrenciesAmountMap().get(defaultCurrency);
        if (currentSum == 0 || currentSum <= amount) {
            this.getCurrenciesAmountMap().put(defaultCurrency, 0.0);
        } else {
            this.getCurrenciesAmountMap().put(defaultCurrency, currentSum - amount);
        }
    }

    /**
     *Decrease amount of the currency passed to this method.
     * @param currency Currency instance
     * @param amount double amount, which is being subtracted from the wallet balance
     */
    public void withdraw(Currency currency, double amount) {
        if (this.getCurrenciesAmountMap().containsKey(currency)) {
            Double currentSum = this.getCurrenciesAmountMap().get(currency);
            if (currentSum == 0.0 || currentSum <= amount) {
                this.getCurrenciesAmountMap().put(currency, 0.0);
            } else {
                this.getCurrenciesAmountMap().put(currency, currentSum - amount);
            }
        } else {
            System.out.println("You have tried to withdraw the " + currency.getCurrencyName() + " amount. " +
                    "There is no such currency in the wallet.");
        }
    }

    /**
     * Searches currency in the current wallet by its name.
     * @param name String name of the currency
     * @return instance of the currency
     */
    public Currency findCurrencyByName(String name) {
        Currency currency = this.getCurrenciesAmountMap()
                .keySet()
                .stream()
                .filter(e -> Objects.equals(e.getCurrencyName(), name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such currency in the current wallet"));
        return currency;
    }

    /**
     * Converts one currency to other, changing the balance of the wallet
     * @param firstCurrency currency, which is converted to other currency
     * @param secondCurrency currency, which amount grows
     * @param amount double amount of a currency, which is converted to other
     */
    public void convert(Currency firstCurrency, Currency secondCurrency, double amount) {
        if (amount > this.getCurrenciesAmountMap().get(firstCurrency)) {
            System.out.println("There is not enough amount of " + firstCurrency + " in your wallet");
        } else if (amount < 0) {
            System.out.println("You have entered negative value");
        } else {
            double secondCurrencyNewAmount = calcCurrencyInOtherCurrency(firstCurrency, secondCurrency, amount);
            this.deposit(secondCurrency, secondCurrencyNewAmount);
            if (secondCurrencyNewAmount == 0) {
                amount = secondCurrencyNewAmount;
            }
            this.withdraw(firstCurrency, amount);
        }
    }

    /**
     * Presents the amount of one currency in other currency according to their rate
     * @param currentCurrency currency, which amount is presented in other currency
     * @param otherCurrency measure currency
     * @param amount double amount to be calculated
     * @return double calculation result
     */
    public double calcCurrencyInOtherCurrency(Currency currentCurrency, Currency otherCurrency, double amount) {
        double calculatedAmount;
        if (currentCurrency.equals(otherCurrency)) {
            calculatedAmount = amount;
        } else {
            try {
                double currenciesRate = currentCurrency.getCurrenciesExchangeRates().get(otherCurrency);
                calculatedAmount = amount * currenciesRate;
            } catch (NullPointerException exception) {
                calculatedAmount = 0.0;
                System.out.println("Rate for this currency pair is not established");
                exception.printStackTrace();
            }
        }
        return calculatedAmount;
    }

    /**
     * Shows the balance of all currencies in the wallet.
     */
    public void showBalance() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (currenciesAmountMap.isEmpty()) {
            System.out.println("There is no currency added in the wallet");
        } else {
            System.out.println("Your balance:");
            for (Currency currency : this.currenciesAmountMap.keySet()) {
                System.out.println(decimalFormat.format(this.currenciesAmountMap.get(currency)) + " " + currency + "(s)");
            }
        }
    }

    /**
     * Shows total balance measured in default (firstly added) currency.
     */
    public double showTotal() {
        Currency defaultCurrency = lookForDefaultCurrency();
        return showTotal(defaultCurrency);
    }

    /**
     * Shows total balance measured in currency passed to this method.
     */
    public double showTotal(Currency currency) {
        double totalSum = 0;
        for (Currency c : this.currenciesAmountMap.keySet()) {
            double calculatedAmount = this.calcCurrencyInOtherCurrency(c, currency, this.currenciesAmountMap.get(c));
            totalSum += calculatedAmount;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        System.out.println("Total amount in " + currency + ": " + decimalFormat.format(totalSum));

        return totalSum;
    }

    @Override
    public String toString() {
        return "\n" + "WalletId: " + this.walletId + "\n" +
                "userName: '" + this.userName + '\'' + "\n" +
                "currenciesAmountMap: " + this.currenciesAmountMap;
    }
}
