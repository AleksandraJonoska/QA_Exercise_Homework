package com.qa1.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(

        plugin = {"html:target/cucumber-report.html", //html report
                "me.jvt.cucumber.report.PrettyReports:target/cucumber",
                "json:target/cucumber.json"}, // cucumber report,
        features = "src/test/resources/features",
        glue = "com/qa1/step_definitions",
        dryRun = false,
        tags =""



)
public class CukesRunner {
}
