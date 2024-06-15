package DTO;

import java.sql.Date;
import java.time.Instant;

public class ParcelaEmprestimoDTO {

    private int idParcela;
    private int emprestimoId;
        private int numParcelas;
    private double valorParcela;
    private double valorJuroParcela;
    private double valorPagarPrestacao;
    private Date dataVencimento;
    private String status;

    public ParcelaEmprestimoDTO() {

    }

    public int getIdParcela() {
        return idParcela;
    }

    public void setIdParcela(int idParcela) {
        this.idParcela = idParcela;
    }

    public int getEmprestimoId() {
        return emprestimoId;
    }

    public void setEmprestimoId(int emprestimoId) {
        this.emprestimoId = emprestimoId;
    }
    
       public int getNumParcelas() {
        return numParcelas;
    }

    public void setNumParcelas(int numParcelas) {
        this.numParcelas = numParcelas;
    }
     
    

    public double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public double getValorJuroParcela() {
        return valorJuroParcela;
    }

    public void setValorJuroParcela(double valorJuroParcela) {
        this.valorJuroParcela = valorJuroParcela;
    }

    public double getValorPagarPrestacao() {
        return valorPagarPrestacao;
    }

    public void setValorPagarPrestacao(double valorPagarPrestacao) {
        this.valorPagarPrestacao = valorPagarPrestacao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
