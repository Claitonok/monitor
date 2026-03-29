package com.painel_usuario.monitor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.painel_usuario.monitor.model.Usuario;

public interface RepositoryUsuario extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}
