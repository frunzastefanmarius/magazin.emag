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

public class BuyerMenu {
    public static void showMenuOptions(Long idUser) {
        Scanner scanner = new Scanner(System.in);
        int userMenuOption;

        do {
            //aici user e deja logat si alege din meniu ce vrea sa faca
            System.out.println("Menu pentru cumparator:");    //aici afisam pagina de "Home";
            System.out.println("1. Vizualizeaza lista de produse");
            System.out.println("2. Adauga un produs in cos");
            System.out.println("3. Afiseaza cosul de cumparaturi");
            System.out.println("4. Sterge un produs din cos");
            System.out.println("5. Afiseaza lista userilor");
            System.out.println("6. Afiseaza toate categoriile");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            userMenuOption = scanner.nextInt();
            DbBasketOperations dbb = new DbBasketOperations();
            boolean b = false;

            switch (userMenuOption) {
                case 1:
                    showAllProducts();
                    showMenuOptions(idUser);
                case 2:
                    addProductsInBasket(idUser);
                    showMenuOptions(idUser);
                case 3:
                    readBasket(idUser);
                    showMenuOptions(idUser);
                case 4:
                    deleteProductFromBasket(idUser);
                    showMenuOptions(idUser);
                case 5:
                    showAllUsers(b);
                    showMenuOptions(idUser);
                case 6:
                    showAllCategories();
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
