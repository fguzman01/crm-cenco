package cencosud.models;

import java.util.List;

public class InputData {
    private String email;
    private int paymentMethod;
    private String app;
    private List<Product> products;
    private int salesChannel;
    private String street;
    private String deliveryType;
    private String sellerName;

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(int salesChannel) {
        this.salesChannel = salesChannel;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    // Clase interna para los productos
    public static class Product {
        private int skuId;
        private int quantity;
        public int getSkuId() {
            return skuId;
        }
        public void setSkuId(int skuId) {
            this.skuId = skuId;
        }
        public int getQuantity() {
            return quantity;
        }
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        
    }

   
}

