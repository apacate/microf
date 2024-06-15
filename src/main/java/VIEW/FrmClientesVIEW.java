package VIEW;

import DAO.ClientesDAO;
import DTO.ClientesDTO;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class FrmClientesVIEW extends JFrame implements ActionListener {

    private JTextField txtClienteIdField, txtNomeField, txtSobrenomeField, txtNUITField,
            txtEnderecoField, txtTelefoneField, txtEmailField, txtDocumentoIdentField, txtObsField, txtPesquisaField;

    private JButton btnGravar, btnAtualizar, btnEmprestimo, btnApagar, btnPesquisar, btnSair;

    private JTable tabelaClientes;
    private DefaultTableModel modelTabelaClientes;
    private final JDateChooser txtDataNascimento;

    public FrmClientesVIEW() {
        setTitle("Tela de Cadastro de Clientes");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel(null);

        txtClienteIdField = new JTextField();
        txtNomeField = new JTextField();
        txtSobrenomeField = new JTextField();
        txtDataNascimento = new com.toedter.calendar.JDateChooser();
        txtNUITField = new JTextField();
        txtEnderecoField = new JTextField();
        txtTelefoneField = new JTextField();
        txtEmailField = new JTextField();
        txtDocumentoIdentField = new JTextField();
        txtObsField = new JTextField();

        JLabel lblClienteId = new JLabel("ID do Cliente");
        lblClienteId.setBounds(20, 40, 80, 30);
        txtClienteIdField.setBounds(20, 70, 80, 30);
        txtClienteIdField.setEditable(false); // ID é auto incremento
        painel.add(lblClienteId);
        painel.add(txtClienteIdField);

        JLabel lblClienteNome = new JLabel("Nome do Cliente");
        lblClienteNome.setBounds(110, 40, 240, 30);
        txtNomeField.setBounds(110, 70, 240, 30);
        painel.add(lblClienteNome);
        painel.add(txtNomeField);

        JLabel lblClienteSobrenome = new JLabel("Sobrenome do Cliente");
        lblClienteSobrenome.setBounds(360, 40, 140, 30);
        txtSobrenomeField.setBounds(360, 70, 140, 30);
        painel.add(lblClienteSobrenome);
        painel.add(txtSobrenomeField);

        JLabel lblClienteDataNascimento = new JLabel("Data de Nascimento");
        lblClienteDataNascimento.setBounds(510, 40, 150, 30);
        txtDataNascimento.setBounds(510, 70, 150, 30);
        painel.add(lblClienteDataNascimento);
        painel.add(txtDataNascimento);

        JLabel lblClienteNUIT = new JLabel("NUIT");
        lblClienteNUIT.setBounds(20, 110, 100, 30);
        txtNUITField.setBounds(20, 140, 100, 30);
        painel.add(lblClienteNUIT);
        painel.add(txtNUITField);

        JLabel lblClienteEndereco = new JLabel("Endereco");
        lblClienteEndereco.setBounds(140, 110, 150, 30);
        txtEnderecoField.setBounds(140, 140, 150, 30);
        painel.add(lblClienteEndereco);
        painel.add(txtEnderecoField);

        JLabel lblClienteTelefone = new JLabel("Telefone");
        lblClienteTelefone.setBounds(300, 110, 100, 30);
        txtTelefoneField.setBounds(300, 140, 100, 30);
        painel.add(lblClienteTelefone);
        painel.add(txtTelefoneField);

        JLabel lblClienteEmail = new JLabel("Email");
        lblClienteEmail.setBounds(410, 110, 100, 30);
        txtEmailField.setBounds(410, 140, 100, 25);
        painel.add(lblClienteEmail);
        painel.add(txtEmailField);

        JLabel lblClienteDocumento = new JLabel("Documento de Ident");
        lblClienteDocumento.setBounds(20, 180, 150, 30);
        txtDocumentoIdentField.setBounds(20, 220, 150, 25);
        painel.add(lblClienteDocumento);
        painel.add(txtDocumentoIdentField);

        JLabel lblClienteObs = new JLabel("Obs");
        lblClienteObs.setBounds(180, 180, 200, 30);
        txtObsField.setBounds(180, 220, 200, 25);
        painel.add(lblClienteObs);
        painel.add(txtObsField);

        btnGravar = new JButton("Gravar");
        btnGravar.setBounds(20, 360, 120, 25);
        btnGravar.addActionListener(this);
        painel.add(btnGravar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(160, 360, 120, 25);
        btnAtualizar.addActionListener(this);
        painel.add(btnAtualizar);

        btnEmprestimo = new JButton("Empréstimo");
        btnEmprestimo.setBounds(300, 360, 120, 25);
        btnEmprestimo.addActionListener(this);
        painel.add(btnEmprestimo);

        btnApagar = new JButton("Apagar");
        btnApagar.setBounds(440, 360, 120, 25);
        btnApagar.addActionListener(this);
        painel.add(btnApagar);

        btnSair = new JButton("Sair");
        btnSair.setBounds(580, 360, 120, 25);
        btnSair.addActionListener(this);
        painel.add(btnSair);

        // Adicionando o campo de pesquisa e o botão "Pesquisar"
        JLabel lblPesquisa = new JLabel("Pesquisar Cliente");
        lblPesquisa.setBounds(20, 270, 150, 30);
        painel.add(lblPesquisa);

        txtPesquisaField = new JTextField();
        txtPesquisaField.setBounds(20, 300, 150, 25);
        painel.add(txtPesquisaField);

        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(180, 300, 100, 25);
        btnPesquisar.addActionListener(this);
        painel.add(btnPesquisar);

        add(painel);
        setVisible(true);
        setLocationRelativeTo(null);

        tabelaClientes = new JTable();
        modelTabelaClientes = new DefaultTableModel();
        tabelaClientes.setModel(modelTabelaClientes);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        scrollPane.setBounds(20, 400, 950, 150);
        painel.add(scrollPane);

        modelTabelaClientes.addColumn("ID");
        modelTabelaClientes.addColumn("Nome");
        modelTabelaClientes.addColumn("Sobrenome");
        modelTabelaClientes.addColumn("Data de Nascimento");
        modelTabelaClientes.addColumn("NUIT");
        modelTabelaClientes.addColumn("Endereço");
        modelTabelaClientes.addColumn("Telefone");
        modelTabelaClientes.addColumn("Email");
        modelTabelaClientes.addColumn("Documento de Identidade");
        modelTabelaClientes.addColumn("Obs");

        tabelaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int linha = tabelaClientes.getSelectedRow();
                if (linha >= 0) {
                    txtClienteIdField.setText(modelTabelaClientes.getValueAt(linha, 0).toString());
                    txtNomeField.setText(modelTabelaClientes.getValueAt(linha, 1).toString());
                    txtSobrenomeField.setText(modelTabelaClientes.getValueAt(linha, 2).toString());
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date data = (Date) modelTabelaClientes.getValueAt(linha, 3);
                        String dataFormatada = dateFormat.format(data);
                        txtDataNascimento.setDate(dateFormat.parse(dataFormatada));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    txtNUITField.setText(modelTabelaClientes.getValueAt(linha, 4).toString());
                    txtEnderecoField.setText(modelTabelaClientes.getValueAt(linha, 5).toString());
                    txtTelefoneField.setText(modelTabelaClientes.getValueAt(linha, 6).toString());
                    txtEmailField.setText(modelTabelaClientes.getValueAt(linha, 7).toString());
                    txtDocumentoIdentField.setText(modelTabelaClientes.getValueAt(linha, 8).toString());
                    txtObsField.setText(modelTabelaClientes.getValueAt(linha, 9).toString());
                }
            }
        });
        atualizarTabelaClientes();
    }

    private void gravarClientes() {
        try {
            // Verificar se todos os campos estão preenchidos
            if (!txtNomeField.getText().isEmpty()
                    && !txtSobrenomeField.getText().isEmpty()
                    && txtDataNascimento.getDate() != null
                    && !txtNUITField.getText().isEmpty()
                    && !txtEnderecoField.getText().isEmpty()
                    && !txtTelefoneField.getText().isEmpty()
                    && !txtEmailField.getText().isEmpty()) {

                // Obtém os valores dos campos
                // Não é necessário obter o ID, pois é auto incremento
                String nome = txtNomeField.getText();
                String sobrenome = txtSobrenomeField.getText();
                java.util.Date dataNascimento = txtDataNascimento.getDate();
                String NUIT = txtNUITField.getText();
                String endereco = txtEnderecoField.getText();
                String telefone = txtTelefoneField.getText();
                String email = txtEmailField.getText();
                String documentoIdent = txtDocumentoIdentField.getText();
                String obs = txtObsField.getText();

                // Cria um novo DTO com os dados
                ClientesDTO cliente = new ClientesDTO();
                cliente.setNome(nome);
                cliente.setSobrenome(sobrenome);
                cliente.setDataNascimento(dataNascimento);
                cliente.setNUIT(NUIT);
                cliente.setEndereco(endereco);
                cliente.setTelefone(telefone);
                cliente.setEmail(email);
                cliente.setDocumentoDeIdent(documentoIdent);
                cliente.setObs(obs);

                // Chama o método para cadastrar o cliente
                ClientesDAO objclienteDAO = new ClientesDAO();
                objclienteDAO.cadastrarClientes(cliente);

                // Atualiza a área de dados
                atualizarTabelaClientes();

                // Limpa os campos
                limparCampos();

                JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!");

            } else {
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Erro de formato numérico: " + ex.getMessage());
        }
    }

    private void atualizarClientes() {
        try {
            // Verificar se todos os campos estão preenchidos
            if (!txtClienteIdField.getText().isEmpty()
                    && !txtNomeField.getText().isEmpty()
                    && !txtSobrenomeField.getText().isEmpty()
                    && txtDataNascimento.getDate() != null
                    && !txtNUITField.getText().isEmpty()
                    && !txtEnderecoField.getText().isEmpty()
                    && !txtTelefoneField.getText().isEmpty()
                    && !txtEmailField.getText().isEmpty()) {

                // Obtém os valores dos campos
                int id = Integer.parseInt(txtClienteIdField.getText());
                String nome = txtNomeField.getText();
                String sobrenome = txtSobrenomeField.getText();
                java.util.Date dataNascimento = txtDataNascimento.getDate();
                String NUIT = txtNUITField.getText();
                String endereco = txtEnderecoField.getText();
                String telefone = txtTelefoneField.getText();
                String email = txtEmailField.getText();
                String documentoIdent = txtDocumentoIdentField.getText();
                String obs = txtObsField.getText();

                // Cria um novo DTO com os dados
                ClientesDTO cliente = new ClientesDTO();
                cliente.setClienteId(id);
                cliente.setNome(nome);
                cliente.setSobrenome(sobrenome);
                cliente.setDataNascimento(dataNascimento);
                cliente.setNUIT(NUIT);
                cliente.setEndereco(endereco);
                cliente.setTelefone(telefone);
                cliente.setEmail(email);
                cliente.setDocumentoDeIdent(documentoIdent);
                cliente.setObs(obs);

                // Atualiza os dados do cliente
                ClientesDAO objclienteDAO = new ClientesDAO();
                objclienteDAO.atualizarClientes(cliente);

                // Atualiza a área de dados
                atualizarTabelaClientes();
                JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Erro de formato numérico: " + ex.getMessage());
        }
    }

    private void apagarClientes() {
        ClientesDAO objclienteDAO = new ClientesDAO();
        try {
            int clienteId = Integer.parseInt(txtClienteIdField.getText());
            objclienteDAO.excluirClientes(clienteId);
            atualizarTabelaClientes();
            limparCampos();
            JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "O ID do cliente deve ser um número válido.");
        }

    }

    private void pesquisarCliente() {
        String termoPesquisa = txtPesquisaField.getText();
          ClientesDAO objclienteDAO = new ClientesDAO();
        if (!termoPesquisa.isEmpty()) {
            try {

                List<ClientesDTO> resultadosPesquisa = objclienteDAO.buscarClientesPorNome(termoPesquisa);
                modelTabelaClientes.setRowCount(0); // Limpa a tabela antes de adicionar os resultados da pesquisa
                for (ClientesDTO cliente : resultadosPesquisa) {
                    Object[] rowData = {
                        cliente.getClienteId(),
                        cliente.getNome(),
                        cliente.getSobrenome(),
                        cliente.getDataNascimento(),
                        cliente.getNUIT(),
                        cliente.getEndereco(),
                        cliente.getTelefone(),
                        cliente.getEmail(),
                        cliente.getDocumentoDeIdent(),
                        cliente.getObs()
                    };
                    modelTabelaClientes.addRow(rowData);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao pesquisar clientes: " + e.getMessage());
            }
        } else {
            atualizarTabelaClientes(); // Se o campo de pesquisa estiver vazio, mostra todos os clientes
        }
    }

    private void atualizarTabelaClientes() {
         ClientesDAO objclienteDAO = new ClientesDAO();
        List<ClientesDTO> clientes = objclienteDAO.listarClientes();
        modelTabelaClientes.setRowCount(0);

        for (ClientesDTO cliente : clientes) {
            Object[] rowData = {
                cliente.getClienteId(),
                cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getDataNascimento(),
                cliente.getNUIT(),
                cliente.getEndereco(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getDocumentoDeIdent(),
                cliente.getObs()
            };
            modelTabelaClientes.addRow(rowData);
        }
    }

    private void limparCampos() {
        txtNomeField.setText("");
        txtSobrenomeField.setText("");
        txtDataNascimento.setDate(null);
        txtNUITField.setText("");
        txtEnderecoField.setText("");
        txtTelefoneField.setText("");
        txtEmailField.setText("");
        txtDocumentoIdentField.setText("");
        txtObsField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnGravar) {
            gravarClientes();
        } else if (e.getSource() == btnAtualizar) {
            atualizarClientes();
        } else if (e.getSource() == btnEmprestimo) {
            new FrmEmprestimosVIEW();
            this.dispose();
        } else if (e.getSource() == btnApagar) {
            apagarClientes();
        } else if (e.getSource() == btnPesquisar) {
            pesquisarCliente();
        } else if (e.getSource() == btnSair) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new FrmClientesVIEW();
    }

}
