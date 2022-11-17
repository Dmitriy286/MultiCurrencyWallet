package models;

import java.text.DecimalFormat;
import java.util.*;


public class Wallet {
    private static int idCount = 1;
    private static List<Wallet> walletList;
    private final int walletId;
    private final String userName;
    Map<Currency, Double> currenciesAmountMap;

    public static List<Wallet> getWalletList() {
        return walletList;
    }

    public Wallet(String userName) {
        this.walletId = idCount;
        idCount++;
        if (walletList == null) {
            walletList = new ArrayList<>();
        }
        walletList.add(this);
        this.userName = userName;
        this.currenciesAmountMap = new LinkedHashMap<>();
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

    //todo возможно надо удалить этот сеттер
    public void setCurrenciesAmountMap(Map<Currency, Double> currenciesAmountMap) {
        this.currenciesAmountMap = currenciesAmountMap;
    }


    public void addCurrency(Currency currency) {
        this.getCurrenciesAmountMap().put(currency, 0.0);
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
                .orElseThrow(() -> {
                    return new RuntimeException("There are no currencies in the current wallet");
                });

        return defaultCurrency;
    }

    /**
     * Increase amount of the first currency added to wallet.
     * @param amount int amount, which is being added to the wallet balance.
     */
    public void deposit(double amount) {
        Currency defaultCurrency = lookForDefaultCurrency();
        double currentSum = this.getCurrenciesAmountMap().get(defaultCurrency);
        this.getCurrenciesAmountMap().put(defaultCurrency, currentSum + amount);
    }

    /**
     * Increase amount of the currency passed to this method.
     * @param amount int amount, which is being added to the wallet balance.
     */
    public void deposit(double amount, Currency currency) {
        if (this.getCurrenciesAmountMap().containsKey(currency)) {
            double currentSum = this.getCurrenciesAmountMap().get(currency);
            this.getCurrenciesAmountMap().put(currency, currentSum + amount);
        } else {
            this.getCurrenciesAmountMap().put(currency, amount);
        }
    }

    public void withdraw(double amount) {
        Currency defaultCurrency = lookForDefaultCurrency();
        Double currentSum = this.getCurrenciesAmountMap().get(defaultCurrency);
        if (currentSum == 0 || currentSum <= amount) {
            this.getCurrenciesAmountMap().put(defaultCurrency, 0.0);
        } else {
            this.getCurrenciesAmountMap().put(defaultCurrency, currentSum - amount);
        }
    }

    public void withdraw(double amount, Currency currency) {
        if (this.getCurrenciesAmountMap().containsKey(currency)) {
            Double currentSum = this.getCurrenciesAmountMap().get(currency);
            if (currentSum == 0.0 || currentSum <= amount) {
                this.getCurrenciesAmountMap().put(currency, 0.0);
            } else {
                this.getCurrenciesAmountMap().put(currency, currentSum - amount);
            }
        } else {
            System.out.println("You have tried to withdraw the " + currency.getName() + " amount. " +
                    "There is no such currency in the wallet.");
        }
    }

    public Currency findCurrencyByName(String name) {
        Currency currency = this.getCurrenciesAmountMap()
                .keySet()
                .stream()
                .filter(e -> Objects.equals(e.getName(), name))
                .findFirst()
                .orElseThrow(() -> {
                    return new RuntimeException("No such currency in the current wallet");
                });
        return currency;
    }

    public void convert(Currency firstCurrency, Currency secondCurrency, double amount) {
        if (amount > this.getCurrenciesAmountMap().get(firstCurrency)) {
            System.out.println("There is not enough amount of " + firstCurrency + " in your wallet");
        } else {
            double secondCurrencyNewAmount = calcCurrencyInOtherCurrency(firstCurrency, secondCurrency, amount);
            this.deposit(secondCurrencyNewAmount, secondCurrency);
            this.withdraw(amount, firstCurrency);
        }

    }

    public double calcCurrencyInOtherCurrency(Currency currentCurrency, Currency otherCurrency, double amount) {
        double calculatedAmount;
        if (currentCurrency.equals(otherCurrency)) {
            calculatedAmount = amount;
        } else {
            double currenciesRate = currentCurrency.getRates().get(otherCurrency);
            calculatedAmount = amount * currenciesRate;
        }
        return calculatedAmount;
    }


    public void showBalance() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (currenciesAmountMap.size() == 0) {
            System.out.println("There is no currency added in the wallet");
        } else {
            System.out.println("Your balance:");
            for (Currency currency : this.currenciesAmountMap.keySet()) {
                System.out.println(decimalFormat.format(this.currenciesAmountMap.get(currency)) + " " + currency + "(s)");
            }
        }
    }

    //todo добавить цвета для текста
    public void showTotal() {
        Currency defaultCurrency = lookForDefaultCurrency();
        showTotal(defaultCurrency);
    }

    public void showTotal(Currency currency) {
        double totalSum = 0;
        for (Currency c : this.currenciesAmountMap.keySet()) {
            double calculatedAmount = this.calcCurrencyInOtherCurrency(c, currency, this.currenciesAmountMap.get(c));
            totalSum += calculatedAmount;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        System.out.println("Total amount in " + currency + ": " + decimalFormat.format(totalSum));
    }

    @Override
    public String toString() {
        return "\n" + "WalletId: " + this.walletId + "\n" +
                "userName: '" + this.userName + '\'' + "\n" +
                "currenciesAmountMap: " + this.currenciesAmountMap +
                '}';
    }
}
