package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ru.ilichev.webprac.annotation.LazyAutowired;
import ru.ilichev.webprac.annotation.TakeScreenshot;
import ru.ilichev.webprac.pages.ListPage;
import ru.ilichev.webprac.pages.MainPage;
import ru.ilichev.webprac.pages.jobs.JobAddPage;
import ru.ilichev.webprac.pages.jobs.JobEditPage;
import ru.ilichev.webprac.pages.jobs.JobPage;

public class JobSteps {
    @LazyAutowired
    private MainPage mainPage;

    @LazyAutowired
    private ListPage jobsListPage;

    @LazyAutowired
    private JobPage jobPage;

    @LazyAutowired
    private JobAddPage jobAddPage;

    @LazyAutowired
    private JobEditPage jobEditPage;

    @Given("Я нахожусь на странице со списком должностей")
    public void iAmAtJobsListPage() {
        mainPage
                .goToMainPage()
                .goToJobsPage();
    }

    @When("Я перехожу по ссылке с должностью {string}")
    public void iClickLinkWithTitle(String title) {
        jobsListPage
                .verifyContains(title)
                .goToPageByName(title);
    }

    @Then("Я вижу название должности {string} и описание {string}")
    public void iSeeJobTitleAndDescription(String title, String description) {
        jobPage
                .verifyTitleIs(title)
                .verifyDescriptionIs(description);
    }

    @And("Количество сотрудников на должности равно {int}")
    public void numberOfEmployeesIs(int count) {
        jobPage.verifyNumberOfEmployeesIs(count);
    }

    @When("Я нажимаю на кнопку Добавить должность")
    public void iClickAddNewJob() {
        jobsListPage.goToAddPage();
    }

    @Then("Я добавляю должность с названием {string} и описанием {string}")
    public void iAddJob(String title, String description) {
        jobAddPage.addPage(title, description);
    }

    @Then("Я редактирую должность названием {string} и описанием {string}")
    public void iEditJob(String title, String description) {
        jobEditPage.editPage(title, description);
    }

    @And("Я вижу сообщение об ошибке {string}")
    public void iSeeErrorMessage(String expectedErrorMessage) {
        jobAddPage.verifyAddFailed(expectedErrorMessage);
    }

    @When("Я нажимаю на кнопку Редактировать")
    public void iClickEditButton() {
        jobPage.goToEditPage();
    }

    @TakeScreenshot
    @And("Кнопка удаления неактивна")
    public void deleteButtonIsDisabled() {
        jobPage.verifyDeleteButtonIsDisabled();
    }

    @TakeScreenshot
    @And("Кнопка удаления активна")
    public void deleteButtonIsEnabled() {
        jobPage.verifyDeleteButtonIsEnabled();
    }

    @When("Я нажимаю на кнопку Удалить")
    public void deletePage() {
        jobPage.deletePageAccept();
    }
}
