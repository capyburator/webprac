package ru.ilichev.webprac.pages.departments;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;
import ru.ilichev.webprac.pages.NonMainPage;

@LazyComponent
public class DepartmentAddPage extends NonMainPage {
    @FindBy(how = How.XPATH, using = "//input[@type='submit']")
    private WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//input[@id='name']")
    private WebElement nameInput;

    public void addPage(String name) {
        writeToElement(nameInput, name);
        click(submitButton);
    }

    public void verifyAddFailed(String expectedErrorMessage) {
        WebElement error = driver.findElement(By.xpath("//div[contains(@class, 'text-danger')]"));
        String errorMessage = readFromElement(error);
        Assertions.assertThat(errorMessage).isEqualTo(expectedErrorMessage);
    }
}
