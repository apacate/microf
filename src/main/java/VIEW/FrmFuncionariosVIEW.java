/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package VIEW;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PACATE
 */
public class FrmFuncionariosVIEW extends JFrame implements ActionListener {

    private JTextField txtfuncionarioIdField, txtNomeField, txtSobrenomeField, txtcargoField,
            txtSalarioField, txtUsuarioField, txtSenhaField, txtPesquisaField;

    private JButton btnGravar, btnAtualizar, btnTelaPrincipal, btnApagar, btnPesquisar, btnSair;

    private JTable tabelaFucionarios;
    private DefaultTableModel modelTabelaFucionarios;
    private final JDateChooser txtDataNascimento;

    public FrmFuncionariosVIEW() {
        setTitle("Tela de Cadastro de Funcionarios");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painel = new JPanel(null);

        txtfuncionarioIdField = new JTextField();
        txtNomeField = new JTextField();
        txtSobrenomeField = new JTextField();
        txtDataNascimento = new com.toedter.calendar.JDateChooser();
        txtcargoField = new JTextField();
        txtSalarioField = new JTextField();
        txtUsuarioField = new JTextField();
        txtSenhaField = new JTextField();
        txtPesquisaField = new JTextField();

        JLabel lblClienteId = new JLabel("ID do Funcionario");
        lblClienteId.setBounds(20, 40, 80, 30);
        txtfuncionarioIdField.setBounds(20, 70, 80, 30);
        txtfuncionarioIdField.setEditable(false); // ID é auto incremento
        painel.add(lblClienteId);
        painel.add(txtfuncionarioIdField);

        JLabel lblClienteNome = new JLabel("Nome do Funcionario");
        lblClienteNome.setBounds(110, 40, 240, 30);
        txtNomeField.setBounds(110, 70, 240, 30);
        painel.add(lblClienteNome);
        painel.add(txtNomeField);

        JLabel lblClienteSobrenome = new JLabel("Sobrenome do Funcionario");
        lblClienteSobrenome.setBounds(360, 40, 140, 30);
        txtSobrenomeField.setBounds(360, 70, 140, 30);
        painel.add(lblClienteSobrenome);
        painel.add(txtSobrenomeField);

        JLabel lblClienteDataNascimento = new JLabel("Data de Nascimento");
        lblClienteDataNascimento.setBounds(510, 40, 150, 30);
        txtDataNascimento.setBounds(510, 70, 150, 30);
        painel.add(lblClienteDataNascimento);
        painel.add(txtDataNascimento);

        JLabel lblClienteNUIT = new JLabel("Cargo");
        lblClienteNUIT.setBounds(20, 110, 100, 30);
        txtcargoField.setBounds(20, 140, 100, 30);
        painel.add(lblClienteNUIT);
        painel.add(txtcargoField);

        JLabel lblClienteEndereco = new JLabel("Salario");
        lblClienteEndereco.setBounds(140, 110, 150, 30);
        txtSalarioField.setBounds(140, 140, 150, 30);
        painel.add(lblClienteEndereco);
        painel.add(txtSalarioField);

        JLabel lblClienteTelefone = new JLabel("Usuario");
        lblClienteTelefone.setBounds(300, 110, 100, 30);
        txtUsuarioField.setBounds(300, 140, 100, 30);
        painel.add(lblClienteTelefone);
        painel.add(txtUsuarioField);

        JLabel lblClienteEmail = new JLabel("Senha");
        lblClienteEmail.setBounds(410, 110, 100, 30);
        txtSenhaField.setBounds(410, 140, 100, 25);
        painel.add(lblClienteEmail);
        painel.add(txtSenhaField);

        btnGravar = new JButton("Gravar");
        btnGravar.setBounds(20, 360, 120, 25);
        btnGravar.addActionListener(this);
        painel.add(btnGravar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(160, 360, 120, 25);
        btnAtualizar.addActionListener(this);
        painel.add(btnAtualizar);

        btnTelaPrincipal = new JButton("Empréstimo");
        btnTelaPrincipal.setBounds(300, 360, 120, 25);
        btnTelaPrincipal.addActionListener(this);
        painel.add(btnTelaPrincipal);

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

        tabelaFucionarios = new JTable();
        modelTabelaFucionarios = new DefaultTableModel();
        tabelaFucionarios.setModel(modelTabelaFucionarios);
        JScrollPane scrollPane = new JScrollPane(tabelaFucionarios);
        scrollPane.setBounds(20, 400, 950, 150);
        painel.add(scrollPane);

        modelTabelaFucionarios.addColumn("ID");
        modelTabelaFucionarios.addColumn("Nome");
        modelTabelaFucionarios.addColumn("Sobrenome");
        modelTabelaFucionarios.addColumn("Data de Nascimento");
        modelTabelaFucionarios.addColumn("Cargo");
        modelTabelaFucionarios.addColumn("Salario");
        modelTabelaFucionarios.addColumn("Usuario");

        tabelaFucionarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int linha = tabelaFucionarios.getSelectedRow();
                if (linha >= 0) {
                    txtfuncionarioIdField.setText(modelTabelaFucionarios.getValueAt(linha, 0).toString());
                    txtNomeField.setText(modelTabelaFucionarios.getValueAt(linha, 1).toString());
                    txtSobrenomeField.setText(modelTabelaFucionarios.getValueAt(linha, 2).toString());
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date data = (Date) modelTabelaFucionarios.getValueAt(linha, 3);
                        String dataFormatada = dateFormat.format(data);
                        txtDataNascimento.setDate(dateFormat.parse(dataFormatada));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    txtcargoField.setText(modelTabelaFucionarios.getValueAt(linha, 4).toString());
                    txtSalarioField.setText(modelTabelaFucionarios.getValueAt(linha, 5).toString());
                    txtUsuarioField.setText(modelTabelaFucionarios.getValueAt(linha, 6).toString());
                    txtSenhaField.setText(modelTabelaFucionarios.getValueAt(linha, 7).toString());
                                  }
            }
        }); 
           // atualizarTabelaClientes();
    }

    private void gravarClientes() {
        try {
            // Verificar se todos os campos estão preenchidos
            if (!txtNomeField.getText().isEmpty()
                    && !txtSobrenomeField.getText().isEmpty()
                    && txtDataNascimento.getDate() != null
                    && !txtcargoField.getText().isEmpty()
                    && !txtSalarioField.getText().isEmpty()
                    && !txtUsuarioField.getText().isEmpty()
                    && !txtSenhaField.getText().isEmpty()) {

                // Obtém os valores dos campos
                // Não é necessário obter o ID, pois é auto incremento
                String nome = txtNomeField.getText();
                String sobrenome = txtSobrenomeField.getText();
                java.util.Date dataNascimento = txtDataNascimento.getDate();
                String cargo = txtcargoField.getText();
                String salario = txtSalarioField.getText();
                String usuario = txtUsuarioField.getText();
                String senha = txtSenhaField.getText();


                // Cria um novo DTO com os dados
                //ClientesDTO cliente = new ClientesDTO();
                //cliente.setNome(nome);
                //cliente.setSobrenome(sobrenome);
                //cliente.setDataNascimento(dataNascimento);
                //cliente.setNUIT(NUIT);
                //cliente.setEndereco(endereco);
                //cliente.setTelefone(telefone);
                //cliente.setEmail(email);
                //cliente.setDocumentoDeIdent(documentoIdent);
                //cliente.setObs(obs);

                // Chama o método para cadastrar o cliente
                //ClientesDAO objclienteDAO = new ClientesDAO();
                //objclienteDAO.cadastrarClientes(cliente);

                // Atualiza a área de dados
               // atualizarTabelaClientes();

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
        /*
    }
        try {
            // Verificar se todos os campos estão preenchidos
            if (!txtfuncionarioIdField.getText().isEmpty()
                    && !txtNomeField.getText().isEmpty()
                    && !txtSobrenomeField.getText().isEmpty()
                    && txtDataNascimento.getDate() != null
                    && !txtcargoField.getText().isEmpty()
                    && !txtSalarioField.getText().isEmpty()
                    && !txtUsuarioField.getText().isEmpty()
                    && !txtSenhaField.getText().isEmpty()) {

                // Obtém os valores dos campos
                int id = Integer.parseInt(txtNomeField.getText());
                String nome = txtNomeField.getText();
                String sobrenome = txtSobrenomeField.getText();
                java.util.Date dataNascimento = txtDataNascimento.getDate();
                String cargo = txtcargoField.getText();
                String salario = txtSalarioField.getText();
                String usuario = txtUsuarioField.getText();
                String senha = txtSenhaField.getText();


                 Cria um novo DTO com os dados
                //ClientesDTO cliente = new ClientesDTO();
                //cliente.setClienteId(id);
                //cliente.setNome(nome);
                //cliente.setSobrenome(sobrenome);
                //cliente.setDataNascimento(dataNascimento);
                //cliente.setNUIT(NUIT);
                //cliente.setEndereco(endereco);
                //cliente.setTelefone(telefone);
                //cliente.setEmail(email);
                //cliente.setDocumentoDeIdent(documentoIdent);
                //cliente.setObs(obs);

                // Atualiza os dados do cliente
                //ClientesDAO objclienteDAO = new ClientesDAO();
                //objclienteDAO.atualizarClientes(cliente);

                // Atualiza a área de dados
               // atualizarTabelaClientes();
                JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Erro de formato numérico: " + ex.getMessage());
        }
       */         
    }
             

    private void apagarClientes() {
        /*ClientesDAO objclienteDAO = new ClientesDAO();
        try {
            int clienteId = Integer.parseInt(txtfuncionarioIdField.getText());
            //objclienteDAO.excluirClientes(clienteId);
            atualizarTabelaClientes();
            limparCampos();
            JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "O ID do cliente deve ser um número válido.");
        }
*/
    }

   private void pesquisarCliente() {
       /* String termoPesquisa = txtPesquisaField.getText();
          ClientesDAO objclienteDAO = new ClientesDAO();
        if (!termoPesquisa.isEmpty()) {
           try {

                List<ClientesDTO> resultadosPesquisa = objclienteDAO.buscarClientesPorNome(termoPesquisa);
                modelTabelaFucionarios.setRowCount(0); // Limpa a tabela antes de adicionar os resultados da pesquisa
                for (//ClientesDTO cliente : resultadosPesquisa) {
              //      Object[] rowData = {
                        //cliente.getClienteId(),
                        //cliente.getNome(),
                        //cliente.getSobrenome(),
                        //cliente.getDataNascimento(),
                        //cliente.getNUIT(),
                        //cliente.getEndereco(),
                        //cliente.getTelefone(),
                        //cliente.getEmail(),
                        //cliente.getDocumentoDeIdent(),
                        //cliente.getObs()
                   // };
                   // modelTabelaFucionarios.addRow(rowData);
            //    }
          //  } //catch (SQLException e) {
               // JOptionPane.showMessageDialog(null, "Erro ao pesquisar clientes: " + e.getMessage());
          //  }
       // }else {
           // atualizarTabelaClientes(); // Se o campo de pesquisa estiver vazio, mostra todos os clientes
        }
       */
   }

    private void atualizarTabelaClientes() {
        /* ClientesDAO objclienteDAO = new ClientesDAO();
       // List<ClientesDTO> clientes = objclienteDAO.listarClientes();
       // modelTabelaClientes.setRowCount(0);

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
*/
    }
    

    private void limparCampos() {
        txtNomeField.setText("");
        txtSobrenomeField.setText("");
        txtDataNascimento.setDate(null);
        txtcargoField.setText("");
        txtSalarioField.setText("");
        txtUsuarioField.setText("");
        txtSenhaField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnGravar) {
            gravarClientes();
        } else if (e.getSource() == btnAtualizar) {
            atualizarClientes();
        } else if (e.getSource() == btnAtualizar) {
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
        new FrmFuncionariosVIEW();
    }



}
