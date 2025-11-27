package dev.gustavo.toolschallenge.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstornoDTO {

    @NotBlank
    @Size(min = 16, max = 16)
    private String id;
}
