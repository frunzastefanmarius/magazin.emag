package entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Order {

    private long id;
    private Timestamp creationDateTime;
    private boolean delivery;
    private boolean payment;
    private long idBasket;
    private long idUserFromBasket;
    private long idProductFromBasket;

    public Order(long id, Timestamp creationDateTime, boolean delivery, boolean payment, long idBasket) {
        this.id = id;
        this.creationDateTime = creationDateTime;
        this.delivery = delivery;
        this.payment = payment;
        this.idBasket = idBasket;
    }

    public Order(Timestamp creationDateTime, boolean delivery, boolean payment, long idBasket, long idUserFromBasket, long idProductFromBasket) {
        this.creationDateTime = creationDateTime;
        this.delivery = delivery;
        this.payment = payment;
        this.idBasket = idBasket;
        this.idUserFromBasket = idUserFromBasket;
        this.idProductFromBasket = idProductFromBasket;
    }

    public Order(Timestamp creationDateTime, boolean delivery, boolean payment, long idBasket) {
        this.creationDateTime = creationDateTime;
        this.delivery = delivery;
        this.payment = payment;
        this.idBasket = idBasket;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Timestamp creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public long getIdBasket() {
        return idBasket;
    }

    public void setIdBasket(long idBasket) {
        this.idBasket = idBasket;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", creationDateTime=" + creationDateTime +
                ", delivery=" + delivery +
                ", payment=" + payment +
                ", idBasket=" + idBasket +
                '}';
    }
}
