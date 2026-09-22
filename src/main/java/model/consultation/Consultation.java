package model.consultation;

import java.time.LocalDate;

import model.patient_class.Patient;
import model.rendez_vous.RendezVous;

public class Consultation {
    private int id;
    private LocalDate dateConsultation;
    private String diagnostique, tension, temperature, poid;
    private Patient patient;
    private RendezVous rdv;

    public Consultation(LocalDate dtc, String diag, String ts, String temp, String pd, Patient patient){
        this.dateConsultation = dtc;
        this.diagnostique = diag;
        this.tension = ts;
        this.temperature = temp;
        this.poid = pd;
        this.patient = patient;
    }

    public int getId(){
        return this.id;
    }
    public LocalDate getDateConsultation(){
        return this.dateConsultation;
    }
    public String getDiagnostique(){
        return this.diagnostique;
    }
    public String getTension(){
        return this.tension;
    }
    public String getTemperature(){
        return this.temperature;
    }
    public String getPoid(){
        return this.poid;
    }
    public Patient getPatient(){
        return this.patient;
    }
    public RendezVous getRendezVous(){
        return this.rdv;
    }
    public String getDonneeMed(){
        return this.tension + "mmHg | " + this.temperature + "°C | " + this.poid + " kg ";
    }

    public void setId(int id){
        this.id = id;
    }
    public void setDateConsultation(LocalDate dtc){
        this.dateConsultation = dtc;
    }
    public void setDiagnostique(String diag){
        this.diagnostique = diag;
    }
    public void setTension(String tension){
        this.tension = tension;
    }
    public void setTemperature(String temp){
        this.temperature = temp;
    }
    public void setPoid(String poid){
        this.poid = poid;
    }
    public void setPatient(Patient patient){
        this.patient = patient;
    }
    public void setRendezVous(RendezVous rdv){
        this.rdv = rdv;
    }
}
