package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import controller.mainController;
import javafx.scene.control.Alert;
import model.consultation.Consultation;
import model.facturatoin.Facturation;

public class FacturationDao {
    private Connection conn;

    public FacturationDao() {
        try {
            conn = db.DBConnection.getConnection();
        } catch (Exception e) {
            mainController.showAlert("Erreur : " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public boolean ajouterFacture(Facturation fac) {
        String sql = "INSERT INTO  facture(montant, date_facture, statut_paiement, id_consultation) VALUES (?,?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, fac.getMontant());
            stmt.setObject(2, fac.getDateFacture());
            stmt.setString(3, fac.getStatus());
            stmt.setObject(4, fac.getConsultation().getId());

            System.out.println("Facturation réussi!");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean modifierFacture(Facturation fac) {
        String sql = "UPDATE facture SET montant = ?, date_facture = ?, statut_paiement = ?, id_consultation = ? WHERE id_facture = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, fac.getMontant());
            stmt.setObject(2, fac.getDateFacture());
            stmt.setString(3, fac.getStatus());
            stmt.setObject(4, fac.getConsultation().getId());
            stmt.setInt(5, fac.getId());

            System.out.println("Facturation réussi!");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean supprimerFacture(Facturation fac) {
        String sql = "DELETE FROM facture WHERE id_facture = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, fac.getId());
            System.out.println("Consultation supprimé");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
