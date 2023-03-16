package org.example;

public class Apartment {
    @Id
    private int id;
    private String district;
    private String address;
    private float area;
    private int rooms;
    private float price;

    public Apartment(int id, String district, String address, float area, int rooms, float price) {
        this.id = id;
        this.district = district;
        this.address = address;
        this.area = area;
        this.rooms = rooms;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", area='" + area + '\'' +
                ", rooms='" + rooms + '\'' +
                ", price=" + price +
                '}';
    }
}
