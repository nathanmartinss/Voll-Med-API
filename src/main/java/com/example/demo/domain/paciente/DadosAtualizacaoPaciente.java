package com.example.demo.domain.paciente;

import jakarta.validation.constraints.NotNull;
import com.example.demo.domain.endereco.*;

public record DadosAtualizacaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
