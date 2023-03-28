package ru.ilichev.webprac.pages.departments;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;
import ru.ilichev.webprac.pages.DeletablePage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@LazyComponent
public class DepartmentPage extends DeletablePage {
    @FindBy(how = How.XPATH, using = "//p[contains(text(), 'Название')]/span")
    private WebElement name;

    @FindBy(how = How.XPATH, using = "//a[contains(text(), 'Добавить')]")
    private WebElement addSubsidiaryButton;

    @FindBy(how = How.XPATH, using = "//input[contains(@value, 'Сбросить')]")
    private WebElement removeParentButton;

    @FindBy(how = How.XPATH, using = "//p[contains(text(), 'Начальник отдела')]/a")
    private List<WebElement> manager;

    @FindBy(how = How.XPATH, using = "//*/p[contains(text(), 'Головное подразделение')]/a")
    private List<WebElement> parent;

    @FindBy(how = How.XPATH, using = "//li/a[contains(@class, 'text-primary')]")
    private List<WebElement> subsidiaries;

    @FindBy(how = How.XPATH, using = "//td/a")
    private List<WebElement> employees;

    @FindBy(how = How.XPATH, using = "//table[2]/tbody/tr/td[1]")
    private List<WebElement> jobs;

    public DepartmentPage verifyHasParent() {
        assertThat(parent).isNotEmpty();
        return this;
    }

    public void verifyHasNoParent() {
        assertThat(parent).isEmpty();
    }

    public void verifyParentIs(String name) {
        verifyHasParent().verifyElementContentIs(parent.iterator().next(), name);
    }

    public void verifyNameIs(String expected) {
        verifyElementContentIs(name, expected);
    }

    public void verifyRemoveParentButtonIsEnabled() {
        verifyElementIsEnabled(removeParentButton);
    }

    public void verifyRemoveParentButtonIsDisabled() {
        verifyElementIsDisabled(removeParentButton);
    }

    public void removeParent() {
        verifyRemoveParentButtonIsEnabled();
        jsClick(removeParentButton);
    }

    public void verifyContainsEmployeesExactly(List<String> employeeFullNames) {
        verifyElementsContainExactlyInAnyOrder(employees, employeeFullNames);
    }

    public void verifyContainsJobsExactly(List<String> jobTitles) {
        verifyElementsContainExactlyInAnyOrder(jobs, jobTitles);
    }

    public void verifyContainsSubsidiariesExactlyInAnyOrder(List<String> subsidiaryNames) {
        verifyElementsContainExactlyInAnyOrder(subsidiaries, subsidiaryNames);
    }

    public DepartmentPage verifyHasManager() {
        assertThat(manager).isNotEmpty();
        return this;
    }

    public void verifyHasNoManager() {
        assertThat(manager).isEmpty();
    }

    public void verifyManagerIs(String expected) {
        verifyHasManager().verifyElementContentIs(manager.iterator().next(), expected);
    }

    public void goToSubsidiaryAddPage() {
        click(addSubsidiaryButton);
    }

    public void goToSubsidiaryPageByName(String name) {
        WebElement subsidiaryLink = driver.findElement(By.xpath("//li/a[text()='" + name + "']"));
        click(subsidiaryLink);
    }
}
