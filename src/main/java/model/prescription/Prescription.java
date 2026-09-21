package model.prescription;

import model.consultation.Consultation;

public class Prescription {
    private int id;
    private String medicament, instruction, duree;
    private Consultation id_consultation;

    public  Prescription(String medicament, String duree, String instruction, Consultation id_consultation){
        this.medicament = medicament;
        this.duree = duree;
        this.instruction = instruction;
    }

    public int getId(){
        return this.id;
    }
    public String getMedicament(){
        return this.medicament;
    }
    public String getDuree(){
        return this.duree;
    }
    public  String getInstruction(){
        return this.instruction;
    }
    public  Consultation getConsultation(){
        return this.id_consultation;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setMedicament(String med){
        this.medicament = med;
    }
    public void setDuree(String dur){
        this.duree = dur;
    }
    public  void setInstruction(String inst){
        this.instruction = inst;
    }
    public  void setConsultation(Consultation idc){
        this.id_consultation = idc;
    }
}
