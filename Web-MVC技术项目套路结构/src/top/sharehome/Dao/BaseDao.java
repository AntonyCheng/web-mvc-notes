package top.sharehome.Dao;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import top.sharehome.Utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BaseDao<T> {
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * 执行增删改的sql语句
     *
     * @param sql
     * @param params
     * @return
     */
    public int Update(String sql, Object... params) {
        Connection conn = JDBCUtils.getConnection();
        try {
            //执行增删改的sql语句，返回受到影响的行数
            return queryRunner.update(conn, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            JDBCUtils.releaseConnection();
        }
    }

    /**
     * 执行查询一行数据的sql语句，将结果集封装到JavaBean对象中
     *
     * @param clazz
     * @param sql
     * @param params
     * @return
     */
    public T getBean(Class<T> clazz, String sql, Object... params) {
        Connection conn = JDBCUtils.getConnection();
        try {
            return queryRunner.query(conn, sql, new BeanHandler<>(clazz), params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 执行查询多行数据的sql语句，并且将结果集封装到List<JavaBean>
     *
     * @param clazz
     * @param sql
     * @param params
     * @return
     */
    public List<T> getBeanList(Class<T> clazz, String sql, Object... params) {
        try {
            return queryRunner.query(JDBCUtils.getConnection(), sql, new BeanListHandler<>(clazz), params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 批处理方法
     *
     * @param sql
     * @param paramArr
     * @return
     */
    public int[] batchUpdate(String sql, Object[][] paramArr) {
        Connection conn = JDBCUtils.getConnection();
        try {
            return queryRunner.batch(conn, sql, paramArr);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}