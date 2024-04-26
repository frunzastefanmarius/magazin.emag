package MenuOptions;

import MainController.emag;
import controller.BasketManagementService;
import controller.CategoryManagementService;
import controller.ProductManagementService;
import controller.UserManagementService;
import db.DbBasketOperations;
import entity.*;

import java.util.List;
import java.util.Scanner;

public class SellerMenu {
    public static void showMenuOptions(Long idUser) {
        Scanner scanner = new Scanner(System.in);
        int userMenuOption;

        do {
            //aici user e deja logat si alege din meniu ce vrea sa faca
            System.out.println("Menu pentru vanzator:");    //aici afisam pagina de "Home";
            System.out.println("1. Adauga un produs");
            System.out.println("2. Afiseaza toate categoriile");
            System.out.println("3. Afiseaza lista userilor");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            userMenuOption = scanner.nextInt();
            DbBasketOperations dbb = new DbBasketOperations();
            boolean b = false;

            switch (userMenuOption) {
                case 1:
                    addProducts(idUser);
                    showMenuOptions(idUser);
                case 2:
                    showAllCategories();
                    showMenuOptions(idUser);
                case 3:
                    showAllUsers(b);
                    showMenuOptions(idUser);
                case 0:
                    endProgram();
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (userMenuOption != 0);
        emag.start();
    }

    public static void showAllProducts() {
        System.out.println("Aceasta este lista de produse");
        // list products
        ProductManagementService pms = new ProductManagementService();
        List<ProductDisplay> lp = pms.showAllProducts();
        for (ProductDisplay p : lp) {
            System.out.println(p);
        }
    }

    public static void addProductsInBasket(Long idUser) {
        System.out.println("Adauga produse in cos:");
        Scanner sc = new Scanner(System.in);
        System.out.print("ce id pui in cos:");
        long idprod = sc.nextLong();

        Basket b = new Basket(idUser, idprod);

        BasketManagementService bms = new BasketManagementService();
        bms.insertInBasket(b);

    }

    public static void readBasket(Long idUser) {
        BasketManagementService bms = new BasketManagementService();
        List<BasketDisplay> lb = bms.readBasket(idUser);
        for (BasketDisplay basketOfCurrentUser : lb) {
            System.out.println(basketOfCurrentUser);
        }

    }

    public static void deleteProductFromBasket(Long idUser) {

        // sterg din cos

        System.out.print("ce id sterg din cos:");
        Scanner sca = new Scanner(System.in);
        Long idCosDeSters = sca.nextLong();
        DbBasketOperations dbb = new DbBasketOperations();
        List<BasketDisplay> lb = dbb.readBasketOfAUser(idUser);
        dbb.deleteBasketItem(idCosDeSters);
        // cer din nou cosul de la db
        lb = dbb.readBasketOfAUser(idUser);
        for (BasketDisplay bask : lb) {
            System.out.println(bask);
        }
    }

    public static void showAllUsers(boolean b) {
        int codUnic = 1234;
        System.out.println("Te rog sa introduci codul unic: ");
        Scanner sca = new Scanner(System.in);
        int codIntrodus = sca.nextInt();
        if (codIntrodus == codUnic) {
            b = true;
            UserManagementService ums = new UserManagementService();
            List<User> lu = ums.ShowAllUsers(b);

            for (User u : lu) {
                System.out.println(u);
            }
        } else {
            System.out.println("Codul introdus nu este corect!");
        }
    }

    public static void addProducts(Long idUser) {

        //aici trebuie sa adaugam partea de sout si sa completeze de la tastatura toate campurile necesare adaugarii produsului in DB
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Te rugam sa introduci datele necesare pentru a adauga produsul in cos.");
            System.out.println("nume produs: ");
            String name = sc.nextLine();
            System.out.println("descriere produs: ");
            String description = sc.nextLine();
            System.out.println("pret produs: ");
            Double price = Double.parseDouble(sc.nextLine());

            System.out.println("categorie produs: ");
            Long idCategoryIntrodus = Long.parseLong(sc.nextLine());
            Long idCategory;
            //trec prin toata lista de idCategory si daca gasesc idCategory introdus, il fac egal cu idCategory si merg mai departe

            if (true)
                idCategory = idCategoryIntrodus;
            Long iduser = idUser;
            Product p = new Product(name, description, price, iduser, idCategory);

            ProductManagementService pms = new ProductManagementService();
            pms.insert(p);
        } catch (NumberFormatException e) {
            System.out.println("Pretul nu poate contine litere.");
            addProducts(idUser);
        }

    }

    public static void showAllCategories() {
        System.out.println("Aceasta este lista de categorii:");

        CategoryManagementService cms = new CategoryManagementService();
        List<CategoryDisplay> lc = cms.showAllCategories();

        for (CategoryDisplay c : lc) {
            System.out.println(c);
        }
    }

    public static void endProgram() {
        System.out.println("==========");
        System.out.println("O zi buna!");
        System.out.println("==========");
        emag.start();
        //System.exit(0);
    }

//    public void pretProdus(String){
//
//    }
}
