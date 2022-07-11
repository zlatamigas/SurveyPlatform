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
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Connection pool. For db initialisation use app.properties file with db extension for properties.
 * If there are no properties for driver and pool size in properties file, then default values are used.
 */
public class ConnectionPool {

    private static final Logger logger = LogManager.getLogger();

    // Properties

    private static final Properties properties = new Properties();

    private static final String DB_PROPERTIES_FILE = "properties/app.properties";
    private static final String DB_PROPERTIES_PREFIX = "db.";
    private static final String DB_URL_PROPERTY = "url";
    private static final String DB_USER_PROPERTY = "user";
    private static final String DB_PASSWORD_PROPERTY = "password";
    private static final String DB_DRIVER_PROPERTY = "driver";
    private static final String DEFAULT_DRIVER_PROPERTY = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL;
    private static final String POOL_PROPERTIES_PREFIX = "pool.";
    private static final String POOL_SIZE_PROPERTY = "size";
    private static final int DEFAULT_CONNECTION_POOL_SIZE = 8;
    private static final int CONNECTION_POOL_SIZE;

    // Singleton

    private static final Lock instanceLock = new ReentrantLock(true);
    private static final AtomicBoolean isInstanceCreated = new AtomicBoolean(false);
    private static ConnectionPool instance;

    // Connection pool

    private final BlockingQueue<ProxyConnection> available = new LinkedBlockingQueue<>(CONNECTION_POOL_SIZE);
    private final BlockingQueue<ProxyConnection> occupied = new LinkedBlockingQueue<>(CONNECTION_POOL_SIZE);

    // Properties and driver initialisation

    static {

        String driverProperty = null;
        try (InputStream propertiesStream = ConnectionPool.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE)) {

            Properties fileProperties = new Properties();
            fileProperties.load(propertiesStream);

            DB_URL = fileProperties.getProperty(DB_PROPERTIES_PREFIX + DB_URL_PROPERTY);
            properties.put(DB_USER_PROPERTY, fileProperties.getProperty(DB_PROPERTIES_PREFIX + DB_USER_PROPERTY));
            properties.put(DB_PASSWORD_PROPERTY, fileProperties.getProperty(DB_PROPERTIES_PREFIX + DB_PASSWORD_PROPERTY));

            if ((driverProperty = fileProperties.getProperty(DB_PROPERTIES_PREFIX + DB_DRIVER_PROPERTY)) == null) {
                driverProperty = DEFAULT_DRIVER_PROPERTY;
            }
            Class.forName(driverProperty);
            logger.debug("Registered driver: {}", driverProperty);

            String poolSizeParameter;
            int poolSize = DEFAULT_CONNECTION_POOL_SIZE;
            if ((poolSizeParameter = fileProperties.getProperty(POOL_PROPERTIES_PREFIX + POOL_SIZE_PROPERTY)) != null) {
                try {
                    poolSize = Integer.parseInt(poolSizeParameter);
                } catch (NumberFormatException nfe) {
                    logger.error("Invalid pool size parameter in properties file: {}", poolSizeParameter);
                }
            }
            CONNECTION_POOL_SIZE = poolSize;
            logger.debug("Pool size: " + poolSize);
        } catch (IOException e) {
            logger.error("Cannot open properties file: {}", DB_PROPERTIES_FILE);
            throw new ExceptionInInitializerError("Cannot open properties file: " + DB_PROPERTIES_FILE);
        } catch (ClassNotFoundException e) {
            logger.error("Error loading driver: {}", driverProperty);
            throw new ExceptionInInitializerError("Error loading driver: " + driverProperty);
        }
    }


    private ConnectionPool() {

        for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
            ProxyConnection connection;
            try {
                connection = new ProxyConnection(DriverManager.getConnection(DB_URL, properties));
                available.put(connection);
            } catch (SQLException | InterruptedException e) {
                logger.error("Error while initialising connection pool: {}", e.getMessage());
                throw new ExceptionInInitializerError("Error while initialising connection pool: " + e.getMessage());
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
                    logger.debug("Connection pool instance created");
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    public boolean releaseConnection(Connection connection) {

        boolean result = false;
        if (connection instanceof ProxyConnection) {
            try {
                occupied.take();
                available.put((ProxyConnection) connection);
                result = true;
            } catch (InterruptedException e) {
                logger.error("Thread killed while waiting ID - {}, name - {}: {}",
                        Thread.currentThread().getId(),
                        Thread.currentThread().getName(),
                        e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        return result;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = available.take();
            occupied.put(connection);
        } catch (InterruptedException e) {
            logger.error("Thread killed while waiting ID - {}, name - {}: {}",
                    Thread.currentThread().getId(),
                    Thread.currentThread().getName(),
                    e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void destroyPool() {
        for (int i = 0; i < CONNECTION_POOL_SIZE; i++) {
            try {
                available.take().finalClose();
            } catch (SQLException e) {
                logger.error("Cannot close connection: " + e.getMessage());
            } catch (InterruptedException e) {
                logger.error("Thread killed while waiting ID - {}, name - {}: {}",
                        Thread.currentThread().getId(),
                        Thread.currentThread().getName(),
                        e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        deregisterDriver();
    }

    public void deregisterDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver;
        while (drivers.hasMoreElements()) {
            driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("Cannot deregister driver: {}", e.getMessage());
            }
        }
    }
}
