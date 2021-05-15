package ec.com.erix.service;

import ec.com.erix.dao.UsuarioDao;
import ec.com.erix.domain.Usuario;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Erick Diaz
 */
@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return this.usuarioDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Usuario objeto) {
        this.usuarioDao.save(objeto);
    }

    @Override
    @Transactional
    public void eliminar(Usuario objeto) {
        this.usuarioDao.delete(objeto);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscar(Usuario objeto) {
        return this.usuarioDao.findById(objeto.getIdUsuario()).orElse(null);
    }
    
}
