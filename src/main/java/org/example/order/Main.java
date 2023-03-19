package org.example.order;

import org.example.order.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in);
             Connection conn = ConnectionFactory.getConnection()) {
            OrderDAO dao = new OrderDAOImpl(conn);
            if (!dao.isTableExists()) {
                dao.createTableGoods();
                dao.createTableClients();
                dao.createTableOrders();
            }

            while (true) {
                printMenu();
                String choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        addGoods(sc, dao);
                        break;
                    case "2":
                        getAllGoods(dao);
                        break;
                    case "3":
                        addClient(sc, dao);
                        break;
                    case "4":
                        getAllClients(dao);
                        break;
                    case "5":
                        addOrder(sc, dao);
                        break;
                    case "6":
                        getAllOrders(dao);
                        break;
                    default:
                        return;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getAllGoods(OrderDAO dao) throws SQLException {
        dao.getAllGoods().forEach(System.out::println);
    }

    private static void addGoods(Scanner sc, OrderDAO dao) throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter price: ");
        double price = Integer.parseInt(sc.nextLine());
        dao.addGoods(name, price);
        System.out.println("Added");
    }

    private static void getAllClients(OrderDAO dao) throws SQLException {
        List<Client> clients = dao.getAllClients();
        for (Client client : clients) {
            System.out.printf("ID: %d, Name: %s, Phone Number: %s\n", client.getId(), client.getName(), client.getPhoneNumber());
        }
    }


    private static void addClient(Scanner sc, OrderDAO dao) throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = sc.nextLine();
        dao.addClient(name, phoneNumber);
        System.out.println("Added");
    }


    private static void getAllOrders(OrderDAO dao) throws SQLException {
        int orderCount = dao.count();
        System.out.println("Total number of orders: " + orderCount);
        System.out.println("\n");
        dao.getAllOrders().forEach(System.out::println);
    }


    private static void addOrder(Scanner sc, OrderDAO dao) throws SQLException {
        dao.getAllClients();
        System.out.print("Enter client name: ");
        String clientName = sc.nextLine();
        System.out.print("Enter client phone number: ");
        String clientPhoneNumber = sc.nextLine();
        System.out.println("Goods:");
        dao.getAllGoods().forEach(System.out::println); // отображаем список товаров
        System.out.print("Enter goods id: ");
        int goodsId = Integer.parseInt(sc.nextLine()); // запрашиваем у пользователя идентификатор товара
        dao.addOrder(clientName, clientPhoneNumber, goodsId);
    }


    public static void printMenu() {
        System.out.println("1: add goods");
        System.out.println("2: view all goods");
        System.out.println("3: add client");
        System.out.println("4: view all client");
        System.out.println("5: add order");
        System.out.println("6: view all order");
        System.out.print("-> ");
    }
}
