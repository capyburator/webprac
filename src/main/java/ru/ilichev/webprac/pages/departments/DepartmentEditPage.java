package ru.ilichev.webprac.pages.departments;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;
import ru.ilichev.webprac.pages.NonMainPage;

@LazyComponent
public class DepartmentEditPage extends NonMainPage {
    @FindBy(how = How.XPATH, using = "//input[@type='submit']")
    private WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//input[@id='name']")
    private WebElement nameInput;

    public void editPage(String name) {
        replaceElementContent(nameInput, name);
        click(submitButton);
    }
}
