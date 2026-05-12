package com.arqob.pf.dto;

import com.arqob.pf.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String nome;
    private String cpf;
    private User.Papel papel;
}
