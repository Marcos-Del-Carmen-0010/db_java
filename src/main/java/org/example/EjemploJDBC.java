package org.example;

import org.example.models.Categoria;
import org.example.models.Producto;
import org.example.repositorio.ProductoRepositorio;
import org.example.repositorio.Repositorio;
import org.example.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class EjemploJDBC {
    public static void main(String[] args) throws SQLException {
        try(Connection conn = ConexionBaseDatos.getIntanse()){
            if(conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
            try {
                Repositorio<Producto> repo = new ProductoRepositorio();
                Producto pro = new Producto();
                Categoria c = new Categoria();
                System.out.println("======== BUSCAR REGISTRO =======");
                System.out.println(repo.buscar(9L));

                System.out.println("======== MOSTRAR REGISTROS =======");
                repo.listar().forEach(System.out::println);

                System.out.println("======== GUARDAR REGISTRO =======");
                pro.setNombre("18 Gabinete");
                pro.setPrecio(5000);
                pro.setFechaRegistro(new Date());
                c.setId(1L);
                pro.setCategoria(c);
                pro.setSku("abc4325");
                repo.guardarEditar(pro);
                repo.listar().forEach(System.out::println);

                System.out.println("======== EDITAR REGISTRO =======");
                pro = new Producto(9L,"Telado Rojo de Robot", 250);
                c.setId(1L);
                pro.setCategoria(c);
                pro.setSku("abc1234");
                repo.guardarEditar(pro);
                repo.listar().forEach(System.out::println);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
}
