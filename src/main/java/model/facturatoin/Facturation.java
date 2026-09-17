package model.facturatoin;

import java.math.BigDecimal;
import java.time.LocalDate;

import model.consultation.Consultation;

public class Facturation {
    private int id;
    private BigDecimal montant;
    private LocalDate dateFacture;
    private String status;
    private Consultation consultation;

    public Facturation(BigDecimal mtt, LocalDate dateFac, String stt, Consultation c){
        this.montant = mtt;
        this.dateFacture = dateFac;
        this.status = stt;
        this.consultation = c;
    }

    public int getId(){
        return this.id;
    }
    public BigDecimal getMontant(){
        return this.montant;
    }
    public LocalDate getDateFacture(){
        return this.dateFacture;
    }
    public String getStatus(){
        return this.status;
    }
    public Consultation getConsultation(){
        return this.consultation;
    }

    public void setId(int id ){
        this.id = id;
    }
    public void setMontant(BigDecimal mtt){
        this.montant = mtt;
    }
    public void setDateFacture(LocalDate dateFac){
        this.dateFacture = dateFac;
    }
    public void setStatus(String stt){
        this.status = stt;
    }
    public void  getConsultation(Consultation c){
        this.consultation = c;
    }

}
