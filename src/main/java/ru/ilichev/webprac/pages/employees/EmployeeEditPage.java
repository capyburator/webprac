package ru.ilichev.webprac.pages.employees;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;
import ru.ilichev.webprac.pages.NonMainPage;

@LazyComponent
public class EmployeeEditPage extends NonMainPage {
    @FindBy(how = How.XPATH, using = "//input[@type='submit']")
    private WebElement editButton;

    @FindBy(how = How.XPATH, using = "//input[@id='fullName']")
    private WebElement fullNameInput;

    @FindBy(how = How.XPATH, using = "//input[@id='email']")
    private WebElement emailInput;

    @FindBy(how = How.XPATH, using = "//input[@id='birthDate']")
    private WebElement birthDateInput;

    public void editEmployee(String fullName, String email, String birthDate) {
        replaceElementContent(fullNameInput, fullName);
        replaceElementContent(emailInput, email);
        writeToElement(birthDateInput, birthDate);
        click(editButton);
    }
}
