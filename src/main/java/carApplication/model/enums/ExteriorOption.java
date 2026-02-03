package carApplication.model.enums;

public enum ExteriorOption {
    SUNROOF("Panoramic Sunroof", 1200.0),
    ROOF_RAILS("Roof Rails", 400.0),
    SPOILER("Rear Spoiler", 600.0),
    CHROME_PACKAGE("Chrome Exterior Package", 800.0),
    TINTED_WINDOWS("Tinted Windows", 350.0),
    RUNNING_BOARDS("Running Boards", 500.0),
    TOW_HITCH("Tow Hitch", 450.0),
    MUD_FLAPS("Mud Flaps", 150.0);

    private final String name;
    private final double price;

    ExteriorOption(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() { return name; }
}