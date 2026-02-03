package carApplication.model;

import carApplication.model.enums.*;

public class CarConfigurationDirector {
    private final CarBuilder builder;

    public CarConfigurationDirector(CarBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(CarBuilder builder) {
    }

    public Car buildEconomyCar(String model) {
        return builder.reset()
                .setModel(model)
                .setYear(java.time.Year.now().getValue())
                .setBasePrice(22000.0)
                .setEngine(EngineType.V4)
                .setTransmission(TransmissionType.MANUAL_5_SPEED)
                .setColor(ExteriorColor.WHITE)
                .setRims(RimType.STANDARD_16)
                .build();
    }

    public Car buildFamilyCar(String model) {
        return builder.reset()
                .setModel(model)
                .setYear(java.time.Year.now().getValue())
                .setBasePrice(35000.0)
                .setEngine(EngineType.V6)
                .setTransmission(TransmissionType.AUTOMATIC_6_SPEED)
                .setColor(ExteriorColor.SILVER)
                .setRims(RimType.ALLOY_17)
                .addInteriorFeatures(
                        InteriorFeature.LEATHER_SEATS,
                        InteriorFeature.GPS_NAVIGATION,
                        InteriorFeature.HEATED_SEATS
                )
                .addExteriorOptions(
                        ExteriorOption.SUNROOF,
                        ExteriorOption.ROOF_RAILS
                )
                .addSafetyFeatures(
                        SafetyFeature.REAR_CAMERA,
                        SafetyFeature.PARKING_SENSORS,
                        SafetyFeature.BLIND_SPOT_MONITOR
                )
                .build();
    }

    public Car buildSportsCar(String model) {
        return builder.reset()
                .setModel(model)
                .setYear(java.time.Year.now().getValue())
                .setBasePrice(55000.0)
                .setEngine(EngineType.V8)
                .setTransmission(TransmissionType.DUAL_CLUTCH)
                .setColor(ExteriorColor.RED)
                .setRims(RimType.PERFORMANCE_21)
                .addInteriorFeatures(
                        InteriorFeature.LEATHER_SEATS,
                        InteriorFeature.PREMIUM_SOUND,
                        InteriorFeature.HEAD_UP_DISPLAY,
                        InteriorFeature.AMBIENT_LIGHTING
                )
                .addExteriorOptions(
                        ExteriorOption.SPOILER,
                        ExteriorOption.CHROME_PACKAGE
                )
                .addSafetyFeatures(
                        SafetyFeature.ADAPTIVE_CRUISE,
                        SafetyFeature.LANE_DEPARTURE,
                        SafetyFeature.COLLISION_WARNING
                )
                .build();
    }

    public Car buildLuxuryCar(String model) {
        return builder.reset()
                .setModel(model)
                .setYear(java.time.Year.now().getValue())
                .setBasePrice(85000.0)
                .setEngine(EngineType.V8)
                .setTransmission(TransmissionType.AUTOMATIC_8_SPEED)
                .setColor(ExteriorColor.BLACK)
                .setRims(RimType.PREMIUM_20)
                .addInteriorFeatures(
                        InteriorFeature.LEATHER_SEATS,
                        InteriorFeature.HEATED_SEATS,
                        InteriorFeature.VENTILATED_SEATS,
                        InteriorFeature.GPS_NAVIGATION,
                        InteriorFeature.PREMIUM_SOUND,
                        InteriorFeature.AMBIENT_LIGHTING,
                        InteriorFeature.WIRELESS_CHARGING,
                        InteriorFeature.HEAD_UP_DISPLAY,
                        InteriorFeature.PANORAMIC_DISPLAY,
                        InteriorFeature.REAR_ENTERTAINMENT
                )
                .addExteriorOptions(
                        ExteriorOption.SUNROOF,
                        ExteriorOption.CHROME_PACKAGE,
                        ExteriorOption.TINTED_WINDOWS
                )
                .addSafetyFeatures(
                        SafetyFeature.AIRBAGS_SIDE,
                        SafetyFeature.AIRBAGS_CURTAIN,
                        SafetyFeature.REAR_CAMERA,
                        SafetyFeature.PARKING_SENSORS,
                        SafetyFeature.BLIND_SPOT_MONITOR,
                        SafetyFeature.LANE_DEPARTURE,
                        SafetyFeature.ADAPTIVE_CRUISE,
                        SafetyFeature.COLLISION_WARNING,
                        SafetyFeature.AUTO_EMERGENCY_BRAKE,
                        SafetyFeature.NIGHT_VISION
                )
                .build();
    }

    public Car buildElectricCar(String model) {
        return builder.reset()
                .setModel(model)
                .setYear(java.time.Year.now().getValue())
                .setBasePrice(45000.0)
                .setEngine(EngineType.ELECTRIC)
                .setTransmission(TransmissionType.CVT)
                .setColor(ExteriorColor.BLUE)
                .setRims(RimType.ALLOY_18)
                .addInteriorFeatures(
                        InteriorFeature.LEATHER_SEATS,
                        InteriorFeature.GPS_NAVIGATION,
                        InteriorFeature.PREMIUM_SOUND,
                        InteriorFeature.WIRELESS_CHARGING,
                        InteriorFeature.PANORAMIC_DISPLAY
                )
                .addExteriorOptions(
                        ExteriorOption.SUNROOF,
                        ExteriorOption.TINTED_WINDOWS
                )
                .addSafetyFeatures(
                        SafetyFeature.REAR_CAMERA,
                        SafetyFeature.PARKING_SENSORS,
                        SafetyFeature.BLIND_SPOT_MONITOR,
                        SafetyFeature.LANE_DEPARTURE,
                        SafetyFeature.ADAPTIVE_CRUISE,
                        SafetyFeature.AUTO_EMERGENCY_BRAKE
                )
                .build();
    }
}
