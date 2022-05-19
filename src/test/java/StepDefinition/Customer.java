package StepDefinition;

public class Customer {

    private final String name;
    private int refund;

    public Customer(String name, int refund) {
        this.name = name;
        this.refund = refund;
    }

    public String getName() {
        return name;
    }

    public int getRefunded() {
        return refund;
    }

    public void refund(int price) {
        this.refund = price;
    }
}
