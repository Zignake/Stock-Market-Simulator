package stock.market.simulator;

public class currencyStock extends stockProfile {

    private final String from;
    private final String to;
   
    // Class constructor
    public currencyStock(String f, String t, double cPrice) {

        super(f + "-" + t, cPrice, 0.01);
        from = f;
        to = t;
        

    }
    
    // Method to retrieve the margin
    @Override
    public double getMargin() {
        return 0.01;
    }
}
