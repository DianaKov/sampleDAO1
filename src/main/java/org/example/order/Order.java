package org.example.order;
public class Order {
    private int id;
    private int clientId;
    private int goodsId;
    private Client client;
    private Goods goods;

    public Client getClient() {
        return client;
    }

    public Order(int id, Client client, Goods goods) {
        this.id = id;
        this.clientId = client.getId();
        this.goodsId = goods.getId();
        this.client = client;
        this.goods = goods;
    }

    public Goods getGoods() {
        return goods;
    }

    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", goodsId=" + goodsId +
                ", client=" + client +
                ", goods=" + goods +
                '}';
    }
}

