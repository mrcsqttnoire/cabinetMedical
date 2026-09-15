
package dao;

import java.sql.*;

import model.patient_class.Patient;
import db.DBConnection;

public class patient_dao {
    private Connection conn;
    
    public patient_dao(){
        try{
            conn  = db.DBConnection.getConnection();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public boolean ajouterPatient(Patient patient){
        String sql = "INSERT INTO patient(nom, prenom, date_naissance, telephone, adresse) VALUES (?,?,?,?,?)";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setString(3, patient.getDateNaiss());
            stmt.setString(4, patient.getTelephone());
            stmt.setString(5, patient.getAdresse());
            
            System.out.println("Patient ajouté avec succès");
            return stmt.executeUpdate()>0;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean modifierPatient(Patient patient){
        String sql = "UPDATE patient SET nom = ?, prenom = ?, date_naissance = ?, telephone = ?, adresse = ? WHERE id_patient = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setString(3, patient.getDateNaiss());
            stmt.setString(4, patient.getTelephone());
            stmt.setString(5, patient.getAdresse());
            stmt.setInt(6, patient.getId());
            
            System.out.println("Patient modifié avec succès");
            return stmt.executeUpdate()>0;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }

    public boolean supprimerPatient(Patient patient){
        String sql = "DELETE patient WHERE id_patient = ?";

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, patient.getId());
            System.out.println("Patient supprimé");
            return stmt.executeUpdate() > 0;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
