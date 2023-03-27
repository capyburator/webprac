package ru.ilichev.webprac.pages.employees;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;
import ru.ilichev.webprac.pages.NonMainPage;

@LazyComponent
public class EmployeeAddPage extends NonMainPage {
    @FindBy(how = How.XPATH, using = "//input[@type='submit']")
    private WebElement addButton;

    @FindBy(how = How.XPATH, using = "//input[@id='fullName']")
    private WebElement fullNameInput;

    @FindBy(how = How.XPATH, using = "//input[@id='email']")
    private WebElement emailInput;

    @FindBy(how = How.XPATH, using = "//input[@id='birthDate']")
    private WebElement birthDateInput;

    @FindBy(how = How.XPATH, using = "//input[@id='address']")
    private WebElement addressInput;

    @FindBy(how = How.XPATH, using = "//input[@id='phoneNo']")
    private WebElement phoneNoInput;

    public void addEmployee(String fullName, String email, String birthDate) {
        writeToElement(fullNameInput, fullName);
        writeToElement(emailInput, email);
        writeToElement(birthDateInput, birthDate);
        writeToElement(addressInput, "Факультет ВМК");
        writeToElement(phoneNoInput, "+7 (495) 939-08-36");
        click(addButton);
    }
}
