package stock.market.simulator;

public class stockProfile {

    private final String profileName;
    private double currentPrice;
    private double change;
    private double margin;
    private int quantity;

    // Class constructor for stock that has been bought 
    public stockProfile(String pName, double cPrice, int quant) {
        profileName = pName;
        currentPrice = cPrice;
        quantity = quant;
    }

    // Class constructor for all stocks when program is launched
    public stockProfile(String pName, double cPrice, double m) {
        profileName = pName;
        currentPrice = cPrice;
        margin = m;
        change = 0;
    }

    // Method to retrieve the quatity of stocks bought
    public int getQuantity() {
        return quantity;
    }

    // Method to set the number of a stock that has been bought
    public void setQuantity(int q) {
        quantity = q;
    }

    // Method to retrieve the profile name
    public String getProfileName() {
        return profileName;
    }

    // Method to retrieve the current price
    public double getCurrentPrice() {
        return currentPrice;
    }  

    // Method to set the buy price
    public void setCurrentPrice(double cPrice) {
        currentPrice = cPrice;
    }

    // Method to get the margin
    public double getMargin() {
        return margin;
    }

    // Method to set the change in current and previous rate
    public void setChange(double c) {
        change = c;
    }
    
    // Method to get the change in current and previous rate
    public double getChange() {
        return change;
    }
}
