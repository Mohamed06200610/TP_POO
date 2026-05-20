package TP_POO.src.smartfarming.model.domain;

import java.util.Date;
import TP_POO.src.smartfarming.enums.StageCroissance;
import TP_POO.src.smartfarming.enums.TypeCulture;

public class Culture {

    private String id;
    private TypeCulture typeCulture;
    private Date datePlantation;
    private Date dateRecoltePrevu;
    private StageCroissance stageActuel;
    private double phOptimal;
    private double humiditeOptimale;

    private static int compteur = 0;

    public Culture(TypeCulture typeCulture, Date datePlantation, Date dateRecoltePrevu,
            double phOptimal, double humiditeOptimale) {
        this.id = "CUL-" + (++compteur);
        this.typeCulture = typeCulture;
        this.datePlantation = datePlantation;
        this.dateRecoltePrevu = dateRecoltePrevu;
        this.stageActuel = StageCroissance.SEMIS; // stade initial
        this.phOptimal = phOptimal;
        this.humiditeOptimale = humiditeOptimale;
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TypeCulture getTypeCulture() {
        return typeCulture;
    }

    public void setTypeCulture(TypeCulture typeCulture) {
        this.typeCulture = typeCulture;
    }

    public TypeCulture getFamille() {
        return typeCulture;
    }

    public Date getDatePlantation() {
        return datePlantation;
    }

    public void setDatePlantation(Date datePlantation) {
        this.datePlantation = datePlantation;
    }

    public Date getDateRecoltePrevu() {
        return dateRecoltePrevu;
    }

    public void setDateRecoltePrevu(Date dateRecoltePrevu) {
        this.dateRecoltePrevu = dateRecoltePrevu;
    }

    public StageCroissance getStageActuel() {
        return stageActuel;
    }

    public void setStageActuel(StageCroissance stageActuel) {
        this.stageActuel = stageActuel;
    }

    public double getPhOptimal() {
        return phOptimal;
    }

    public void setPhOptimal(double phOptimal) {
        this.phOptimal = phOptimal;
    }

    public double getHumiditeOptimale() {
        return humiditeOptimale;
    }

    public void setHumiditeOptimale(double humiditeOptimale) {
        this.humiditeOptimale = humiditeOptimale;
    }

    @Override
    public String toString() {
        return "Culture [" + id + "] " + typeCulture + " — Stade: " + stageActuel;
    }
}
