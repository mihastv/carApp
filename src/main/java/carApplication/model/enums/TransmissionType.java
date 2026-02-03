package carApplication.model.enums;

public enum TransmissionType {
    MANUAL_5_SPEED("5-Speed Manual", 5, false),
    MANUAL_6_SPEED("6-Speed Manual", 6, false),
    AUTOMATIC_6_SPEED("6-Speed Automatic", 6, true),
    AUTOMATIC_8_SPEED("8-Speed Automatic", 8, true),
    CVT("CVT", 0, true),
    DUAL_CLUTCH("Dual-Clutch Automatic", 7, true);

    private final String name;
    private final int gears;
    private final boolean automatic;

    TransmissionType(String name, int gears, boolean automatic) {
        this.name = name;
        this.gears = gears;
        this.automatic = automatic;
    }

    public String getName() { return name; }
    public int getGears() { return gears; }
    public boolean isAutomatic() { return automatic; }

    @Override
    public String toString() { return name; }
}