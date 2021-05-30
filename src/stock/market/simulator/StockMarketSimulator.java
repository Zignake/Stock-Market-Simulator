package stock.market.simulator;

import java.text.DecimalFormat;
import java.io.*;

import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StockMarketSimulator {

    public static final String DATAFILEPATH = "D:/Laukik/SY/OOP/CP/Stock-Market-Simulator/src/stock/market/simulator/data/";

    public static void main(String[] args) throws IOException {

        accountProfile accProfile = accountCreation();
        stockProfile[][] stockProfile = createAllStocks();

        mainWindow window = new mainWindow(accProfile, stockProfile);

        foreverLoop(stockProfile, window);

    }

    // Using a timer to recalculate the stock rates every 5 seconds and display them
    public static void foreverLoop(stockProfile[][] stockProfile, mainWindow window) {
        Timer t1 = new Timer();
        t1.schedule(new TimerTask() {
            @Override
            public void run() {
                // here add to read new data from the file and set it it to stockProfile
                // recalulationPrice(stockProfile);
                getCryptoPrices(stockProfile);
                window.setTextBoxValues(stockProfile);

            }
        }, 0, 1000);
    }

    // Function to create an account profile for the user
    public static accountProfile accountCreation() {
        createAccount accCreate = new createAccount();

        boolean successfulCreate;
        Object sync = new Object();
        do {
            // Variables are synchronized inorder to break from the do-while loop
            // when boolean expression is met
            synchronized (sync) {
                successfulCreate = accCreate.getCreated();
            }
        } while (!successfulCreate);

        accountProfile accProfile = accCreate.getAccountProfile();
        accCreate.terminate();
        return accProfile;
    }

    // Rounding to 2 decimal place
    public static Double roundTo2DP(double number) {
        DecimalFormat roundFormat = new DecimalFormat(".##");
        return (Double.parseDouble(roundFormat.format(number)));
    }

    // Rounding to 4 decimal place
    public static Double roundTo4DP(double number) {
        DecimalFormat roundFormat = new DecimalFormat(".####");
        return (Double.parseDouble(roundFormat.format(number)));
    }

    // ..................................................................................
    // Function to create the crypto stocks
    public static CryptoStock[] createCryptoStock(String[] crypto, double[] cryptoPrice) {
        CryptoStock[] stocks = new CryptoStock[crypto.length];

        for (int i = 0; i < crypto.length; i++) {
            stocks[i] = new CryptoStock(crypto[i], cryptoPrice[i]);

        }
        return stocks;
    }

    // Function to create the Indian stocks
    public static IndianStock[] createIndianStock(String[] indianStock, double[] indianStockPrice) {
        IndianStock[] stocks = new IndianStock[indianStock.length];

        for (int i = 0; i < indianStock.length; i++) {
            stocks[i] = new IndianStock(indianStock[i], indianStockPrice[i]);

        }
        return stocks;
    }

    public static stockProfile[][] createAllStocks() {
        // Creating Crypto Stock
        String[] crypto = { "BTCUSDT", "ETHUSDT", "BNBUSDT", "MATICUSDT" };
        double[] cryptoPrice = { 0, 0, 0, 0 };

        // Creating Indian Stock
        String[] indianStock = { "TATMOT", "BEL", "RELIANCE", "LT" };
        double[] indianStockPrice = { 0, 0, 0, 0 };

        stockProfile[][] stocks = { createCryptoStock(crypto, cryptoPrice),
                createIndianStock(indianStock, indianStockPrice) };

        return stocks;
    }

    // get prices from the files and set the values
    public static void getCryptoPrices(stockProfile[][] stocks) {

        String[] files = {  DATAFILEPATH + "BTCUSDT.csv",DATAFILEPATH + "ETHUSDT.csv", DATAFILEPATH + "BNBUSDT.csv",
                DATAFILEPATH + "MATICUSDT.csv" };
        Double[] currentPrices = new Double[4];
        Double[] startPrices = { null, null, null, null };
        Double[] differences = new Double[4];

        BufferedReader reader = null;
        String line = "";

        try {
            for (int i = 0; i < files.length; i++) {
                String[] row = new String[2];
                reader = new BufferedReader(new FileReader(files[i]));
                while ((line = reader.readLine()) != null) {
                    row = line.split(",");
                    if (startPrices[i] == null) {
                        startPrices[i] = Double.valueOf(row[1]).doubleValue();
                    }
                }
                currentPrices[i] = Double.valueOf(row[1]).doubleValue();
                differences[i] = (currentPrices[i] - startPrices[i]) / startPrices[i] * 100;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        for (stockProfile[] stockArray : stocks) {
            for (int i=0; i<stockArray.length; i++) {
                stockArray[i].setCurrentPrice(roundTo4DP(currentPrices[i]));
                stockArray[i].setChange(roundTo2DP(differences[i]));
            }
        }

    }
}

   // Procedure to recalculate the stock rate when called on by the timer
//    public static void recalulationPrice(stockProfile[][] stocks) {
//
//        Random number = new Random();
//
//        double priceChange;
//        stockProfile stockToEdit;
//
//        for (stockProfile[] stockArray : stocks) {
//            for (stockProfile stock : stockArray) {
//                priceChange = number.nextDouble() + 0.1;
//                int increaseORdecrease = number.nextInt(10);
//
//                stockToEdit = stock;
//                if (increaseORdecrease > 8) {
//                    increasePrice(stockToEdit, priceChange);
//
//                } else {
//                    decreasePrice(stockToEdit, priceChange);
//                }
//
//            }
//        }
//
//    }
//
//    // Setting the stock rates once they have been recalculated
//    public static void setPriceChange(stockProfile profile, double buyChange) {
//        double difference = ((buyChange - profile.getCurrentPrice()) / profile.getCurrentPrice() * 100);
//        profile.setChange(roundTo2DP(difference));
//        profile.setCurrentPrice(roundTo4DP(buyChange));
//
//    }
//    
//    // Setting the stock rates once they have been recalculated
//    public static void setPriceCrypto(stockProfile profile, double buyChange) {
//        double difference = ((buyChange - profile.getCurrentPrice()) / profile.getCurrentPrice() * 100);
//        profile.setChange(roundTo2DP(difference));
//        profile.setCurrentPrice(roundTo4DP(buyChange));
//
//    }
//
//    // Calculating an increase in stock price
//    public static void increasePrice(stockProfile profile, double priceChange) {
//        double buyChange = priceChange + (profile.getCurrentPrice() + ((profile.getCurrentPrice() * profile.getMargin()) / 2));
//
//        setPriceChange(profile, buyChange);
//
//    }
//
//    // Calculating a decrease in stock price
//    public static void decreasePrice(stockProfile profile, double priceChange) {
//        double buyChange = priceChange + (profile.getCurrentPrice() + ((profile.getCurrentPrice() * profile.getMargin()) / 2));
//
//        setPriceChange(profile, buyChange);
//
//    }

   // Creating all stock profiles
//    public static stockProfile[][] createAllStocks() {
//        // BASIC INFORMATION FOR CURRENCY STOCK
//        String[][] from_To = { { "EUR", "USD" }, { "GBP", "USD" }, { "EUR", "GBP" }, { "GBP", "JPY" } };
//        double[][] currencyPrice = { { 1.2183, 1.2185 }, { 1.3767, 1.3768 }, { 0.88491, 0.88511 },
//                { 147.279, 147.320 } };
//
//        // BASIC INFORMATION FOR COMPANY STOCK
//        String[] companyName = { "Facebook", "Apple", "Microsoft", "BMW" };
//        double[] companyMargin = { 0.05, 0.05, 0.05, 0.5 };
//        double[][] companyPrice = { { 178.31, 178.56 }, { 178.12, 178.37 }, { 93.96, 93.05 }, { 85.87, 85.99 } };
//
//        // BASIC INFORMATION FOR ECONOMY STOCK
//        String[] countryName = { "UK", "USA", "AUS", "JPY" };
//        String[] stockName = { "FTSE 100", "Dow Jones", "$AUSSIE200", "NIKKEI 225" };
//        double[] economyMargin = { 0.005, 0.005, 0.01, 0.01 };
//        double[][] economyPrice = { { 718.18, 718.28 }, { 25056, 25060 }, { 5974.8, 5978.3 }, { 21634, 21642 } };
//
//        stockProfile[][] stocks = { createCurrencyStock(from_To, currencyPrice),
//                createCompanyStock(companyName, companyMargin, companyPrice),
//                createEconomyStock(countryName, stockName, economyMargin, economyPrice) };
//
//        return stocks;
//    }
//
//    // Function to create the currency stocks
//    public static currencyStock[] createCurrencyStock(String[][] from_To, double[][] price) {
//        currencyStock[] stocks = new currencyStock[from_To.length];
//
//        for (int i = 0; i < from_To.length; i++) {
//            stocks[i] = new currencyStock(from_To[i][0], from_To[i][1], price[i][0]);
//
//        }
//
//        return stocks;
//    }
//
//    // Function to create the company stocks
//    public static companyStock[] createCompanyStock(String[] companyName, double[] margin, double[][] price) {
//        companyStock[] stocks = new companyStock[companyName.length];
//
//        for (int i = 0; i < companyName.length; i++) {
//
//            stocks[i] = new companyStock(companyName[i], margin[i], price[i][0]);
//
//        }
//
//        return stocks;
//    }
//
//    // Function to create the economy stocks
//    public static economyStock[] createEconomyStock(String[] countryName, String[] stockName, double[] margin,
//            double[][] price) {
//        economyStock[] stocks = new economyStock[countryName.length];
//
//        for (int i = 0; i < countryName.length; i++) {
//
//            stocks[i] = new economyStock(countryName[i], stockName[i], margin[i], price[i][0]);
//
//        }
//
//        return stocks;
//    }
//
//    // Procedure to create the history files
//    public static void createHistoryFiles(stockProfile[][] stocks) throws IOException {
//
//        String fileName;
//        FileWriter fileWriter;
//
//        for (stockProfile[] stockArray : stocks) {
//            for (stockProfile stock : stockArray) {
//                fileName = stock.getProfileName() + ".csv";
//                fileWriter = new FileWriter(HISTORYFILEPATH + fileName);
//            }
//        }
//
//    }
//
//    // Procedure to delete the history files
//    public static void deleteHistoryFiles(File directory) {
//        for (File file : directory.listFiles()) {
//            if (!file.isDirectory()) {
//                file.delete();
//            }
//        }
//
//    }