package br.com.fiap.dao;

import br.com.fiap.to.PontuacaoTO;
import br.com.fiap.to.UsuarioTO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PontuacaoDAO {
    // Atributo
    private PontuacaoTO pontuacao;

    // Metodos exclusivo
    // Metodo para fazer a leitura e definir sempre um id novo
    public int obterProximoId() throws SQLException {
        // Comando SQL que busca o próximo id disponível
        String sql = "SELECT NVL(MAX(id_usuario), 0) + 1 AS proximo_id FROM PONTUACAO";
        // try-with-resourses passando o comando sql para o objeto ps e armazenando a execução em um objeto rs
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // Condicional que avalia se a próxima linha está preenchida
            if (rs.next()) {
                // retorna o próximo id
                return rs.getInt("proximo_id");
            }
        } finally {
            ConnectionFactory.closeConnection();
        }
        throw new SQLException("Não foi possível obter o próximo ID.");
    }

    // CREATE / INSERT
    public PontuacaoTO save(PontuacaoTO pontuacao){
        // Comando sql para inserir dentro da tabela Usuario
        String sql = "INSERT INTO PONTUACAO(id_pontuacao, vl_pontuacao, ds_origempontuacao, dt_pontuacao) VALUES (?, ?, ?, ?)";
        // Try with resourses
        // Objeto criado e instanciado dentro do try para fechar automaticamente
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)){
            // Atribuindo valores ao comando INSERT
            ps.setInt(1, pontuacao.getIdPontuacao());
            ps.setFloat(2, pontuacao.getValor());
            ps.setString(3, "Avaliação");
            ps.setDate(4, java.sql.Date.valueOf(pontuacao.getDataPontuacao()));
            // Verificação
            if (ps.executeUpdate() > 0) {
                return pontuacao;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    // UPDATE
    public PontuacaoTO update(PontuacaoTO pontuacao){
        // Comando sql para alterar dentro da tabela Usuario
        String sql = "UPDATE PONTUACAO SET vl_pontuacao = ?, dt_pontuacao = ? WHERE id_pontuacao = ?";
        // Try with resourses
        // Objeto criado e instanciado dentro do try para fechar automaticamente
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)){
            // Atribuindo valores ao comando INSERT
            ps.setDouble(1, pontuacao.getValor());
            ps.setDate(2, Date.valueOf(pontuacao.getDataPontuacao()));
            ps.setInt(3, pontuacao.getIdPontuacao());
            // Verificação
            if (ps.executeUpdate() > 0) {
                return pontuacao;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro de SQl: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    // SELECT
    public PontuacaoTO findById(int idPontuacao){
        pontuacao = new PontuacaoTO();
        // Comando SQL
        String sql = "SELECT * FROM PONTUACAO WHERE id_pontuacao = ?";
        // try-with-resources
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)){
            // Substituição de ?
            ps.setInt(1, idPontuacao);
            try (ResultSet rs = ps.executeQuery()){
                // Validação se há usuários
                if (rs.next()) {
                    pontuacao.setIdPontuacao(idPontuacao);
                    pontuacao.setValor(rs.getFloat("vl_pontuacao"));
                    pontuacao.setDataPontuacao(rs.getDate("dt_pontuacao").toLocalDate());
                } else {
                    return null;
                }
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return pontuacao;
    }

    // Metodo para exibir usuários cadastrados
    public ArrayList<PontuacaoTO> findAll(){
        // lista para armazenar todos os usuários
        ArrayList<PontuacaoTO> pontuacoesCadastradas = new ArrayList<>();
        // Comando sql
        String sql = "SELECT id_pontuacao, vl_pontuacao FROM PONTUACAO ORDER BY id_pontuacao";
        // try-with-resourses passando o comando sql para o objeto ps e armazenando a execução em um objeto rs
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            // Confere se há usuários na tabela
            if (rs != null) {
                // laço que pega usuário por usuário
                while (rs.next()){
                    // Novo objeto
                    PontuacaoTO pontuacao = new PontuacaoTO();
                    // Pegando valor das colunas
                    pontuacao.setIdPontuacao(rs.getInt("id_pontuacao"));
                    pontuacao.setValor(rs.getFloat("vl_pontuacao"));
                    // Adição de objeto à lista
                    pontuacoesCadastradas.add(pontuacao);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro de SQL!: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return pontuacoesCadastradas;
    }

    // Excluir
    public boolean delete(int idPontuacao){
        // Comando sql para excluir dentro da tabela Usuario
        String sql = "DELETE FROM PONTUACAO WHERE id_pontuacao = ?";
        // Try with resourses
        // Objeto criado e instanciado dentro do try para fechar automaticamente
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)){
            // Atribuindo valores ao comando INSERT
            ps.setInt(1, idPontuacao);
            // Retorno
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao Excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }
}
