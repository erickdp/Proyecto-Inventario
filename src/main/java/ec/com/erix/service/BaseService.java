package ec.com.erix.service;

import java.util.List;

/**
 *
 * @author Erick Diaz
 */
public interface BaseService<T> {

    List<T> listar();

    void guardar(T objeto);

    void eliminar(T objeto);

    T buscar(T objeto);

}
