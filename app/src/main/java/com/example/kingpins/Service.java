package com.example.kingpins;

public class Service {

    private String title,category, seller;
    private double price;

    public Service(String title, double price, String seller, String category){
        this.category=category;
        this.price=price;
        this.seller=seller;
        this.title=title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getSeller() {
        return seller;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
