package org.example.order;

public class Client {
    private int id;
    private String name;
    private String phoneNumber;

    public Client(int id, String name, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}


