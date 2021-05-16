package ec.com.erix.service;

import ec.com.erix.dao.UsuarioDAO;
import ec.com.erix.domain.Rol;
import ec.com.erix.domain.Usuario;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Erick Diaz
 * Se define la clase para obtener los roles de los usuarios
 */
@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        Usuario usuario = this.usuarioDAO.findByUsername(string);
        
        if(usuario == null) {
            throw new UsernameNotFoundException(string);
        }
        
        var roles = new ArrayList<GrantedAuthority>();
        
        for (Rol role : usuario.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getRol()));
        }
        
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }

}
