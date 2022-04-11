package epam.zlatamigas.surveyplatform.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger();

    private static final Properties properties = new Properties();
    private static final String DB_PROPERTIES_FILE = "database.properties";
    private static final String DB_URL = "url";
//    private static final String DB_PASSWORD = "password";
//    private static final String DB_USER = "user";
    private static final int CONNECTION_POOL_SIZE = 8;

    private static final Lock instanceLock = new ReentrantLock(true);
    private static ConnectionPool instance;
    private static AtomicBoolean isInstanceCreated = new AtomicBoolean(false);

    private BlockingQueue<ProxyConnection> available = new LinkedBlockingQueue<>(CONNECTION_POOL_SIZE);
    private BlockingQueue<ProxyConnection> occupied = new LinkedBlockingQueue<>(CONNECTION_POOL_SIZE);

    static {

        try(InputStream propertiesStream = ConnectionPool.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE)) {
            properties.load(propertiesStream);
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e.getMessage());
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e.getMessage());
        }
    }


    private ConnectionPool() {

        String url = properties.getProperty(DB_URL);
//        String user = properties.getProperty(DB_USER);
//        String password = properties.getProperty(DB_PASSWORD);

        for(int i = 0; i < CONNECTION_POOL_SIZE; i++){
            ProxyConnection connection = null;
            try {
                connection = new ProxyConnection(DriverManager.getConnection(url, properties));
                available.put(connection);
            } catch (SQLException | InterruptedException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    public static ConnectionPool getInstance() {
        if (!isInstanceCreated.get()) {
            try {
                instanceLock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isInstanceCreated.set(true);
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    public boolean releaseConnection(Connection connection) {

        boolean result = false;

        if(connection instanceof ProxyConnection) {
            try {
                // поток может быть убит во время ожидания
                occupied.take();
                available.put((ProxyConnection) connection);
                result = true;
            } catch (InterruptedException e) {
                // лог
                Thread.currentThread().interrupt();
            }
        }
        return result;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            // поток может быть убит во время ожидания
            connection = available.take();
            occupied.put(connection);
        } catch (InterruptedException e) {
            // лог
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void destroyPool(){
        for(int i = 0; i < CONNECTION_POOL_SIZE; i++){
            try {
                available.take().releaseConnection();
            } catch (SQLException | InterruptedException e) {
                //e.printStackTrace();
            }
        }
        deregisterDriver();
    }

    public void deregisterDriver(){
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver;
        while (drivers.hasMoreElements()){
            driver = drivers.nextElement();
            try{
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                //logger
            }
        }
    }
}
