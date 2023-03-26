package ru.ilichev.webprac.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class EditablePage extends NonMainPage {
    @FindBy(how = How.XPATH, using = "//a[contains(text(), 'Редактировать')]")
    protected WebElement editButton;

    public void goToEditPage() {
        click(editButton);
    }
}
