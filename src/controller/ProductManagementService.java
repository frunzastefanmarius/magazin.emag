package controller;

import db.DbProductsOperations;
import db.DbUsersOperations;
import entity.Product;
import entity.ProductDisplay;
import entity.User;

import java.awt.*;
import java.util.List;

public class ProductManagementService {

    public List<ProductDisplay> showAllProducts() {
        DbProductsOperations db = new DbProductsOperations();
        return db.readAllProducts();
    }

    public boolean insert(Product p) {
        DbProductsOperations dbProductsOperations = new DbProductsOperations();
        return dbProductsOperations.insert(p);
    }

    public List<ProductDisplay> idProductForOrder(Long idUser) {
        DbProductsOperations db = new DbProductsOperations();
        return db.readIdForOrder(idUser);
    }
}
