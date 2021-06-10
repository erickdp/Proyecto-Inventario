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
    public String listarProductos(@PathVariable(name = "categoria") Long id, Producto producto, Model model) {
        var categoria = this.categoriaService.buscar(id);
        model.addAttribute("categoriaP", categoria);
        model.addAttribute("productos", categoria.getProductos());
        return "layout/listadoProductos";
    }

    @PostMapping("/agregarProducto")
    public String agregarProducto(@SessionAttribute(name = "categoriaP") Categoria categoria, Producto producto, SessionStatus status) {
        producto.setCategoria(categoria); // Se agrega al producto la categoria
        categoria.getProductos().add(producto); // Se agrega la categoria el producto
        this.categoriaService.guardar(categoria); // Se persiste la categoria al tener la generacion de cascada
        status.setComplete();                               
        return "redirect:/proInventario/listarProductos/".concat(String.valueOf(categoria.getIdCategoria()));
    }

}
