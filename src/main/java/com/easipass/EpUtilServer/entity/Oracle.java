package com.easipass.EpUtilServer.entity;

import com.easipass.EpUtilServer.exception.ErrorException;
import java.sql.*;

public class Oracle {

    /**
     * 地址
     * */
    private String url;

    /**
     * 端口
     * */
    private int port;

    /**
     * sid
     * */
    private String sid;

    /**
     * 用户名
     * */
    private String username;

    /**
     * 密码
     * */
    private String password;

    /**
     * 连接
     * */
    private Connection connection = null;

    /**
     * 面板
     * */
    private PreparedStatement preparedStatement = null;

    /**
     * 连接标识
     * */
    private boolean isConnect = false;

    public Oracle(String url, int port, String sid, String username, String password) {
        this.url = url;
        this.port = port;
        this.sid = sid;
        this.username = username;
        this.password = password;
    }

    /**
     * 初始化
     * */
    private void init() {
        if (!isConnect) {
            throw new ErrorException("未连接");
        }
    }

    /**
     * 连接数据库
     * */
    public boolean connect() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            this.connection = DriverManager.getConnection("jdbc:oracle:thin:@" + url + ":" + port + ":" + sid, username, password);
            this.isConnect = true;
        } catch (ClassNotFoundException e) {
            throw new ErrorException(e.getMessage());
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    /**
     * 关闭连接
     */
    public void close() {
        try {
            if (this.connection != null) {
                connection.close();
                connection = null;
            }
            if (this.preparedStatement != null) {
                preparedStatement.close();
                preparedStatement = null;
            }
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }
    }

    /**
     * 增删改
     */
    public boolean update(String sql, Object[] objects) {
        this.init();

        try {
            this.preparedStatement = this.connection.prepareStatement(sql);
            if (objects != null && objects.length > 0) {
                for(int i = 0; i<objects.length; i++) {
                    preparedStatement.setObject(i+1, objects[i]);
                }
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    /**
     * 查
     */
    public ResultSet query(String sql, Object[] objects) {
        this.init();

        try {
            this.preparedStatement = connection.prepareStatement(sql);
            if (objects != null && objects.length > 0) {
                for (int i= 0; i<objects.length; i++) {
                    preparedStatement.setObject(i+1,objects[i]);
                }
            }
            return preparedStatement.executeQuery();
        }catch (SQLException e){
            return null;
        }
    }

}
