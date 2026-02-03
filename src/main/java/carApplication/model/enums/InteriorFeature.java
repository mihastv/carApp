package carApplication.model.enums;

public enum InteriorFeature {
    LEATHER_SEATS("Leather Seats", 1500.0),
    HEATED_SEATS("Heated Seats", 500.0),
    VENTILATED_SEATS("Ventilated Seats", 750.0),
    GPS_NAVIGATION("GPS Navigation System", 1200.0),
    PREMIUM_SOUND("Premium Sound System", 800.0),
    AMBIENT_LIGHTING("Ambient Lighting", 300.0),
    WIRELESS_CHARGING("Wireless Phone Charging", 250.0),
    HEAD_UP_DISPLAY("Head-Up Display", 1000.0),
    PANORAMIC_DISPLAY("Panoramic Display", 1500.0),
    REAR_ENTERTAINMENT("Rear Seat Entertainment", 2000.0);

    private final String name;
    private final double price;

    InteriorFeature(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() { return name; }
}

