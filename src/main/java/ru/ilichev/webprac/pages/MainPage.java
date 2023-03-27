package ru.ilichev.webprac.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.springframework.beans.factory.annotation.Value;
import ru.ilichev.webprac.annotation.LazyComponent;

@LazyComponent
public class MainPage extends BasePage {
    @Value("${application.url}")
    private String URL;

    @FindBy(how = How.XPATH, using = "//a[text()='Сотрудники']")
    private WebElement employeesButton;

    @FindBy(how = How.XPATH, using = "//a[text()='Подразделения']")
    private WebElement departmentsButton;

    @FindBy(how = How.XPATH, using = "//a[text()='Должности']")
    private WebElement jobsButton;

    public MainPage goToMainPage() {
        driver.get(URL);
        return this;
    }

    public void goToDepartmentsPage() {
        click(departmentsButton);
    }

    public void goToEmployeesPage() {
        click(employeesButton);
    }

    public void goToJobsPage() {
        click(jobsButton);
    }
}
