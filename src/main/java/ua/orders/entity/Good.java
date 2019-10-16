package ua.orders.entity;

import ua.orders.daoService.Id;
import ua.orders.daoService.NotUse;

public class Good {
    @Id
    private int id;
    private String name;
    private double cost;
    @NotUse
    private double count;
    @NotUse
    private String checked = "";

    public Good() {
    }

    public Good(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public Good(int id, String name, double cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    public Good(int id, String name, double cost, double count) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.count = count;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked() {
        this.checked = "checked";
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", count=" + count +
                ", checked='" + checked + '\'' +
                '}';
    }
}