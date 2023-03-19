package org.example.order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private final Connection connection;

    public OrderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTableGoods() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS  Goods(" +
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(30) NOT NULL," +
                    "price DOUBLE NOT NULL" +
                    ")");
        }

    }
    @Override
    public void createTableClients() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS Clients (" +
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(30) NOT NULL," +
                    "phoneNumber VARCHAR(13) NOT NULL COMMENT 'phoneNumber'" +
                    ")");
            statement.execute("ALTER TABLE Orders ADD FOREIGN KEY (clientId) REFERENCES Clients(id)");
        }
    }


    @Override
    public void createTableOrders() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS orders ("
                    + "id INT NOT NULL AUTO_INCREMENT,"
                    + "clientId INT NOT NULL,"
                    + "goodsId INT NOT NULL," // добавляем колонку 'goodsId'
                    + "PRIMARY KEY (id),"
                    + "FOREIGN KEY (clientId) REFERENCES clients(id),"
                    + "FOREIGN KEY (goodsId) REFERENCES goods(id))");
        }
    }

    @Override
    public void addClient(String name, String phoneNumber) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Clients (name, phoneNumber) VALUES (?, ?)"
        )) {
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            statement.executeUpdate(); // execute the SQL INSERT query
        }
    }




    @Override
    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Clients");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phoneNumber = resultSet.getString("phoneNumber");
                clients.add(new Client(id, name, phoneNumber));
            }
        }
        return clients;
    }

    @Override
    public boolean isTableExists() {
        return false;
    }

    @Override
    public void addGoods(String name, double price) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Goods (name, price) VALUES (?, ?)")) {
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.executeUpdate();
        }
    }

    @Override
    public List<Goods> getAllGoods() throws SQLException {
        List<Goods> goodsList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Goods")) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    Goods goods = new Goods(id, name, price);
                    goodsList.add(goods);
                }
            }
        }
        return goodsList;
    }

    @Override
    public void addOrder(String clientName, String clientPhoneNumber, int goodsId) throws SQLException {
        int clientId = getClientId(clientName, clientPhoneNumber);
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO orders (clientId, goodsId) VALUES (?, ?)")) {
            stmt.setInt(1, clientId);
            stmt.setInt(2, goodsId);
            stmt.executeUpdate();

        }
    }


    @Override
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        final String SQL_GET_ALL_ORDERS = "SELECT o.id, o.clientId, o.goodsId, c.name AS clientName, c.phoneNumber, g.name AS goodsName, g.price " +
                "FROM Orders o " +
                "INNER JOIN Clients c ON o.clientId = c.id " +
                "INNER JOIN Goods g ON o.goodsId = g.id " +
                "ORDER BY o.id ASC";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_ORDERS)) {
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("id"),
                        new Client(
                                resultSet.getInt("clientId"),
                                resultSet.getString("clientName"),
                                resultSet.getString("phoneNumber")
                        ),
                        new Goods(
                                resultSet.getInt("goodsId"),
                                resultSet.getString("goodsName"),
                                resultSet.getDouble("price")
                        )
                );
                orders.add(order);
            }
            for (Order order : orders) {
                System.out.println("Order #" + order.getId());
                System.out.println("Client: " + order.getClient().getName() + ", Phone Number: " + order.getClient().getPhoneNumber());
                System.out.println("Goods: " + order.getGoods().getName() + ", Price: " + order.getGoods().getPrice());
                System.out.println("---------------------");
            }
        }
        return orders;
    }


    @Override
    public int count() {
        try {
            try (Statement st = connection.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM  `Orders`")) {
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
    public int getClientId(String name, String phoneNumber) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id FROM Clients WHERE name = ? AND phoneNumber = ?"
        )) {
            statement.setString(1, name);
            statement.setString(2, phoneNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                throw new SQLException("Client not found");
            }
        }
    }
}
