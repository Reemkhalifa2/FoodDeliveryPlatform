package com.example.FoodDeliveryPlatformDemo.dto.request;

import com.example.FoodDeliveryPlatformDemo.entities.ComboMeal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComboMealRequestDTO {
    @NotBlank(message = "Combo name is required")
    private String comboName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Total price is required")
    @DecimalMin(value = "0.0")
    private Double totalPrice;

    public static ComboMeal toEntity(ComboMealRequestDTO dto) {
        ComboMeal comboMeal = new ComboMeal();

        comboMeal.setComboName(dto.getComboName());
        comboMeal.setDescription(dto.getDescription());
        comboMeal.setTotalPrice(dto.getTotalPrice());
        return comboMeal;
    }


}
