package controller;

import model.consultation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import dao.FacturationDao;
import model.facturatoin.Facturation;

public class facturationController {
    
    public static  void createDataFacture(BigDecimal mtt, Consultation c){
        try{
            Facturation fac = new Facturation(mtt, LocalDate.now(), "paye",c);
            new FacturationDao().ajouterFacture(fac);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
