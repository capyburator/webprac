package ru.ilichev.webprac.pages.employees;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;
import ru.ilichev.webprac.pages.DeletablePage;

import java.util.List;

@LazyComponent
public class EmployeePage extends DeletablePage {
    @FindBy(how = How.XPATH, using = "//p[contains(text(), 'Имя')]/span")
    private WebElement fullName;

    @FindBy(how = How.XPATH, using = "//p[contains(text(), 'Email')]/span")
    private WebElement email;

    @FindBy(how = How.XPATH, using = "//p[contains(text(), 'Дата рождения')]/span")
    private WebElement birthDate;

    @FindBy(how = How.XPATH, using = "//input[contains(@value, 'Снять')]")
    private WebElement removeFromCurrentJobButton;

    @FindBy(how = How.XPATH, using = "//a[contains(text(), 'Назначить')]")
    private WebElement assignNewJobButton;

    @FindBy(how = How.XPATH, using = "//table/tbody/tr/td[5][not(text())]/../td[2]")
    private List<WebElement> currentJob;

    @FindBy(how = How.XPATH, using = "//table/tbody/tr/td[5][not(text())]/../td[3]")
    private List<WebElement> currentDepartment;

    @FindBy(how = How.XPATH, using = "//table/tbody/tr/td[2]")
    private List<WebElement> jobHistoryJobs;

    @FindBy(how = How.XPATH, using = "//table/tbody/tr/td[3]")
    private List<WebElement> jobHistoryDepartments;

    public EmployeePage verifyFullNameIs(String expected) {
        verifyElementContentIs(fullName, expected);
        return this;
    }

    public EmployeePage verifyEmailIs(String expected) {
        verifyElementContentIs(email, expected);
        return this;
    }

    public EmployeePage verifyBirthDateIs(String expected) {
        verifyElementContentIs(birthDate, expected);
        return this;
    }

    public EmployeePage verifyRemoveFromCurrentJobButtonIsEnabled() {
        verifyElementIsEnabled(removeFromCurrentJobButton);
        return this;
    }

    public EmployeePage verifyRemoveFromCurrentJobButtonIsDisabled() {
        verifyElementIsDisabled(removeFromCurrentJobButton);
        return this;
    }

    public EmployeePage verifyAssignNewJobButtonIsEnabled() {
        verifyElementIsEnabled(assignNewJobButton);
        return this;
    }

    public EmployeePage verifyAssignNewJobButtonIsDisabled() {
        verifyElementIsDisabled(assignNewJobButton);
        return this;
    }

    public EmployeePage verifyHasCurrentJob() {
        Assertions.assertThat(currentJob).isNotEmpty();
        return this;
    }

    public EmployeePage verifyHasNoCurrentJob() {
        Assertions.assertThat(currentJob).isEmpty();
        return this;
    }

    public EmployeePage verifyCurrentJobIs(String expected) {
        verifyHasCurrentJob().verifyElementContentIs(currentJob.iterator().next(), expected);
        return this;
    }

    public EmployeePage verifyCurrentDepartmentIs(String expected) {
        verifyHasCurrentJob().verifyElementContentIs(currentDepartment.iterator().next(), expected);
        return this;
    }

    public EmployeePage verifyJobsContainsExactly(List<String> jobsList) {
        Assertions.assertThat(jobHistoryJobs)
                .map(this::readFromElement)
                .containsExactlyElementsOf(jobsList);
        return this;
    }

    public EmployeePage verifyDepartmentsContainsExactly(List<String> departmentsList) {
        Assertions.assertThat(jobHistoryDepartments)
                .map(this::readFromElement)
                .containsExactlyElementsOf(departmentsList);
        return this;
    }

    public void removeFromCurrentJob() {
        verifyRemoveFromCurrentJobButtonIsEnabled().jsClick(removeFromCurrentJobButton);
    }

    public void goToAssignNewJobPage() {
        verifyAssignNewJobButtonIsEnabled().click(assignNewJobButton);
    }
}
