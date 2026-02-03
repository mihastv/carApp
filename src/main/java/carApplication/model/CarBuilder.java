package carApplication.model;

import carApplication.model.enums.*;

public interface CarBuilder {
    CarBuilder setModel(String model);
    CarBuilder setYear(int year);
    CarBuilder setBasePrice(double price);
    CarBuilder setEngine(EngineType engine);
    CarBuilder setTransmission(TransmissionType transmission);
    CarBuilder setColor(ExteriorColor color);
    CarBuilder setRims(RimType rims);
    CarBuilder addInteriorFeature(InteriorFeature feature);
    CarBuilder addInteriorFeatures(InteriorFeature... features);
    CarBuilder addExteriorOption(ExteriorOption option);
    CarBuilder addExteriorOptions(ExteriorOption... options);
    CarBuilder addSafetyFeature(SafetyFeature feature);
    CarBuilder addSafetyFeatures(SafetyFeature... features);
    CarBuilder reset();
    Car build();
}
