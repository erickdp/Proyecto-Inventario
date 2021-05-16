package ec.com.erix.dao;

import ec.com.erix.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Erick Diaz
 */
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username); // Este metodo es necesario para trabajar con spring security
}
