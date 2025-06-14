package test;

import org.junit.jupiter.api.*;
import mode.DbUtils;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;
import mode.SQLHelper;

import static com.codeborne.selenide.Selenide.open;
import static mode.SQLHelper.cleanAuthCodes;
import static mode.SQLHelper.cleanDatabase;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbInteraction {
    LoginPage loginPage;
    DbUtils.AuthInfo authInfo = DbUtils.getAuthInfoWithTestData();

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist logn and password from sut test data")
    void shouldSuccessfulLogin() {
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationIfLoginWithRandomUserWithoutAddingToBase() {
        var authInfo = DbUtils.generateRandomUser();
        loginPage.login(authInfo);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
    }
}