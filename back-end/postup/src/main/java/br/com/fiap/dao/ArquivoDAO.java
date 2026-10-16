package br.com.fiap.dao;

import br.com.fiap.to.ArquivoTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArquivoDAO {
    // Atributo
    private ArquivoTO arquivo;

    // Metodos Exclusivo
    // Metodo para fazer a leitura e definir sempre um id novo
    public int obterProximoId() throws SQLException {
        // Comando SQL que busca o próximo id disponível
        String sql = "SELECT NVL(MAX(id_arquivo), 0) + 1 AS proximo_id FROM ARQUIVO";
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
    public ArquivoTO save(ArquivoTO arquivo){
        // Comando sql para inserir dentro da tabela Arquivo
        String sql = "INSERT INTO ARQUIVO(id_arquivo, nm_arquivo, in_tipoarquivo, ds_tamanho, cod_url, dt_upload) VALUES (?, ?, ?, ?, ?, ?)";
        // Try with resourses
        // Objeto criado e instanciado dentro do try para fechar automaticamente
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)){
            // Atribuindo valores ao comando INSERT
            ps.setInt(1, arquivo.getIdArquivo());
            ps.setString(2, arquivo.getNomeArquivo());
            ps.setString(3, arquivo.getTipo());
            ps.setInt(4, arquivo.getTamanho());
            ps.setString(5, arquivo.getUrl());
            ps.setDate(6, java.sql.Date.valueOf(arquivo.getDataUpload()));
            // Verificação
            if (ps.executeUpdate() > 0) {
                return arquivo;
            } else {
                System.out.println("Erro ao Inserir!");
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
    public ArquivoTO update(ArquivoTO arquivo){
        // Comando sql para inserir dentro da tabela Arquivo
        String sql = "UPDATE ARQUIVO SET nm_arquivo = ?, in_tipoarquivo = ?, ds_tamanho = ?, cod_url = ? WHERE id_arquivo = ?";
        // Try with resourses
        // Objeto criado e instanciado dentro do try para fechar automaticamente
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)){
            // Atribuindo valores ao comando UPDATE
            ps.setString(1, arquivo.getNomeArquivo());
            ps.setString(2, arquivo.getTipo());
            ps.setInt(3, arquivo.getTamanho());
            ps.setString(4, arquivo.getUrl());
            ps.setInt(5, arquivo.getIdArquivo());
            // Verificação
            if (ps.executeUpdate() > 0) {
                return arquivo;
            } else {
                System.out.println("Erro ao Inserir!");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    // Metodo para exibir arquivos cadastrados
    public ArrayList<ArquivoTO> findAll(){
        // Comando sql
        String sql = "SELECT id_arquivo, nm_arquivo FROM ARQUIVO ORDER BY id_arquivo";
        // lista para armazenar todos os usuários
        ArrayList<ArquivoTO> arquivosCadastrados = new ArrayList<>();
        // try-with-resourses passando o comando sql para o objeto ps e armazenando a execução em um objeto rs
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            // Confere se há usuários na tabela
            if (ps != null) {
                // laço que pega usuário por usuário
                while (rs.next()){
                    // Novo objeto
                    ArquivoTO arquivo = new ArquivoTO();
                    // Pegando valor das colunas
                    arquivo.setIdArquivo(rs.getInt(1));
                    arquivo.setNomeArquivo(rs.getString(2));
                    // Adição de objeto à lista
                    arquivosCadastrados.add(arquivo);
                }
            } else {
                System.out.println("Não há postagens cadastradas!");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro de SQL!: " + e.getMessage());
            return null;
        } finally {
            ConnectionFactory.closeConnection();
        }
        // Retorno de lista
        return arquivosCadastrados;
    }

    // SELECT
    public ArquivoTO findById(int idArquivo){
        // Comando SQL
        String sql = "SELECT id_arquivo, nm_arquivo, in_tipoarquivo, ds_tamanho, cod_url, dt_upload FROM ARQUIVO WHERE id_arquivo = ?";
        // try-with-resources
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)){
            // Substituição de ?
            ps.setInt(1, idArquivo);
            try (ResultSet rs = ps.executeQuery()){
                // Validação se há postagens
                if (rs.next()) {
                    arquivo.setIdArquivo(rs.getInt("id_arquivo"));
                    arquivo.setNomeArquivo(rs.getString("nm_arquivo"));
                    arquivo.setTipo(rs.getString("in_tipoarquivo"));
                    arquivo.setTamanho(rs.getInt("ds_tamanho"));
                    arquivo.setUrl(rs.getString("cod_url"));
                    arquivo.setDataUpload(rs.getDate("dt_upload").toLocalDate());
                } else {
                    System.out.println("Registro não encontrado");
                    return null;
                }
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return arquivo;
    }

    // DELETE
    public boolean delete(int idArquivo){
        // Comando sql para deletar dentro da tabela Postagem
        String sql = "DELETE FROM ARQUIVO WHERE id_arquivo = ?";
        // Try with resourses
        // Objeto criado e instanciado dentro do try para fechar automaticamente
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)){
            // Atribuindo valores ao comando INSERT
            ps.setInt(1, idArquivo);
            // Retorno
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        return false;
    }
}
