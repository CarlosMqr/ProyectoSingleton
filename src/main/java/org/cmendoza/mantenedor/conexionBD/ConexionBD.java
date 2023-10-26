package org.cmendoza.mantenedor.conexionBD;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBD {
    private static String url = "jdbc:mysql://localhost:3306/usuario";
    private static String userName = "root";
    private static String password = "1234";

    private static BasicDataSource pool;

    public static BasicDataSource getInstance()throws SQLException{
        if (pool == null){
            pool = new BasicDataSource();

            pool.setUrl(url);
            pool.setUsername(userName);
            pool.setPassword(password);

            pool.setInitialSize(3);
            pool.setMinIdle(3);
            pool.setMaxTotal(8);
            pool.setMaxTotal(8);
        }
        return pool;
    }


    public static Connection getConnection()throws SQLException{
        return getInstance().getConnection();
    }
}
