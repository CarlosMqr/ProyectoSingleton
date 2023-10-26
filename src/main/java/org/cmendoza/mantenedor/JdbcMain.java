package org.cmendoza.mantenedor;

import org.apache.commons.dbcp2.BasicDataSource;
import org.cmendoza.mantenedor.conexionBD.ConexionBD;
import org.cmendoza.mantenedor.controlador.Controlador;
import org.cmendoza.mantenedor.irepositorio.IRepositorio;
import org.cmendoza.mantenedor.models.Categoria;
import org.cmendoza.mantenedor.models.Usuario;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JdbcMain {
    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        int opcion = 0;
        System.out.println("Selecciona el número de la operación que desea realizar");
        try (BasicDataSource conn = ConexionBD.getInstance()) {
            IRepositorio<Usuario> response = new Controlador();
            do {
                Map<String, Integer> option = new HashMap<>();
                option.put("listar", 1);
                option.put("Crear", 2);
                option.put("actualizar", 3);
                option.put("Borrar", 4);
                option.put("Salir", 5);

                for (String llave : option.keySet()) {
                    Object valor = option.get(llave);
                    System.out.println(llave + ":" + valor);
                }



                opcion = scanner.nextInt();
                Long id;
                String nombre;
                String apellido;
                String email;
                String password;
                Long idCategoria;

                switch (opcion) {
                    case 1:
                        response.listar().forEach(System.out::println);
                        break;
                    case 2:
                        Usuario usuario = new Usuario();
                        Categoria ct = new Categoria();
                        System.out.println("Ingresa nombre: ");
                        nombre = scanner.next();
                        usuario.setNombre(nombre);
                        System.out.println("Ingresa apellido: ");
                        apellido = scanner.next();
                        usuario.setApellido(apellido);
                        System.out.println("ingresa tú email: ");
                        email = scanner.next();
                        usuario.setEmail(email);
                        System.out.println("Ingresa un password: ");
                        password = scanner.next();
                        usuario.setPassword(password);
                        System.out.println("ingresa la categoria 1:Alumno 2:Maestro 3:Maestra 4:Ad");
                        idCategoria = scanner.nextLong();
                        ct.setId(idCategoria);
                        usuario.setCategoria(ct);
                        response.save(usuario);
                        System.out.println("Usuario guardado con exito!!");
                        break;
                    case 3:
                        System.out.println("ingresa el ID del Usuario que desea actualizar");
                        id = scanner.nextLong();
                        Usuario u = response.findId(id);
                        if (u != null) {
                            u.setId(id);
                            System.out.println("Ingresa nuevo nombre: " + u.getNombre());
                            nombre = scanner.next();
                            u.setNombre(nombre);

                            System.out.println("Ingresa nuevo apellido: " + u.getApellido());
                            apellido = scanner.next();
                            u.setApellido(apellido);

                            System.out.println("Ingresa nuevo correo: " + u.getEmail());
                            email = scanner.next();
                            u.setEmail(email);

                            System.out.println("Ingresa la nuevo contraseña");
                            password = scanner.next();
                            u.setPassword(password);
                            response.save(u);
                            System.out.println("Usuario actualizado Exitosamente!!");
                        } else {
                            System.out.println("El usuario no existe");
                        }
                        break;
                    case 4:
                        System.out.println("Ingresa el ID del Usuario que desea eliminar: ");
                        id = scanner.nextLong();
                        response.delete(id);
                        System.out.println("Usuario eliminado exitosamente");
                        break;
                    default:

                            System.out.println("Ingresa solo números");

                }
            } while (opcion != 5);
            System.out.println("Salida del sistema con exito");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
