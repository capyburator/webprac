package ru.ilichev.webprac.configs;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import ru.ilichev.webprac.annotation.LazyConfiguration;

import java.time.Duration;

@LazyConfiguration
public class WebDriverConfig {
    @Value("${webdriver.explicit-timeout}")
    private int EXPLICIT_TIMEOUT;

    @Value("${webdriver.implicit-timeout}")
    private int IMPLICIT_TIMEOUT;

    @Bean
    public WebDriver webDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*","ignore-certificate-errors", "start-maximized",
                "--disable-extensions","--disable-dev-shm-usage");
        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(IMPLICIT_TIMEOUT));
        return driver;
    }

    @Bean
    public WebDriverWait webDriverWait() {
        return new WebDriverWait(webDriver(), Duration.ofMillis(EXPLICIT_TIMEOUT));
    }
}
