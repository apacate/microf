package VIEW;

import DAO.EmprestimosDAO;
import DTO.EmprestimosDTO;
import DTO.ParcelaEmprestimoDTO;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class FrmPrincipalVIEW extends JFrame implements ActionListener {

    private JComboBox<String> comboBox;
    private JLabel lblcomboBox, lblPesqEmprestimo;
    private JButton btnCadastroClientes, btnCriarEmprestimo, sair;
    private JTable tabelaEmprestimos;
    private DefaultTableModel modelTabelaEmprestimos;
    private JTextField txtpesquisarEmprestimo;
    private JMenuBar menubar;
    private JMenu microf, microf1, menu, submenu1, submenu2, submenu3;
    private JMenuItem jmenuItem1, jmenuClientes, jmenuItem3, jmenuEmrestimos, jmenuItem5, jmenuItem6, jmenuItem7, jmenuItem8;

    public FrmPrincipalVIEW() {
        setTitle("Tela Principal");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setLayout(null);

        menubar = new JMenuBar();
        microf = new JMenu("Sistema de emprestimos");
        microf1 = new JMenu(" ");
        menu = new JMenu("Menu");
        submenu1 = new JMenu("Cadastrar");
        submenu2 = new JMenu("Criar");
        submenu3 = new JMenu("Relatorio");

        jmenuItem1 = new JMenuItem("Funcionarios");
        jmenuClientes = new JMenuItem("Clientes");
        jmenuClientes.addActionListener(this);
        jmenuItem3 = new JMenuItem("Outros");
        jmenuEmrestimos = new JMenuItem("Emprestimos");
        jmenuEmrestimos.addActionListener(this);
        jmenuItem5 = new JMenuItem("Outros");
        jmenuItem7 = new JMenuItem("Realtorio Mensal");
        jmenuItem8 = new JMenuItem("Relatorio Personalizada");

        submenu1.add(jmenuItem1);
        submenu1.add(jmenuClientes);
        submenu1.add(jmenuItem3);
        submenu2.add(jmenuEmrestimos);
        submenu2.add(jmenuItem5);
        submenu3.add(jmenuItem7);
        submenu3.add(jmenuItem8);
        menu.add(submenu1);
        menu.add(submenu2);
        menu.add(submenu3);
        menubar.add(microf);
        menubar.add(microf1);
        menubar.add(menu);

        setJMenuBar(menubar);

        lblPesqEmprestimo = new JLabel("Pesquisar");
        lblPesqEmprestimo.setBounds(50, 30, 80, 25);
        painel.add(lblPesqEmprestimo);

        txtpesquisarEmprestimo = new JTextField();
        txtpesquisarEmprestimo.setBounds(130, 30, 150, 25);
        painel.add(txtpesquisarEmprestimo);

        txtpesquisarEmprestimo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarTabela();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarTabela();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarTabela();
            }
        });

        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(300, 30, 200, 25);
        searchPanel.setLayout(new FlowLayout());
        comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(100, 25));
        lblcomboBox = new JLabel("Status:");
        searchPanel.add(lblcomboBox);
        searchPanel.add(comboBox);

        comboBox.addItem("Todos");
        comboBox.addItem("A TEMPO");
        comboBox.addItem("PENDENTE");
        comboBox.addItem("VENCIDO");
        comboBox.addActionListener(this);

        painel.add(searchPanel);

        modelTabelaEmprestimos = new DefaultTableModel();
        tabelaEmprestimos = new JTable(modelTabelaEmprestimos);
        JScrollPane scrollPane = new JScrollPane(tabelaEmprestimos);
        scrollPane.setBounds(50, 80, 1100, 400);
        painel.add(scrollPane);

        tabelaEmprestimos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaEmprestimos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = tabelaEmprestimos.getSelectedRow();
                    int emprestimoId = (int) tabelaEmprestimos.getValueAt(row, 0);
                    mostrarDetalhesEmprestimo(emprestimoId);
                }
            }
        });

        btnCadastroClientes = new JButton("Cadastro de Clientes");
        btnCadastroClientes.setBounds(50, 500, 200, 25);
        btnCadastroClientes.addActionListener(this);
        painel.add(btnCadastroClientes);

        btnCriarEmprestimo = new JButton("Criar Emprestimo");
        btnCriarEmprestimo.setBounds(300, 500, 150, 25);
        btnCriarEmprestimo.addActionListener(this);
        painel.add(btnCriarEmprestimo);

        sair = new JButton("Sair");
        sair.setBounds(500, 500, 150, 25);
        sair.setBackground(Color.RED);
        sair.addActionListener(this);
        painel.add(sair);

        preencherTabelaEmprestimos();

        add(painel);
        setVisible(true);
        setLocationRelativeTo(null);
        setExtendedState(Frame.MAXIMIZED_BOTH);
    }

    private void preencherTabelaEmprestimos() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        modelTabelaEmprestimos.setColumnIdentifiers(new Object[]{
            "ID Empréstimo", "Nome", "Sobrenome",
            "Valor Empréstimo", "Data Vencimento", "Status", "Qtd Parcelas"
        });

        EmprestimosDAO emprestimosDAO = new EmprestimosDAO();
        emprestimosDAO.atualizarStatusParcelas();
        List<EmprestimosDTO> emprestimos = emprestimosDAO.getAllEmprestimos();

        for (EmprestimosDTO emprestimo : emprestimos) {
            int quantidadeParcelas = emprestimo.getParcelas().size();
            emprestimo.getParcelas().sort(Comparator.comparing(ParcelaEmprestimoDTO::getDataVencimento));
            if (!emprestimo.getParcelas().isEmpty()) {
                ParcelaEmprestimoDTO primeiraParcela = emprestimo.getParcelas().get(0);
                modelTabelaEmprestimos.addRow(new Object[]{
                    emprestimo.getEmprestimoId(),
                    emprestimo.getCliente().getNome(),
                    emprestimo.getCliente().getSobrenome(),
                    df.format(emprestimo.getValorEmprestimo()),
                    primeiraParcela.getDataVencimento(),
                    primeiraParcela.getStatus(),
                    quantidadeParcelas
                });
            }
        }

        tabelaEmprestimos.getColumnModel().getColumn(5).setCellRenderer(new StatusCellRenderer());
    }

    private void formatarValores() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        for (int row = 0; row < tabelaEmprestimos.getRowCount(); row++) {
            Object value = tabelaEmprestimos.getValueAt(row, 3);
            if (value instanceof Double) {
                double number = (double) value;
                tabelaEmprestimos.setValueAt(df.format(number), row, 3);
            }
        }
    }

    private void filtrarTabela() {
        DefaultTableModel model = (DefaultTableModel) tabelaEmprestimos.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tabelaEmprestimos.setRowSorter(sorter);

        String text = txtpesquisarEmprestimo.getText();
        String status = comboBox.getSelectedItem().toString();

        RowFilter<Object, Object> textFilter = RowFilter.regexFilter("(?i)" + text);
        RowFilter<Object, Object> statusFilter = status.equals("Todos")
                ? RowFilter.regexFilter("")
                : RowFilter.regexFilter(status);

        List<RowFilter<Object, Object>> filters = new ArrayList<>(2);
        filters.add(textFilter);
        filters.add(statusFilter);

        sorter.setRowFilter(RowFilter.andFilter(filters));
    }

    private void filtrarTabelaPorStatus(String status) {
    DefaultTableModel model = (DefaultTableModel) tabelaEmprestimos.getModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    tabelaEmprestimos.setRowSorter(sorter);

    RowFilter<Object, Object> statusFilter = status.equals("Todos")
            ? RowFilter.regexFilter("")
            : RowFilter.regexFilter(status);

    sorter.setRowFilter(statusFilter);
}

    
    private void mostrarDetalhesEmprestimo(int emprestimoId) {
        // Buscar o empréstimo pelo ID
        EmprestimosDAO emprestimosDAO = new EmprestimosDAO();
        EmprestimosDTO emprestimo = emprestimosDAO.getEmprestimoById(emprestimoId);

        if (emprestimo != null) {
            // Criar e exibir a janela de detalhes do empréstimo
            FrmDetalhesEmprestimo detalhesEmprestimo = new FrmDetalhesEmprestimo(emprestimo);
            detalhesEmprestimo.setVisible(true);
        } else {
            // Exibir uma mensagem de erro se o empréstimo não for encontrado
            JOptionPane.showMessageDialog(this, "Empréstimo não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override

public void actionPerformed(ActionEvent e) {
    if (e.getSource() == comboBox) {
        // Verificar se a origem do evento é o JComboBox
        String status = comboBox.getSelectedItem().toString();
        filtrarTabelaPorStatus(status);
    } else if (e.getSource() == btnCadastroClientes || e.getSource() == jmenuClientes) {
        FrmClientesVIEW clienteVIEW = new FrmClientesVIEW();
        clienteVIEW.setVisible(true);
    } else if (e.getSource() == btnCriarEmprestimo || e.getSource() == jmenuEmrestimos) {
        FrmEmprestimosVIEW emprestimoVIEW = new FrmEmprestimosVIEW();
        emprestimoVIEW.setVisible(true);
    } else if (e.getSource() == sair) {
        System.exit(0);
    }
}


    private class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = (String) value;

            if ("A TEMPO".equals(status)) {
                setBackground(Color.GREEN);
            } else if ("PENDENTE".equals(status)) {
                setBackground(Color.YELLOW);
            } else if ("VENCIDO".equals(status)) {
                setBackground(Color.RED);
            } else {
                setBackground(Color.WHITE);
            }
            return this;
        }
    }

    public static void main(String[] args) {
        new FrmPrincipalVIEW();
    }
}
