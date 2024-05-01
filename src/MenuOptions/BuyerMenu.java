package MenuOptions;

import MainController.emag;
import controller.*;
import db.DbAddressesOperations;
import db.DbBasketOperations;
import entity.*;

import java.sql.Time;
import java.sql.Timestamp;
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
            System.out.println("7. Adauga o adresa");
            System.out.println("8. Afiseaza adresele mele");
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
                case 7:
                    addAddress(idUser);
                    showMenuOptions(idUser);
                case 8:
                    readAddresses(idUser);
                    showMenuOptions(idUser);
                case 9:
                    createAnOrder(idUser);
                    showMenuOptions(idUser);
                case 10:
                    accessOrders();
                    showMenuOptions(idUser);
                case 11:
                    clearAllBassket(idUser);
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

        //TODO:integrat partea de sters cu BasketManagementService

        System.out.print("Ce id sterg din cos:");
        Scanner sca = new Scanner(System.in);
        Long idCosDeSters = sca.nextLong();
        DbBasketOperations dbb = new DbBasketOperations();
        List<BasketDisplay> lb = dbb.readBasketOfAUser(idUser);
        dbb.deleteBasketItem(idCosDeSters);
        // cer din nou cosul de la db
        for (BasketDisplay bask : lb) {
            System.out.println(bask);
        }
    }
    public static void clearAllBassket(Long idUser){
        BasketManagementService bms = new BasketManagementService();
        List<BasketDisplay> lb = bms.readBasket(idUser);
        if(!lb.isEmpty()){

        }else {
            System.out.println("Nu ai produse in cos");
            showMenuOptions(idUser);
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

    public static void addAddress(Long idUser){
        Scanner sca = new Scanner(System.in);
        System.out.println("Te rugam sa introduci adresa: ");
        String address = sca.nextLine();
        if(address!=null){
        Addresses address1 = new Addresses(address,idUser);
        AddressesManagementService ams = new AddressesManagementService();
        ams.insertInAddresses(address1);
        } else {
            System.out.println("Acest camp nu poate fi gol.");
            showMenuOptions(idUser);
        }
    }
    public static void readAddresses(Long idUser){
        System.out.println("adresa dumneavoastra este: ");
        AddressesManagementService ams = new AddressesManagementService();
        List<AddressesDisplay> la = ams.readAddresses(idUser);
        for (AddressesDisplay a : la) {
            System.out.println(a);
        }
    }
    public static void createAnOrder(Long idUser){
        Scanner sca = new Scanner(System.in);
        System.out.println("Doresti sa plasezi o comanda cu produsele pe care le ai in cos?");
        System.out.println("1. Da");
        System.out.println("2. Nu");
        int raspuns= sca.nextInt();
        if (raspuns ==1 || raspuns==2){
            if (raspuns==1){
                BasketManagementService bms = new BasketManagementService();
                List<BasketDisplay> lb = bms.readBasket(idUser);
                if(!lb.isEmpty()){
                OrdersManagementService oms = new OrdersManagementService();
                //aici pun datele pentru a crea un obiect order pe care sa il trimit mai departe

                //cat timp Lista de idBasket care corespund acestui idUser nu se termina, adauga produsele in order

                // iau din lista de basket display idbasket??

                    //fac o metoda in dbbasketopperations select all from baskt where iduser is ? si fac o lista cu idbasket

                Timestamp creationDateTime = new Timestamp(System.currentTimeMillis());
                boolean delivery=true;
                boolean payment=true;
                long idbasket = 1L;
                Order order = new Order(creationDateTime,delivery,payment,1);
                oms.insertInOrder(order);
                }else {
                    System.out.println("Nu ai produse in cos.");
                    showMenuOptions(idUser);
                }
            }else {
                showMenuOptions(idUser);
            }
        }else {
            System.out.println("Nu ai introdus un numar vlaid");
            showMenuOptions(idUser);
        }



    }
    public static void accessOrders(){
        System.out.println();

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
