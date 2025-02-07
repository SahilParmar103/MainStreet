package com.example.mainstreet.Model;

public class Products {
    private String pname, description, price, image, brand, pid, quantity;
    public Products()
    {

    }
    public Products(String pname, String description, String price, String image, String brand, String pid, String quantity) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.brand = brand;
        this.pid = pid;
        this.quantity = quantity;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
