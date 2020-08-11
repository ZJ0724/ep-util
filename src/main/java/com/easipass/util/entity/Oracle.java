package com.easipass.util.entity;

import com.easipass.util.exception.ErrorException;
import com.easipass.util.exception.OracleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * oracle数据库
 *
 * @author ZJ
 * */
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
     * 日志
     * */
    private static final Logger log = LoggerFactory.getLogger(Oracle.class);

    /**
     * 构造函数
     *
     * @param url 地址
     * @param port 端口
     * @param  sid sid
     * @param username 用户名
     * @param password 密码
     * */
    public Oracle(String url, int port, String sid, String username, String password) {
        this.url = url;
        this.port = port;
        this.sid = sid;
        this.username = username;
        this.password = password;
    }

    /**
     * 检查连接
     * */
    private void checkConnect() {
        if (!isConnect) {
            throw new OracleException("数据库未连接");
        }
    }

    /**
     * 连接数据库
     * */
    public void connect() {
        // 已连接不进行重连
        if (this.isConnect) {
            return;
        }

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            this.connection = DriverManager.getConnection("jdbc:oracle:thin:@" + url + ":" + port + ":" + sid, username, password);
            this.isConnect = true;
        } catch (ClassNotFoundException e) {
            throw new ErrorException(e.getMessage());
        } catch (SQLException e) {
            throw new OracleException("数据库连接失败");
        }

        log.info("oracle: {}, 已连接", this.url);
    }

    /**
     * 关闭连接
     */
    public void close() {
        // 已关闭不进行关闭
        if (!this.isConnect) {
            return;
        }

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

        log.info("oracle: {}, 已关闭", this.url);
    }

    /**
     * 增删改
     *
     * @param sql sql
     * @param objects 参数
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
            throw new ErrorException("sql错误");
        }
    }

    /**
     * 查
     *
     * @param sql sql
     * @param objects 参数
     *
     * @return ResultSet
     */
    public final ResultSet query(String sql, Object[] objects) {
        this.checkConnect();

        try {
            this.preparedStatement = connection.prepareStatement(sql);
            if (objects != null && objects.length > 0) {
                for (int i= 0; i<objects.length; i++) {
                    preparedStatement.setObject(i + 1, objects[i]);
                }
            }

            return preparedStatement.executeQuery();
        }catch (SQLException e) {
            throw new ErrorException("sql错误");
        }
    }

    /**
     * 获取字段内容
     *
     * @param resultSet resultSet
     * @param filedName 字段名
     *
     * @return 字段值
     * */
    public static String getFiledData(ResultSet resultSet, String filedName) {
        try {
            return resultSet.getString(filedName);
        } catch (SQLException e) {
            throw new ErrorException(e.getMessage());
        }
    }

}