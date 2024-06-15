package VIEW;

import DTO.EmprestimosDTO;
import DTO.ParcelaEmprestimoDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;

public class FrmDetalhesEmprestimo extends JFrame {
    private JPanel contentPane;
    private JTable tableParcelas;
    private JTextField txtCliente;
    private JTextField txtValorEmprestimo;

    public FrmDetalhesEmprestimo(EmprestimosDTO emprestimo) {
        setTitle("Detalhes do Empréstimo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panelTop = new JPanel();
        contentPane.add(panelTop, BorderLayout.NORTH);
        panelTop.setLayout(new GridLayout(2, 2, 5, 5));

        JLabel lblCliente = new JLabel("Cliente:");
        panelTop.add(lblCliente);

        txtCliente = new JTextField();
        txtCliente.setEditable(false);
        panelTop.add(txtCliente);
        txtCliente.setColumns(10);

        JLabel lblValorEmprestimo = new JLabel("Valor do Empréstimo:");
        panelTop.add(lblValorEmprestimo);

        txtValorEmprestimo = new JTextField();
        txtValorEmprestimo.setEditable(false);
        panelTop.add(txtValorEmprestimo);
        txtValorEmprestimo.setColumns(10);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        tableParcelas = new JTable();
        tableParcelas.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Nº Parcela", "Valor Parcela", "Data Vencimento", "Status", "Valor Juro", "Valor a Pagar"
            }
        ));
        scrollPane.setViewportView(tableParcelas);

        carregarDetalhesEmprestimo(emprestimo);
    }

    private void carregarDetalhesEmprestimo(EmprestimosDTO emprestimo) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        if (emprestimo != null) {
            txtCliente.setText(emprestimo.getCliente().getNome() + " " + emprestimo.getCliente().getSobrenome());
            txtValorEmprestimo.setText(emprestimo.getValorEmprestimo().toString());

            DefaultTableModel model = (DefaultTableModel) tableParcelas.getModel();
            model.setRowCount(0); // Limpar tabela
 
            for (ParcelaEmprestimoDTO parcela : emprestimo.getParcelas()) {
                model.addRow(new Object[]{
                    parcela.getNumParcelas(),
                    df.format(parcela.getValorParcela()),
                    parcela.getDataVencimento(),
                    parcela.getStatus(),
                    df.format(parcela.getValorJuroParcela()),
                    df.format(parcela.getValorPagarPrestacao())
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao carregar detalhes do empréstimo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

