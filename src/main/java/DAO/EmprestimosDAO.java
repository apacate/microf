package DAO;

import DTO.ClientesDTO;
import DTO.EmprestimosDTO;
import DTO.ParcelaEmprestimoDTO;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmprestimosDAO {
    private Connection conn;

    public EmprestimosDAO() {
        this.conn = new ConexaoDAO().ConectaBD();
    }

    public boolean saveDataToDatabase(int clienteId, int numParcelas, BigDecimal valorEmprestimo, Date dataContratacao, float taxaJuro) {
        try {
            String sqlEmprestimo = "INSERT INTO emprestimos (cliente_id, valor_emprestimo, data_contratacao) VALUES (?, ?, ?)";
            String sqlParcela = "INSERT INTO parcelas (emprestimo_id, numero_parcela, valor_parcela, data_vencimento, status, valor_juro, valor_PagarPrestacao) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmEmprestimo = conn.prepareStatement(sqlEmprestimo, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement pstmParcela = conn.prepareStatement(sqlParcela)) {

                // Inserindo dados na tabela 'emprestimos'
                pstmEmprestimo.setInt(1, clienteId);
                pstmEmprestimo.setBigDecimal(2, valorEmprestimo);
                pstmEmprestimo.setDate(3, dataContratacao);

                int rowsAffectedEmprestimo = pstmEmprestimo.executeUpdate();

                if (rowsAffectedEmprestimo > 0) {
                    // Obtendo o id do empréstimo recém-inserido
                    try (ResultSet rs = pstmEmprestimo.getGeneratedKeys()) {
                        if (rs.next()) {
                            int emprestimoId = rs.getInt(1);

                            // Inserindo dados na tabela 'parcelas' para cada parcela
                            BigDecimal saldoDevedor = valorEmprestimo;
                            for (int i = 1; i <= numParcelas; i++) {
                                BigDecimal valorParcela = saldoDevedor.divide(BigDecimal.valueOf(numParcelas), 2, BigDecimal.ROUND_HALF_UP);
                                BigDecimal valorJuroParcela = saldoDevedor.multiply(BigDecimal.valueOf(taxaJuro)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                                BigDecimal valorPagarPrestacao = valorParcela.add(valorJuroParcela);

                                pstmParcela.setInt(1, emprestimoId);
                                pstmParcela.setInt(2, i);
                                pstmParcela.setBigDecimal(3, valorParcela);
                                pstmParcela.setDate(4, Date.valueOf(dataContratacao.toLocalDate().plusMonths(i)));
                                pstmParcela.setString(5, "Pendente");
                                pstmParcela.setBigDecimal(6, valorJuroParcela);
                                pstmParcela.setBigDecimal(7, valorPagarPrestacao);

                                int rowsAffectedParcela = pstmParcela.executeUpdate();

                                if (rowsAffectedParcela <= 0) {
                                    throw new SQLException("Erro ao inserir parcela");
                                }

                                saldoDevedor = saldoDevedor.subtract(valorParcela);
                            }
                        } else {
                            throw new SQLException("Erro ao obter o id do empréstimo");
                        }
                    }
                } else {
                    throw new SQLException("Erro ao inserir empréstimo");
                }

                // Se chegou aqui, tudo foi inserido corretamente
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "EmprestimoDAO - Erro ao salvar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public List<EmprestimosDTO> getAllEmprestimos() {
        List<EmprestimosDTO> emprestimos = new ArrayList<>();
        String sql = "SELECT e.emprestimo_id, e.valor_emprestimo, c.nome, c.sobrenome, p.parcela_id, p.numero_parcela, " +
                     "p.valor_parcela, p.data_vencimento, p.status, p.valor_juro, p.valor_PagarPrestacao " +
                     "FROM emprestimos e " +
                     "LEFT JOIN parcelas p ON e.emprestimo_id = p.emprestimo_id " +
                     "LEFT JOIN clientes c ON e.cliente_id = c.cliente_id";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int emprestimoId = rs.getInt("emprestimo_id");
                EmprestimosDTO emprestimo = emprestimos.stream()
                        .filter(e -> e.getEmprestimoId() == emprestimoId)
                        .findFirst()
                        .orElseGet(() -> {
                            EmprestimosDTO newEmprestimo = new EmprestimosDTO();
                            newEmprestimo.setEmprestimoId(emprestimoId);
                    try {
                        newEmprestimo.setValorEmprestimo(rs.getBigDecimal("valor_emprestimo"));
                    } catch (SQLException ex) {
                        Logger.getLogger(EmprestimosDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }

                            ClientesDTO cliente = new ClientesDTO();
                    try {
                        cliente.setNome(rs.getString("nome"));
                    } catch (SQLException ex) {
                        Logger.getLogger(EmprestimosDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        cliente.setSobrenome(rs.getString("sobrenome"));
                    } catch (SQLException ex) {
                        Logger.getLogger(EmprestimosDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                            newEmprestimo.setCliente(cliente);

                            emprestimos.add(newEmprestimo);
                            return newEmprestimo;
                        });

                ParcelaEmprestimoDTO parcela = new ParcelaEmprestimoDTO();
                parcela.setIdParcela(rs.getInt("parcela_id"));
                parcela.setNumParcelas(rs.getInt("numero_parcela"));
                parcela.setValorParcela(rs.getDouble("valor_parcela"));
                parcela.setDataVencimento(rs.getDate("data_vencimento"));
                parcela.setStatus(rs.getString("status"));
                parcela.setValorJuroParcela(rs.getDouble("valor_juro"));
                parcela.setValorPagarPrestacao(rs.getDouble("valor_PagarPrestacao"));

                emprestimo.getParcelas().add(parcela);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }

    public List<ParcelaEmprestimoDTO> getAllParcelasByEmprestimoId(int emprestimoId) {
        List<ParcelaEmprestimoDTO> parcelas = new ArrayList<>();
        String sql = "SELECT * FROM parcelas WHERE emprestimo_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, emprestimoId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ParcelaEmprestimoDTO parcela = new ParcelaEmprestimoDTO();
                parcela.setIdParcela(rs.getInt("parcela_id"));
                parcela.setEmprestimoId(rs.getInt("emprestimo_id"));
                parcela.setNumParcelas(rs.getInt("numero_parcela"));
                parcela.setValorParcela(rs.getDouble("valor_parcela"));
                parcela.setDataVencimento(rs.getDate("data_vencimento"));
                parcela.setStatus(rs.getString("status"));
                parcela.setValorJuroParcela(rs.getDouble("valor_juro"));
                parcela.setValorPagarPrestacao(rs.getDouble("valor_PagarPrestacao"));
                parcelas.add(parcela);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parcelas;
    }

    public EmprestimosDTO getEmprestimoById(int emprestimoId) {
        String sql = "SELECT e.emprestimo_id, e.valor_emprestimo, c.nome, c.sobrenome, p.parcela_id, p.numero_parcela, " +
                     "p.valor_parcela, p.data_vencimento, p.status, p.valor_juro, p.valor_PagarPrestacao " +
                     "FROM emprestimos e " +
                     "LEFT JOIN parcelas p ON e.emprestimo_id = p.emprestimo_id " +
                     "LEFT JOIN clientes c ON e.cliente_id = c.cliente_id " +
                     "WHERE e.emprestimo_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, emprestimoId);

            try (ResultSet rs = pstmt.executeQuery()) {
                EmprestimosDTO emprestimo = null;
                List<ParcelaEmprestimoDTO> parcelas = new ArrayList<>();

                while (rs.next()) {
                    if (emprestimo == null) {
                        emprestimo = new EmprestimosDTO();
                        emprestimo.setEmprestimoId(rs.getInt("emprestimo_id"));
                        emprestimo.setValorEmprestimo(rs.getBigDecimal("valor_emprestimo"));

                        ClientesDTO cliente = new ClientesDTO();
                        cliente.setNome(rs.getString("nome"));
                        cliente.setSobrenome(rs.getString("sobrenome"));
                        emprestimo.setCliente(cliente);
                    }

                    ParcelaEmprestimoDTO parcela = new ParcelaEmprestimoDTO();
                    parcela.setIdParcela(rs.getInt("parcela_id"));
                    parcela.setNumParcelas(rs.getInt("numero_parcela"));
                    parcela.setValorParcela(rs.getDouble("valor_parcela"));
                    parcela.setDataVencimento(rs.getDate("data_vencimento"));
                    parcela.setStatus(rs.getString("status"));
                    parcela.setValorJuroParcela(rs.getDouble("valor_juro"));
                    parcela.setValorPagarPrestacao(rs.getDouble("valor_PagarPrestacao"));

                    parcelas.add(parcela);
                }

                if (emprestimo != null) {
                    emprestimo.setParcelas(parcelas);
                }

                return emprestimo;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void atualizarStatusParcelas() {
        String sql = 
            "UPDATE parcelas p1 " +
            "JOIN ( " +
            "    SELECT p1.parcela_id, " +
            "           p1.data_vencimento, " +
            "           COALESCE(DATEDIFF(p1.data_vencimento, p2.data_vencimento), DATEDIFF(p1.data_vencimento, CURDATE())) AS dias_para_vencimento " +
            "    FROM parcelas p1 " +
            "    LEFT JOIN parcelas p2 " +
            "    ON p1.emprestimo_id = p2.emprestimo_id AND p1.numero_parcela = p2.numero_parcela + 1 " +
            ") subquery ON p1.parcela_id = subquery.parcela_id " +
            "SET p1.status = CASE " +
            "    WHEN subquery.dias_para_vencimento >= 0 THEN 'A TEMPO' " +
            "    WHEN subquery.dias_para_vencimento BETWEEN -5 AND -1 THEN 'PENDENTE' " +
            "    ELSE 'VENCIDO' " +
            "END " +
            "WHERE p1.status <> 'PAGO';";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void verificarPrestacoesPendentes() {
        // Lógica para verificar e enviar alertas de prestações vencidas
    }

    public void verificarPrestacoesAVencer(int diasAntes) {
        // Lógica para verificar e enviar alertas de prestações próximas ao vencimento
    }
}

