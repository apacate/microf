package VIEW;

import DAO.ClientesDAO;
import DAO.EmprestimosDAO;
import DTO.ClientesDTO;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrmEmprestimosVIEW extends JFrame implements ActionListener {

    private JTextField txtClienteId, txtValorEmprestimo, txtNome, txtSobrenome;
    private JComboBox<Integer> comboNumeroPrestacao, comboTaxaJuro;
    private JButton btnCadastrar, btnVisualizar, btnTelaPrincipal, btnSair;
    private JTable tblAmortizacao, tblClientes;
    private JLabel lblTotalJuros, lblTotalPagar;
    private final JDateChooser txtDataCriacao;

    public FrmEmprestimosVIEW() {
        setSize(800, 550);
        setTitle("Tela de Empréstimos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(null);

        JLabel labelClienteId = new JLabel("ID do Cliente");
        labelClienteId.setBounds(10, 10, 250, 25);
        mainPanel.add(labelClienteId);

        txtClienteId = new JTextField();
        txtClienteId.setBounds(10, 35, 250, 25);
        txtClienteId.setEditable(false);
        mainPanel.add(txtClienteId);

        JLabel labelNome = new JLabel("Nome");
        labelNome.setBounds(270, 10, 250, 25);
        mainPanel.add(labelNome);

        txtNome = new JTextField();
        txtNome.setBounds(270, 35, 250, 25);
        txtNome.addKeyListener(new KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                try {
                    buscarClientesPorNome(txtNome.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmEmprestimosVIEW.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        mainPanel.add(txtNome);

        JLabel labelSobreNome = new JLabel("Sobrenome");
        labelSobreNome.setBounds(530, 10, 250, 25);
        mainPanel.add(labelSobreNome);

        txtSobrenome = new JTextField();
        txtSobrenome.setBounds(530, 35, 250, 25);
        txtSobrenome.addKeyListener(new KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                try {
                    buscarClientesPorNome(txtSobrenome.getText());
                } catch (SQLException ex) {
                    Logger.getLogger(FrmEmprestimosVIEW.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        mainPanel.add(txtSobrenome);

        JLabel labelValorEmprestimo = new JLabel("Valor do Empréstimo");
        labelValorEmprestimo.setBounds(150, 140, 150, 25);
        mainPanel.add(labelValorEmprestimo);

        txtValorEmprestimo = new JTextField();
        txtValorEmprestimo.setBounds(150, 165, 100, 25);
        mainPanel.add(txtValorEmprestimo);

        JLabel labelNumeroPrestacao = new JLabel("Nº de Prestações");
        labelNumeroPrestacao.setBounds(300, 140, 120, 25);
        mainPanel.add(labelNumeroPrestacao);

        Integer[] numPrestacoes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        comboNumeroPrestacao = new JComboBox<>(numPrestacoes);
        comboNumeroPrestacao.setBounds(300, 165, 120, 25);
        mainPanel.add(comboNumeroPrestacao);

        JLabel labelTaxaJuro = new JLabel("Taxa de Juro (%)");
        labelTaxaJuro.setBounds(450, 140, 120, 25);
        mainPanel.add(labelTaxaJuro);

        Integer[] taxasJuros = {10, 15, 20, 25, 30};
        comboTaxaJuro = new JComboBox<>(taxasJuros);
        comboTaxaJuro.setBounds(450, 165, 120, 25);
        mainPanel.add(comboTaxaJuro);

        JLabel labelDataCriacao = new JLabel("Data de Criação");
        labelDataCriacao.setBounds(600, 140, 120, 25);
        mainPanel.add(labelDataCriacao);

        txtDataCriacao = new JDateChooser();
        txtDataCriacao.setBounds(600, 165, 150, 25);
        mainPanel.add(txtDataCriacao);

        DefaultTableModel tableModelAmortizacao = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModelAmortizacao.addColumn("Prestação");
        tableModelAmortizacao.addColumn("Data de Pagamento");
        tableModelAmortizacao.addColumn("Juros");
        tableModelAmortizacao.addColumn("Amortização");
        tableModelAmortizacao.addColumn("Valor a Pagar");
        tableModelAmortizacao.addColumn("Saldo Devedor");

        tblAmortizacao = new JTable(tableModelAmortizacao);
        JScrollPane scrollPaneAmortizacao = new JScrollPane(tblAmortizacao);
        scrollPaneAmortizacao.setBounds(10, 270, 750, 150);
        mainPanel.add(scrollPaneAmortizacao);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        for (int i = 2; i < tblAmortizacao.getColumnCount(); i++) {
            tblAmortizacao.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

        DefaultTableModel tableModelClientes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModelClientes.addColumn("");
        tableModelClientes.addColumn("");
        tableModelClientes.addColumn("");

        tblClientes = new JTable(tableModelClientes);
        JScrollPane scrollPaneClientes = new JScrollPane(tblClientes);
        scrollPaneClientes.setBounds(10, 60, 770, 60);
        mainPanel.add(scrollPaneClientes);

        tblClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblClientes.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
                    txtClienteId.setText(model.getValueAt(selectedRow, 0).toString());
                    txtNome.setText(model.getValueAt(selectedRow, 1).toString());
                    txtSobrenome.setText(model.getValueAt(selectedRow, 2).toString());
                    tblClientes.clearSelection();
                    tblClientes.setVisible(false);
                }
            }
        });

        btnVisualizar = new JButton("Visualizar");
        btnVisualizar.setBounds(10, 230, 100, 25);
        btnVisualizar.addActionListener(this);
        mainPanel.add(btnVisualizar);

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(450, 440, 90, 25);
        btnCadastrar.addActionListener(this);
        mainPanel.add(btnCadastrar);

        btnTelaPrincipal = new JButton("Tela Principal");
        btnTelaPrincipal.setBounds(550, 440, 120, 25);
        btnTelaPrincipal.addActionListener(this);
        mainPanel.add(btnTelaPrincipal);

        btnSair = new JButton("Sair");
        btnSair.setBounds(680, 440, 60, 25);
        btnSair.setBackground(Color.red);
        btnSair.addActionListener(this);
        mainPanel.add(btnSair);

        lblTotalJuros = new JLabel("Total de Juros: ");
        lblTotalJuros.setBounds(10, 440, 200, 25);
        mainPanel.add(lblTotalJuros);

        lblTotalPagar = new JLabel("Total a Pagar: ");
        lblTotalPagar.setBounds(220, 440, 200, 25);
        mainPanel.add(lblTotalPagar);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmEmprestimosVIEW telaEmprestimos = new FrmEmprestimosVIEW();
            telaEmprestimos.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCadastrar) {
            try {
                cadastrarEmprestimo();
            } catch (SQLException ex) {
                Logger.getLogger(FrmEmprestimosVIEW.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == btnVisualizar) {
            mostrarCalculos();
        } else if (e.getSource() == btnTelaPrincipal) {
            FrmPrincipalVIEW principalVIEW = new FrmPrincipalVIEW();
            principalVIEW.setVisible(true);
            dispose();
        } else if (e.getSource() == btnSair) {
            System.exit(0);
        }
    }

private void cadastrarEmprestimo() throws SQLException {
    String valorEmprestimoStr = txtValorEmprestimo.getText().replace(",", "");
    if (valorEmprestimoStr.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, insira o valor do empréstimo.");
    } else {
        BigDecimal valorEmprestimo = new BigDecimal(valorEmprestimoStr);
        Date dataCriacao = new Date(txtDataCriacao.getDate().getTime());
        int numParcelas = (int) comboNumeroPrestacao.getSelectedItem();
        float taxaJuro = (int) comboTaxaJuro.getSelectedItem() / 100f;
        String clienteIdStr = txtClienteId.getText();
        if (clienteIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, insira o Nome de cliente");
        } else {
            int clienteId = Integer.parseInt(clienteIdStr);
            EmprestimosDAO emprestimosDAO = new EmprestimosDAO();
            emprestimosDAO.saveDataToDatabase(clienteId, numParcelas, valorEmprestimo, dataCriacao, taxaJuro);
            JOptionPane.showMessageDialog(null, "Empréstimo cadastrado com sucesso!");
            limparCampos();
        }
    }
}


    private void mostrarCalculos() {
        try {
            if (txtValorEmprestimo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, insira o valor do empréstimo.");
                return;
            }

            DecimalFormat df = new DecimalFormat("#,##0.00");
            BigDecimal valorEmprestimo = new BigDecimal(txtValorEmprestimo.getText().replace(",", ""));
            int numParcelas = (int) comboNumeroPrestacao.getSelectedItem();
            float taxaJuro = (int) comboTaxaJuro.getSelectedItem() / 100f;

            DefaultTableModel tableModel = (DefaultTableModel) tblAmortizacao.getModel();
            tableModel.setRowCount(0);

            double totalJuros = 0;
            double totalPagar = 0;
            double saldoDevedor = valorEmprestimo.doubleValue();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date dataPagamento = txtDataCriacao.getDate();

            Calendar calendar = Calendar.getInstance();

            for (int i = 1; i <= numParcelas; i++) {
                double juros = saldoDevedor * taxaJuro;
                double amortizacao = valorEmprestimo.doubleValue() / numParcelas;
                double valorPagar = juros + amortizacao;
                saldoDevedor -= amortizacao;
                totalJuros += juros;
                totalPagar += valorPagar;

                calendar.setTime(dataPagamento);
                calendar.add(Calendar.MONTH, 1);
                dataPagamento = calendar.getTime();

                tableModel.addRow(new Object[]{
                    i,
                    dateFormat.format(dataPagamento),
                    df.format(juros),
                    df.format(amortizacao),
                    df.format(valorPagar),
                    df.format(saldoDevedor)
                });
            }

            lblTotalJuros.setText("Total de Juros: " + df.format(totalJuros));
            lblTotalPagar.setText("Total a Pagar: " + df.format(totalPagar));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Erro de formato numérico: " + ex.getMessage());
        }
    }

    private void buscarClientesPorNome(String nome) throws SQLException {
        ClientesDAO clientesDAO = new ClientesDAO();
        ArrayList<ClientesDTO> lista = (ArrayList<ClientesDTO>) clientesDAO.buscarClientesPorNome(nome);
        DefaultTableModel model = (DefaultTableModel) tblClientes.getModel();
        model.setRowCount(0);

        for (ClientesDTO cliente : lista) {
            model.addRow(new Object[]{
               cliente.getClienteId(),
                cliente.getNome(),
                cliente.getSobrenome()
            });
        }

        tblClientes.setVisible(true);
    }

    private void limparCampos() {
        txtClienteId.setText("");
        txtNome.setText("");
        txtSobrenome.setText("");
        txtValorEmprestimo.setText("");
        txtDataCriacao.setDate(null);
        comboNumeroPrestacao.setSelectedIndex(0);
        comboTaxaJuro.setSelectedIndex(0);

        // Limpa a tabela de amortização
        DefaultTableModel tableModelAmortizacao = (DefaultTableModel) tblAmortizacao.getModel();
        tableModelAmortizacao.setRowCount(0);

        lblTotalJuros.setText("Total de Juros: ");
        lblTotalPagar.setText("Total a Pagar: ");
    }

}
