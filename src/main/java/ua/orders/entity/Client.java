package ua.orders.entity;

import ua.orders.daoService.Id;
import ua.orders.daoService.NotUse;

public class Client {
    @Id
    private int id;
    private String fullName;
    private String phone;
    @NotUse
    private String selected = "";

    public Client() {
    }

    public Client(String fullName, String phone) {
        this.fullName = fullName;
        this.phone = phone;
    }

    public Client(int id, String fullName, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected() {
        this.selected = "selected";
    }

    @Override
    public String toString() {
        return fullName;
    }
}