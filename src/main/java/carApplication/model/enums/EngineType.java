package carApplication.model.enums;

public enum EngineType {
    V4("V4", 150, 4),
    V6("V6", 280, 6),
    V8("V8", 400, 8),
    ELECTRIC("Electric", 350, 0),
    HYBRID("Hybrid", 220, 4);

    private final String name;
    private final int horsepower;
    private final int cylinders;

    EngineType(String name, int horsepower, int cylinders) {
        this.name = name;
        this.horsepower = horsepower;
        this.cylinders = cylinders;
    }

    public String getName() { return name; }
    public int getHorsepower() { return horsepower; }
    public int getCylinders() { return cylinders; }

    @Override
    public String toString() {
        return String.format("%s (%d HP, %d cylinders)", name, horsepower, cylinders);
    }
}

