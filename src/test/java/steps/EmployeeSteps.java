package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ru.ilichev.webprac.annotation.LazyAutowired;
import ru.ilichev.webprac.pages.AssignJobPage;
import ru.ilichev.webprac.pages.ListPage;
import ru.ilichev.webprac.pages.MainPage;
import ru.ilichev.webprac.pages.employees.EmployeeAddPage;
import ru.ilichev.webprac.pages.employees.EmployeeEditPage;
import ru.ilichev.webprac.pages.employees.EmployeePage;
import ru.ilichev.webprac.util.ListUtil;

import java.util.List;

public class EmployeeSteps {
    @LazyAutowired
    private MainPage mainPage;

    @LazyAutowired
    private ListPage employeeListPage;

    @LazyAutowired
    private EmployeePage employeePage;

    @LazyAutowired
    private EmployeeAddPage employeeAddPage;

    @LazyAutowired
    private EmployeeEditPage employeeEditPage;

    @LazyAutowired
    private AssignJobPage assignJobPage;

    @Given("Я нахожусь на странице со списком сотрудников")
    public void iAmAtEmployeeListPage() {
        mainPage
                .goToMainPage()
                .goToEmployeesPage();
    }


    @When("Я перехожу по ссылке с сотрудником {string}")
    public void iClickLinkWithEmployeeName(String fullName) {
        employeeListPage
                .verifyContains(fullName)
                .goToPageByName(fullName);
    }

    @Then("Я вижу имя {string}, почту {string} и дату рождения {string}")
    public void iSeeFullNameEmailBirthDate(String fullName, String email, String birthDate) {
        employeePage
                .verifyFullNameIs(fullName)
                .verifyEmailIs(email)
                .verifyBirthDateIs(birthDate);
    }

    @And("Я вижу текущую должность {string} и текущее подразделение {string}")
    public void iSeeCurrentJobAndCurrentDepartment(String job, String department) {
        if (job.isEmpty() && department.isEmpty()) {
            employeePage.verifyHasNoCurrentJob();
        } else {
            employeePage
                    .verifyHasCurrentJob()
                    .verifyCurrentJobIs(job)
                    .verifyCurrentDepartmentIs(department);
        }
    }

    @And("Я вижу историю должностей с должностями {string} и подразделениями {string}")
    public void iSeeJobsHistory(String jobsCommaSeparated, String departmentsCommaSeparated) {
        List<String> jobs = ListUtil.commaSeparatedToList(jobsCommaSeparated);
        List<String> departments = ListUtil.commaSeparatedToList(departmentsCommaSeparated);
        employeePage
                .verifyJobsContainsExactly(jobs)
                .verifyDepartmentsContainsExactly(departments);
    }

    @When("Я нажимаю на кнопку Добавить сотрудника")
    public void iClickAddNewEmployeeButton() {
        employeeListPage.goToAddPage();
    }

    @Then("Я добавляю сотрудника с именем {string}, почтой {string} и датой рождения {string}")
    public void iAddNewEmployeeWithFullNameEmailBirthDate(String fullName, String email, String birthDate) {
        employeeAddPage.addEmployee(fullName, email, birthDate);
    }

    @When("Я нажимаю на кнопку Редактировать сотрудника")
    public void iClickEditEmployeeButton() {
        employeePage.goToEditPage();
    }

    @Then("Я редактирую сотрудника именем {string}, почтой {string} и датой рождения {string}")
    public void iEditEmployeeWithFullNameEmailBirthDate(String fullName, String email, String birthDate) {
        employeeEditPage.editEmployee(fullName, email, birthDate);
    }

    @When("Я нажимаю на кнопку Снять с текущей должности")
    public void iClickRemoveFromCurrentJobButton() {
        employeePage.removeFromCurrentJob();
    }

    @And("Кнопка Снять с текущей должности активна")
    public void removeFromCurrentJobButtonIsEnabled() {
        employeePage.verifyRemoveFromCurrentJobButtonIsEnabled();
    }

    @And("Кнопка снятия с текущей должности неактивна")
    public void removeFromCurrentJobButtonIsDisabled() {
        employeePage.verifyRemoveFromCurrentJobButtonIsDisabled();
    }

    @And("Кнопка Назначить на новую должность неактивна")
    public void assignNewJobButtonIsDisabled() {
        employeePage.verifyAssignNewJobButtonIsDisabled();
    }

    @And("Кнопка Назначить на новую должность активна")
    public void assignNewJobButtonIsEnabled() {
        employeePage.verifyAssignNewJobButtonIsEnabled();
    }

    @When("Я назначаю сотрудника {string} на должность {string} в подразделении {string}")
    public void iAssignNewJobAndDepartmentForEmployee(String fullName, String jobTitle, String departmentName) {
        assignJobPage.assignNewJob(fullName, jobTitle, departmentName);
    }

    @When("Я нажимаю на кнопку Назначить на новую должность")
    public void iClickAssignNewJobButton() {
        employeePage.goToAssignNewJobPage();
    }

    @Then("Я нажимаю на кнопку Удалить сотрудника")
    public void iClickDeleteEmployeeButton() {
        employeePage.deletePageAccept();
    }
}
