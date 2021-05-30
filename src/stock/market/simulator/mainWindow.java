package stock.market.simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.border.Border;
import java.util.*;

public class mainWindow {

    // CRYPTO STOCK TEXT FIELDS
    JTextField BTCUSDT;
    JTextField diffBTCUSDT;

    JTextField ETHUSDT;
    JTextField diffETHUSDT;

    JTextField BNBUSDT;
    JTextField diffGBNBUSDT;

    JTextField MATICUSDT;
    JTextField diffMATICUSDT;

    // INDIAN STOCK TEXT FIELDS
    JTextField TATAMOTORS;
    JTextField diffTATMOT;

    JTextField BEL;
    JTextField diffBEL;

    JTextField RELIANCE;
    JTextField diffRELIANCE;

    JTextField LT;
    JTextField diffLT;

    JTextField[][] textFields;
    JTextField[][] differenceTextFields;

    JTextArea stocksBoughtInfo;
    accountProfile accProfile;
    stockProfile[][] stocksProfiles;

    String priceTitle = "Price";

    // Class constructor
    public mainWindow(accountProfile account, stockProfile[][] profiles) {

        accProfile = account;
        stocksProfiles = profiles;

        ButtonListener listener = new ButtonListener();

        JFrame frame = new JFrame("Stock Simulator");
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));

        textFields = new JTextField[2][4];
        differenceTextFields = new JTextField[2][4];

        JPanel stockInfo = new JPanel();
        Border stockInfoBorder = BorderFactory.createTitledBorder("STOCK INFORMATION");
        stockInfo.setBorder(stockInfoBorder);
        stockInfo.setLayout(new GridLayout(1, 0, 10, 0));

        // CRYPTO STOCK PANEL
        JLabel lblBTCUSDT = new JLabel("BTC/USDT", SwingConstants.CENTER);
        JLabel lblETHUSDT = new JLabel("ETH/USDT", SwingConstants.CENTER);
        JLabel lblBNBUSDT = new JLabel("BNB/USDT", SwingConstants.CENTER);
        JLabel lblMATICUSDT = new JLabel("MATIC/USDT", SwingConstants.CENTER);

        BTCUSDT = new JTextField("");
        ETHUSDT = new JTextField("");
        BNBUSDT = new JTextField("");
        MATICUSDT = new JTextField("");

        textFields[0][0] = BTCUSDT;
        textFields[0][1] = ETHUSDT;
        textFields[0][2] = BNBUSDT;
        textFields[0][3] = MATICUSDT;

        diffBTCUSDT = new JTextField("");
        diffETHUSDT = new JTextField("");
        diffGBNBUSDT = new JTextField("");
        diffMATICUSDT = new JTextField("");

        differenceTextFields[0][0] = diffBTCUSDT;
        differenceTextFields[0][1] = diffETHUSDT;
        differenceTextFields[0][2] = diffGBNBUSDT;
        differenceTextFields[0][3] = diffMATICUSDT;

        JPanel currencyPanel = new JPanel();
        currencyPanel.setLayout(new GridLayout(5, 3, 5, 5));

        Border currencyBorder = BorderFactory.createTitledBorder("Crypto Stock");
        currencyPanel.setBorder(currencyBorder);

        currencyPanel.add(new JLabel(""));
        currencyPanel.add(new JLabel(priceTitle, SwingConstants.CENTER));
        currencyPanel.add(new JLabel(""));

        currencyPanel.add(lblBTCUSDT);
        currencyPanel.add(textFields[0][0]);
        currencyPanel.add(differenceTextFields[0][0]);

        currencyPanel.add(lblETHUSDT);
        currencyPanel.add(textFields[0][1]);
        currencyPanel.add(differenceTextFields[0][1]);

        currencyPanel.add(lblBNBUSDT);
        currencyPanel.add(textFields[0][2]);
        currencyPanel.add(differenceTextFields[0][2]);

        currencyPanel.add(lblMATICUSDT);
        currencyPanel.add(textFields[0][3]);
        currencyPanel.add(differenceTextFields[0][3]);

        // INDIAN STOCK PANEL
        JLabel lbltatmot = new JLabel("TATAMOTORS", SwingConstants.CENTER);
        JLabel lblbel = new JLabel("BEL", SwingConstants.CENTER);
        JLabel lblreliance = new JLabel("RELIANCE", SwingConstants.CENTER);
        JLabel lbllt = new JLabel("LT", SwingConstants.CENTER);

        TATAMOTORS = new JTextField("");
        BEL = new JTextField("");
        RELIANCE = new JTextField("");
        LT = new JTextField("");

        textFields[1][0] = TATAMOTORS;
        textFields[1][1] = BEL;
        textFields[1][2] = RELIANCE;
        textFields[1][3] = LT;

        diffTATMOT = new JTextField("");
        diffBEL = new JTextField("");
        diffRELIANCE = new JTextField("");
        diffLT = new JTextField("");

        differenceTextFields[1][0] = diffTATMOT;
        differenceTextFields[1][1] = diffBEL;
        differenceTextFields[1][2] = diffRELIANCE;
        differenceTextFields[1][3] = diffLT;

        JPanel companyPanel = new JPanel();
        companyPanel.setLayout(new GridLayout(5, 3, 5, 5));

        Border companyBorder = BorderFactory.createTitledBorder("Indian Stock");
        companyPanel.setBorder(companyBorder);

        companyPanel.add(new JLabel(""));
        companyPanel.add(new JLabel(priceTitle, SwingConstants.CENTER));
        companyPanel.add(new JLabel(""));

        companyPanel.add(lbltatmot);
        companyPanel.add(textFields[1][0]);
        companyPanel.add(differenceTextFields[1][0]);

        companyPanel.add(lblbel);
        companyPanel.add(textFields[1][1]);
        companyPanel.add(differenceTextFields[1][1]);

        companyPanel.add(lblreliance);
        companyPanel.add(textFields[1][2]);
        companyPanel.add(differenceTextFields[1][2]);

        companyPanel.add(lbllt);
        companyPanel.add(textFields[1][3]);
        companyPanel.add(differenceTextFields[1][3]);

        // Adding the currency panel and company panel into a new panel
        JPanel currencyCompanyHolder = new JPanel();
        currencyCompanyHolder.setLayout(new GridLayout(1, 2, 10, 0));

        currencyCompanyHolder.add(currencyPanel);
        currencyCompanyHolder.add(companyPanel);

        // Assign all the JTextFields a dimension
        for (JTextField[] textFieldArray : textFields) {
            for (JTextField textField : textFieldArray) {
                textField.setSize(new Dimension(150, 20));
            }
        }

        // Create a Panel to display your portfolio
        JPanel stocksBought = new JPanel();
        Border stocksBoughtBorder = BorderFactory.createTitledBorder("YOUR PORTFOLIO");
        stocksBought.setBorder(stocksBoughtBorder);

        stocksBoughtInfo = new JTextArea(15, 30);
        stocksBought.add(stocksBoughtInfo);

        stockInfo.add(currencyCompanyHolder, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));

        mainPanel.add(stockInfo);
        mainPanel.add(stocksBought);

        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));

        // Code for the Button Bar at the Bottom
        JPanel buttonPanel = new JPanel();
        Border graphViewBorder = BorderFactory.createEtchedBorder();
        buttonPanel.setBorder(graphViewBorder);

        Button btnShowGraph = new Button("View Graph");
        btnShowGraph.addActionListener(listener);

        Button btnBuySell = new Button("Buy/Sell Stock");
        btnBuySell.addActionListener(listener);

        buttonPanel.setLayout(new GridLayout(1, 3, 5, 5));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.add(btnBuySell);
        buttonPanel.add(btnShowGraph);
        // buttonPanel.setPreferredSize(new Dimension(110, 75));

        frame.add(mainPanel);
        frame.add(buttonPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 650);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

    }

    // Event handler for when buttons are pressed
    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent et) {

            String btnName = et.getActionCommand();

            try {
                if (btnName.equals("Buy/Sell Stock")) {
                    buy_sellShare window = new buy_sellShare(accProfile, stocksProfiles);
                }

                if (btnName.equals("View Graph")) {
                    viewGraph graph = new viewGraph(stocksProfiles);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.WARNING_MESSAGE);
            }

        }

    }

    // Method to set the values of the text boxes with the stock prices
    public void setTextBoxValues(stockProfile[][] stocksProfiles) {

        for (int i = 0; i < stocksProfiles.length; i++) {
            for (int j = 0; j < stocksProfiles[i].length; j++) {

                stockProfile stock = stocksProfiles[i][j];

                setStockPriceTextField(textFields[i][j], stock);
                setDifferenceTextField(differenceTextFields[i][j], stock.getChange());
                setTextFieldColour(differenceTextFields[i][j], stock.getChange());

            }
        }

        setStockBought();

    }

    // Method to set the buy and sell rates for a stock
    public void setStockPriceTextField(JTextField field, stockProfile profile) {
        field.setText(String.valueOf(profile.getCurrentPrice()));

    }

    // Setting the difference between current rate and previous rates
    public void setDifferenceTextField(JTextField field, double change) {
        String difference = (change > 0) ? ("+" + change) : ("" + change);
        field.setText(difference + "%");
    }

    // Method to display the bought stocks in the relative text area
    public void setStockBought() {
        ArrayList<stockProfile> stocksBought = accProfile.getStocks();
        int numOfStock = stocksBought.size();
        String message = "STOCK NAME - PRICE - Quantity\n";

        for (int i = 0; i < numOfStock; i++) {
            message = message + stocksBought.get(i).getProfileName() + " - " + stocksBought.get(i).getCurrentPrice()
                    + "/" + " - " + stocksBought.get(i).getQuantity() + "\n";
        }

        message = message + "\nBALANCE: " + roundTo2DP(accProfile.getBalance());
        stocksBoughtInfo.setText(message);
    }

    // Method to set the font colour of the difference
    public void setTextFieldColour(JTextField field, double change) {
        if (change >= 0) {
            field.setForeground(Color.green);
        } else {
            field.setForeground(Color.red);
        }
    }

    // Rounding to 2 decimal place
    public static Double roundTo2DP(double number) {
        DecimalFormat roundFormat = new DecimalFormat(".##");
        return (Double.parseDouble(roundFormat.format(number)));
    }

}
