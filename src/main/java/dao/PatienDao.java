
package dao;

import java.sql.*;
import java.time.LocalDate;

import controller.mainController;
import model.patient_class.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class PatienDao {
    private Connection conn;
    
    public PatienDao(){
        try{
            conn  = db.DBConnection.getConnection();
        }
        catch(Exception e){
            mainController.showAlert("Erreur : " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    public   boolean ajouterPatient(Patient patient){
        String sql = "INSERT INTO patient(nom, prenom, date_naissance, telephone, adresse) VALUES (?,?,?,?,?)";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, patient.getNom());
            stmt.setString(2, patient.getPrenom());
            stmt.setObject(3, patient.getDateNaiss());
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
            stmt.setObject(3, patient.getDateNaiss());
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
        String sql = "DELETE FROM patient WHERE id_patient = ?";

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

    private ResultSet result;
    public ObservableList<Patient> patientGetData(){
      ObservableList<Patient> listData = FXCollections.observableArrayList();

      String sql = "SELECT * FROM patient";
      try{
        Statement query =  conn.createStatement();
        result = query.executeQuery(sql);

        Patient patient;

        while (result.next()) {
            patient = new  Patient(result.getString("nom"), result.getString("prenom"), result.getObject("date_naissance", LocalDate.class),  result.getString("telephone"),  result.getString("adresse"));
            patient.setId(result.getInt("id_patient"));
            listData.add(patient);
        }
        
      } catch (Exception err){
        err.printStackTrace();
      }

      return listData;

    } 

    public Patient findById(int id) {
    Patient patient = null;
    String sql = "SELECT * FROM patient WHERE id_patient = ?";

    try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();

        if (result.next()) {
            patient = new Patient(result.getString("nom"), result.getString("prenom"), result.getObject("date_naissance", LocalDate.class), result.getString("telephone"), result.getString("adresse"));
            patient.setId(result.getInt("id_patient"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return patient;
}
}
