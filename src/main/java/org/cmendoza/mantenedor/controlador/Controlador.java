package org.cmendoza.mantenedor.controlador;

import org.cmendoza.mantenedor.conexionBD.ConexionBD;
import org.cmendoza.mantenedor.irepositorio.IRepositorio;
import org.cmendoza.mantenedor.models.Categoria;
import org.cmendoza.mantenedor.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Controlador implements IRepositorio {

    public Connection getConnection ()throws SQLException{
        return ConexionBD.getConnection();

    }


    @Override
    public List listar() {
        List<Usuario> usuarioList = new ArrayList<>();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT u.*,c.nombre as categoria from usuario as u " +
                     "inner join categoria c on(u.id_categoria=c.id)")){
            while (rs.next()){
            Usuario u = crearUsuario(rs);
            usuarioList.add(u);
            }
        }catch (SQLException e){
        }
        return usuarioList;
    }

    @Override
    public Object findId(Long id) throws SQLException {
        Usuario us = null;
        try (Connection c = getConnection();
             PreparedStatement stmt = c.prepareStatement("select u.*,c.nombre as categoria from usuario as u " +
                     "inner join categoria c on (u.id_categoria where c.id= ?)")){
            stmt.setLong(1,id);
                try (ResultSet rs = stmt.executeQuery()){
                    if(rs.next()){
                        us = crearUsuario(rs);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return us;
    }

    @Override
    public void save(Usuario usuario) {
        String sql;
        if (usuario.getId() != null && usuario.getId() >0){
            sql = "UPDATE usuario set nombre =?, apellido=?, email=?, password=?, id_categoria=? where id=?";
        }else {
            sql = "insert into usuario(nombre,apellido,email,password,id_categoria) values(?,?,?,?,?)";
        }
        try (Connection c = getConnection();
        PreparedStatement stmt = c.prepareStatement(sql)){
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getPassword());
            stmt.setLong(5,usuario.getCategoria().getId());

            if (usuario.getId() != null && usuario.getId()>0){
                stmt.setLong(6,usuario.getId());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void delete(Long id) {
        try(Connection c = getConnection();
        PreparedStatement stmt = c.prepareStatement("delete from usuario where id = ?")) {
        stmt.setLong(1,id);
        stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static Usuario crearUsuario(ResultSet rs) throws SQLException{
        Usuario us = new Usuario();
        us.setId(rs.getLong("id"));
        us.setNombre(rs.getString("nombre"));
        us.setApellido(rs.getString("apellido"));
        us.setEmail(rs.getString("email"));
        us.setPassword(rs.getString("password"));
        Categoria ct = new Categoria();
        ct.setId(rs.getLong("id"));
        ct.setCategoria(rs.getString("categoria"));
        us.setCategoria(ct);
        return us;



    }
}
