package ec.com.erix.web;

import ec.com.erix.domain.Rol;
import ec.com.erix.domain.Usuario;
import ec.com.erix.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Erick Diaz
 */
@Controller
@Slf4j
public class ControladorPrincipal {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String inicio(Model model) {
        var usuario = this.usuarioService.buscar(Usuario.builder().idUsuario(Long.parseLong("1")).build());
        log.info(usuario.getNombreUsuario() + " " + usuario.getContrasena());
        for (Rol role : usuario.getRoles()) {
            log.info(role.getRol());
        }
        model.addAttribute("usuario", usuario);
        return "index";
    }
}
