package ua.orders.daoService;

import ua.orders.entity.Client;
import ua.orders.entity.Good;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientsDAOImlp extends AbstractDAO {

    public ClientsDAOImlp(Connection conn, String table) {
        super(conn, table);
    }

    public void initBD() {
        try {

            try (Statement st = getConn().createStatement()) {
                st.execute("CREATE TABLE IF NOT EXISTS " + getTable() + " (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, fullName VARCHAR(100),phone VARCHAR(20))");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public List<Client> getClients() {
        List<Client> res = new ArrayList<>();
        String sql = "SELECT Clients.id AS id, Clients.fullName AS fullName, Clients.phone AS phone " +
                "FROM Clients " +
                "ORDER BY Clients.id";

        try (Statement st = getConn().createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String fullName = rs.getString("fullName");
                String phone = rs.getString("phone");

                Client clients = new Client(id, fullName, phone);

                res.add(clients);
            }
            return res;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}