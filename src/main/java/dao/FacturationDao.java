package dao;

import java.sql.*;
import java.time.LocalDate;

import controller.mainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
            System.out.println("Facture supprimée");
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private ResultSet result;

    public ObservableList<Facturation> facturationGetData() {
        ObservableList<Facturation> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM facture";
        try {
            Statement query = conn.createStatement();
            result = query.executeQuery(sql);

            Facturation fac;

            while (result.next()) {

                int id_consultation = result.getInt("id_consultation");
                Consultation c = new ConsultationDao().findById(id_consultation);

                fac = new Facturation(result.getBigDecimal("montant"), result.getObject("date_facture", LocalDate.class), result.getString("statut_paiement"), c );

                listData.add(fac);
            }

        } catch (Exception err) {
            err.printStackTrace();
        }

        return listData;
    }

    public Facturation findById(int id) {
        Facturation fac = null;
        String sql = "SELECT * FROM consultation WHERE id_consultation = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {

                int id_consultation = result.getInt("id_consultation");
                Consultation c = new ConsultationDao().findById(id_consultation);

                fac = new Facturation(result.getBigDecimal("montant"), result.getObject("date_facture", LocalDate.class), result.getString("statut_paiement"), c );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return fac;
    }
}
