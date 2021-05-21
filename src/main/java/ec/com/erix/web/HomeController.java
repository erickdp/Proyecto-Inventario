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
        return "redirect:/inventario/inicio";
    }
    
}
