package ec.com.erix.dao;

import ec.com.erix.domain.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Erick Diaz
 */
public interface FacturaDAO extends JpaRepository<Factura, Long>{
    
}
