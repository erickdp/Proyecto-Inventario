package ec.com.erix.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Erick Diaz
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usuario")
public class Usuario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_p_usuario")
    private int idUsuario;
    
    @Column(name = "nombre_usuario")
    @NotEmpty
    private String nombreUsuario;
    
    @NotEmpty
    private String contrasena;

    private String estado;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_p_usuario", referencedColumnName = "id_persona")
    private Persona persona;
    
}
