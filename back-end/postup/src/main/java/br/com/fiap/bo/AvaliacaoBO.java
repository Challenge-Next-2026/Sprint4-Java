package br.com.fiap.bo;

import br.com.fiap.dao.AvaliacaoDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.AvaliacaoTO;
import br.com.fiap.to.UsuarioTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class AvaliacaoBO {
    // Atributo
    private AvaliacaoDAO avaliacaoDAO;

    // obterProximoId()
    public int obterProximoId() throws SQLException {
        avaliacaoDAO = new AvaliacaoDAO();
        // Regras de negócio
        return avaliacaoDAO.obterProximoId();
    }

    // save()
    public AvaliacaoTO save(AvaliacaoTO avaliacao){
        avaliacaoDAO = new AvaliacaoDAO();
        // Regras de negócio
        return avaliacaoDAO.save(avaliacao);
    }

    // update()
    public AvaliacaoTO update(AvaliacaoTO avaliacao) {
        avaliacaoDAO = new AvaliacaoDAO();
        // Regras de negócio
        return avaliacaoDAO.update(avaliacao);
    }

    // findAll()
    public ArrayList<AvaliacaoTO> findAll() {
        avaliacaoDAO = new AvaliacaoDAO();
        // Regras de negócio
        return avaliacaoDAO.findAll();
    }

    // findById()
    public AvaliacaoTO findById(int idAvaliacao) {
        avaliacaoDAO = new AvaliacaoDAO();
        // Regras de negócio
        return avaliacaoDAO.findById(idAvaliacao);
    }

    // delete()
    public boolean delete(int idAvaliacao) {
        avaliacaoDAO = new AvaliacaoDAO();
        // Regras de negócio
        return avaliacaoDAO.delete(idAvaliacao);
    }
}
