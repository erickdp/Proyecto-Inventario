package ec.com.erix.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.*;

/**
 *
 * @author Erick Diaz
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "persona")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private int idPersona;

    @NotEmpty(message = "Cédula de identidad inválida")
    private String ci;

    @NotEmpty
    private String nombre;
    
    @NotEmpty
    private String apellido;
    
    @Email(message = "Debe ingresar un email valido")
    private String email;
    
    private String telefono;
    
}
