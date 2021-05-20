package ec.com.erix.service;

import ec.com.erix.dao.CategoriaDAO;
import ec.com.erix.domain.Categoria;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Erick Diaz
*/
@Service
public class CategoriaServiceImp implements CategoriaService {

    @Autowired
    private CategoriaDAO categoriaDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<Categoria> listar() {
        return this.categoriaDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(Categoria objeto) {
        this.categoriaDAO.save(objeto);
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        this.categoriaDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria buscar(Long id) {
        return this.categoriaDAO.findById(id).orElse(null);
    }
}
