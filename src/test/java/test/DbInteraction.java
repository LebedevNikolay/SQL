package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import mode.DbUtils;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;

import static SQLHelper.cleanAuthCodes;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbInteraction {
    DbUtils.AuthInfo authInfo = DbUtils.getAuthInfoWithTestData();

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @BeforeEach
    void setUp() {
        LoginPage loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    @DisplayName(Should successfully login to dashboard with exist logn and password from sut test data")
            void shouldSuccessfulLogin(){
            var verificationPage=loginPage.validLogin(authInfo);
            var verificationCode=SQLHelper.getVerificationCode();
            verificationPage.validVerify(verificationCode.getCode());
            }

            @Test
            @DisplayName("Should get error notification if user is not exist in base")
            void shouldGetErrorNotificationIfLoginWithRandomUserWithoutAddingToBase(){
            var authInfo=DbUtils.generateRandomUser();
            loginPage.login(authInfo);
            loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
            }
}