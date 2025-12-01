
package com.chandu.user.model;

public class UserWithProductResponse {

    private String userId;
    private String userName;
    private ProductResponse product;

    public UserWithProductResponse() {}

    public UserWithProductResponse(String userId, String userName, ProductResponse product) {
        this.userId = userId;
        this.userName = userName;
        this.product = product;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public void setProduct(ProductResponse product) {
        this.product = product;
    }
}
