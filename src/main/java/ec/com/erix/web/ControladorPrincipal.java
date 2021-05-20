package ec.com.erix.web;

import ec.com.erix.domain.Categoria;
import ec.com.erix.domain.Producto;
import ec.com.erix.service.CategoriaService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Erick Diaz
 */
@Controller
@RequestMapping("/inventario") // Este se llama ruta de primer nivel
@Slf4j
public class ControladorPrincipal {

    @Autowired
    private CategoriaService categoriaService;

    private static List<Producto> productosAsociados;

//    Se llaman ruta de segundo nivel del metodo handler
//    Primero se debe de pasar por el primer nivel y luego esta ruta
    @GetMapping({"/", "/inicio"})
    public String inicio(Model model, @AuthenticationPrincipal User user) {

        log.info("Usuario accedido " + user);

        var categorias = this.categoriaService.listar();
        model.addAttribute("categorias", categorias);
        model.addAttribute("totalCategorias", categorias.size());

        return "index"; // Como el index esta en la raiz de templates no se debe de poner /ruta/index
    }

//    Para redireccionar sin usar /editarCategoria/{idCategoria} y no se vea la en la URL se envia de esta forma /editarCategoria solamente
//    Usar request cuando se usa th:href
//    @RequestMapping("/editarCategoria")
//    public String editar(Model modelo, Categoria categoria) { // Se inyecta e inicializa un objeto de tipo categoria de manera automatica solo enviando el idCategoria y obteniendolo en {idCategoria}
//        var categoriaEditar = this.categoriaService.buscar(categoria);
//        log.info("Entrando al controlador para la redireccion");
//        log.info(categoriaEditar.toString());
//        this.productosAsociados = categoriaEditar.getProductos();
//        modelo.addAttribute("categoria", categoriaEditar);
//        return "formularioCategoria";
//    }
//    Ejemplo usando requestParam
    @RequestMapping("/editarCategoria")
    public String editar(Model modelo, @RequestParam(name = "idCategoria", required = false, defaultValue = "1") Long id) { // Request se usa cuando se desea de manera obligada o no un parametro
        var categoriaEditar = this.categoriaService.buscar(id);
        log.info("Entrando al controlador para la redireccion");
        log.info(categoriaEditar.toString());
        this.productosAsociados = categoriaEditar.getProductos();
        modelo.addAttribute("categoria", categoriaEditar);
        return "formularioCategoria";
    }

    @RequestMapping("/agregarCategoria")
    public String agregar(Categoria categoria) {
        return "formularioCategoria";
    }

//    El error esta al momento de actualizar la lista de productos asociados con la categoria REVISAR (ARREGLADO)
    @PostMapping("/guardarCategoria") // Post no se miran los parametros en la URL
    public String guardar(@Valid Categoria categoria, Errors errores) {
        log.info(categoria.toString());

        if (errores.hasErrors()) {
            return "formularioCategoria";
        }
        categoria.setProductos(this.productosAsociados);
        log.info("Agregando relacion de productos: " + categoria.toString());

        this.categoriaService.guardar(categoria);
        
        return "redirect:/inventario/";
    }
    
    @RequestMapping("/cancelar")
    public String cancelarForm() {
        this.productosAsociados = null;
        return "redirect:/inventario/";
    }

    @GetMapping("/eliminarCategoria")
    public String eliminar(@RequestParam(name = "idCategoria") Long id) { // Se deberia de quitar el nombre de idCategoria
        this.categoriaService.eliminarPorId(id);
        return "redirect:/inventario/";
    }

}
