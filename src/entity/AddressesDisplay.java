package entity;

public class AddressesDisplay {

    private Long id;
    private String addressName;

    public AddressesDisplay(Long id, String addressName) {
        this.id = id;
        this.addressName = addressName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    @Override
    public String toString() {
        return "AddressesDisplay{" +
                "id=" + id +
                ", addressName='" + addressName + '\'' +
                '}';
    }
}
