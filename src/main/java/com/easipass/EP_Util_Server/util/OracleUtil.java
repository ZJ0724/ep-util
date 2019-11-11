package com.easipass.EP_Util_Server.util;

import java.sql.*;

public class OracleUtil {

    private static Connection connection=null;
    private static PreparedStatement preparedStatement=null;

    /**
     * 连接数据库
     * */
    public static void connect(String url,int port,String sid,String username,String password) throws ClassNotFoundException,SQLException{

        Class.forName("oracle.jdbc.OracleDriver");
        connection = DriverManager.getConnection("jdbc:oracle:thin:@"+url+":"+port+":"+sid, username,password);

    }

    /**
     * 关闭连接
     */
    public static void close() throws SQLException{

        if(connection!=null){
            connection.close();
            connection=null;
        }
        if(preparedStatement!=null){
            preparedStatement.close();
            preparedStatement=null;
        }

    }

    /**
     * 增删改
     */
    public static void update(String sql,Object[] objects) throws SQLException{

        preparedStatement=connection.prepareStatement(sql);
        if(objects!=null&&objects.length>0){
            for(int i=0;i<objects.length;i++){
                preparedStatement.setObject(i+1,objects[i]);
            }
        }
        preparedStatement.execute();

    }

    /**
     * 查
     */
    public static ResultSet query(String sql,Object[] objects) throws SQLException{

        preparedStatement=connection.prepareStatement(sql);
        if(objects!=null&&objects.length>0){
            for(int i=0;i<objects.length;i++){
                preparedStatement.setObject(i+1,objects[i]);
            }
        }
        return preparedStatement.executeQuery();

    }

}