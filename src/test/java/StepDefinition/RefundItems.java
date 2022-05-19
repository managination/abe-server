package StepDefinition;

import io.cucumber.java8.En;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RefundItems implements En {

    private Customer customer;
    private Item item;

    public RefundItems() {

        Given("^that \"([^\"]*)\" bought a faulty \"([^\"]*)\" for \\$(\\d+)$",
                (String name, String itemType, Integer price) -> {
                    customer = new Customer(name, 0);
                    item = new Item(itemType, price);
                });

        When("^she return the \"([^\"]*)\" to the store$", (String itemType) -> {
            Item returnedItem = new Item(itemType, 0);
            assertThat(item.getItemType(), is(returnedItem.getItemType()));
        });

        And("^she show her receipt$", () -> {
            customer.refund(item.getPrice());
        });

        Then("^she will get \\$(\\d+) refunded$", (Integer expected) -> {
            assertThat(customer.getRefunded(), is(expected));
        });
    }

}
