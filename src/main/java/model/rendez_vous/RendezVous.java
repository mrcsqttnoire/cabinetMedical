package model.rendez_vous;

import java.time.LocalDate;
import java.time.LocalTime;

import model.patient_class.Patient;

public class RendezVous {
    private int id;
    private LocalDate dateRdv;
    private LocalTime heureRdv;
    private String motif, status;
    private Patient patient;

    public RendezVous(LocalDate dateRdv, LocalTime heureRdv, String motif, String status, Patient Patient){
        this.dateRdv = dateRdv;
        this.heureRdv = heureRdv;
        this.motif = motif;
        this.status = status;
        this.patient = patient;
    }

    public int getId(){
        return this.id;
    }
    public LocalDate getDateRdv(){
        return this.dateRdv;
    }
    public LocalTime getHeureRdv(){
        return this.heureRdv;
    }
    public String getMotif(){
        return this.motif;
    }
    public String getStatus(){
        return this.status;
    }
    public Patient getPatient(){
        return this.patient;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setDateRdv(LocalDate dateRdv){
        this.dateRdv = dateRdv;
    }
    public  void setHeureRdv(LocalTime heureRdv){
        this.heureRdv = heureRdv;
    }
    public void setMotif(String motif){
        this.motif = motif;
    }
    public void setStatus(String status){
        this.status = status; 
    }
    public void setPatient(Patient patient){
        this.patient = patient;
    }
}
