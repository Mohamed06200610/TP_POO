package TP_POO.src.smartfarming.model.zones;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import TP_POO.src.smartfarming.enums.StageCroissance;
import TP_POO.src.smartfarming.enums.TypeCulture;
import TP_POO.src.smartfarming.enums.TypeZone;
import TP_POO.src.smartfarming.model.capteurs.Capteur;
import TP_POO.src.smartfarming.model.domain.Culture;
import TP_POO.src.smartfarming.model.historique.HistoriqueProductionCulture;
import TP_POO.src.smartfarming.model.releves.Releve;
import TP_POO.src.smartfarming.model.releves.ReleveNumerique;

public class ZoneCulture extends Zone {

    private List<Culture> cultures;

    public ZoneCulture(String code, String nom) {
        super(code, nom, TypeZone.CULTURE);
        this.cultures = new ArrayList<>();
    }

    // ── Getters / Setters ──────────────────────────────────

    public List<Culture> getCultures() {
        return cultures;
    }

    public void setCultures(List<Culture> cultures) {
        this.cultures = cultures;
    }

    // ── FONCTION 2 — Gérer les cultures ────────────────────

    public void ajouterCulture(Culture culture) {
        this.cultures.add(culture);
    }

    public void enregistrerCulture(TypeCulture type, Date datePlantation,
            Date dateRecoltePrevu, double phOptimal,
            double humiditeOptimale) {
        Culture culture = new Culture(type, datePlantation, dateRecoltePrevu,
                phOptimal, humiditeOptimale);
        this.cultures.add(culture);
    }

    public void mettreAJourStageCroissance(String idCulture, StageCroissance stage) {
        for (Culture c : cultures) {
            if (c.getId().equals(idCulture)) {
                c.setStageActuel(stage);
                return;
            }
        }
        System.out.println("Culture non trouvée : " + idCulture);
    }

    public StageCroissance afficherStageCroissance(String idCulture) {
        for (Culture c : cultures) {
            if (c.getId().equals(idCulture)) {
                return c.getStageActuel();
            }
        }
        return null;
    }

    public String genererRapportEtatCultures() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══ RAPPORT DES CULTURES — Zone ").append(getNom()).append(" ═══\n\n");

        for (Culture c : cultures) {
            sb.append("──── Culture: ").append(c.getId()).append(" ────\n");
            sb.append("  Type         : ").append(c.getTypeCulture()).append("\n");
            sb.append("  Famille      : ").append(c.getFamille()).append("\n");
            sb.append("  Stade actuel : ").append(c.getStageActuel()).append("\n");
            sb.append("  Plantation   : ").append(c.getDatePlantation()).append("\n");
            sb.append("  Récolte prév.: ").append(c.getDateRecoltePrevu()).append("\n");
            sb.append("  pH optimal   : ").append(c.getPhOptimal()).append("\n");
            sb.append("  Humidité opt.: ").append(c.getHumiditeOptimale()).append("%\n");

            // Relevés courants des capteurs de la zone
            sb.append("  Relevés capteurs :\n");
            for (Capteur capteur : getCapteurs()) {
                List<Releve> historique = capteur.getHistorique();
                if (!historique.isEmpty()) {
                    Releve dernier = historique.get(historique.size() - 1);
                    sb.append("    - ").append(capteur.getCode()).append(": ")
                            .append(dernier.getDescription()).append("\n");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // ── Zone abstraite ─────────────────────────────────────

    @Override
    public int getNombreEntites() {
        return cultures.size();
    }

    @Override
    public void enregistrerProduction() {
        double rendement = calculerRendement();
        String nomCulture = getCulturesPrincipales();

        HistoriqueProductionCulture h = new HistoriqueProductionCulture(
                getCode(), rendement, nomCulture);
        h.enregistrer();
        getHistoriques().add(h);
    }

    // ── Méthodes utilitaires privées ───────────────────────

    private double calculerRendement() {
        if (cultures.isEmpty()) {
            return 0.0;
        }
        // Simulation : rendement basé sur le nombre de cultures au stade RECOLTE
        double rendement = 0.0;
        for (Culture c : cultures) {
            switch (c.getStageActuel()) {
                case RECOLTE:
                    rendement += 500.0;
                    break;
                case MATURITE:
                    rendement += 350.0;
                    break;
                case CROISSANCE:
                    rendement += 200.0;
                    break;
                case GERMINATION:
                    rendement += 50.0;
                    break;
                case SEMIS:
                    rendement += 0.0;
                    break;
            }
        }
        return rendement / cultures.size();
    }

    private String getCulturesPrincipales() {
        if (cultures.isEmpty()) {
            return "Aucune";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cultures.size(); i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(cultures.get(i).getTypeCulture().name());
        }
        return sb.toString();
    }
}
