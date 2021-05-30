package stock.market.simulator;

public class economyStock extends stockProfile {

    private final String country;
    private final double margin;
    
    // Class constructor
    public economyStock(String c, String name, double m, double cPrice) {
        super(name, cPrice, m);
        country = c;
        margin = m;
    }

    
}
