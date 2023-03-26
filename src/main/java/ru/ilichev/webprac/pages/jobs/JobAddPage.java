package ru.ilichev.webprac.pages.jobs;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;
import ru.ilichev.webprac.pages.NonMainPage;

@LazyComponent
public class JobAddPage extends NonMainPage {
    @FindBy(how = How.XPATH, using = "//input[@type='submit']")
    private WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//input[@id='title']")
    private WebElement titleInput;

    @FindBy(how = How.XPATH, using = "//textarea[@id='description']")
    private WebElement descriptionInput;

    public JobAddPage addPage(String title, String description) {
        writeToElement(titleInput, title);
        writeToElement(descriptionInput, description);
        click(submitButton);
        return this;
    }

    public JobAddPage verifyAddFailed(String expectedErrorMessage) {
        WebElement error = driver.findElement(By.xpath("//div[contains(@class, 'text-danger')]"));
        String errorMessage = readFromElement(error);
        Assertions.assertThat(errorMessage).isEqualTo(expectedErrorMessage);
        return this;
    }
}
