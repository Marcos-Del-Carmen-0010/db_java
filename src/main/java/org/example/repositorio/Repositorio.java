package org.example.repositorio;
import java.util.List;

public interface Repositorio<T> {
    List<T> listar();
    T buscar(Long id);
    void guardarEditar(T t);
    void eliminar(Long id);
}
