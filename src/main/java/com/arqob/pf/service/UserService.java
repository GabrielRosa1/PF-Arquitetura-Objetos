package com.arqob.pf.service;

import com.arqob.pf.dto.UserEditDTO;
import com.arqob.pf.dto.UserResponseDTO;
import com.arqob.pf.dto.UserSaveDTO;
import com.arqob.pf.exception.ValidationException;
import com.arqob.pf.exception.ForbiddenException;
import com.arqob.pf.exception.NotFoundException;
import com.arqob.pf.models.User;
import com.arqob.pf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserResponseDTO criarUsuario(UserSaveDTO dto) {
        if (dto.getCpf() == null || dto.getCpf().isBlank()) {
            throw new ValidationException("CPF é obrigatório");
        }
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new ValidationException("Nome é obrigatório");
        }

        User user = new User();
        user.setNome(dto.getNome());
        user.setCpf(dto.getCpf());
        user.setPapel(dto.getPapel());

        User criado = repository.save(user);
        return toDTO(criado);
    }

    public UserResponseDTO editarUser(UserEditDTO dto, Long id) {
        if (dto.getCpf() == null || dto.getCpf().isBlank()) {
            throw new ValidationException("CPF é obrigatório");
        }
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new ValidationException("Nome é obrigatório");
        }

        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        user.setNome(dto.getNome());
        user.setCpf(dto.getCpf());
        user.setPapel(dto.getPapel());

        return toDTO(repository.save(user));
    }

    public UserResponseDTO buscarPorId(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return toDTO(user);
    }

    public List<UserResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public void deletarUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        repository.delete(user);
    }

    public void validarAdmin(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        if (user.getPapel() != User.Papel.ADMIN) {
            throw new ForbiddenException("Acesso negado: apenas ADMIN pode realizar esta ação");
        }
    }

    private UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getNome(), user.getCpf(), user.getPapel());
    }
}
