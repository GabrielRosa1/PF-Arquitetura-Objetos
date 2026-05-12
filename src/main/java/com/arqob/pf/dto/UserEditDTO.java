package com.arqob.pf.dto;

import com.arqob.pf.models.User;
import lombok.Data;

@Data
public class UserEditDTO {
    private String nome;
    private String cpf;
    private User.Papel papel;
}
