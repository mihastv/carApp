package carApplication.model.enums;

public enum SafetyFeature {
    ABS("Anti-lock Braking System", 0.0, true),
    AIRBAGS_FRONT("Front Airbags", 0.0, true),
    AIRBAGS_SIDE("Side Airbags", 300.0, false),
    AIRBAGS_CURTAIN("Curtain Airbags", 400.0, false),
    REAR_CAMERA("Rear View Camera", 500.0, false),
    PARKING_SENSORS("Parking Sensors", 400.0, false),
    BLIND_SPOT_MONITOR("Blind Spot Monitoring", 600.0, false),
    LANE_DEPARTURE("Lane Departure Warning", 700.0, false),
    ADAPTIVE_CRUISE("Adaptive Cruise Control", 1200.0, false),
    COLLISION_WARNING("Forward Collision Warning", 800.0, false),
    AUTO_EMERGENCY_BRAKE("Automatic Emergency Braking", 1000.0, false),
    NIGHT_VISION("Night Vision System", 2000.0, false);

    private final String name;
    private final double price;
    private final boolean standard;

    SafetyFeature(String name, double price, boolean standard) {
        this.name = name;
        this.price = price;
        this.standard = standard;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isStandard() { return standard; }

    @Override
    public String toString() { return name + (standard ? " (Standard)" : ""); }
}
