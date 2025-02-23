  package netology.test;

import com.codeborne.selenide.Configuration;
import netology.data.DataHelper;
import netology.data.SQLHelper;
import netology.page.LoginPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static com.codeborne.selenide.Selenide.open;
import static netology.data.SQLHelper.cleanDatabase;

public class SqlLoginTest {

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;
        open("http://localhost:9999");
    }

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    void shouldSuccessFullLogin() throws SQLException {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        var dashboardPage = verificationPage.validVerify(verificationCode.getCode());
    }
}