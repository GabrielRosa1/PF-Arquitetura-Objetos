package com.arqob.pf.controller;

import com.arqob.pf.dto.UserEditDTO;
import com.arqob.pf.dto.UserResponseDTO;
import com.arqob.pf.dto.UserSaveDTO;
import com.arqob.pf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> criar(@RequestBody UserSaveDTO dto) {
        return ResponseEntity.status(201).body(service.criarUsuario(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> editar(
            @PathVariable Long id,
            @RequestBody UserEditDTO dto,
            @RequestHeader("X-USER-ID") Long userId) {

        service.validarAdmin(userId);
        return ResponseEntity.ok(service.editarUser(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long userId) {

        service.validarAdmin(userId);
        service.deletarUser(id);
        return ResponseEntity.noContent().build();
    }

}
