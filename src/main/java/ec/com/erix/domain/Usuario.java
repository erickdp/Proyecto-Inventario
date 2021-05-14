package ec.com.erix.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.*;

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
    private Long idUsuario;

    @Column(name = "nombre_usuario")
    @NotEmpty
    private String nombreUsuario;

    @NotEmpty
    private String contrasena;

    private String estado;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_p_usuario", referencedColumnName = "id_persona") // name llave foranea en mi tabla, referencedColumn nombre en la tabla origen
    private Persona persona;

    @OneToMany
    @JoinColumn(name = "id_p_usuario") // El mapeo no es bidireccional, por esta razon hago un joinColum y con el nombre en la bbdd de la llave foranea en la tabla destino
    private List<Rol> roles;

    @OneToMany(mappedBy = "usuario", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private List<Factura> facturas;
}
