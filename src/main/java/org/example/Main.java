package org.example;

import org.example.models.Producto;
import org.example.models.Usuario;
import org.example.repositorio.Repositorio;
import org.example.repositorio.UsuarioRepositorio;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Repositorio<Usuario> repo = new UsuarioRepositorio();
        int opcionIndice = 0;

        Map<String, Integer> operaciones = new HashMap();
        operaciones.put("Actualizar", 1);
        operaciones.put("Eliminar", 2);
        operaciones.put("Agregar", 3);
        operaciones.put("Listar", 4);
        operaciones.put("Salir", 5);

        Object[] opArreglo = operaciones.keySet().toArray();
        boolean blnSalir = true;
        do {
            Object opcion = JOptionPane.showInputDialog(null,
                    "Seleccione un Operación",
                    "Mantenedor de Usuarios",
                    JOptionPane.INFORMATION_MESSAGE, null, opArreglo, opArreglo[0]);

            if (opcion == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una operación");
            } else {
                opcionIndice = operaciones.get(opcion.toString());

                switch (opcionIndice) {
                    case 1:
                        String idActualizar = JOptionPane.showInputDialog("Ingrese el id del usuario a actualizar");
                        Long idActualizarLong = Long.parseLong(idActualizar);
                        Usuario u = new Usuario();
                        Usuario ua = repo.buscar(idActualizarLong);
                        String usernameA = JOptionPane.showInputDialog("Nombre del usuario a actualizar " + ua.getUsername());
                        u.setUsername(usernameA);
                        String pa = JOptionPane.showInputDialog("Nombre del usuario a actualizar " + ua.getPassword());
                        u.setPassword(pa);
                        String ea = JOptionPane.showInputDialog("Nombre del usuario a actualizar " + ua.getEmail());
                        u.setEmail(ea);
                        u.setId(idActualizarLong);
                        repo.guardarEditar(u);
                        break;
                    case 2:
                        String id = JOptionPane.showInputDialog("Ingrese el id del usuario a eliminar");
                        Long idLong = Long.parseLong(id);
                        repo.eliminar(idLong);
                        break;
                    case 3:
                        Usuario usu = new Usuario();
                        String username = JOptionPane.showInputDialog("Ingrese el nombre del usuario");
                        usu.setUsername(username);
                        String password = JOptionPane.showInputDialog("Ingrese la contraseña del usuario");
                        usu.setPassword(password);
                        String email = JOptionPane.showInputDialog("Ingrese el email del usuario");
                        usu.setEmail(email);
                        repo.guardarEditar(usu);
                        break;
                    case 4:
                        if (repo.listar().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No hay usuarios registrados" ,"Lista de usuarios", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            repo.listar().forEach(p -> sb.append(p).append('\n'));

                            JOptionPane.showMessageDialog(null, sb.toString(), "Lista de usuarios", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    case 5:
                        System.out.println("Salir");
                        blnSalir = false;
                        break;
                    default:
                        break;
                }
            }
        } while (blnSalir);
    }
}