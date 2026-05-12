package com.arqob.pf.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    public enum Papel {
        ADMIN,
        USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Papel papel;
}