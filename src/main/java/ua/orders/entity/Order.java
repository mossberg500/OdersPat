package ua.orders.entity;

import ua.orders.daoService.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    @Id
    private int id;
    private LocalDate date;
    private double sum;
    private Client client;
    private List<Good> goods = new ArrayList<>();

    public Order() {
    }

    public Order(int id, LocalDate date, Client client, double sum) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getSum() {
        return sum;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    public void addGood(Good good) {
        goods.add(good);
        sum += good.getCount() * good.getCost();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", client=" + client +
                ", goods=" + goods +
                '}';
    }


}