package com.example.mainstreet.Model;

public class Cart {
    private String pid, pname, price, brand, image, quantity;

    public Cart()
    {

    }
    public Cart(String pid, String pname, String price, String brand, String image, String quantity) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.brand = brand;
        this.image = image;
        this.quantity = quantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

