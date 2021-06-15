package ec.com.erix.web;

import ec.com.erix.domain.Categoria;
import ec.com.erix.domain.Producto;
import ec.com.erix.service.CategoriaService;
import ec.com.erix.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@SessionAttributes("categoriaP")
@Slf4j
public class ProductoController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listarProductos/{categoria}")
    public String listarProductos(@PathVariable(name = "categoria") Long id, 
            Producto producto,
            Model model) {
        var categoria = this.categoriaService.buscar(id);
        model.addAttribute("categoriaP", categoria);
        model.addAttribute("productos", categoria.getProductos());
        model.addAttribute("productosCarrito", ControladorPrincipal.productosId.size());
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

}
