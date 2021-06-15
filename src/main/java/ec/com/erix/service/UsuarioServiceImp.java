package ec.com.erix.service;

import ec.com.erix.domain.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ec.com.erix.dao.UsuarioDAO;
import org.springframework.context.annotation.Primary;

/**
 *
 * @author Erick Diaz
 */
@Primary // Define que si hay mas implementaciones, esta es la primaria y la que inyectara cuando no se defina otra
@Service("servicioUsuario")
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return this.usuarioDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(Usuario objeto) {
        this.usuarioDAO.save(objeto);
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        this.usuarioDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscar(Long id) {
        return this.usuarioDAO.findById(id).orElse(null);
    }

    @Override
    public Usuario buscarPorNombreDeUsuario(String nombreUsuario) {
        return this.usuarioDAO.findByUsername(nombreUsuario);
    }
    
}
