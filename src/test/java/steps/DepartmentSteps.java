package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ru.ilichev.webprac.annotation.LazyAutowired;
import ru.ilichev.webprac.pages.ListPage;
import ru.ilichev.webprac.pages.MainPage;
import ru.ilichev.webprac.pages.departments.DepartmentAddPage;
import ru.ilichev.webprac.pages.departments.DepartmentEditPage;
import ru.ilichev.webprac.pages.departments.DepartmentPage;
import ru.ilichev.webprac.pages.departments.SubsidiaryAddPage;
import ru.ilichev.webprac.util.ListUtil;

import java.util.List;

public class DepartmentSteps {
    @LazyAutowired
    private MainPage mainPage;

    @LazyAutowired
    private ListPage departmentsListPage;

    @LazyAutowired
    private DepartmentPage departmentPage;

    @LazyAutowired
    private DepartmentAddPage departmentAddPage;

    @LazyAutowired
    private DepartmentEditPage departmentEditPage;

    @LazyAutowired
    private SubsidiaryAddPage subsidiaryAddPage;

    @Given("Я нахожусь на странице со списком подразделений")
    public void iAmAtDepartmentsListPage() {
        mainPage
                .goToMainPage()
                .goToDepartmentsPage();
    }

    @When("Я перехожу по ссылке с подразделением {string}")
    public void iClickLinkWithName(String name) {
        departmentsListPage
                .verifyContains(name)
                .goToPageByName(name);
    }

    @Then("Я вижу название подразделения {string}")
    public void iSeeDepartmentName(String name) {
        departmentPage.verifyNameIs(name);
    }

    @And("Я вижу головное подразделение {string}")
    public void iSeeParent(String name) {
        if (name.isEmpty()) {
            departmentPage.verifyHasNoParent();
        } else {
            departmentPage
                    .verifyHasParent()
                    .verifyParentIs(name);
        }
    }

    @And("Я вижу сотрудников подразделения {string}")
    public void iSeeEmployees(String employeesCommaSeparated) {
        List<String> employees = ListUtil.commaSeparatedToList(employeesCommaSeparated);
        departmentPage.verifyContainsEmployeesExactly(employees);
    }

    @And("Я вижу должности подразделения {string}")
    public void iSeeJobs(String jobsCommaSeparated) {
        List<String> jobs = ListUtil.commaSeparatedToList(jobsCommaSeparated);
        departmentPage.verifyContainsJobsExactly(jobs);
    }

    @And("Я вижу руководителя подразделения {string}")
    public void iSeeManager(String managerFullName) {
        if (managerFullName.isEmpty()) {
            departmentPage.verifyHasNoManager();
        } else {
            departmentPage
                    .verifyHasManager()
                    .verifyManagerIs(managerFullName);
        }
    }

    @And("Я вижу внутренние подразделения {string}")
    public void iSeeSubsidiaries(String subsidiariesCommaSeparated) {
        List<String> subsidiaries = ListUtil.commaSeparatedToList(subsidiariesCommaSeparated);
        departmentPage.verifyContainsSubsidiariesExactly(subsidiaries);
    }

    @When("Я нажимаю на кнопку Добавить подразделение")
    public void iClickAddDepartmentButton() {
        departmentsListPage.goToAddPage();
    }

    @Then("Я добавляю подразделение с названием {string}")
    public void iAddDepartment(String name) {
        departmentAddPage.addPage(name);
    }

    @When("Я нажимаю на кнопку Редактировать подразделение")
    public void iClickEditDepartmentButton() {
        departmentPage.goToEditPage();
    }

    @Then("Я редактирую подразделение названием {string}")
    public void iEditDepartment(String name) {
        departmentEditPage.editPage(name);
    }

    @Then("Кнопка удаления подразделения активна")
    public void departmentDeleteButtonIsEnabled() {
        departmentPage.verifyDeleteButtonIsEnabled();;
    }

    @When("Я нажимаю на кнопку Удалить подразделение")
    public void iClickDepartmentDeleteButton() {
        departmentPage.deletePageAccept();
    }

    @And("Кнопка удаления подразделения неактивна")
    public void departmentDeleteButtonIsDisabled() {
        departmentPage.verifyDeleteButtonIsDisabled();
    }

    @And("Кнопка Сбросить головное подразделение активна")
    public void removeParentButtonIsEnabled() {
        departmentPage.verifyRemoveParentButtonIsEnabled();
    }

    @When("Я нажимаю на кнопку Сбросить головное подразделение")
    public void iClickRemoveParentButton() {
        departmentPage.removeParent();
    }

    @And("Кнопка Сбросить головное подразделение неактивна")
    public void removeParentButtonIsDisabled() {
        departmentPage.verifyRemoveParentButtonIsDisabled();
    }

    @And("Я вижу сообщение об ошибке добавления подразделения {string}")
    public void iSeeDepartmentAddError(String expectedErrorMessage) {
        departmentAddPage.verifyAddFailed(expectedErrorMessage);
    }

    @Then("Я нажимаю на кнопку Добавить внутреннее подразделение")
    public void iClickAddSubsidiaryButton() {
        departmentPage.goToSubsidiaryAddPage();
    }

    @And("Я добавляю внутреннее подразделение {string} для {string}")
    public void iAddSubsidiary(String subsidiaryName, String departmentName) {
        subsidiaryAddPage
                .verifyDepartmentNameIs(departmentName)
                .addSubsidiary(subsidiaryName);
    }

    @When("Я перехожу по ссылке с внутренним подразделением {string}")
    public void iClickSubsidiaryLink(String name) {
        departmentPage.goToSubsidiaryPageByName(name);
    }
}
