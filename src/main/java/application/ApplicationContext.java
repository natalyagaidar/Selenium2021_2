package application;

import org.openqa.selenium.WebDriver;

public class ApplicationContext {
    private WebDriver driver;
    private String baseUrl;
    private AppUser user;

    public WebDriver getDriver() {
        return driver;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public AppUser getUser() {
        return user;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public static class AppUser {

        String loginName;
        String password;

        public AppUser(String loginName, String password) {
            this.loginName = loginName;
            this.password = password;
        }

        public String getLoginName() {
            return loginName;
        }

        public String getPassword() {
            return password;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
