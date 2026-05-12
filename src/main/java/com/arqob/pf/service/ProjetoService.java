package com.arqob.pf.service;

import com.arqob.pf.dto.ProjetoEditDTO;
import com.arqob.pf.dto.ProjetoResponseDTO;
import com.arqob.pf.dto.ProjetoSaveDTO;
import com.arqob.pf.dto.UserResponseDTO;
import com.arqob.pf.exception.ValidationException;
import com.arqob.pf.exception.NotFoundException;
import com.arqob.pf.models.Projeto;
import com.arqob.pf.models.User;
import com.arqob.pf.repository.ProjetoRepository;
import com.arqob.pf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetoService {

    private final ProjetoRepository repository;
    private final UserRepository userRepository;

    public ProjetoResponseDTO criarProjeto(ProjetoSaveDTO dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new ValidationException("Nome é obrigatório");
        }
        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new ValidationException("Descrição é obrigatória");
        }

        Projeto projeto = new Projeto();
        projeto.setNome(dto.getNome());
        projeto.setDescricao(dto.getDescricao());

        return toDTO(repository.save(projeto));
    }

    public ProjetoResponseDTO editarProjeto(Long id, ProjetoEditDTO dto) {
        Projeto projeto = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado"));

        projeto.setNome(dto.getNome());
        projeto.setDescricao(dto.getDescricao());

        return toDTO(repository.save(projeto));
    }

    public ProjetoResponseDTO buscarPorId(Long id) {
        Projeto projeto = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado"));
        return toDTO(projeto);
    }

    public List<ProjetoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public void deletarProjeto(Long id) {
        Projeto projeto = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado"));
        repository.delete(projeto);
    }

    public void adicionarUsuario(Long projetoId, Long usuarioId) {
        Projeto projeto = repository.findById(projetoId)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado"));
        User user = userRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (!projeto.getUsuarios().contains(user)) {
            projeto.getUsuarios().add(user);
            repository.save(projeto);
        }
    }

    public void removerUsuario(Long projetoId, Long usuarioId) {
        Projeto projeto = repository.findById(projetoId)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado"));
        User user = userRepository.findById(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        projeto.getUsuarios().remove(user);
        repository.save(projeto);
    }

    private ProjetoResponseDTO toDTO(Projeto projeto) {
        List<UserResponseDTO> usuarios = projeto.getUsuarios().stream()
                .map(u -> new UserResponseDTO(u.getId(), u.getNome(), u.getCpf(), u.getPapel()))
                .toList();
        return new ProjetoResponseDTO(projeto.getId(), projeto.getNome(), projeto.getDescricao(), usuarios);
    }
}
