package ua.orders.daoService;

import ua.orders.entity.Client;
import ua.orders.entity.Good;
import ua.orders.entity.Order;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersDAOImpl extends AbstractDAO {
    private final String tblGoodsOfOrders = "GoodOfOrders";

    public OrdersDAOImpl(Connection conn, String table) {
        super(conn, table);
    }

    public void initBD() {

        try (Statement st = getConn().createStatement()) {

            st.execute("CREATE TABLE IF NOT EXISTS " + getTable() +
                    "(id INT NOT NULL AUTO_INCREMENT," +
                    "date DATETIME NOT NULL, " +
                    "sum DOUBLE, " +
                    "clients_id INT NOT NULL, " +
                    "PRIMARY KEY (id), " +
                    "INDEX fk_clients (clients_id ASC) VISIBLE, " +
                    "CONSTRAINT fk_clients " +
                    "FOREIGN KEY (clients_id) " +
                    "REFERENCES clients (id) " +
                    "ON DELETE CASCADE " +
                    "ON UPDATE NO ACTION)");

            st.execute("CREATE TABLE IF NOT EXISTS " + tblGoodsOfOrders +
                    "(orders_id INT NOT NULL, " +
                    "goods_id INT NOT NULL, " +
                    "price DOUBLE, " +
                    "count DOUBLE , " +
                    "INDEX fk_orders (orders_id ASC) INVISIBLE, " +
                    "INDEX fk_goods (goods_id ASC) VISIBLE, " +
                    "CONSTRAINT fk_orders " +
                    "FOREIGN KEY (orders_id) " +
                    "REFERENCES orders (id) " +
                    "ON DELETE CASCADE " +
                    "ON UPDATE NO ACTION, " +
                    "CONSTRAINT fk_goods " +
                    "FOREIGN KEY (goods_id) " +
                    "REFERENCES goods (id) " +
                    "ON DELETE RESTRICT " +
                    "ON UPDATE NO ACTION)");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String getSQLQueryForAdd(Order order, boolean isEdit) {
        String result = "";
        if (isEdit) {
            result = "INSERT INTO " + getTable() + " (id, date, sum, clients_id) " +
                    " VALUES('" + order.getId() + "', '" + java.sql.Date.valueOf(order.getDate()) + "','" +
                    order.getSum() + "', '" +
                    order.getClient().getId() + "')";
        } else {
            result = "INSERT INTO " + getTable() + " (date, sum, clients_id) " +
                    " VALUES('" + java.sql.Date.valueOf(order.getDate()) + "','" +
                    order.getSum() + "', '" +
                    order.getClient().getId() + "')";
        }
        return result;
    }

    public void add(Order order, boolean isEdit) {
        Connection conn = getConn();
        String sql = "";
        try (Statement statement = conn.createStatement()) {
            if (isEdit) {
                delete(order.getId());
            }
            sql = getSQLQueryForAdd(order, isEdit);
            statement.execute(sql);

            if (!isEdit) {
                try (ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()")) {
                    if (rs.next()) {
                        order.setId(rs.getInt(1));
                    }
                }
            }

            List<Good> goods = order.getGoods();
            String[] qt = new String[goods.size()];
            for (int i = 0; i < qt.length; i++) {
                qt[i] = "INSERT INTO " + tblGoodsOfOrders + " (orders_id, goods_id, price, count) VALUES( " + "'" +
                        order.getId() + "', '" + goods.get(i).getId() + "', '" + goods.get(i).getCost() + "', '" + goods.get(i).getCount() + "')";
            }

            conn.setAutoCommit(false);
            for (String q : qt) {
                statement.addBatch(q);
            }
            statement.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAll() {
        List<Order> res = new ArrayList<>();
        String sql = "SELECT orders.id AS id, orders.date AS date, orders.sum AS summ, " +
                "clients.id AS clientsId, clients.fullName AS fullName, clients.phone AS phone " +
                "FROM orders " +
                "LEFT JOIN " +
                "clients " +
                "on orders.clients_id = clients.id " +
                "ORDER BY orders.id";

        try (Statement st = getConn().createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("id");
                LocalDate date = rs.getDate("date").toLocalDate();
                double sum = rs.getDouble("summ");

                int clientsId = rs.getInt("clientsId");
                String clientsFullName = rs.getString("fullName");
                String clientsPhone = rs.getString("phone");

                Order order = new Order(id, date, new Client(clientsId, clientsFullName, clientsPhone), sum);

                res.add(order);
            }
            return res;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Order getOrderFromDB(String param) {
        Order res = null;
        String sql = "SELECT orders.id AS id, orders.date AS date, orders.sum AS summ, " +
                "clients.id AS clientsId, clients.fullName AS fullName, clients.phone AS phone, " +
                "goods.id AS goodId, goods.name AS goodName, goods.cost AS goodCost, " +
                "goodoforders.count AS goodCount " +
                "FROM orders " +
                "LEFT JOIN " +
                "clients on orders.clients_id = clients.id " +
                "LEFT JOIN " +
                "goodoforders ON orders.id = goodoforders.orders_id " +
                "LEFT JOIN " +
                "goods ON goodoforders.goods_id = goods.id " +
                "WHERE " + param;
        try (Statement st = getConn().createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {

                int id = rs.getInt("id");
                LocalDate date = rs.getDate("date").toLocalDate();
                double sum = rs.getDouble("summ");

                int clientsId = rs.getInt("clientsId");
                String clientsFullName = rs.getString("fullName");
                String clientsPhone = rs.getString("phone");

                int goodId = rs.getInt("goodId");
                String goodName = rs.getString("goodName");
                double goodCost = rs.getDouble("goodCost");
                double goodCount = rs.getDouble("goodCount");

                if (res == null) {
                    res = new Order(id, date, new Client(clientsId, clientsFullName, clientsPhone), sum);
                }

                res.addGood(new Good(goodId, goodName, goodCost, goodCount));

            }
            return res;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}