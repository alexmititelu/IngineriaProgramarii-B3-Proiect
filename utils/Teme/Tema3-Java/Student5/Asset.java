package Interfaces;

public interface Asset {
    double computeProfit();
    double getPrice();
    default double financial_risk() {
        double range = 1;
        return (Math.random() * range);
    }
}

