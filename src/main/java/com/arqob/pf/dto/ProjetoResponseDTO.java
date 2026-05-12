package com.arqob.pf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjetoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
}
