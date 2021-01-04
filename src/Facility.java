public class Facility {
    private String id;
    private String description;
    private int capacity;
    private int price;

    Facility(String id, String description, int capacity, int price) {
        this.id = id;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
    }

    String getId() {
        return id;
    }

    String getDescription() {
        return description;
    }

    int getCapacity() {
        return capacity;
    }
    
    int getPrice() {
        return price;
    }
}