import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import screens.CartScreen;
import screens.CheckoutScreen;
import screens.HomeScreen;
import screens.LoginScreen;
import screens.ProductScreen;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Pruebas del flujo de e-commerce usando Screen Objects (Page Factory). */
public class FlujoEcommerceTest extends BaseTest {

    private static final String USUARIO = "bob@example.com";
    private static final String PASSWORD = "10203040";

    private HomeScreen home;
    private LoginScreen login;
    private ProductScreen producto;
    private CartScreen carrito;
    private CheckoutScreen checkout;

    // Se ejecuta despues del @BeforeEach de BaseTest, cuando driver y wait ya existen
    @BeforeEach
    void crearScreens() {
        home = new HomeScreen(driver, wait);
        login = new LoginScreen(driver, wait);
        producto = new ProductScreen(driver, wait);
        carrito = new CartScreen(driver, wait);
        checkout = new CheckoutScreen(driver, wait);
    }

    private void iniciarSesion() {
        assertTrue(home.muestraCatalogo());
        home.irALogin();
        login.ingresar(USUARIO, PASSWORD);
        assertTrue(home.muestraCatalogo(), "Tras el login debe verse el catalogo");
    }

    @Test
    void loginExitoso() {
        iniciarSesion();
    }

    @Test
    void loginConCredencialesInvalidas() {
        home.irALogin();
        login.ingresar(USUARIO, "clave-incorrecta");
        assertTrue(login.estaEnLogin(), "Con credenciales invalidas se permanece en Login");
        assertFalse(home.textoTitulo().contains("Products"));
    }

    @Test
    void agregarProductoAlCarrito() {
        assertTrue(home.muestraCatalogo());
        home.abrirPrimerProducto();
        assertTrue(producto.textoTitulo().contains("Backpack"));
        producto.agregarAlCarrito();
        producto.abrirCarrito();
        assertTrue(carrito.textoProducto().contains("Backpack"));
    }

    @Test
    void flujoCompletoDeCompraYLogout() {
        iniciarSesion();

        home.abrirPrimerProducto();
        assertTrue(producto.textoTitulo().contains("Backpack"));
        assertTrue(producto.textoPrecio().contains("29.99"));
        producto.agregarAlCarrito();
        producto.abrirCarrito();

        assertTrue(carrito.textoProducto().contains("Backpack"));
        carrito.irACheckout();

        checkout.completarEnvio("Ramiro Gonzalez", "Calle 123", "Buenos Aires", "1000", "Argentina");
        checkout.completarPago("Ramiro Gonzalez", "325812657568789", "03/28", "123");
        checkout.confirmarOrden();
        assertTrue(checkout.textoCompra().contains("Checkout Complete"));

        home.cerrarSesion();
        assertTrue(login.estaEnLogin(), "Tras el logout la app vuelve a Login");
    }
}
