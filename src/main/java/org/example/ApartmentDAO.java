package org.example;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface ApartmentDAO {
    void createTable() throws SQLException;
    void addApartment(String district, String address, float area, int rooms, float price) throws SQLException;
    List<Apartment> getApartments(PreparedStatement statement) throws SQLException;
    List<Apartment> getByAddress(String address);
    List<Apartment> getByArea(float minArea, float maxArea);
    int count() throws SQLException;
    boolean isTableExists();
    List<Apartment> getAll() throws SQLException;
    List<Apartment> getDistrict(String district) throws SQLException;
    List<Apartment> getByRooms(int rooms) throws SQLException;
}
