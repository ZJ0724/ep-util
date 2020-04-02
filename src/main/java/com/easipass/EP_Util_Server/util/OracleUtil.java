package com.easipass.EP_Util_Server.util;

import com.easipass.EP_Util_Server.exception.BugException;
import com.easipass.EP_Util_Server.exception.UtilTipException;
import java.sql.*;

public class OracleUtil {

    private String url;
    private int port;
    private String sid;
    private String username;
    private String password;
    private Connection connection=null;
    private PreparedStatement preparedStatement=null;
    private boolean isConnect=false;

    public OracleUtil(String url, int port, String sid, String username, String password) {
        this.url = url;
        this.port = port;
        this.sid = sid;
        this.username = username;
        this.password = password;
    }

    private void check(){
        if(!isConnect){
            throw new BugException("未连接");
        }
    }

    /**
     * 连接数据库
     * */
    public void connect() throws UtilTipException{
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@"+url+":"+port+":"+sid, username,password);
            isConnect=true;
        }catch (ClassNotFoundException e){
            throw new BugException(e.getMessage());
        }catch (SQLException e){
            throw new UtilTipException("连接失败");
        }
    }

    /**
     * 关闭连接
     */
    public void close(){
        try {
            if(connection!=null){
                connection.close();
                connection=null;
            }
            if(preparedStatement!=null){
                preparedStatement.close();
                preparedStatement=null;
            }
        }catch (SQLException e){
            throw new BugException(e.getMessage());
        }
    }

    /**
     * 增删改
     */
    public void update(String sql, Object[] objects){
        check();
        try {
            preparedStatement=connection.prepareStatement(sql);
            if(objects!=null&&objects.length>0){
                for(int i=0;i<objects.length;i++){
                    preparedStatement.setObject(i+1,objects[i]);
                }
            }
            preparedStatement.execute();
        }catch (SQLException e){
            throw new BugException(e.getMessage());
        }
    }

    /**
     * 查
     */
    public ResultSet query(String sql,Object[] objects){
        check();
        try {
            preparedStatement=connection.prepareStatement(sql);
            if(objects!=null&&objects.length>0){
                for(int i=0;i<objects.length;i++){
                    preparedStatement.setObject(i+1,objects[i]);
                }
            }
            return preparedStatement.executeQuery();
        }catch (SQLException e){
            throw new BugException(e.getMessage());
        }
    }

}