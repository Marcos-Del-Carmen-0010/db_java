package org.example.repositorio;
import java.sql.SQLException;
import java.util.List;

public interface Repositorio<T> {
    List<T> listar() throws SQLException;
    T buscar(Long id) throws SQLException;
    void guardarEditar(T t) throws SQLException;
    void eliminar(Long id) throws SQLException;
}
