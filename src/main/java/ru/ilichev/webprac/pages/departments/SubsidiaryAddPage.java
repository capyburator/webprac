package ru.ilichev.webprac.pages.departments;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.ilichev.webprac.annotation.LazyComponent;
import ru.ilichev.webprac.pages.NonMainPage;

import static org.assertj.core.api.Assertions.assertThat;

@LazyComponent
public class SubsidiaryAddPage extends NonMainPage {
    @FindBy(how = How.XPATH, using = "//input[@type='submit']")
    private WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//select[@id='department']/option")
    private WebElement department;

    @FindBy(how = How.XPATH, using = "//select[@id='subsidiary']")
    private WebElement subsidiarySelect;

    public SubsidiaryAddPage verifyDepartmentNameIs(String expected) {
        String text = readFromElement(department);
        assertThat(text).isEqualTo(expected);
        return this;
    }

    public void addSubsidiary(String name) {
        click(subsidiarySelect);
        WebElement option = driver.findElement(
                By.xpath("//select[@id='subsidiary']/option[text()='" + name + "']"));
        click(option);
        click(submitButton);
    }
}
