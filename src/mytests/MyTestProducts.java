package mytests;

import db.DbProductsOperations;
import db.DbUsersOperations;
import entity.Product;
import entity.ProductDisplay;
import entity.User;

import java.util.List;

public class MyTestProducts {

    public static void main(String[] args) {

    new MyTestProducts().testInsert();

    }

    private void testInsert () {


        Product p1= new Product("telefon", "descriere telefon", 4500, 1,1 );
        Product p2= new Product("laptop", "descriere laptop", 1500, 1,1 );

        Product p3= new Product("pizza", "descriere pizza", 25, 2,1 );
        Product p4= new Product("apa", "descriere apa", 3.99, 2,1 );


        DbProductsOperations dbProductsOperations = new DbProductsOperations();
        dbProductsOperations.insert(p1);
        dbProductsOperations.insert(p2);
        dbProductsOperations.insert(p3);
        dbProductsOperations.insert(p4);

    }
    private void testSelect() {

        DbProductsOperations db = new DbProductsOperations();
        List<ProductDisplay> lp = db.readAllProducts();

        for(ProductDisplay p: lp) {
            System.out.println(p);
        }

    }
}
