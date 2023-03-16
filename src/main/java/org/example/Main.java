package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in);
             Connection conn = ConnectionFactory.getConnection()) {
            ApartmentDAO dao = new ApartmentsDAOImpl(conn);
            if (!dao.isTableExists()) {
                dao.createTable();
            }

            while (true) {
                printMenu();
                String choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        addApartment(sc, dao);
                        break;
                    case "2":
                        viewApartments(dao);
                        break;
                    case "3":
                        viewApartmentsByDistrict(sc, dao);
                        break;
                    case "4":
                        viewApartmentsByAddress(sc, dao);
                        break;
                    case "5":
                        viewApartmentsByArea(sc, dao);
                        break;
                    case "6":
                        viewApartmentsByRooms(sc, dao);
                        break;
                    case "7":
                        viewApartmentsByPrice(sc, dao);
                        break;
                    case "8":
                        viewCount(dao);
                        break;
                    default:
                        return;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printMenu() {
        System.out.println("1: add apartment");
        System.out.println("2: view apartment");
        System.out.println("3: view district");
        System.out.println("4: view address");
        System.out.println("5: view area");
        System.out.println("6: view rooms");
        System.out.println("7: view price");
        System.out.println("8: view count");
        System.out.print("-> ");
    }

    private static void addApartment(Scanner sc, ApartmentDAO dao) throws SQLException {
        System.out.print("Enter apartment district: ");
        String district = sc.nextLine();
        System.out.print("Enter apartment address: ");
        String address = sc.nextLine();
        System.out.print("Enter client area: ");
        float area = Float.parseFloat(sc.nextLine());
        System.out.print("Enter client rooms: ");
        int rooms = Integer.parseInt(sc.nextLine());
        System.out.print("Enter client price: ");
        float price = Float.parseFloat(sc.nextLine());
        dao.addApartment(district, address, area, rooms, price);
    }

    private static void viewApartments(ApartmentDAO dao) throws SQLException {
        List<Apartment> list = dao.getAll();
        for (Apartment apartment : list) {
            System.out.println(apartment);
        }
    }

    private static void viewApartmentsByDistrict(Scanner sc, ApartmentDAO dao) throws SQLException {
        System.out.print("Enter apartment district: ");
        String district = sc.nextLine();
        List<Apartment> byDistrict = dao.getDistrict(district);
        for (Apartment apartment : byDistrict) {
            System.out.println(apartment);
        }
    }

    private static void viewApartmentsByAddress(Scanner sc, ApartmentDAO dao) {
        System.out.print("Enter address: ");
        String address = sc.nextLine();
        List<Apartment> apartmentsByAddress = dao.getByAddress(address);
        for (Apartment apartment : apartmentsByAddress) {
            System.out.println(apartment);
        }
    }
    private static void viewApartmentsByArea(Scanner scanner, ApartmentDAO dao) {
        System.out.print("Enter minimum area: ");
        String sMinArea = scanner.nextLine();
        float minArea = Float.parseFloat(sMinArea);
        System.out.print("Enter maximum area: ");
        String sMaxArea = scanner.nextLine();
        float maxArea = Float.parseFloat(sMaxArea);
        List<Apartment> apartmentsByArea = dao.getByArea(minArea, maxArea);
        for (Apartment apartment : apartmentsByArea) {
            System.out.println(apartment);
        }
    }

    private static void viewApartmentsByRooms(Scanner sc, ApartmentDAO dao) throws SQLException {
        System.out.print("Enter number of rooms: ");
        String sRooms = sc.nextLine();
        int rooms = Integer.parseInt(sRooms);
        List<Apartment> byRooms = dao.getByRooms(rooms);
        for (Apartment apartment : byRooms) {
            System.out.println(apartment);
        }
    }

    private static void viewApartmentsByPrice(Scanner sc, ApartmentDAO dao) {
        System.out.print("Enter minimum price: ");
        String sMinPrice = sc.nextLine();
        float minPrice = Float.parseFloat(sMinPrice);
        System.out.print("Enter maximum price: ");
        String sMaxPrice = sc.nextLine();
        float maxPrice = Float.parseFloat(sMaxPrice);
        List<Apartment> byPrice = ((ApartmentsDAOImpl) dao).getByPrice(minPrice, maxPrice);
        for (Apartment apartment : byPrice) {
            System.out.println(apartment);
        }
    }

    private static void viewCount(ApartmentDAO dao) throws SQLException {
        System.out.println("Count: " + dao.count());
    }
}

