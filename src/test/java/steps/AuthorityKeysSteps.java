package steps;
//
//import com.example.abe.model.AuthorityRequestPayload;
//import com.example.abe.model.Client;
//import com.example.abe.dcpabe.other.AuthorityKeys;
//import com.example.abe.dcpabe.other.DCPABE;
//import com.example.abe.dcpabe.other.PersonalKeys;
//import com.example.abe.dcpabe.other.PublicKeys;
//import io.cucumber.java.En;
//
//import java.util.Set;
//
//import static com.example.abe.dcpabe.other.GlobalParameters.gp;
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class AuthorityKeysSteps implements En {
//
//    private AuthorityRequestPayload authorityRequestPayload;
//    private AuthorityKeys authorityKeys;
//    private PublicKeys publicKeys;
//    private Client clientBob;
//    private PersonalKeys bobsPeKs;
//
//    public AuthorityKeysSteps () {
//
//        //Scenario: Create Authority with list of attributes
//        Given("^that client defines request payload by \"([^\"]*)\" and list of attributes: \"([^\"]*)\", \"([^\"]*)\"$",
//                (String authorityName, String attribute1, String attribute2) -> {
//
//                    authorityRequestPayload = new AuthorityRequestPayload(authorityName, new String[]{attribute1, attribute2});
//                });
//        When("^client creates an authority by an authority request payload$", () -> {
//
//                    authorityKeys = DCPABE.authoritySetup(authorityRequestPayload.getName(), gp, authorityRequestPayload.getAttributes());
//        });
//        Then("^authority should exists with \"([^\"]*)\" and list of attributes: \"([^\"]*)\", \"([^\"]*)\"",
//                (String authorityName, String attribute1, String attribute2) -> {
//
//                    assertThat(authorityKeys.getAuthorityName(), is(authorityName));
//                    assertThat(authorityKeys.getPublicKeys().keySet(), is(Set.of(attribute1, attribute2)));
//                    assertThat(authorityKeys.getSecretKeys().keySet(), is(Set.of(attribute1, attribute2)));
//        });
//
//
//        //Scenario: Subscribe authority public keys to all public keys
//        Given("^that public keys object exists$", () -> {
//            publicKeys = new PublicKeys();
//        });
//        When("^an authority is created by \"([^\"]*)\" and list of attributes: \"([^\"]*)\", \"([^\"]*)\"$",
//                (String authorityName, String attribute1, String attribute2) -> {
//            authorityRequestPayload = new AuthorityRequestPayload(authorityName, new String[]{attribute1, attribute2});
//            authorityKeys = DCPABE.authoritySetup(authorityRequestPayload.getName(), gp, authorityRequestPayload.getAttributes());
//        });
//        And("^its public keys subscribes to all public keys$", () -> {
//            publicKeys.subscribeAuthority(authorityKeys.getPublicKeys());
//        });
//        Then("^all public keys object should contain authority public keys$", () -> {
//            assertThat(publicKeys.getPublicKeys().keySet().containsAll(authorityKeys.getPublicKeys().keySet()), is(true));
//        });
//
//
//        //Scenario: Client get personal key from authority
//        Given("^that client \"([^\"]*)\" has personal keys \"([^\"]*)\" and authority \"([^\"]*)\" with attribute \"([^\"]*)\" exists$",
//                (String clientName, String clientPeKs, String authorityName, String attribute) -> {
//
//                    clientBob = new Client(clientName, "bob@gmail.com");
//                    bobsPeKs = new PersonalKeys(clientName);
//                    authorityRequestPayload = new AuthorityRequestPayload(authorityName, new String[]{attribute});
//                    authorityKeys = DCPABE.authoritySetup(authorityRequestPayload.getName(), gp, authorityRequestPayload.getAttributes());
//                });
//        When("^client \"([^\"]*)\" requests personal key by \"([^\"]*)\" from \"([^\"]*)\"$",
//                (String clientName, String attribute, String authorityName) -> {
//
//                    bobsPeKs.addKey(DCPABE.keyGen(clientName, attribute, authorityKeys.getSecretKeys().get(attribute), gp));
//        });
//        Then("^object \"([^\"]*)\" should contain personal key by \"([^\"]*)\" from \"([^\"]*)\"",
//                (String clientPeks, String attribute, String authorityName) -> {
//
//                    assertThat(bobsPeKs.getAttributes().contains(attribute), is(true));
//                });
//
//
//
//    }
//
//}
