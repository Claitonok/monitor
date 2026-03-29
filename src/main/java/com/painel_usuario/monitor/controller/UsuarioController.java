package com.painel_usuario.monitor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.painel_usuario.monitor.model.Usuario;
import com.painel_usuario.monitor.service.UsuarioService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private com.painel_usuario.monitor.security.JwtService jwtService;

    /**
     * Obtém todos os usuários. Se não houver nenhum usuário, cria um usuário padrão
     * "Admin".
     * 
     * @return Lista de usuários.
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> ObterTodos() {

        try {
            return new ResponseEntity<>(usuarioService.ObterTodos(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Obtém um usuário pelo ID.
     * 
     * @param id
     * @return O usuário encontrado ou null se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> ObterPorId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(usuarioService.ObterPorId(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Salva um novo usuário ou atualiza um usuário existente.
     * 
     * @param usuario O usuário a ser salvo ou atualizado.
     * @return O usuário salvo ou atualizado.
     */
    @PostMapping
    public ResponseEntity<Usuario> salvar_usuario(@RequestBody Usuario usuario) {
        try {
            return new ResponseEntity<>(usuarioService.Salvar(usuario), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Usuario> AtualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {

        try {
            Optional<Usuario> usuarioId = usuarioService.ObterPorId(id);
            if (usuarioId.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(usuarioService.Atualizar(id, usuario), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Autentica um usuário com base no email e senha fornecidos.
     * Se a autenticação for bem-sucedida, retorna um token JWT e os detalhes do
     * usuário.
     * 
     * @param usuario
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {

        Usuario usuarioLogin = usuarioService.loginUsers(
                usuario.getEmail(),
                usuario.getSenha());

        if (usuarioLogin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha inválidos");
        }

        String token = jwtService.generateToken(usuarioLogin.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("usuario", usuarioLogin);

        return ResponseEntity.ok(response);
    }

    /**
     * Exclui um usuário pelo ID.
     * 
     * @param id O ID do usuário a ser excluído.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> excluir_usuario(@PathVariable Long id) {
        try {
            usuarioService.Excluir(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
