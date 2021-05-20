package ec.com.erix.dao;

import ec.com.erix.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Erick Diaz
 */
public interface ProductoDAO extends JpaRepository<Producto, Long> {

}
