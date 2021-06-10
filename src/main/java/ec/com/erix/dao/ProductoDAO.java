package ec.com.erix.dao;

import ec.com.erix.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Erick Diaz
 */
public interface ProductoDAO extends JpaRepository<Producto, Long> {

//    @Query("select p from Producto p where p.categoria.idCategoria = ?1")
//    List<Producto> findByCategoria(Long categoria);
}
