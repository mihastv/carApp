package carApplication.dto;

import carApplication.model.enums.EngineType;
import carApplication.model.enums.TransmissionType;
import carApplication.model.enums.ExteriorColor;
import carApplication.model.enums.RimType;

public class CustomCarRequest {
    private String customerName;
    private String model;
    private EngineType engine; //
    private TransmissionType transmission; //
    private ExteriorColor color; //
    private RimType rims; //

    // Getters and Setters are required for Spring to map the JSON
    public String getCustomerName() { return customerName; }
    public String getModel() { return model; }
    public EngineType getEngine() { return engine; }
    public TransmissionType getTransmission() { return transmission; }
    public ExteriorColor getColor() { return color; }
    public RimType getRims() { return rims; }

    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setModel(String model) { this.model = model; }
    public void setEngine(EngineType engine) { this.engine = engine; }
    public void setTransmission(TransmissionType transmission) { this.transmission = transmission; }
    public void setColor(ExteriorColor color) { this.color = color; }
    public void setRims(RimType rims) { this.rims = rims; }
}