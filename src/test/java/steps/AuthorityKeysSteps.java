package steps;

import com.example.abe.authority.AuthorityRequest;
import com.example.abe.dcpabe.other.AuthorityKeys;
import com.example.abe.dcpabe.other.DCPABE;
import io.cucumber.java8.En;

import java.util.Set;

import static com.example.abe.dcpabe.other.GlobalParameters.gp;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AuthorityKeysSteps implements En {

    private AuthorityRequest authorityRequest;
    private AuthorityKeys authorityKeys;

    public AuthorityKeysSteps () {

        Given("^that client defines request body by ([^\"]*) and list of attributes: ([^\"]*), ([^\"]*)$",
                (String authorityName, String attribute1, String attribute2) -> {

                    authorityRequest = new AuthorityRequest(authorityName, new String[]{attribute1, attribute2});
                });

        When("^client creates an authority by an authority request body$", () -> {

                    authorityKeys = DCPABE.authoritySetup(authorityRequest.getName(), gp, authorityRequest.getAttributes());
        });

        Then("^authority should exists with ([^\"]*) and list of attributes: ([^\"]*), ([^\"]*)",
                (String authorityName, String attribute1, String attribute2) -> {

                    assertThat(authorityKeys.getAuthorityName(), is(authorityName));
                    assertThat(authorityKeys.getPublicKeys().keySet(), is(Set.of(attribute1, attribute2)));
                    assertThat(authorityKeys.getSecretKeys().keySet(), is(Set.of(attribute1, attribute2)));
        });

    }

}
