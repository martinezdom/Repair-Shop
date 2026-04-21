package io.github.martinezdom.repairshop.dtos;

import jakarta.validation.constraints.NotBlank;

public class VehicleUpdateDTO {
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @NotBlank
    private Integer year;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
