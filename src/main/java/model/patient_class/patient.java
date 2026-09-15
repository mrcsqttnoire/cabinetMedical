
package model.patient_class;

public class Patient {
    private int id; 
    private String nom, prenom, date_naiss, telephone, adresse;
    
    public Patient(int id, String nom, String prenom, String date_naiss, String telephone, String adresse){
        this.id = id;
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
    public String getPrenom(){
        return this.prenom;
    }
    public String getDateNaiss(){
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
    public void setDateNaiss(String date_naiss){
        this.date_naiss = date_naiss;
    }
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    public void setAdresse(String Adresse){
        this.adresse = Adresse;
    }
}
