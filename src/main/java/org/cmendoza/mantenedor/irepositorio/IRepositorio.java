package org.cmendoza.mantenedor.irepositorio;

import org.cmendoza.mantenedor.models.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface IRepositorio <T>{

    List<T> listar();

    T findId(Long id)throws SQLException;

     void save(Usuario usuario);

     void delete(Long id);

}
