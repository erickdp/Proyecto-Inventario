package ec.com.erix.service;

import java.util.List;

/**
 *
 * @author Erick Diaz
 */
public interface BaseService<T, G extends Number> {

    List<T> listar();

    void guardar(T objeto);

    void eliminarPorId(G id);

    T buscar(G id);

}
