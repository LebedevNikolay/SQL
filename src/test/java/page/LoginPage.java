package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import mode.DbUtils;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");

    public void verifyErrorerrorNotification(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    public VerificationPage validLogin(DbUtils.AuthInfo info) {
        login(info);
        return new VerificationPage();
    }
    public void login(DbUtils.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }
}