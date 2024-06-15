package DAO;

import DTO.FuncionariosDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FuncionariosDAO {

    Connection conn;

    public FuncionariosDAO() {
        this.conn=new ConexaoDAO().ConectaBD();
    }

    public boolean autenticacaoUsuario(FuncionariosDTO objFuncionariosDTO) {
        try {
            String sql = "SELECT * FROM funcionarios WHERE usuario=? AND senha=?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, objFuncionariosDTO.getUsuario());
            pstm.setString(2, objFuncionariosDTO.getSenha());
            ResultSet rs = pstm.executeQuery();
            return rs.next();

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "FuncionarioDAO " + erro.getMessage());
            return false;
        }
    }
}
