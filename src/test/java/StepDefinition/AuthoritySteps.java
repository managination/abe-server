package StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AuthoritySteps {

//    private final AuthorityService authorityService;
//    private final AuthorityRepository authorityRepository;
//
//    String authority = "Auth1";
//    String attribute = "Attribute1";
//
//    public AuthoritySteps(AuthorityService authorityService,
//                          AuthorityRepository authorityRepository) {
//        this.authorityService = authorityService;
//        this.authorityRepository = authorityRepository;
//    }

    @Given("Client defines {string}, {string}")
    public void client_defines(String string, String string2) {
        System.out.println("Client defines authority");
    }

    @When("Client creates authority with {string}, {string}")
    public void client_creates_authority_with(String string, String string2) {
        System.out.println("Client creates authority");
//        AuthorityRequest body = new AuthorityRequest(authority, new String[]{attribute});
//        authorityService.addNewAuthority(body);
    }

    @Then("{string} exists in database")
    public void existsInDatabase(String string) {
        System.out.println("Authority exists in DB");
//        Optional<AuthorityKeys> optionalAuthority = authorityRepository
//                .findAuthorityByName(authority);
//        assertTrue(optionalAuthority.isPresent());
    }

}
