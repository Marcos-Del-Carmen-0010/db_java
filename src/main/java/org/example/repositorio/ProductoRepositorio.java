package org.example.repositorio;

import org.example.models.Categoria;
import org.example.models.Producto;
import org.example.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorio implements Repositorio<Producto> {

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getConnection();
    };

    @Override
    public List<Producto> listar() {
        List<Producto> listaProductos = new ArrayList<>();
        try(Connection con = ConexionBaseDatos.getConnection();
                Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT p.*, c.nombre as categoria FROM cat_productos as p INNER JOIN cat_categorias as c ON (p.id_categoria = c.id_categoria)")) {
            while (rs.next()) {
                listaProductos.add(crearProducto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaProductos;
    }

    @Override
    public Producto buscar(Long id) {
        Producto producto = null;
        try (Connection con = ConexionBaseDatos.getConnection();
                PreparedStatement stmt = con
                .prepareStatement("SELECT p.*, c.nombre as categoria FROM cat_productos as p INNER JOIN cat_categorias as c ON (p.id_categoria = c.id_categoria) WHERE id_producto = ?")) {
            stmt.setLong(1, id);

            try(ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    producto = crearProducto(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producto;
    }

    @Override
    public void guardarEditar(Producto producto) {
        String sql;
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "UPDATE cat_productos SET nombre=?, precio=?, id_categoria=? WHERE id_producto = ?";
        } else {
            sql = "INSERT INTO cat_productos(nombre, precio, id_categoria, fecha_registro) VALUES(?, ?, ?, ?)";
        }

        try (Connection con = ConexionBaseDatos.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setLong(2, producto.getPrecio());
            stmt.setLong(3, producto.getCategoria().getId());

            if (producto.getId() != null && producto.getId() > 0) {
                stmt.setLong(4, producto.getId());
            } else {
                stmt.setDate(4, new Date(producto.getFechaRegistro().getTime()));
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        try (Connection con = ConexionBaseDatos.getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM cat_productos WHERE id_producto = ?")){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Producto crearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id_producto"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("fecha_registro"));
        Categoria c = new Categoria();
        c.setId(rs.getLong("id_categoria"));
        c.setNombre(rs.getString("categoria"));
        p.setCategoria(c);
        return p;
    }
}
