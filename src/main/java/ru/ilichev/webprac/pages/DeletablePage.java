package ru.ilichev.webprac.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static org.assertj.core.api.Assertions.assertThat;

public class DeletablePage extends EditablePage {
    @FindBy(how = How.XPATH, using = "//input[@value='Удалить']")
    protected WebElement deleteButton;

    public void verifyDeleteButtonIsEnabled() {
        String disabled = deleteButton.getAttribute("ariaDisabled");
        assertThat(disabled).isEqualTo("false");
    }

    public void verifyDeleteButtonIsDisabled() {
        String disabled = deleteButton.getAttribute("ariaDisabled");
        assertThat(disabled).isEqualTo("true");
    }

    public void deletePageAccept() {
        verifyDeleteButtonIsEnabled();
        click(deleteButton);
    }
}
