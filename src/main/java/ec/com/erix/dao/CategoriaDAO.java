package ec.com.erix.dao;

import ec.com.erix.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Erick Diaz
 */
public interface CategoriaDAO extends JpaRepository<Categoria, Long>{
    
}
