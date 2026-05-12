package com.arqob.pf.controller;

import com.arqob.pf.dto.ProjetoEditDTO;
import com.arqob.pf.dto.ProjetoResponseDTO;
import com.arqob.pf.dto.ProjetoSaveDTO;
import com.arqob.pf.service.ProjetoService;
import com.arqob.pf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos")
@RequiredArgsConstructor
public class ProjetoController {

    private final ProjetoService service;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProjetoResponseDTO> criar(
            @RequestBody ProjetoSaveDTO dto,
            @RequestHeader("X-USER-ID") Long userId) {

        userService.validarAdmin(userId);
        return ResponseEntity.status(201).body(service.criarProjeto(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> editar(
            @PathVariable Long id,
            @RequestBody ProjetoEditDTO dto,
            @RequestHeader("X-USER-ID") Long userId) {

        userService.validarAdmin(userId);
        return ResponseEntity.ok(service.editarProjeto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            @RequestHeader("X-USER-ID") Long userId) {

        userService.validarAdmin(userId);
        service.deletarProjeto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/usuarios/{usuarioId}")
    public ResponseEntity<Void> adicionarUsuario(
            @PathVariable Long id,
            @PathVariable Long usuarioId,
            @RequestHeader("X-USER-ID") Long userId) {

        userService.validarAdmin(userId);
        service.adicionarUsuario(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/usuarios/{usuarioId}")
    public ResponseEntity<Void> removerUsuario(
            @PathVariable Long id,
            @PathVariable Long usuarioId,
            @RequestHeader("X-USER-ID") Long userId) {

        userService.validarAdmin(userId);
        service.removerUsuario(id, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
