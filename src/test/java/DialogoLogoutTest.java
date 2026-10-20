import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DialogoLogoutTest extends BaseTest {

    private static final By TITULO = AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV");
    private static final By MENU = AppiumBy.accessibilityId("View menu");
    private static final By LOGIN_MENU = AppiumBy.accessibilityId("Login Menu Item");
    private static final By USUARIO = AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET");
    private static final By PASSWORD = AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET");
    private static final By BOTON_LOGIN = AppiumBy.accessibilityId("Tap to login with given credentials");
    private static final By TEXTO_DIALOG = AppiumBy.id("android:id/message");
    private static final By BOTON_LOGOUT = AppiumBy.accessibilityId("Logout Menu Item");
    private static final By CONFIRM_BOTON_LOGOUT = AppiumBy.id("android:id/button1");
    // Evidencia de que la sesión se cerró: la app vuelve a la pantalla de Login
    private static final By CAMPO_USUARIO_LOGIN = AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET");

    @Test
    void confirmarLogOut(){
        esperarVisible(TITULO);
        tap(MENU);
        tap(LOGIN_MENU);
        escribir(USUARIO,"bod@example.com");
        escribir(PASSWORD , "10203040");
        tap(BOTON_LOGIN);
        assertTrue(esperarVisible(TITULO).getText().contains("Products"));

        // Acciones necesarias para cerrar la sesión
        tap(MENU);
        tap(BOTON_LOGOUT);
        wait.until(this::dialogoVisible);
        assertTrue(esperarVisible(TEXTO_DIALOG).getText().contains("logout"));
        tap(CONFIRM_BOTON_LOGOUT);

        // Espera fluida (FluentWait): tiempo máximo, intervalo de polling y
        // excepciones ignoradas mientras la app termina de cerrar la sesión.
        Wait<WebDriver> esperaFluida = new FluentWait<>((WebDriver) driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        // Esperamos hasta tener EVIDENCIA real de que el logout finalizó:
        // la pantalla de Login (campo de usuario) vuelve a estar visible.
        WebElement campoUsuarioLogin = esperaFluida.until(this::pantallaLoginVisible);

        // Validación final: el usuario ya no está dentro de la sesión.
        assertTrue(campoUsuarioLogin.isDisplayed(),
                "El campo de usuario de la pantalla de Login debe estar visible tras el logout");
        assertTrue(driver.findElements(TITULO).isEmpty(),
                "La pantalla de Products no debe seguir visible: la sesión debe estar cerrada");
    }

    private boolean dialogoVisible(WebDriver d){
        List<WebElement> textos = d.findElements(TEXTO_DIALOG);
        return !textos.isEmpty() && textos.get(0).isDisplayed();
    }

    // Devuelve el campo de usuario de la pantalla de Login solo cuando está
    // realmente visible; en caso contrario FluentWait sigue reintentando.
    private WebElement pantallaLoginVisible(WebDriver d){
        List<WebElement> campos = d.findElements(CAMPO_USUARIO_LOGIN);
        if (!campos.isEmpty() && campos.get(0).isDisplayed()){
            return campos.get(0);
        }
        return null;
    }
}
