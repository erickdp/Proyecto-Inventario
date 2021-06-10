package ec.com.erix.service;

import ec.com.erix.dao.ProductoDAO;
import ec.com.erix.domain.Producto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Erick Diaz
 */
@Service
public class ProductoServiceImp implements ProductoService {

    @Autowired
    private ProductoDAO productoDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> listar() {
        return this.productoDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(Producto objeto) {
        this.productoDAO.save(objeto);
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        this.productoDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto buscar(Long id) {
        return this.productoDAO.findById(id).orElse(null);
    }

//    @Override
//    public List<Producto> buscarPorCategoria(Long categoria) {
//        return this.productoDAO.findByCategoria(categoria);
//    }

}
