package ec.com.erix.service;

import ec.com.erix.domain.Usuario;

/**
 *
 * @author Erick Diaz
 */
public interface UsuarioService extends BaseService<Usuario, Long> {
    
    Usuario buscarPorNombreDeUsuario(String nombreUsuario);
    
}
