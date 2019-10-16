package ua.orders.daoService;

import ua.orders.entity.Client;
import ua.orders.entity.Good;
import ua.orders.entity.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GoodsDAOImlp extends AbstractDAO {

    public GoodsDAOImlp(Connection conn, String table) {
        super(conn, table);
    }

    public void initBD() {
        try {

            try (Statement st = getConn().createStatement()) {
            //    st.execute("DROP TABLE IF EXISTS " + getTable());
                st.execute("CREATE TABLE IF NOT EXISTS " + getTable() + " (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), cost DOUBLE)");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Good> getGoods() {
        List<Good> res = new ArrayList<>();
        String sql = "SELECT goods.id AS id, goods.name AS name, goods.cost AS cost " +
                "FROM goods " +
                "ORDER BY goods.id";

        try (Statement st = getConn().createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                double cost = rs.getDouble("cost");

                Good good = new Good(id, name, cost);

                res.add(good);
            }
            return res;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}