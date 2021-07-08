package com.example.kingpins;

public class prod {
        private String prod_name, seller,prodCode,image;
        private double price;
        private int id;
        public prod (String prod_name, Double price,String seller, String image,int id){
            this.id=id;
            this.prod_name = prod_name;
            this.image = image;
            this.seller = seller;
            this.price = price;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProd_name() {
            return prod_name;
        }

        public void setProd_name(String prod_name) {
            this.prod_name = prod_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getSeller() {
            return seller;
        }

        public void setSeller(String seller) {
            this.seller = seller;
        }

}
