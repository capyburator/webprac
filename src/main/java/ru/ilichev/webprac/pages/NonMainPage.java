package ru.ilichev.webprac.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NonMainPage extends BasePage {
    @FindBy(how = How.XPATH, using = "//a[contains(@class, 'nav-link')]")
    private WebElement toMainButton;

    public void goToMainPage() {
        click(toMainButton);
    }
}
