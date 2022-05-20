package StepDefinition;

public class Item {

    private final String itemType;
    private final int price;

    public Item(String itemType, int price) {
        this.itemType = itemType;
        this.price = price;
    }

    public String getItemType() {
        return itemType;
    }

    public int getPrice() {
        return price;
    }
}
