package com.easipass.EpUtilServer.entity;

import com.easipass.EpUtilServer.config.KSDDBConfig;
import com.easipass.EpUtilServer.config.SWGDConfig;
import com.easipass.EpUtilServer.exception.ErrorException;
import java.sql.*;

public class Oracle {

    /**
     * 地址
     * */
    private final String url;

    /**
     * 端口
     * */
    private final int port;

    /**
     * sid
     * */
    private final String sid;

    /**
     * 用户名
     * */
    private final String username;

    /**
     * 密码
     * */
    private final String password;

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
    public Oracle(String url, int port, String sid, String username, String password) {
        this.url = url;
        this.port = port;
        this.sid = sid;
        this.username = username;
        this.password = password;
    }

    /**
     * 基础方法
     * */
    private void base() {
        if (!isConnect) {
            throw ErrorException.getErrorException("数据库未连接");
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
        this.isConnect = false;
    }

    /**
     * 增删改
     */
    public void update(String sql, Object[] objects) {
        this.base();

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
        this.base();

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

    /**
     * 获取SWGD数据库
     * */
    public static Oracle getSWGDOracle() {
        return new Oracle(SWGDConfig.url, SWGDConfig.port, SWGDConfig.sid, SWGDConfig.username, SWGDConfig.password);
    }

    /**
     * 获取KSDDB数据库
     * */
    public static Oracle getKSDDBOracle() {
        return new Oracle(KSDDBConfig.url, KSDDBConfig.port, KSDDBConfig.sid, KSDDBConfig.username, KSDDBConfig.password);
    }

    /**
     * 获取数据库url
     * */
    public String getUrl() {
        return url;
    }

}
