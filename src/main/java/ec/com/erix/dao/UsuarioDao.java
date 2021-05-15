package ec.com.erix.dao;

import ec.com.erix.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Erick Diaz
 */
public interface UsuarioDao extends JpaRepository<Usuario, Long> {
    
}
