package ec.com.erix.web;

import ec.com.erix.domain.Categoria;
import ec.com.erix.domain.DetalleFactura;
import ec.com.erix.domain.Factura;
import ec.com.erix.domain.Producto;
import ec.com.erix.service.CategoriaService;
import ec.com.erix.service.FacturaService;
import ec.com.erix.service.ProductoService;
import ec.com.erix.service.UsuarioService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 *
 * @author Erick Diaz
 */
@Controller
@RequestMapping("/proInventario")
@SessionAttributes({"categoriaP", "detallesFactura"})
@Slf4j
public class ProductoController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FacturaService facturaService;

    @GetMapping("/listarProductos/{categoria}")
    public String listarProductos(@PathVariable(name = "categoria") Long id,
            Producto producto,
            Model model) {
        var categoria = this.categoriaService.buscar(id);
        model.addAttribute("categoriaP", categoria);
        model.addAttribute("productos", categoria.getProductos());
        int cantidad = 0;
        for (Integer value : ControladorPrincipal.productosId.values()) {
            cantidad += value;
        }
        model.addAttribute("productosCarrito", cantidad);
        return "layout/listadoProductos";
    }

    @PostMapping("/agregarProducto")
    public String agregarProducto(@SessionAttribute(name = "categoriaP") Categoria categoria, Producto producto, SessionStatus status) {
        if (producto.getIdProducto() != null) { // Utilizo este metodo para editar los productos cuando tienen id sino es nuevo Producto
            producto.setCategoria(categoria);
            this.productoService.guardar(producto);
        } else {
            producto.setCategoria(categoria); // Se agrega al producto la categoria
            categoria.getProductos().add(producto); // Se agrega la categoria el producto
            this.categoriaService.guardar(categoria); // Se persiste la categoria al tener la generacion de cascada
            status.setComplete();
        }
        return "redirect:/proInventario/listarProductos/".concat(String.valueOf(categoria.getIdCategoria()));
    }

    @GetMapping("/eliminar")
    public String eliminarProducto(@RequestParam(name = "productoPK") Long identificador,
            SessionStatus status) {
        Producto producto = this.productoService.buscar(identificador);
        producto.setEstado("INACTIVO");
        this.productoService.guardar(producto);
        status.setComplete();
//        return "forward:/proInventario/listarProductos/".concat(String.valueOf(producto.getCategoria().getIdCategoria())); // LLama al metodo y cambia la URL
        return "redirect:/proInventario/listarProductos/".concat(String.valueOf(producto.getCategoria().getIdCategoria())); // LLama al metodo pero no cambia la URL
    }

    @GetMapping("/editar/{productoPK}")
    public String editarProducto(@PathVariable(name = "productoPK") Long identificador, Model modelo) {
        Producto producto = this.productoService.buscar(identificador);
        modelo.addAttribute("producto", producto);
        modelo.addAttribute("categoriaP", producto.getCategoria());
        modelo.addAttribute("productos", producto.getCategoria().getProductos());
        return "layout/listadoProductos";
    }

    @GetMapping("/carrito")
    public String verCarrito(Model modelo) {

        List<DetalleFactura> detallesFactura = new ArrayList<>();
        var articulosCarrito = ControladorPrincipal.productosId;
        for (Map.Entry<Long, Integer> entry : articulosCarrito.entrySet()) {
            Long identificador = entry.getKey();
            Integer cantidad = entry.getValue();

            var productoFacturar = this.productoService.buscar(identificador);

            detallesFactura.add(DetalleFactura.builder().
                    producto(productoFacturar)
                    .cantidad((int) cantidad)
                    .total(productoFacturar.getPrecioUnitario() * cantidad)
                    .build());

        }

        modelo.addAttribute("detallesFactura", detallesFactura);

        return "carritoForm";

    }

    /*
    Este metodo muestra la factura formada y la persiste
    */
    @GetMapping("/carrito/facturar")
    public String facturarCompra(
            @SessionAttribute("detallesFactura") List<DetalleFactura> detallesFactura,
            SessionStatus status,
            @AuthenticationPrincipal User user,
            Model modelo) {

        var codigoFactura = new StringBuilder();
        var random = new Random();
        for (int i = 0; i < 10; i++) {
            codigoFactura.append((char) (random.nextInt(26) + 'A'));
        }

        var detallesFacturaProcesar = detallesFactura;

        var subtotalProcesado = 0d;

        for (DetalleFactura detalleFactura : detallesFacturaProcesar) {
            subtotalProcesado += detalleFactura.getTotal();
        }

        var ivaProcesado = subtotalProcesado * 0.14d; // Se debe de redondear pero se deben usar valor Long desde el inicio

        var totalFactura = subtotalProcesado + ivaProcesado;

        var cliente = this.usuarioService.buscarPorNombreDeUsuario(user.getUsername());

        var nuevaFactura = Factura.builder()
                .codigoFactura(codigoFactura.toString())
                .fechaFactura(LocalDate.now())
                .usuario(cliente)
                .detallesFactura(detallesFactura)
                .subtotal(subtotalProcesado)
                .iva(ivaProcesado)
                .total(totalFactura)
                .build();
        
//        Se debe de poner en cada detalle la factura asociada para persistir
        for (DetalleFactura detalleFactura : detallesFacturaProcesar) {
            detalleFactura.setFactura(nuevaFactura);
        }

        this.facturaService.guardar(nuevaFactura); 
        
        modelo.addAttribute("factura", nuevaFactura);
        modelo.addAttribute("detallesFactura", detallesFacturaProcesar);
        
        status.setComplete();
        
        return "facturaForm";
    }
}
