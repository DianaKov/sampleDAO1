package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartmentsDAOImpl implements ApartmentDAO {
    private final Connection conn;

    public ApartmentsDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void createTable() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Apartments (" +
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "district VARCHAR(30) NOT NULL," +
                    "address VARCHAR(100) NOT NULL," +
                    "area FLOAT NOT NULL," +
                    "rooms INT NOT NULL," +
                    "price FLOAT NOT NULL" +
                    ")");
        }
    }
    @Override
    public void addApartment(String district, String address, float area, int rooms, float price) {
        try {
            try (PreparedStatement st = conn.prepareStatement("INSERT INTO Apartments (district, address, area, rooms, price) VALUES(?, ?, ?, ?, ?)")) {
                st.setString(1, district);
                st.setString(2, address);
                st.setFloat(3, area);
                st.setInt(4, rooms);
                st.setFloat(5, price);
                st.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Apartment> getAll() {
       List<Apartment> res;
        try {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT * FROM Apartments")) {
                    res = getApartmentsFromResultSet(rs);
                }
            }
            return res;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Метод для выборки квартир по количеству комнат
    public List<Apartment> getByRooms(int rooms) {
        try (PreparedStatement statement = conn.prepareStatement(
                "SELECT * FROM apartments WHERE rooms = ?"
        )) {
            statement.setInt(1, rooms);
            return getApartments(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для выборки квартир по цене в заданном диапазоне
    public List<Apartment> getByPrice(float minPrice, float maxPrice) {
        try (PreparedStatement statement = conn.prepareStatement(
                "SELECT * FROM apartments WHERE price >= ? AND price <= ?"
        )) {
            statement.setFloat(1, minPrice);
            statement.setFloat(2, maxPrice);
            return getApartments(statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Apartment> getApartmentsFromResultSet(ResultSet rs) throws SQLException {
        List<Apartment> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Apartment(
                    rs.getInt("id"),
                    rs.getString("district"),
                    rs.getString("address"),
                    rs.getFloat("area"),
                    rs.getInt("rooms"),
                    rs.getFloat("price")
            ));
        }
        return list;
    }
    @Override
    public List<Apartment> getDistrict(String district) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM apartments WHERE district = ?")) {
            stmt.setString(1, district);
            try (ResultSet rs = stmt.executeQuery()) {
                return getApartmentsFromResultSet(rs);
            }
        }
    }
    @Override
    public List<Apartment> getApartments(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery();
        return getApartmentsFromResultSet(resultSet);
    }

    // Получение списка квартир по адресу
    @Override
    public List<Apartment> getByAddress(String address) {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM Apartments WHERE address = ?")) {
            ps.setString(1, address);
            try (ResultSet rs = ps.executeQuery()) {
                return getApartmentsFromResultSet(rs);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Получение списка квартир по площади
    @Override
    public List<Apartment> getByArea(float minArea, float maxArea) {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM Apartments WHERE area >= ? AND area <= ?")) {
            ps.setFloat(1, minArea);
            ps.setFloat(2, maxArea);
            try (ResultSet rs = ps.executeQuery()) {
                return getApartmentsFromResultSet(rs);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public int count() {
        try {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM Apartments")) {
                    if (rs.next())
                        return rs.getInt(1);
                    else
                        throw new RuntimeException("Count failed");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public boolean isTableExists() {
        return false;
    }
}
