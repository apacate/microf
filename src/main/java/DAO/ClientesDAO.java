package DAO;

import DTO.ClientesDTO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ClientesDAO {

    private Connection conn;

    public ClientesDAO() {
        this.conn = new ConexaoDAO().ConectaBD();
    }

    public void cadastrarClientes(ClientesDTO cliente) {
        String sql = "INSERT INTO clientes (nome, sobrenome, data_nascimento, NUIT, endereco, telefone, email, documento_de_ident, obs) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, cliente.getNome());
            stm.setString(2, cliente.getSobrenome());
            // Certifique-se de que a data de nascimento seja um tipo java.sql.Date
            stm.setDate(3, new java.sql.Date(cliente.getDataNascimento().getTime()));
            stm.setString(4, cliente.getNUIT());
            stm.setString(5, cliente.getEndereco());
            stm.setString(6, cliente.getTelefone());
            stm.setString(7, cliente.getEmail());
            stm.setString(8, cliente.getDocumentoDeIdent());
            stm.setString(9, cliente.getObs());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao cadastrar cliente.");
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir dados: " + erro.getMessage());
            erro.printStackTrace(); // Mostra a stack trace do erro no console
        }
    }
    
    
    public List<ClientesDTO> pesquisarClientes(String termo) {
    String sql = "SELECT * FROM clientes WHERE nome LIKE ? OR sobrenome LIKE ?";
    List<ClientesDTO> clientes = new ArrayList<>();
    PreparedStatement stm = null;
    ResultSet rs = null;

    try {
        stm = conn.prepareStatement(sql);
        stm.setString(1, "%" + termo + "%");
        stm.setString(2, "%" + termo + "%"); // Usei o mesmo valor para nome e sobrenome
        rs = stm.executeQuery();

        while (rs.next()) {
            ClientesDTO cliente = new ClientesDTO();
            cliente.setClienteId(rs.getInt("cliente_id"));
            cliente.setNome(rs.getString("nome"));
            cliente.setSobrenome(rs.getString("sobrenome"));
            cliente.setDataNascimento(rs.getDate("data_nascimento"));
            cliente.setNUIT(rs.getString("NUIT"));
            cliente.setEndereco(rs.getString("endereco"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setEmail(rs.getString("email"));
            cliente.setDocumentoDeIdent(rs.getString("documento_de_ident"));
            cliente.setObs(rs.getString("obs"));
            clientes.add(cliente);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao pesquisar clientes: " + e.getMessage());
    } finally {
        closeResources(stm, rs);
    }

    return clientes;
}


    public void excluirClientes(int clienteId) {
        String sql = "DELETE FROM clientes WHERE cliente_id = ?";
        PreparedStatement stm = null;
        try {
            stm = conn.prepareStatement(sql);
            stm.setInt(1, clienteId);
            int linhasAfetadas = stm.executeUpdate();
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Dados excluídos com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum registro encontrado para excluir.");
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir dados: " + erro.getMessage());
        } finally {
            closeResources(stm, null);
        }
    }

    public List<ClientesDTO> listarClientes() {
        String sql = "SELECT * FROM clientes";
        List<ClientesDTO> clientes = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                ClientesDTO cliente = new ClientesDTO();
                cliente.setClienteId(rs.getInt("cliente_id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setSobrenome(rs.getString("sobrenome"));
                cliente.setDataNascimento(rs.getDate("data_nascimento"));
                cliente.setNUIT(rs.getString("NUIT"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setDocumentoDeIdent(rs.getString("documento_de_ident"));
                cliente.setObs(rs.getString("obs"));

                clientes.add(cliente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar clientes: " + e.getMessage());
        } finally {
            closeResources(stm, rs);
        }

        return clientes;
    }

public List<ClientesDTO> buscarClientesPorNome(String nome) throws SQLException {
        List<ClientesDTO> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nome LIKE ? OR sobrenome LIKE ?";
        
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, "%" + nome + "%");
            pstm.setString(2, "%" + nome + "%");
            ResultSet rs = pstm.executeQuery();
            
            while (rs.next()) {
                ClientesDTO cliente = new ClientesDTO();
                cliente.setClienteId(rs.getInt("cliente_id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setSobrenome(rs.getString("sobrenome"));
              
                clientes.add(cliente);
            }
        }
        return clientes;
    }


    public void atualizarClientes(ClientesDTO cliente) {
        String sql = "UPDATE clientes SET nome = ?, sobrenome = ?, data_nascimento = ?, NUIT = ?, endereco = ?, telefone = ?, email = ?, documento_de_ident = ?, obs = ? WHERE cliente_id = ?";

        try (PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setString(1, cliente.getNome());
            stm.setString(2, cliente.getSobrenome());
            stm.setDate(3, new java.sql.Date(cliente.getDataNascimento().getTime()));
            stm.setString(4, cliente.getNUIT());
            stm.setString(5, cliente.getEndereco());
            stm.setString(6, cliente.getTelefone());
            stm.setString(7, cliente.getEmail());
            stm.setString(8, cliente.getDocumentoDeIdent());
            stm.setString(9, cliente.getObs());
            stm.setInt(10, cliente.getClienteId());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao atualizar cliente.");
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar dados: " + erro.getMessage());
            erro.printStackTrace(); // Mostra a stack trace do erro no console
        }
    }
    
    
    

    private void closeResources(PreparedStatement stm, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
