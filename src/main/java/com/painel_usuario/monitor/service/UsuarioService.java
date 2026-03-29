package com.painel_usuario.monitor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.painel_usuario.monitor.model.Usuario;
import com.painel_usuario.monitor.repository.RepositoryUsuario;

@Service
public class UsuarioService {

    @Autowired
    private RepositoryUsuario repositoryUsuario;

    /**
     * Obtém todos os usuários. Se não houver nenhum usuário, cria um usuário padrão "Admin".
     * @return Lista de usuários.
    */
    public List<Usuario> ObterTodos() {

        if (repositoryUsuario.count() == 0) {

            Usuario usuario = new Usuario();

            usuario.setNome("Admin");
            usuario.setEmail("admin@example.com");
            usuario.setSenha("admin123");

            repositoryUsuario.save(usuario);
        }
        return repositoryUsuario.findAll();
    }

    /**
     * Salva um novo usuário ou atualiza um usuário existente.
     * @param usuario O usuário a ser salvo ou atualizado.
     * @return O usuário salvo ou atualizado.
     */
    public Usuario Salvar(Usuario usuario) {
        return repositoryUsuario.save(usuario);
    }

    /**
     * Exclui um usuário pelo ID.
     * @param id O ID do usuário a ser excluído.
     */
    public void Excluir(Long id) {
        repositoryUsuario.deleteById(id);
    }

    /**
     * Obtém um usuário pelo ID.
     * @param id O ID do usuário a ser obtido.
     * @return O usuário encontrado ou null se não encontrado.
     */
    public Optional<Usuario> ObterPorId(Long id) {

        Optional<Usuario> usuOptional = repositoryUsuario.findById(id);

        if (usuOptional.isEmpty()) {
           System.out.println("Não foi encontrado o id: " + id + " do usuario");
        }
        return usuOptional;
    }

    /**
     * Atualiza um usuário existente.
     * @param id O ID do usuário a ser atualizado.
     * @param usuario O usuário com os dados atualizados.
     * @return O usuário atualizado.
     */
    public Usuario Atualizar(Long id, Usuario usuario) {

        Optional<Usuario> usuOptional = repositoryUsuario.findById(id);

        if (usuOptional.isEmpty()) {
           System.out.println("Não foi encontrado o id: " + id + " do usuario");
        } else {

        System.out.println("Foi encontrado o id: " + id + " do usuario");

        usuario.setId(id);
        Usuario usuario_save = repositoryUsuario.save(usuario);
        return usuario_save;
        }

        return usuOptional.get();
    }

    /**
     * Realiza o login de um usuário.
     * @param usuario O usuário com os dados de login.
     * @return O usuário encontrado ou null se não encontrado.
     */
    public Usuario loginUsers(String email, String senha){
        Optional<Usuario> usuarioLogin = repositoryUsuario.findByEmailAndSenha(email, senha);
        if (usuarioLogin.isEmpty()) {
            System.out.println("Usuario não Cadastrado");
            return null;
        } else {
            System.out.println("Foi encontrado o Cadastrado: " + usuarioLogin.get() + " do usuario");
            return usuarioLogin.get();
        }
    }
}
