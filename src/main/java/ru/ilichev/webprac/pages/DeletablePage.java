package ru.ilichev.webprac.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class DeletablePage extends EditablePage {
    @FindBy(how = How.XPATH, using = "//input[@value='Удалить']")
    protected WebElement deleteButton;

    public DeletablePage verifyDeleteButtonIsEnabled() {
        verifyElementIsEnabled(deleteButton);
        return this;
    }

    public void verifyDeleteButtonIsDisabled() {
        verifyElementIsDisabled(deleteButton);
    }

    public void deletePageAccept() {
        verifyDeleteButtonIsEnabled().click(deleteButton);
    }
}
