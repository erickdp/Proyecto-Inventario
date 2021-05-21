package ec.com.erix.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Erick Diaz
*/
@Controller
public class HomeController {
    
    @GetMapping("/")
    public String redirigirInicio() {
        return "redirect:/inventario/inicio"; // Reincia los parametros para el nuevo request
//        return "forward:/inventario/inicio"; // Los parametros se mantienen al hacer el request, no se puede hacer a paginas solo a conrtoladores
    }
    
}
