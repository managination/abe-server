package ch.bonq;

import com.example.abe.controller.ClientController;
import com.example.abe.model.Client;
import com.example.abe.repository.ClientRepository;
import com.example.abe.repository.PersonalKeysRepository;
import com.example.abe.service.ClientService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BonqTroveStepDefs {
    private PersonalKeysRepository personalKeysRepository;
    private ClientRepository clientRepository;
    private ClientService clientService = new ClientService(clientRepository, personalKeysRepository);
    private ClientController clientController = new ClientController(clientService);

    public BonqTroveStepDefs() {
    }

    @Given("The Trove Factory contract has been deployed")
    public void theTroveFactoryContractHasBeenDeployed() {
        Client bob = new Client("Bob", "bob@gmail.com");
        System.out.println("Name " + bob.getName());
        assertThat(bob.getName(), is("Bob"));
    }

    @And("The user has at least {int} EWT")
    public void theUserHasAtLeastEWT(int arg0) {
    }

    @When("The user asks the Trove Factory to create a trove with {string} as collateral")
    public void theUserAsksTheTroveFactoryToCreateATroveWithAsCollateral(String arg0) {
    }

    @Then("A new Trove smart contract is deployed")
    public void aNewTroveSmartContractIsDeployed() {
    }

    @And("The Trove can be found in the list of valid troves with {string} as collateral")
    public void theTroveCanBeFoundInTheListOfValidTrovesWithAsCollateral(String arg0) {
    }

    @And("The Trove has a TCR of MAX_INT")
    public void theTroveHasATCROfMAX_INT() {
    }

    @Given("A Trove from the list of valid troves with {string} as collateral")
    public void aTroveFromTheListOfValidTrovesWithAsCollateral(String arg0) {
    }

    @And("The Trove has {int} debt")
    public void theTroveHasDebt(int arg0) {
    }

    @When("{int} {string} are transferred to the Trove")
    public void areTransferredToTheTrove(int arg0, String arg1) {
    }

    @Then("The Trove has a TCR of {string}")
    public void theTroveHasATCROf(String arg0) {
    }

    @And("The Trove has {int} {string} collateral")
    public void theTroveHasCollateral(int arg0, String arg1) {
    }

    @And("The price of EWT is {int} bEUR")
    public void thePriceOfEWTIsBEUR(int arg0) {
    }

    @When("The Trove owner borrows {int} bEUR with {string} as recipient")
    public void theTroveOwnerBorrowsBEURWithAsRecipient(int arg0, String arg1) {
    }

    @Then("{int} bEUR are transferred to the Fee Recipient contract")
    public void beurAreTransferredToTheFeeRecipientContract(int arg0) {
    }

    @And("{int} bEUR are transferred to {string}")
    public void beurAreTransferredTo(int arg0, String arg1) {
    }

    @And("The net debt is {int} bEUR")
    public void theNetDebtIsBEUR(int arg0) {
    }

    @And("The total debt is {int} bEUR")
    public void theTotalDebtIsBEUR(int arg0) {
    }

}
