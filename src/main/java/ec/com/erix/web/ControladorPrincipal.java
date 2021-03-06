package ec.com.erix.web;

import ec.com.erix.domain.Categoria;
import ec.com.erix.domain.Producto;
import ec.com.erix.service.CategoriaService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 *
 * @author Erick Diaz
 */
@Controller
@RequestMapping("/inventario") // Este se llama ruta de primer nivel
@SessionAttributes({"categoriaN", "productosCarrito"}) // Mantiene los objetos que no estan mapeados con los form como el id esto hasta terminarlos
@Slf4j
public class ControladorPrincipal {

    @Value("${desarrollador.autor}") // Inyecto valores definidos en texto.properties
    private String autor;

    @Autowired // Busca dentro del contenedor de spring este bean
//    @Qualifier("servicioUsuario") Se define el componente a inyectar, sustituye a primary
    private CategoriaService categoriaService;

    private static List<Producto> productosAsociados;

    public static Map<Long, Integer> productosId;

//    Se llaman ruta de segundo nivel del metodo handler
//    Primero se debe de pasar por el primer nivel y luego esta ruta
    @GetMapping({"/", "/inicio"})
    public String inicio(Model model, @AuthenticationPrincipal User user) {
        if (productosId == null) {
            productosId = new HashMap<>();
        }
        log.info("Usuario accedido " + user);
        log.warn("Desarrollador del proyecto: ".concat(autor));

        var categorias = this.categoriaService.listar();
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());
        
        int cantidad = 0;
        for (Integer value : ControladorPrincipal.productosId.values()) {
            cantidad += value;
        }
        
        model.addAttribute("productosCarrito", cantidad);

        return "index"; // Como el index esta en la raiz de templates no se debe de poner /ruta/index
    }

    /*
    Este metodo ayuda a aumentar los articulos que vaya a agregar al carrito, s?? se puede usar atributos estaticos en otra clase
     */
    @GetMapping("/articuloCarrito/{articulo}/{categoria}")
    public String articuloCarrito(
            @PathVariable(name = "articulo") Long identificadorArticulo,
            @PathVariable(name = "categoria") Long identificadorCategoria,
            Model modelo) {
        if (productosId.containsKey(identificadorArticulo)) { // Ya hay un articulo y se aumenta la canitadad
            productosId.put(identificadorArticulo, Integer.sum(productosId.get(identificadorArticulo), 1));
        } else { // Es un nuevo articulo agregado al carrito
            productosId.put(identificadorArticulo, 1);
        }
        return "redirect:/proInventario/listarProductos/".concat(String.valueOf(identificadorCategoria));
    }

//    Ejemplo usando requestParam
    @GetMapping("/editarCategoria")
    public String editar(Model modelo, @RequestParam(name = "idCategoria", required = false, defaultValue = "1") Long id) { // Request se usa cuando se desea de manera obligada o no un parametro
        var categoria = this.categoriaService.buscar(id);
//        ControladorPrincipal.productosAsociados = categoriaN.getProductos();
        modelo.addAttribute("categoriaN", categoria);
        return "/forms/formularioCategoria";
    }

    @GetMapping("/agregarCategoria")
    public String agregar(Categoria categoria, Model modelo) { // @ModelAttribute es para definir el nombre con que se usan los objetos en los forms
        modelo.addAttribute("categoriaN", categoria);
        return "/forms/formularioCategoria";
    }

//    El error esta al momento de actualizar la lista de productos asociados con la categoria REVISAR (ARREGLADO)
    @PostMapping("/guardarCategoria") // Post no se miran los parametros en la URL
    public String guardar(@Valid @ModelAttribute("categoriaN") Categoria categoria, Errors errores, SessionStatus status) {

        if (errores.hasErrors()) {
            return "/forms/formularioCategoria";
        }

        this.categoriaService.guardar(categoria);

        status.setComplete(); // Elimino los datos de tipo SessionAttribute

        return "redirect:/inventario/";
    }

    @GetMapping("/cancelar")
    public String cancelarForm(SessionStatus status) {
        status.setComplete();
        return "redirect:/inventario/"; // Si se redirige se hace un nuevo request (peticion) y se pierden todos los datos
    }

    @DeleteMapping("/eliminarCategoria")
    public String eliminar(@RequestParam(name = "idCategoria") Long id) { // Se deberia de quitar el nombre de idCategoria
        this.categoriaService.eliminarPorId(id);
        return "redirect:/inventario/";
    }

    @GetMapping("/productosAsociados/{categoria}")
    public String productosAsociados(@PathVariable(name = "categoria") Long cat) {
        return "redirect:/proInventario/listarProductos/".concat(String.valueOf(cat));
    }
    
    @GetMapping("/terminar")
    public String terminarTransaccion() {
        productosId = null;
        return "redirect:/inventario/";
    }

}
