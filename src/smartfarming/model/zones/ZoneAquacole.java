package TP_POO.src.smartfarming.model.zones;

import java.util.ArrayList;
import java.util.List;
import TP_POO.src.smartfarming.enums.TypeZone;
import TP_POO.src.smartfarming.model.domain.Bassin;
import TP_POO.src.smartfarming.model.domain.EspeceAquacole;
import TP_POO.src.smartfarming.model.domain.ProgrammeAlimentation;
import TP_POO.src.smartfarming.model.historique.HistoriqueProductionAquacole;

public class ZoneAquacole extends Zone {

    private Bassin bassin;
    private List<EspeceAquacole> especesAquacoles;
    private ProgrammeAlimentation programmeAlimentation;

    public ZoneAquacole(String code, String nom) {
        super(code, nom, TypeZone.AQUACOLE);
        this.bassin = new Bassin(100.0); // bassin par défaut
        this.especesAquacoles = new ArrayList<>();
        this.programmeAlimentation = null;
    }

    // ── Getters / Setters ──────────────────────────────────

    public Bassin getBassin() {
        return bassin;
    }

    public void setBassin(Bassin bassin) {
        this.bassin = bassin;
    }

    public List<EspeceAquacole> getEspecesAquacoles() {
        return especesAquacoles;
    }

    public void setEspecesAquacoles(List<EspeceAquacole> especesAquacoles) {
        this.especesAquacoles = especesAquacoles;
    }

    public ProgrammeAlimentation getProgrammeAlimentation() {
        return programmeAlimentation;
    }

    public void setProgrammeAlimentation(ProgrammeAlimentation programmeAlimentation) {
        this.programmeAlimentation = programmeAlimentation;
    }

    // ── Méthodes métier ────────────────────────────────────

    public void ajouterEspece(EspeceAquacole espece) {
        this.especesAquacoles.add(espece);
        this.bassin.ajouterEspece(espece);
    }

    public void definirProgrammeAlimentation(String typeAliment, double quantiteParRepas,
            int nombreRepas) {
        this.programmeAlimentation = new ProgrammeAlimentation(
                typeAliment, quantiteParRepas, nombreRepas);
    }

    @Override
    public int getNombreEntites() {
        return especesAquacoles.size();
    }

    @Override
    public void enregistrerProduction() {
        double poids = calculerPoidsRecolte();
        String espece = getEspecePrincipale();

        HistoriqueProductionAquacole h = new HistoriqueProductionAquacole(
                getCode(), poids, espece);
        h.enregistrer();
        getHistoriques().add(h);
    }

    // ── Méthodes utilitaires privées ───────────────────────

    private double calculerPoidsRecolte() {
        double total = 0.0;
        for (EspeceAquacole e : especesAquacoles) {
            // Simulation : poids moyen par individu × quantité
            total += e.getQuantite() * 0.5; // 0.5 kg en moyenne par individu
        }
        return total;
    }

    private String getEspecePrincipale() {
        if (especesAquacoles.isEmpty()) {
            return "Aucune";
        }
        EspeceAquacole principale = especesAquacoles.get(0);
        for (EspeceAquacole e : especesAquacoles) {
            if (e.getQuantite() > principale.getQuantite()) {
                principale = e;
            }
        }
        return principale.getNom();
    }
}
