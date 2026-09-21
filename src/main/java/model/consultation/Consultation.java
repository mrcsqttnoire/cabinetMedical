package model.consultation;

import java.time.LocalDate;

import model.patient_class.Patient;
import model.rendez_vous.RendezVous;

public class Consultation {
    private int id;
    private LocalDate dateConsultation;
    private String diagnostique;
    private Patient patient;
    private RendezVous rdv;

    public Consultation(LocalDate dtc, String diag, Patient patient){
        this.dateConsultation = dtc;
        this.diagnostique = diag;
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
    public Patient getPatient(){
        return this.patient;
    }
    public RendezVous getRendezVous(){
        return this.rdv;
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
    public void setPatient(Patient patient){
        this.patient = patient;
    }
    public void setRendezVous(RendezVous rdv){
        this.rdv = rdv;
    }
}
