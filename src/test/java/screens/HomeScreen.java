package screens;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Catalogo de productos (pantalla principal tras abrir la app o iniciar sesion). */
public class HomeScreen extends BaseScreen {

    @AndroidFindBy(accessibility = "Login Menu Item")
    private WebElement menuLogin;

    @AndroidFindBy(accessibility = "Logout Menu Item")
    private WebElement menuLogout;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productIV\").instance(0)")
    private WebElement primerProducto;

    @AndroidFindBy(id = "android:id/message")
    private WebElement mensajeDialogo;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement botonConfirmarDialogo;

    public HomeScreen(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean muestraCatalogo() {
        return textoTitulo().contains("Products");
    }

    public void irALogin() {
        abrirMenu();
        tap(menuLogin);
    }

    public void abrirPrimerProducto() {
        tap(primerProducto);
    }

    public void cerrarSesion() {
        abrirMenu();
        tap(menuLogout);
        esperarVisible(mensajeDialogo);
        tap(botonConfirmarDialogo);
    }

    public String textoDialogo() {
        return esperarVisible(mensajeDialogo).getText();
    }
}
