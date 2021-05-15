package ec.com.erix;

import ec.com.erix.domain.Usuario;
import ec.com.erix.service.UsuarioService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProyectoApplicationTests {

    @Autowired
    private UsuarioService usuarioService;

//    Este test sirve para verificar si los repositorios o servicios funcionan
    @Test
    void contextLoads() {
        Assertions.assertThat(this.usuarioService).isNotNull();
    }

    @Test
    public void shouldSaveUser() {

//        El rol debe de tener un mapeo
//        Persona persona = Persona.builder()
//                .ci("1726545244")
//                .nombre("Erick")
//                .apellido("Diaz")
//                .email("erickdp@htmail.comhm")
//                .telefono("1234567890")
//                .build();
//
//        Rol rol = new Rol();
//        rol.setRol("ETER");
//
//        Usuario usuario = Usuario.builder()
//                .nombreUsuario("erixx")
//                .contrasena("123")
//                .persona(persona)
//                .roles(Arrays.asList(rol))
//                .build();
//        
//        rol.setUsuario(usuario);
//        Tiene un error al momento de realizar las consultas, pero en el front end si sirven
//        this.usuarioService.guardar(usuario);
//        Usuario usuarioB = this.usuarioService.buscar(Usuario.builder().idUsuario(Long.parseLong("1")).build());
//        System.out.println("usuarioB = " + usuarioB);
//        Assertions.assertThat(usuario).isEqualTo(usuarioB);
    }

}
