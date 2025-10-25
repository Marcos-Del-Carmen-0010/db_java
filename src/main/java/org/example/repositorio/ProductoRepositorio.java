package org.example.repositorio;

import org.example.models.Categoria;
import org.example.models.Producto;
import org.example.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.util.ConexionBaseDatos.getIntanse;

public class ProductoRepositorio implements Repositorio<Producto> {

    private Connection getConnection() throws SQLException {
        return getIntanse();
    };

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> listaProductos = new ArrayList<>();
        try(Statement stmt = getIntanse().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT p.*, c.nombre as categoria FROM cat_productos as p INNER JOIN cat_categorias as c ON (p.id_categoria = c.id_categoria)")) {
            while (rs.next()) {
                listaProductos.add(crearProducto(rs));
            }
        }
        return listaProductos;
    }

    @Override
    public Producto buscar(Long id) throws SQLException {
        Producto producto = null;
        try (PreparedStatement stmt = getIntanse()
            .prepareStatement("SELECT p.*, c.nombre as categoria FROM cat_productos as p INNER JOIN cat_categorias as c ON (p.id_categoria = c.id_categoria) WHERE id_producto = ?")) {
            stmt.setLong(1, id);

            try(ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    producto = crearProducto(rs);
                }
            }
        }
        return producto;
    }

    @Override
    public void guardarEditar(Producto producto) throws SQLException{
        String sql;
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "UPDATE cat_productos SET nombre=?, precio=?, id_categoria=?, sku=? WHERE id_producto = ?";
        } else {
            sql = "INSERT INTO cat_productos(nombre, precio, id_categoria, sku, fecha_registro) VALUES(?, ?, ?, ?, ?)";
        }

        try (PreparedStatement stmt = getIntanse().prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setLong(2, producto.getPrecio());
            stmt.setLong(3, producto.getCategoria().getId());
            stmt.setString(4, producto.getSku());

            if (producto.getId() != null && producto.getId() > 0) {
                stmt.setLong(5, producto.getId());
            } else {
                stmt.setDate(5, new Date(producto.getFechaRegistro().getTime()));
            }

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (PreparedStatement stmt = getIntanse().prepareStatement("DELETE FROM cat_productos WHERE id_producto = ?")){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Producto crearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id_producto"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setFechaRegistro(rs.getDate("fecha_registro"));
        p.setSku(rs.getString("sku"));
        Categoria c = new Categoria();
        c.setId(rs.getLong("id_categoria"));
        c.setNombre(rs.getString("categoria"));
        p.setCategoria(c);
        return p;
    }
}
