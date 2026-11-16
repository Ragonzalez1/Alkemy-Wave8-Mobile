package screens;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginScreen extends BaseScreen {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameET")
    private WebElement campoUsuario;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/passwordET")
    private WebElement campoPassword;

    @AndroidFindBy(accessibility = "Tap to login with given credentials")
    private WebElement botonLogin;

    public LoginScreen(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void esperarPantalla() {
        esperarVisible(campoUsuario);
    }

    public void escribirUsuario(String usuario) {
        escribir(campoUsuario, usuario);
    }

    public void escribirPassword(String password) {
        escribir(campoPassword, password);
    }

    public void tapLogin() {
        tap(botonLogin);
    }

    public void ingresar(String usuario, String password) {
        esperarPantalla();
        escribirUsuario(usuario);
        escribirPassword(password);
        tapLogin();
    }

    public boolean estaEnLogin() {
        return estaVisible(campoUsuario);
    }
}
