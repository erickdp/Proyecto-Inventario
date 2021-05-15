package ec.com.erix.dao;

import ec.com.erix.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Erick Diaz
 */
public interface PersonaDAO extends JpaRepository<Persona, Long> {
    
}
