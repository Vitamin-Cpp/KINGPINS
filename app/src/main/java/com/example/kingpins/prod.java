package com.example.kingpins;

public class prod {
        private String prod_name, seller, price,prodCode;
        private int image;

        public prod (String prod_name, String price,String seller, int image){

            this.prod_name = prod_name;
            this.image = image;
            this.seller = seller;
            this.price = price;
        }



    public String getProd_name() {
            return prod_name;
        }

        public void setProd_name(String prod_name) {
            this.prod_name = prod_name;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSeller() {
            return seller;
        }

        public void setSeller(String seller) {
            this.seller = seller;
        }

}
