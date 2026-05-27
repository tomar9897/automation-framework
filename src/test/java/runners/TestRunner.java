//package runners;

//import org.junit.platform.suite.api.ConfigurationParameter;
//import org.junit.platform.suite.api.IncludeEngines;
//import org.junit.platform.suite.api.SelectClasspathResource;
//import org.junit.platform.suite.api.Suite;
//
//@Suite
//@IncludeEngines("cucumber")
//@SelectClasspathResource("features")
//@ConfigurationParameter(key = "cucumber.plugin", value = "pretty")
//@ConfigurationParameter(key = "cucumber.glue", value = "stepdefinitions")
//@ConfigurationParameter(key = "cucumber.filter.tags", value = "@tag2")
//public class TestRunner {
//}
//


package runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/healingValidation.feature")

@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "stepdefinitions")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, json:target/cucumber.json, com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
)

public class TestRunner {
}
