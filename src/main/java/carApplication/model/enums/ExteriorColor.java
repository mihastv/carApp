package carApplication.model.enums;

public enum ExteriorColor {
    WHITE("Pearl White"),
    BLACK("Midnight Black"),
    SILVER("Metallic Silver"),
    RED("Racing Red"),
    BLUE("Ocean Blue"),
    GRAY("Gunmetal Gray"),
    GREEN("Forest Green");

    private final String displayName;

    ExteriorColor(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() { return displayName; }
}
