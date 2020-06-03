package com.easipass.epUtil.entity;

import com.easipass.epUtil.exception.ErrorException;
import com.easipass.epUtil.exception.OracleException;
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

    /**
     * 构造函数
     * */
    protected Oracle() {}
    protected Oracle(String url, int port, String sid, String username, String password) {
        this.url = url;
        this.port = port;
        this.sid = sid;
        this.username = username;
        this.password = password;
    }

    /**
     * get,set
     * */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 检查连接
     * */
    private void checkConnect() {
        if (!isConnect) {
            throw OracleException.connectFail(this.url);
        }
    }

    /**
     * 连接数据库
     * */
    public void connect() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            this.connection = DriverManager.getConnection("jdbc:oracle:thin:@" + url + ":" + port + ":" + sid, username, password);
            this.isConnect = true;
            Log.getLog().info("oracle:" + this.getUrl() + "连接成功!");
        } catch (ClassNotFoundException e) {
            throw ErrorException.getErrorException(e.getMessage());
        } catch (SQLException e) {
            throw OracleException.connectFail(this.url);
        }
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
            throw ErrorException.getErrorException(e.getMessage());
        }
        this.isConnect = false;
        Log.getLog().info("oracle:" + this.getUrl() + "已关闭!");
    }

    /**
     * 增删改
     */
    public void update(String sql, Object[] objects) {
        this.checkConnect();

        try {
            this.preparedStatement = this.connection.prepareStatement(sql);
            if (objects != null && objects.length > 0) {
                for(int i = 0; i<objects.length; i++) {
                    preparedStatement.setObject(i+1, objects[i]);
                }
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            throw ErrorException.getErrorException("sql错误");
        }
    }

    /**
     * 查
     */
    public ResultSet query(String sql, Object[] objects) {
        this.checkConnect();

        try {
            this.preparedStatement = connection.prepareStatement(sql);
            if (objects != null && objects.length > 0) {
                for (int i= 0; i<objects.length; i++) {
                    preparedStatement.setObject(i+1,objects[i]);
                }
            }
            return preparedStatement.executeQuery();
        }catch (SQLException e){
            throw ErrorException.getErrorException("sql错误");
        }
    }

}