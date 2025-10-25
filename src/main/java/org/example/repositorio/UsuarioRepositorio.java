package org.example.repositorio;

import org.example.models.Usuario;
import org.example.util.ConexionBaseDatosPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio implements Repositorio<Usuario> {
    public String sqlMostra = "SELECT * FROM usuarios";
    public String sqlBuscar = "SELECT * FROM usuarios WHERE id = ?";
    public String sqlActualizar = "UPDATE usuarios SET username=?, password=?, email=? WHERE id = ?";
    public String sqlGuardar = "INSERT INTO usuarios(username, password, email) VALUES(?, ?, ?)";
    public String sqlEliminar = "DELETE FROM usuarios WHERE id = ?";

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatosPool.getConnection();
    };

    @Override
    public List<Usuario> listar() {
        List<Usuario> listar = new ArrayList<>();
        try(Connection con = ConexionBaseDatosPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sqlMostra)) {
            while (rs.next()) {
                listar.add(crearUsuario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listar;
    }

    @Override
    public Usuario buscar(Long id) {
        Usuario usu = null;
        try (Connection con = ConexionBaseDatosPool.getConnection();
             PreparedStatement stmt = con
                .prepareStatement(sqlBuscar)) {
            stmt.setLong(1, id);

            try(ResultSet rs = stmt.executeQuery();) {
                while (rs.next()) {
                    usu = crearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usu;
    }

    @Override
    public void guardarEditar(Usuario usu) {
        String sql;
        if (usu.getId() != null && usu.getId() > 0) {
            sql = sqlActualizar;
        } else {
            sql = sqlGuardar;
        }

        try (Connection con = ConexionBaseDatosPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, usu.getUsername());
            stmt.setString(2, usu.getPassword());
            stmt.setString(3, usu.getEmail());

            if (usu.getId() != null && usu.getId() > 0) {
                stmt.setLong(4, usu.getId());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        try (Connection con = ConexionBaseDatosPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(sqlEliminar)){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Usuario crearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setEmail(rs.getString("email"));
        return u;
    }
}
