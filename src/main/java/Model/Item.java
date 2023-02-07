package Model;

public class Item {
    private int id;
    private String itemName, itemColour;
    private int itemSize, itemQuantity;
    private String dateItemAdded;

    public Item() {
    }

    public Item(int id, String itemName, String itemColour, int itemSize, int itemQuantity, String dateItemAdded) {
        this.id = id;
        this.itemName = itemName;
        this.itemColour = itemColour;
        this.itemSize = itemSize;
        this.itemQuantity = itemQuantity;
        this.dateItemAdded = dateItemAdded;
    }

    public Item(String itemName, String itemColour, int itemSize, int itemQuantity, String dateItemAdded) {
        this.itemName = itemName;
        this.itemColour = itemColour;
        this.itemSize = itemSize;
        this.itemQuantity = itemQuantity;
        this.dateItemAdded = dateItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemColour() {
        return itemColour;
    }

    public void setItemColour(String itemColour) {
        this.itemColour = itemColour;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }
}
