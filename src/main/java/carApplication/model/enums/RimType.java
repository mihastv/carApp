package carApplication.model.enums;

public enum RimType {
    STANDARD_16("16-inch Standard", 16),
    ALLOY_17("17-inch Alloy", 17),
    ALLOY_18("18-inch Alloy", 18),
    SPORT_19("19-inch Sport", 19),
    PREMIUM_20("20-inch Premium", 20),
    PERFORMANCE_21("21-inch Performance", 21);

    private final String name;
    private final int size;

    RimType(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public int getSize() { return size; }

    @Override
    public String toString() { return name; }
}