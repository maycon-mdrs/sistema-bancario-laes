package ufrn.imd.sistema_bancario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record ContaDto(
    @NotBlank
    String numeroConta,
    @PositiveOrZero
    Double saldoInicial
) {}
