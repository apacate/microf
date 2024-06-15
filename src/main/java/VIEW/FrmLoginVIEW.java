package VIEW;

import DAO.EmprestimosDAO;
import DAO.FuncionariosDAO;
import DTO.FuncionariosDTO;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FrmLoginVIEW extends JFrame implements ActionListener {

    private JButton btnEntrarNoSistema, btnSairDoSistema;
    private JLabel jLlUsuario, jLlSenha;
    private JTextField txtNomeUsuario, txtSenhaUsuario;

    public FrmLoginVIEW() {
        setTitle("Login");
        setSize(380, 230);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Criando o painel frontal
        JPanel painelfrontal = new JPanel();
        painelfrontal.setLayout(null); // Definindo layout como null para posicionar manualmente os componentes
        painelfrontal.setBackground(Color.lightGray); // Definindo a cor de fundo do painel

        // Criando os componentes
        btnEntrarNoSistema = new JButton("Entrar");
        btnEntrarNoSistema.addActionListener(this);

        btnSairDoSistema = new JButton("Sair");
        btnSairDoSistema.addActionListener(this);

        jLlUsuario = new JLabel("Usuário:");
        jLlSenha = new JLabel("Senha:");
        txtNomeUsuario = new JTextField();
        txtSenhaUsuario = new JTextField();

        // Adicionando os componentes ao painel frontal
        painelfrontal.add(jLlUsuario);
        painelfrontal.add(txtNomeUsuario);
        painelfrontal.add(jLlSenha);
        painelfrontal.add(txtSenhaUsuario);
        painelfrontal.add(btnEntrarNoSistema);
        painelfrontal.add(btnSairDoSistema);

        // Definindo as posições e tamanhos dos componentes
        jLlUsuario.setBounds(30, 30, 50, 30);
        jLlSenha.setBounds(30, 65, 50, 30);
        txtNomeUsuario.setBounds(100, 30, 200, 30);
        txtSenhaUsuario.setBounds(100, 65, 200, 30);
        btnEntrarNoSistema.setBounds(100, 120, 90, 30);
        btnSairDoSistema.setBounds(210, 120, 90, 30);

        // Adicionando o painel frontal à janela
        add(painelfrontal);

        // Tornando a janela visível e centralizada na tela
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void autenticacaoUsuario() {
        String nomeUsuario = txtNomeUsuario.getText();
        String senhaUsuario = txtSenhaUsuario.getText();

        FuncionariosDTO objFuncionariosDTO = new FuncionariosDTO();
        objFuncionariosDTO.setUsuario(nomeUsuario);
        objFuncionariosDTO.setSenha(senhaUsuario);
        FuncionariosDAO objFuncionariosDAO = new FuncionariosDAO();
        if (objFuncionariosDAO.autenticacaoUsuario(objFuncionariosDTO)) {
            JOptionPane.showMessageDialog(null, "Usuário autenticado com sucesso!");

            // Actualiza o status
            EmprestimosDAO objEmprestimosDAO = new EmprestimosDAO();
            objEmprestimosDAO.atualizarStatusParcelas();
            // Aqui você pode chamar a próxima tela ou realizar outras operações após a autenticação
            new FrmPrincipalVIEW();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "  Usuário ou senha inválidos !  ");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEntrarNoSistema) {
            autenticacaoUsuario();
        } else if (e.getSource() == btnSairDoSistema) {
            System.exit(0);
        }
    }
    
        public static void main(String[] args) {
        new FrmLoginVIEW();
    }

}
