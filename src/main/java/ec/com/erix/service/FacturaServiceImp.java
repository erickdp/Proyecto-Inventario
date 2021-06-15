package ec.com.erix.service;

import ec.com.erix.dao.FacturaDAO;
import ec.com.erix.domain.Factura;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Erick Diaz
 */
@Service
public class FacturaServiceImp implements FacturaService {

    @Autowired
    private FacturaDAO facturaDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Factura> listar() {
        return this.facturaDAO.findAll();
    }

    @Override
    @Transactional
    public void guardar(Factura objeto) {
        this.facturaDAO.save(objeto);
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        this.facturaDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura buscar(Long id) {
        return this.facturaDAO.findById(id).orElse(null);
    }

}
