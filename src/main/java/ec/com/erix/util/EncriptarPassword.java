package ec.com.erix.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Erick Diaz
 */
public class EncriptarPassword {
    
    public static void main(String[] args) {
        String contrasena = "123";
        System.out.println("contrasena = " + contrasena);
        System.out.println("contrasena encriptada = " + encriptarPassword(contrasena));
    }
    
    public static String encriptarPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    
}
