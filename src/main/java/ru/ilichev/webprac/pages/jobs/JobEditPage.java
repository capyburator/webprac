package ru.ilichev.webprac.pages.jobs;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;
import ru.ilichev.webprac.pages.NonMainPage;

@LazyComponent
public class JobEditPage extends NonMainPage {
    @FindBy(how = How.XPATH, using = "//input[@type='submit']")
    private WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//input[@id='title']")
    private WebElement titleInput;

    @FindBy(how = How.XPATH, using = "//textarea[@id='description']")
    private WebElement descriptionInput;

    public JobEditPage editPage(String title, String description) {
        replaceElementContent(titleInput, title);
        replaceElementContent(descriptionInput, description);
        click(submitButton);
        return this;
    }
}
