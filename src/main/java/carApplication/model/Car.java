package carApplication.model;

import carApplication.model.enums.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

    @Entity
    @Table(name = "cars")
    public class Car {
        @Id
        private String vin;

        private String model;
        @Column(name = "model_year")
        private int year;
        private double basePrice;

        @Enumerated(EnumType.STRING)
        private EngineType engine;

        @Enumerated(EnumType.STRING)
        private TransmissionType transmission;

        @Enumerated(EnumType.STRING)
        private ExteriorColor color;

        @Enumerated(EnumType.STRING)
        private RimType rims;

        @ElementCollection(targetClass = InteriorFeature.class)
        @Enumerated(EnumType.STRING)
        private Set<InteriorFeature> interiorFeatures;

        @ElementCollection(targetClass = ExteriorOption.class)
        @Enumerated(EnumType.STRING)
        private Set<ExteriorOption> exteriorOptions;

        @ElementCollection(targetClass = SafetyFeature.class)
        @Enumerated(EnumType.STRING)
        private Set<SafetyFeature> safetyFeatures;

        protected Car() {}

        public Car(String model, int year, EngineType engine, TransmissionType transmission,
                   ExteriorColor color, RimType rims, Set<InteriorFeature> interiorFeatures,
                   Set<ExteriorOption> exteriorOptions, Set<SafetyFeature> safetyFeatures,
                   double basePrice) {
            this.vin = "VIN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            this.model = model;
            this.year = year;
            this.engine = engine;
            this.transmission = transmission;
            this.color = color;
            this.rims = rims;
            this.interiorFeatures = new HashSet<>(interiorFeatures);
            this.exteriorOptions = new HashSet<>(exteriorOptions);
            this.safetyFeatures = new HashSet<>(safetyFeatures);
            this.basePrice = basePrice;
        }


    private String generateVIN() {
        return "VIN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getModel() { return model; }
    public int getModelYear() { return year; }
    public EngineType getEngine() { return engine; }
    public TransmissionType getTransmission() { return transmission; }
    public ExteriorColor getColor() { return color; }
    public RimType getRims() { return rims; }
    public Set<InteriorFeature> getInteriorFeatures() { return interiorFeatures; }
    public Set<ExteriorOption> getExteriorOptions() { return exteriorOptions; }
    public Set<SafetyFeature> getSafetyFeatures() { return safetyFeatures; }
    public double getBasePrice() { return basePrice; }
    public String getVin() { return vin; }

    public double getTotalPrice() {
        double total = basePrice;

        for (InteriorFeature feature : interiorFeatures) {
            total += feature.getPrice();
        }
        for (ExteriorOption option : exteriorOptions) {
            total += option.getPrice();
        }
        for (SafetyFeature feature : safetyFeatures) {
            total += feature.getPrice();
        }

        return total;
    }

    public double getOptionsPrice() {
        return getTotalPrice() - basePrice;
    }

    @Override
    public String toString() {
        return String.format("%d %s", year, model);
    }

    public String getFullSpecification() {
        StringBuilder sb = new StringBuilder();
        String separator = "═".repeat(60);

        sb.append("\n").append(separator).append("\n");
        sb.append("  CAR CONFIGURATION SUMMARY\n");
        sb.append(separator).append("\n\n");

        sb.append(String.format("  Model: %d %s\n", year, model));
        sb.append(String.format("  VIN: %s\n\n", vin));

        sb.append("  POWERTRAIN\n");
        sb.append("  ──────────────────────────────────────\n");
        sb.append(String.format("  Engine: %s\n", engine));
        sb.append(String.format("  Transmission: %s\n\n", transmission));

        sb.append("  EXTERIOR\n");
        sb.append("  ──────────────────────────────────────\n");
        sb.append(String.format("  Color: %s\n", color));
        sb.append(String.format("  Rims: %s\n", rims));
        if (!exteriorOptions.isEmpty()) {
            sb.append("  Options:\n");
            for (ExteriorOption option : exteriorOptions) {
                sb.append(String.format("    • %s ($%.2f)\n", option.getName(), option.getPrice()));
            }
        }
        sb.append("\n");

        sb.append("  INTERIOR\n");
        sb.append("  ──────────────────────────────────────\n");
        if (interiorFeatures.isEmpty()) {
            sb.append("  Standard Interior\n");
        } else {
            for (InteriorFeature feature : interiorFeatures) {
                sb.append(String.format("    • %s ($%.2f)\n", feature.getName(), feature.getPrice()));
            }
        }
        sb.append("\n");

        sb.append("  SAFETY FEATURES\n");
        sb.append("  ──────────────────────────────────────\n");
        for (SafetyFeature feature : safetyFeatures) {
            String priceStr = feature.isStandard() ? "Standard" : String.format("$%.2f", feature.getPrice());
            sb.append(String.format("    • %s (%s)\n", feature.getName(), priceStr));
        }
        sb.append("\n");

        sb.append("  PRICING\n");
        sb.append("  ──────────────────────────────────────\n");
        sb.append(String.format("  Base Price:    $%,.2f\n", basePrice));
        sb.append(String.format("  Options:       $%,.2f\n", getOptionsPrice()));
        sb.append(String.format("  ─────────────────────────\n"));
        sb.append(String.format("  TOTAL PRICE:   $%,.2f\n", getTotalPrice()));

        sb.append("\n").append(separator).append("\n");

        return sb.toString();
    }
}
