package br.com.fiap.dao;

import br.com.fiap.to.AvaliacaoTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AvaliacaoDAO {
    // Atributo
    private AvaliacaoTO avaliacao;

    // Metodo Exclusivo
    // Metodo para fazer a leitura e definir sempre um id novo
    public int obterProximoId() throws SQLException {
        // Comando SQL que busca o próximo id disponível
        String sql = "SELECT NVL(MAX(id_avaliacao), 0) + 1 AS proximo_id FROM AVALIACAO";
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
    public AvaliacaoTO save(AvaliacaoTO avaliacao){
        // Comando sql para inserir dentro da tabela Arquivo
        String sql = "INSERT INTO AVALIACAO(id_avaliacao, ds_criterioavaliacao, vl_notaimpacto, vl_notadificuldade, vl_notaconfiabilidade, vl_notafrequencia, dt_avaliacao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        // Try with resourses
        // Objeto criado e instanciado dentro do try para fechar automaticamente
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)){
            // Atribuindo valores ao comando INSERT
            ps.setInt(1, avaliacao.getIdAvaliacao());
            ps.setString(2, avaliacao.getCriterio());
            ps.setInt(3, avaliacao.getNotaImpacto());
            ps.setInt(4, avaliacao.getNotaDificuldade());
            ps.setInt(5, avaliacao.getNotaConfiabilidade());
            ps.setInt(6, avaliacao.getNotaFrequencia());
            ps.setDate(7, java.sql.Date.valueOf(avaliacao.getDataAvaliacao()));
            // Verificação
            if (ps.executeUpdate() > 0) {
                System.out.println("Inserido com Sucesso!");
                return avaliacao;
            } else {
                System.out.println("Erro ao Inserir!");
            }
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public AvaliacaoTO update(AvaliacaoTO avaliacao){
        // Comando sql para alterar dentro da tabela Usuario
        String sql = "UPDATE AVALIACAO SET ds_criterioavaliacao = ?, vl_notaimpacto = ?, vl_notadificuldade = ?, vl_notaconfiabilidade = ?, vl_notafrequencia = ? WHERE id_avaliacao = ?";
        // Try with resourses
        // Objeto criado e instanciado dentro do try para fechar automaticamente
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)){
            // Atribuindo valores ao comando INSERT
            ps.setString(1, avaliacao.getCriterio());
            ps.setDouble(2, avaliacao.getNotaImpacto());
            ps.setDouble(3, avaliacao.getNotaDificuldade());
            ps.setDouble(4, avaliacao.getNotaConfiabilidade());
            ps.setDouble(5, avaliacao.getNotaFrequencia());
            ps.setInt(6, avaliacao.getIdAvaliacao());
            // Verificação
            if (ps.executeUpdate() > 0) {
                return avaliacao;
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
    public AvaliacaoTO findById(int idAvaliacao){
        avaliacao = new AvaliacaoTO();
        // Comando SQL
        String sql = "SELECT * FROM AVALIACAO WHERE id_avaliacao = ?";
        // try-with-resources
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)){
            // Substituição de ?
            ps.setInt(1, idAvaliacao);
            try (ResultSet rs = ps.executeQuery()){
                // Validação se há postagens
                if (rs.next()) {
                    avaliacao.setIdAvaliacao(rs.getInt("id_avaliacao"));
                    avaliacao.setCriterio(rs.getString("ds_criterioavaliacao"));
                    avaliacao.setNotaImpacto(rs.getInt("vl_notaimpacto"));
                    avaliacao.setNotaDificuldade(rs.getInt("vl_notadificuldade"));
                    avaliacao.setNotaConfiabilidade(rs.getInt("vl_notaconfiabilidade"));
                    avaliacao.setNotaFrequencia(rs.getInt("vl_notafrequencia"));
                    avaliacao.setDataAvaliacao(rs.getDate("dt_avaliacao").toLocalDate());
                } else {
                    return null;
                }
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erro de SQL!: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return avaliacao;
    }

    // Metodo para exibir avaliações cadastrados
    public ArrayList<AvaliacaoTO> findAll(){
        // lista para armazenar todos os usuários
        ArrayList<AvaliacaoTO> avaliacoesCadastradas = new ArrayList<>();
        // Comando sql
        String sql = "SELECT id_avaliacao, ds_criterioavaliacao FROM AVALIACAO ORDER BY id_avaliacao";
        // try-with-resourses passando o comando sql para o objeto ps e armazenando a execução em um objeto rs
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            // Confere se há usuários na tabela
            if (rs != null) {
                // laço que pega usuário por usuário
                while (rs.next()){
                    // Novo objeto
                    AvaliacaoTO avaliacao = new AvaliacaoTO();
                    // Pegando valor das colunas
                    avaliacao.setIdAvaliacao(rs.getInt("id_avaliacao"));
                    avaliacao.setCriterio(rs.getString("ds_criterioavaliacao"));
                    // Adição de objeto à lista
                    avaliacoesCadastradas.add(avaliacao);
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro de SQL!: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return avaliacoesCadastradas;
    }

    // Excluir
    public boolean delete(int idAvaliacao){
        // Comando sql para excluir dentro da tabela Usuario
        String sql = "DELETE FROM AVALIACAO WHERE id_avaliacao = ?";
        // Try with resourses
        // Objeto criado e instanciado dentro do try para fechar automaticamente
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)){
            // Atribuindo valores ao comando INSERT
            ps.setInt(1, idAvaliacao);
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
