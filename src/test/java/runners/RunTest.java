package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/FirstTest.feature",
        glue = {"StepDefinition"}
)
public class RunTest extends AbstractTestNGCucumberTests {

}
