package org.example;

import org.example.models.Categoria;
import org.example.models.Producto;
import org.example.repositorio.ProductoRepositorio;
import org.example.repositorio.Repositorio;
import org.example.util.ConexionBaseDatos;

import java.sql.*;
import java.util.Date;

public class EjemploJDBC {
    public static void main(String[] args) {
        Repositorio<Producto> repo = new ProductoRepositorio();
        System.out.println("======== MOSTRAR REGISTROS =======");
        repo.listar().forEach(System.out::println);

        System.out.println("======== BUSCAR REGISTRO =======");
        System.out.println(repo.buscar(2L));

        System.out.println("======== GUARDAR REGISTRO =======");
        Producto pro = new Producto();
        pro.setNombre("Telado red dragons");
        pro.setPrecio(450);
        pro.setFechaRegistro(new Date());
        Categoria c = new Categoria();
        c.setId(2L);
        pro.setCategoria(c);
        repo.guardarEditar(pro);
        repo.listar().forEach(System.out::println);
    }
}
