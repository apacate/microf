package DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmprestimosDTO {
    private int emprestimoId;
        private int parcelaId;
    private ClientesDTO cliente;
    private BigDecimal valorEmprestimo;
    private Date dataContratacao;
    private List<ParcelaEmprestimoDTO> parcelas;

    public EmprestimosDTO() {
        this.parcelas = new ArrayList<>();
    }

  
    // Getters e setters
            
                public int getEmprestimoId() {
        return emprestimoId;
    }

    public void setEmprestimoId(int emprestimoId) {
        this.emprestimoId = emprestimoId;
        
    }
    public int getParcelaId() {
        return parcelaId;
    }

    public void setParcelaId(int parcelaId) {
        this.parcelaId = parcelaId;
    }

    public ClientesDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClientesDTO cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorEmprestimo() {
        return valorEmprestimo;
    }

    public void setValorEmprestimo(BigDecimal valorEmprestimo) {
        this.valorEmprestimo = valorEmprestimo;
    }

    public Date getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(Date dataContratacao) {
        this.dataContratacao = dataContratacao;
    }


    public List<ParcelaEmprestimoDTO> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<ParcelaEmprestimoDTO> parcelas) {
        this.parcelas = parcelas;
    }
}
