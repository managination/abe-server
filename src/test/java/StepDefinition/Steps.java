package StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Steps {

    @Given("{string} has created {string} and {string}")
    public void hasCreatedAnd(String string, String string2, String string3) {
        System.out.println("Authority has attributes");
    }

    @Given("{string} creates an access structure of {string}")
    public void creates_an_access_structure_of(String string, String string2) {
        System.out.println("Alice creates access structure");
    }

    @When("{string} encrypts the message {string} with the access structure")
    public void encrypts_the_message_with_the_access_structure(String string, String string2) {
        System.out.println("Alice encrypts");
    }

    @Then("{string} receives a ciphertext which can be decrypted")
    public void receives_a_ciphertext_which_can_be_decrypted(String string) {
        System.out.println("Alice gets ciphertext");
    }

}
