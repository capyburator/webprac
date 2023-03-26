package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.springframework.context.ApplicationContext;
import ru.ilichev.webprac.annotation.LazyAutowired;
import ru.ilichev.webprac.util.ScreenshotUtil;

public class CucumberHooks {
    @LazyAutowired
    private ApplicationContext ctx;

    @LazyAutowired
    private ScreenshotUtil screenshotUtil;

    @AfterStep
    public void afterStep(Scenario scenario){
        if(scenario.isFailed()){
            scenario.attach(this.screenshotUtil.getScreenshot(), "image/png", scenario.getName());
        }
    }

    @After
    public void afterScenario() {
        ctx.getBean(WebDriver.class).quit();
    }
}
