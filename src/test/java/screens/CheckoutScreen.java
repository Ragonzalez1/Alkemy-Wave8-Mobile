package screens;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Checkout completo: envio, pago, revision de la orden y confirmacion. */
public class CheckoutScreen extends BaseScreen {

    private static final String ID = "com.saucelabs.mydemoapp.android:id/";

    // Envio
    @AndroidFindBy(id = ID + "fullNameET")
    private WebElement nombreEnvio;

    @AndroidFindBy(id = ID + "address1ET")
    private WebElement direccion;

    @AndroidFindBy(id = ID + "cityET")
    private WebElement ciudad;

    @AndroidFindBy(id = ID + "zipET")
    private WebElement codigoPostal;

    @AndroidFindBy(id = ID + "countryET")
    private WebElement pais;

    // Pago
    @AndroidFindBy(id = ID + "nameET")
    private WebElement nombreTarjeta;

    @AndroidFindBy(id = ID + "cardNumberET")
    private WebElement numeroTarjeta;

    @AndroidFindBy(id = ID + "expirationDateET")
    private WebElement vencimiento;

    @AndroidFindBy(id = ID + "securityCodeET")
    private WebElement codigoSeguridad;

    // Boton principal: se reutiliza en envio, pago y revision de la orden
    @AndroidFindBy(id = ID + "paymentBtn")
    private WebElement botonContinuar;

    // Confirmacion
    @AndroidFindBy(id = ID + "completeTV")
    private WebElement mensajeCompra;

    public CheckoutScreen(AndroidDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void completarEnvio(String nombre, String dir, String ciudadEnvio, String zip, String paisEnvio) {
        escribir(nombreEnvio, nombre);
        escribir(direccion, dir);
        escribir(ciudad, ciudadEnvio);
        escribir(codigoPostal, zip);
        escribir(pais, paisEnvio);
        tap(botonContinuar);
    }

    public void completarPago(String titular, String numero, String vence, String cvv) {
        escribir(nombreTarjeta, titular);
        escribir(numeroTarjeta, numero);
        escribir(vencimiento, vence);
        escribir(codigoSeguridad, cvv);
        tap(botonContinuar);
    }

    public void confirmarOrden() {
        tap(botonContinuar);
    }

    public String textoCompra() {
        return esperarVisible(mensajeCompra).getText();
    }
}
