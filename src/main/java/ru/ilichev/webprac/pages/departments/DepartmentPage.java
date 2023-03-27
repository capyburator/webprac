package ru.ilichev.webprac.pages.departments;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;
import ru.ilichev.webprac.pages.DeletablePage;

import java.util.List;
import java.util.Optional;

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
        String parentName = readFromElement(parent.iterator().next());
        assertThat(parentName).isEqualTo(name);
    }

    public void verifyNameIs(String expected) {
        String nameContent = readFromElement(name);
        assertThat(nameContent).isEqualTo(expected);
    }

    public void verifyRemoveParentButtonIsEnabled() {
        String disabled = removeParentButton.getAttribute("ariaDisabled");
        assertThat(disabled).isEqualTo("false");
    }

    public void verifyRemoveParentButtonIsDisabled() {
        String disabled = removeParentButton.getAttribute("ariaDisabled");
        assertThat(disabled).isEqualTo("true");
    }

    public void removeParent() {
        verifyRemoveParentButtonIsEnabled();
        jsClick(removeParentButton);
    }

    public void verifyContainsEmployeesExactly(List<String> employeeFullNames) {
        verifyElementsContainsExactly(employees, employeeFullNames);
    }

    public void verifyContainsJobsExactly(List<String> jobTitles) {
        verifyElementsContainsExactly(jobs, jobTitles);
    }

    public void verifyContainsSubsidiariesExactly(List<String> subsidiaryNames) {
        verifyElementsContainsExactly(subsidiaries, subsidiaryNames);
    }

    public DepartmentPage verifyHasManager() {
        assertThat(manager).isNotEmpty();
        return this;
    }

    public void verifyHasNoManager() {
        assertThat(manager).isEmpty();
    }

    public void verifyManagerIs(String expected) {
        String fullName = readFromElement(manager.iterator().next());
        assertThat(fullName).isEqualTo(expected);
    }

    public void goToSubsidiaryAddPage() {
        click(addSubsidiaryButton);
    }

    public void goToSubsidiaryPageByName(String name) {
        WebElement subsidiaryLink = driver.findElement(By.xpath("//li/a[text()='" + name + "']"));
        click(subsidiaryLink);
    }
}
