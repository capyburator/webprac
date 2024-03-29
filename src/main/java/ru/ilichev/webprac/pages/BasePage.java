package ru.ilichev.webprac.pages;

import jakarta.annotation.PostConstruct;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BasePage {
    @Autowired
    protected WebDriver driver;

    @Autowired
    protected WebDriverWait wait;

    @PostConstruct
    private void init() {
        PageFactory.initElements(this.driver, this);
    }

    public <T> void waitElement(T elementAttr) {
        if (elementAttr.getClass().getName().contains("By")) {
            wait.until(ExpectedConditions.presenceOfElementLocated((By) elementAttr));
        } else {
            wait.until(ExpectedConditions.visibilityOf((WebElement) elementAttr));
        }
    }

    public <T> void click(T elementAttr) {
        waitElement(elementAttr);
        if (elementAttr.getClass().getName().contains("By")) {
            driver.findElement((By) elementAttr).click();
        } else {
            ((WebElement) elementAttr).click();
        }
    }

    public void jsClick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", wait.until(ExpectedConditions.visibilityOf(element)));
    }

    public <T> void writeToElement(T elementAttr, String text) {
        waitElement(elementAttr);
        if (elementAttr.getClass().getName().contains("By")) {
            driver.findElement((By) elementAttr).sendKeys(text);
        } else {
            ((WebElement) elementAttr).sendKeys(text);
        }
    }

    public <T> void replaceElementContent(T elementAttr, String text) {
        WebElement element;
        waitElement(elementAttr);
        if (elementAttr.getClass().getName().contains("By")) {
            element = driver.findElement((By) elementAttr);
        } else {
            element = (WebElement) elementAttr;
        }
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        writeToElement(elementAttr, text);
    }

    public <T> String readFromElement(T elementAttr) {
        waitElement(elementAttr);
        if (elementAttr.getClass().getName().contains("By")) {
            return driver.findElement((By) elementAttr).getText();
        } else {
            return ((WebElement) elementAttr).getText();
        }
    }

    public void verifyElementsContainExactlyInAnyOrder(List<WebElement> elements, List<String> needles) {
        List<String> haystack = elements.stream().map(this::readFromElement).toList();
        assertThat(haystack).containsExactlyInAnyOrderElementsOf(needles);
    }

    public void verifyElementIsEnabled(WebElement element) {
        String disabled = element.getAttribute("ariaDisabled");
        assertThat(disabled).isEqualTo("false");
    }

    public void verifyElementIsDisabled(WebElement element) {
        String disabled = element.getAttribute("ariaDisabled");
        assertThat(disabled).isEqualTo("true");
    }

    public void verifyElementContentIs(WebElement element, String expected) {
        String elementContent = readFromElement(element);
        assertThat(elementContent).isEqualTo(expected);
    }
}
