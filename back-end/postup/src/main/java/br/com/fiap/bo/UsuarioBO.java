package br.com.fiap.bo;

import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.UsuarioTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioBO{
    // Atributo
    private UsuarioDAO usuarioDAO;
    private UsuarioTO usuario;

    // save()
    public int obterProximoId() throws SQLException {
        usuarioDAO = new UsuarioDAO();
        // Regras de negócio
        return usuarioDAO.obterProximoId();
    }

    public UsuarioTO save(UsuarioTO usuario){
        usuarioDAO = new UsuarioDAO();
        // Regras de negócio
        return usuarioDAO.save(usuario);
    }

    public UsuarioTO update(UsuarioTO usuario) {
        usuarioDAO = new UsuarioDAO();
        // Regras de negócio
        return usuarioDAO.update(usuario);
    }

    public ArrayList<UsuarioTO> findAll() {
        usuarioDAO = new UsuarioDAO();
        // Regras de negócio
        return usuarioDAO.findAll();
    }

    public UsuarioTO findById(int idUsuario) {
        usuarioDAO = new UsuarioDAO();
        // Regras de negócio
        return usuarioDAO.findById(idUsuario);
    }

    public boolean delete(int idUsuario) {
        usuarioDAO = new UsuarioDAO();
        // Regras de negócio
        return usuarioDAO.delete(idUsuario);
    }
}
