package carApplication.model;

import carApplication.model.enums.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomCarBuilder implements CarBuilder {
    private String model;
    private int year;
    private double basePrice;
    private EngineType engine;
    private TransmissionType transmission;
    private ExteriorColor color;
    private RimType rims;
    private Set<InteriorFeature> interiorFeatures;
    private Set<ExteriorOption> exteriorOptions;
    private Set<SafetyFeature> safetyFeatures;

    public CustomCarBuilder() {
        reset();
    }

    @Override
    public CarBuilder reset() {
        this.model = "Custom Car";
        this.year = java.time.Year.now().getValue();
        this.basePrice = 25000.0;
        this.engine = EngineType.V4;
        this.transmission = TransmissionType.MANUAL_5_SPEED;
        this.color = ExteriorColor.WHITE;
        this.rims = RimType.STANDARD_16;
        this.interiorFeatures = new HashSet<>();
        this.exteriorOptions = new HashSet<>();
        this.safetyFeatures = new HashSet<>();

        for (SafetyFeature feature : SafetyFeature.values()) {
            if (feature.isStandard()) {
                this.safetyFeatures.add(feature);
            }
        }

        return this;
    }

    @Override
    public CarBuilder setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model name cannot be null or empty");
        }
        this.model = model.trim();
        return this;
    }

    @Override
    public CarBuilder setYear(int year) {
        int currentYear = java.time.Year.now().getValue();
        if (year < currentYear || year > currentYear + 2) {
            throw new IllegalArgumentException(
                    String.format("Year must be between %d and %d", currentYear, currentYear + 2));
        }
        this.year = year;
        return this;
    }

    @Override
    public CarBuilder setBasePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }
        this.basePrice = price;
        return this;
    }

    @Override
    public CarBuilder setEngine(EngineType engine) {
        if (engine == null) {
            throw new IllegalArgumentException("Engine type cannot be null");
        }
        this.engine = engine;
        return this;
    }

    @Override
    public CarBuilder setTransmission(TransmissionType transmission) {
        if (transmission == null) {
            throw new IllegalArgumentException("Transmission type cannot be null");
        }
        this.transmission = transmission;
        return this;
    }

    @Override
    public CarBuilder setColor(ExteriorColor color) {
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }
        this.color = color;
        return this;
    }

    @Override
    public CarBuilder setRims(RimType rims) {
        if (rims == null) {
            throw new IllegalArgumentException("Rim type cannot be null");
        }
        this.rims = rims;
        return this;
    }

    @Override
    public CarBuilder addInteriorFeature(InteriorFeature feature) {
        if (feature == null) {
            throw new IllegalArgumentException("Interior feature cannot be null");
        }
        this.interiorFeatures.add(feature);
        return this;
    }

    @Override
    public CarBuilder addInteriorFeatures(InteriorFeature... features) {
        for (InteriorFeature feature : features) {
            addInteriorFeature(feature);
        }
        return this;
    }

    @Override
    public CarBuilder addExteriorOption(ExteriorOption option) {
        if (option == null) {
            throw new IllegalArgumentException("Exterior option cannot be null");
        }
        this.exteriorOptions.add(option);
        return this;
    }

    @Override
    public CarBuilder addExteriorOptions(ExteriorOption... options) {
        for (ExteriorOption option : options) {
            addExteriorOption(option);
        }
        return this;
    }

    @Override
    public CarBuilder addSafetyFeature(SafetyFeature feature) {
        if (feature == null) {
            throw new IllegalArgumentException("Safety feature cannot be null");
        }
        this.safetyFeatures.add(feature);
        return this;
    }

    @Override
    public CarBuilder addSafetyFeatures(SafetyFeature... features) {
        for (SafetyFeature feature : features) {
            addSafetyFeature(feature);
        }
        return this;
    }

    @Override
    public Car build() {
        validateConfiguration();
        return new Car(model, year, engine, transmission, color, rims,
                interiorFeatures, exteriorOptions, safetyFeatures, basePrice);
    }

    private void validateConfiguration() {
        List<String> errors = new ArrayList<>();

        if (model == null || model.trim().isEmpty()) {
            errors.add("Model name is required");
        }
        if (engine == null) {
            errors.add("Engine type is required");
        }
        if (transmission == null) {
            errors.add("Transmission type is required");
        }
        if (color == null) {
            errors.add("Exterior color is required");
        }
        if (rims == null) {
            errors.add("Rim type is required");
        }

        if (engine == EngineType.ELECTRIC && transmission == TransmissionType.MANUAL_5_SPEED) {
            errors.add("Electric vehicles cannot have manual transmission");
        }
        if (engine == EngineType.ELECTRIC && transmission == TransmissionType.MANUAL_6_SPEED) {
            errors.add("Electric vehicles cannot have manual transmission");
        }

        if (!errors.isEmpty()) {
            throw new IllegalStateException("Invalid car configuration:\n  - " +
                    String.join("\n  - ", errors));
        }
    }

    public String getCurrentConfigurationSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Configuration:\n");
        sb.append(String.format("  Model: %d %s\n", year, model));
        sb.append(String.format("  Engine: %s\n", engine));
        sb.append(String.format("  Transmission: %s\n", transmission));
        sb.append(String.format("  Color: %s\n", color));
        sb.append(String.format("  Rims: %s\n", rims));
        sb.append(String.format("  Interior Features: %d\n", interiorFeatures.size()));
        sb.append(String.format("  Exterior Options: %d\n", exteriorOptions.size()));
        sb.append(String.format("  Safety Features: %d\n", safetyFeatures.size()));
        return sb.toString();
    }
}
