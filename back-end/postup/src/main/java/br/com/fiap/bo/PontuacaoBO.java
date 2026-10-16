package br.com.fiap.bo;

import br.com.fiap.dao.PontuacaoDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.PontuacaoTO;
import br.com.fiap.to.UsuarioTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class PontuacaoBO {
    // Atributo
    private PontuacaoDAO pontuacaoDAO;

    // obterProximoId()
    public int obterProximoId() throws SQLException {
        pontuacaoDAO = new PontuacaoDAO();
        // Regras de negócio
        return pontuacaoDAO.obterProximoId();
    }

    // save()
    public PontuacaoTO save(PontuacaoTO pontuacao){
        pontuacaoDAO = new PontuacaoDAO();
        // Regras de negócio
        return pontuacaoDAO.save(pontuacao);
    }

    // update()
    public PontuacaoTO update(PontuacaoTO pontuacao) {
        pontuacaoDAO = new PontuacaoDAO();
        // Regras de negócio
        return pontuacaoDAO.update(pontuacao);
    }

    // findAll()
    public ArrayList<PontuacaoTO> findAll() {
        pontuacaoDAO = new PontuacaoDAO();
        // Regras de negócio
        return pontuacaoDAO.findAll();
    }

    // findById()
    public PontuacaoTO findById(int idPontuacao) {
        pontuacaoDAO = new PontuacaoDAO();
        // Regras de negócio
        return pontuacaoDAO.findById(idPontuacao);
    }

    // delete()
    public boolean delete(int idPontuacao) {
        pontuacaoDAO = new PontuacaoDAO();
        // Regras de negócio
        return pontuacaoDAO.delete(idPontuacao);
    }
}
