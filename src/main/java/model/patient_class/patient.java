
package model.patient_class;

import java.time.LocalDate;

public class Patient {
    private int id; 
    private String nom, prenom, telephone, adresse;
    private LocalDate date_naiss;
    
    public Patient(String nom, String prenom, LocalDate date_naiss, String telephone, String adresse){
        this.nom = nom;
        this.prenom = prenom;
        this.date_naiss = date_naiss;
        this.telephone = telephone;
        this.adresse = adresse;
    }
    
    public int getId(){
        return this.id;
    }
    public String getNom(){
        return this.nom;
    }
    public String getNomPrenom(){
        return this.nom + " " + this.prenom;
    }
    public String getPrenom(){
        return this.prenom;
    }
    public LocalDate getDateNaiss(){
        return this.date_naiss;
    }
    public String getTelephone(){
        return this.telephone;
    }
    public String getAdresse(){
        return this.adresse;
    }
    
    public void setId(int id){
        this.id = id;
    }
    public void setNom(String nom){
        this.nom = nom;
    }
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }
    public void setDateNaiss(LocalDate date_naiss){
        this.date_naiss = date_naiss;
    }
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    public void setAdresse(String Adresse){
        this.adresse = Adresse;
    }
}
