package screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Clase base de todas las Screens. Inicializa los elementos con Page Factory
 * y concentra los elementos y metodos comunes (menu, carrito, esperas, tap,
 * escritura y scroll).
 */
public abstract class BaseScreen {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;

    // Elementos comunes a todas las pantallas (barra superior de la app)
    @AndroidFindBy(accessibility = "View menu")
    private WebElement menu;

    @AndroidFindBy(accessibility = "View cart")
    private WebElement iconoCarrito;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement titulo;

    protected BaseScreen(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        // Duration.ZERO: la espera la controlan las esperas explicitas de esta clase.
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ZERO), this);
    }

    // ----- Metodos comunes -----

    public void abrirMenu() {
        tap(menu);
    }

    public void abrirCarrito() {
        tap(iconoCarrito);
    }

    public String textoTitulo() {
        return esperarVisible(titulo).getText();
    }

    // ----- Esperas y acciones reutilizables -----

    protected WebElement esperarVisible(WebElement elemento) {
        return wait.until(ExpectedConditions.visibilityOf(elemento));
    }

    protected void tap(WebElement elemento) {
        wait.until(ExpectedConditions.elementToBeClickable(elemento)).click();
    }

    protected void escribir(WebElement campo, String texto) {
        WebElement visible = esperarVisible(campo);
        visible.clear();
        visible.sendKeys(texto);
        ocultarTeclado();
    }

    protected boolean estaVisible(WebElement elemento) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(elemento)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void ocultarTeclado() {
        try {
            if (driver.isKeyboardShown()) {
                driver.hideKeyboard();
            }
        } catch (Exception ignored) {
            // si el teclado no se puede ocultar, se continua
        }
    }

    protected void scrollHastaTexto(String texto) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"
                        + ".scrollIntoView(new UiSelector().text(\"" + texto + "\"))"));
    }
}
