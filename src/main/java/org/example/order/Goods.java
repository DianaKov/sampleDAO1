package org.example.order;

public class Goods {
    private int id;
    private String name;
    private double price;

    public Goods(int id, String name, double price) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
