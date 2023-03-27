package ru.ilichev.webprac.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;

@LazyComponent
public class AssignJobPage extends NonMainPage {
    @FindBy(how = How.XPATH, using = "//input[@type='submit']")
    private WebElement assignButton;

    @FindBy(how = How.XPATH, using = "//select[@id='employee']/option")
    private WebElement employee;

    @FindBy(how = How.XPATH, using = "//select[@id='department']")
    private WebElement departmentSelect;

    @FindBy(how = How.XPATH, using = "//select[@id='job']")
    private WebElement jobSelect;

    public void verifyEmployeeIs(String fullName) {
        verifyElementContentIs(employee, fullName);
    }

    public void assignNewJob(String fullName, String jobTitle, String departmentName) {
        verifyEmployeeIs(fullName);

        click(departmentSelect);
        WebElement department = driver.findElement(
                By.xpath("//select[@id='department']/option[text()='" + departmentName + "']"));
        click(department);

        click(jobSelect);
        WebElement job = driver.findElement(
                By.xpath("//select[@id='job']/option[text()='" + jobTitle + "']"));
        click(job);

        click(assignButton);
    }
}
