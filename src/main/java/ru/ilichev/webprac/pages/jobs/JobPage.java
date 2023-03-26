package ru.ilichev.webprac.pages.jobs;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;
import ru.ilichev.webprac.pages.DeletablePage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@LazyComponent
public class JobPage extends DeletablePage {
    @FindBy(how = How.XPATH, using = "//p[contains(text(), 'Название')]/span")
    private WebElement title;

    @FindBy(how = How.XPATH, using = "//p[contains(text(), 'Описание')]/span")
    private WebElement description;

    @FindBy(how = How.XPATH, using = "//*/li/a[contains(@class, 'text-primary')]")
    private List<WebElement> employees;

    public JobPage verifyTitleIs(String expected) {
        String titleContent = readFromElement(title);
        assertThat(titleContent).isEqualTo(expected);
        return this;
    }

    public JobPage verifyDescriptionIs(String expected) {
        String descriptionContent = readFromElement(description);
        assertThat(descriptionContent).isEqualTo(expected);
        return this;
    }

    public JobPage verifyNumberOfEmployeesIs(int count) {
        assertThat(employees).hasSize(count);
        return this;
    }
}
