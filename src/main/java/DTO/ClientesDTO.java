package DTO;

import java.time.LocalDate;

import java.util.Date;

public class ClientesDTO {

    private int clienteId;
    private String nome;
    private String sobrenome;
    private Date dataNascimento;
    private String NUIT;
    private String endereco;
    private String telefone;
    private String email;
    private String documentoDeIdent;
    private String obs;

    // Getters e Setters
    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNUIT() {
        return NUIT;
    }

    public void setNUIT(String NUIT) {
        this.NUIT = NUIT;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumentoDeIdent() {
        return documentoDeIdent;
    }

    public void setDocumentoDeIdent(String documentoDeIdent) {
        this.documentoDeIdent = documentoDeIdent;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
