package top.sharehome.Utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 这个工具类中会提供仨方法:
 * 1. 获取连接池对象
 * 2. 从连接池中获取连接
 * 3. 将连接归还到连接池
 * 4. 从连接池中获取连接，确保连接在同一线程中的唯一性
 * 5. 将连接归还到连接池，确保连接在同一线程中的唯一性
 */
public class JDBCUtils {
    private static DataSource dataSource;
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    static {
        try {
            //1. 使用类加载器读取配置文件，转成字节输入流
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            //2. 使用Properties对象加载字节输入流
            Properties properties = new Properties();
            properties.load(is);
            //3. 使用DruidDataSourceFactory创建连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接池对象
     *
     * @return
     */
    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 获取连接对象
     *
     * @return
     */
    public static Connection getConnectionByApp() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 归还连接到连接池
     *
     * @return
     */
    public static void releaseConnectionByApp(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 获取连接，让它在同一个线程中调用的时候拿到的是同一个线程中的连接
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            Connection conn = connectionThreadLocal.get();
            if (conn == null) {
                //说明ThreadLocal中还没有数据
                //那么就从连接池中获取一个连接
                conn = dataSource.getConnection();
                //将这个连接存储到ThreadLocal
                connectionThreadLocal.set(conn);
            }
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 在同一线程下，归还连接至连接池
     */
    public static void releaseConnection() {
        try {
            //归还连接到连接池
            getConnection().close();
            //移除ThreadLocal中的连接
            connectionThreadLocal.remove();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}