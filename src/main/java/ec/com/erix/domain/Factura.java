package ec.com.erix.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@Table(name = "factura")
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;

    @Size(max = 10)
    @NotEmpty
    private String codigoFactura;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;
    
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) // No se pone el mapped por que no es bidireccional
    @JoinColumn(name = "id_factura") // El mapeo no es bidireccional, defino la llave foranea que tiene la tabla DetalleFactura
    private List<DetalleFactura> detallesFactura;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_factura")
    private Date fechaFactura;

    @NotNull
    private double subtotal;

    @NotNull
    private double iva;

    @NotNull
    private double total;
}
