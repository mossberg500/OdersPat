package ua.orders.servlets;

import ua.orders.daoService.ClientsDAOImlp;
import ua.orders.daoService.ConnectionFactory;
import ua.orders.daoService.GoodsDAOImlp;
import ua.orders.daoService.OrdersDAOImpl;
import ua.orders.entity.DbProperties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class ContextListenerServlet implements ServletContextListener {

    public static final Logger LOG = Logger.getLogger(ContextListenerServlet.class.getName());

    public static GoodsDAOImlp goodsDAOImpl;
    public static ClientsDAOImlp clientsDAOImlp;
    public static OrdersDAOImpl ordersDAOImpl;


    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("ServletContextListener started");
        DbProperties props = new DbProperties();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        goodsDAOImpl = new GoodsDAOImlp(conn, "Goods");
        goodsDAOImpl.initBD();
        clientsDAOImlp = new ClientsDAOImlp(conn, "Clients");
        clientsDAOImlp.initBD();
        ordersDAOImpl = new OrdersDAOImpl(conn, "Orders");
        ordersDAOImpl.initBD();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == cl) {
                try {
                    LOG.info("Deregistering JDBC driver {}");
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ex) {
                    LOG.warning("Error deregistering JDBC driver {}" + ex.getMessage());
                }
            } else {
                LOG.warning("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader");
            }
        }

    }
}