package org.example.order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {
    boolean isTableExists();
    void createTableGoods() throws SQLException;
    List<Goods> getAllGoods() throws SQLException;

    void createTableClients() throws SQLException;

    void addClient(String name, String phoneNumber) throws SQLException;

    List<Client> getAllClients() throws SQLException;

    void createTableOrders() throws SQLException;

    void addGoods(String name, double price) throws SQLException;

    void addOrder(String clientName, String clientPhoneNumber, int goodsId) throws SQLException;

    List<Order> getAllOrders() throws  SQLException;

    int count();

    int getClientId(String clientName, String clientPhoneNumber) throws SQLException;
}