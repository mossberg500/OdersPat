package ua.orders.daoService;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<K, T> {
    private final Connection conn;
    private final String table;

    public AbstractDAO(Connection conn, String table) {
        this.conn = conn;
        this.table = table;
    }

    public Connection getConn() {
        return conn;
    }

    public String getTable() {
        return table;
    }

    public void add(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();

            StringBuilder names = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (Field f : fields) {
                if (f.isAnnotationPresent(NotUse.class)) continue;
                f.setAccessible(true);

                System.out.println(t.getClass().getName() + "         " + f.getName() + "," + f.get(t)+"\",");

                names.append(f.getName()).append(',');
                values.append('"').append(f.get(t)).append("\",");
            }
            names.deleteCharAt(names.length() - 1); // last ','
            values.deleteCharAt(values.length() - 1); // last ','

            String sql = "INSERT INTO " + table + "(" + names.toString() +
                    ") VALUES(" + values.toString() + ")";

            try (Statement st = conn.createStatement()) {
                st.execute(sql.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete(int id) {
        try {
            String sql = "DELETE FROM " + getTable() + " WHERE id = " + id;

            try (Statement st = getConn().createStatement()) {
                st.execute(sql.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void delete(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = null;

            for (Field f : fields) {
                if (f.isAnnotationPresent(Id.class)) {
                    id = f;
                    id.setAccessible(true);
                    break;
                }
            }
            if (id == null)
                throw new RuntimeException("No Id field");

            String sql = "DELETE FROM " + table + " WHERE " + id.getName() +
                    " = \"" + id.get(t) + "\"";

            try (Statement st = conn.createStatement()) {
                st.execute(sql.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void update(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = null;

            for (Field f : fields) {
                if (f.isAnnotationPresent(Id.class)) {
                    id = f;
                    id.setAccessible(true);
                    break;
                }
            }
            if (id == null)
                throw new RuntimeException("No Id field");

            StringBuilder params = new StringBuilder();
            for (Field f : fields) {
                f.setAccessible(true);
                if ((f.isAnnotationPresent(Id.class)) || (f.isAnnotationPresent(NotUse.class))) continue;
                params.append(f.getName()).append(" = ").append('"').append(f.get(t)).append("\",");
            }
            params.deleteCharAt(params.length() - 1); // last ','

            String sql = "UPDATE " + table + " SET " + params.toString() + " WHERE " + id.getName() +
                    " = " + id.get(t);

            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<T> getAll(Class<T> cls) {
        List<T> res = new ArrayList<>();

        try {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT * FROM " + table)) {
                    ResultSetMetaData md = rs.getMetaData();

                    while (rs.next()) {
                        T obj = (T) cls.newInstance();

                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            String columnName = md.getColumnName(i);

                            Field field = cls.getDeclaredField(columnName);
                            field.setAccessible(true);

                            field.set(obj, rs.getObject(columnName));
                        }

                        res.add(obj);
                    }
                }
            }

            return res;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Object[]> getAll(Class<T> cls, String... fields) {
        List<Object[]> result = new ArrayList<>();

        try {
            try (Statement st = conn.createStatement()) {
                StringBuilder params = new StringBuilder();
                for (String f : fields) {
                    params.append(f).append(",");
                }
                params.deleteCharAt(params.length() - 1);
                try (ResultSet rs = st.executeQuery("SELECT " + params.toString() + " FROM " + table)) {
                    ResultSetMetaData md = rs.getMetaData();

                    while (rs.next()) {
                        Object[] recorfFields = new Object[fields.length];
                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            recorfFields[i - 1] = rs.getObject(i);
                        }
                        result.add(recorfFields);
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    public List<T> getAll(Class<T> cls, String params) {
        List<T> res = new ArrayList<>();

        try {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT * FROM " + table + " WHERE " + params)) {
                    ResultSetMetaData md = rs.getMetaData();

                    while (rs.next()) {
                        T client = (T) cls.newInstance();

                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            String columnName = md.getColumnName(i);
                            Field field = cls.getDeclaredField(columnName);
                            field.setAccessible(true);
                            field.set(client, rs.getObject(columnName));
                        }

                        res.add(client);
                    }
                }
            }

            return res;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}