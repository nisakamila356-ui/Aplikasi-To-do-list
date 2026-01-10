package CRUD;

import db.DatabaseConnection;
import Model.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    private Connection conn;

    public TodoDAO() {
        conn = DatabaseConnection.getConnection();
        createTable();
    }

    private void createTable() {
        if (conn == null) return;

        String sql =
                "CREATE TABLE IF NOT EXISTS todo (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "title TEXT, " +
                        "priority TEXT, " +
                        "deadline TEXT" +
                        ")";

        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Todo todo) {
        String sql = "INSERT INTO todo(title, priority, deadline) VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getPriority());
            ps.setString(3, todo.getDeadline());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Todo todo) {
        String sql = "UPDATE todo SET title=?, priority=?, deadline=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getPriority());
            ps.setString(3, todo.getDeadline());
            ps.setInt(4, todo.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM todo WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Todo> getAll(String key) {
        List<Todo> list = new ArrayList<>();
        String sql = "SELECT * FROM todo WHERE title LIKE ? ORDER BY id DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Todo(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("priority"),
                        rs.getString("deadline")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
