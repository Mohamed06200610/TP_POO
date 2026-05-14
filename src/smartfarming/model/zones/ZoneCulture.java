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

/**
 * Classe ZoneCulture — Zone dédiée aux cultures végétales.
 *
 * Rôle : Gère les cultures (céréales, légumes, fruits) avec leurs stades
 * de croissance, capteurs environnementaux et capteurs de sol.
 * Implémente les méthodes de la Fonction 2 (Gérer les cultures).
 *
 * Concept POO :
 * HÉRITAGE — étend Zone avec des attributs et méthodes spécifiques aux
 * cultures.
 * POLYMORPHISME — enregistrerProduction() crée un HistoriqueProductionCulture.
 */
public class ZoneCulture extends Zone {

    private List<Culture> cultures;

    /**
     * Constructeur.
     *
     * @param code code unique de la zone
     * @param nom  nom de la zone
     */
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

    /**
     * Ajoute une culture existante à la zone.
     *
     * @param culture la culture à ajouter
     */
    public void ajouterCulture(Culture culture) {
        this.cultures.add(culture);
    }

    /**
     * Enregistre une nouvelle culture dans la zone.
     * Crée l'objet Culture et l'ajoute à la liste.
     *
     * @param type             type de culture (CEREALE, LEGUME, FRUIT)
     * @param datePlantation   date de plantation
     * @param dateRecoltePrevu date de récolte prévue
     * @param phOptimal        pH optimal du sol
     * @param humiditeOptimale humidité optimale en %
     */
    public void enregistrerCulture(TypeCulture type, Date datePlantation,
            Date dateRecoltePrevu, double phOptimal,
            double humiditeOptimale) {
        Culture culture = new Culture(type, datePlantation, dateRecoltePrevu,
                phOptimal, humiditeOptimale);
        this.cultures.add(culture);
    }

    /**
     * Met à jour le stade de croissance d'une culture identifiée par son ID.
     *
     * @param idCulture identifiant de la culture
     * @param stage     nouveau stade de croissance
     */
    public void mettreAJourStageCroissance(String idCulture, StageCroissance stage) {
        for (Culture c : cultures) {
            if (c.getId().equals(idCulture)) {
                c.setStageActuel(stage);
                return;
            }
        }
        System.out.println("Culture non trouvée : " + idCulture);
    }

    /**
     * Affiche (retourne) le stade de croissance actuel d'une culture.
     *
     * @param idCulture identifiant de la culture
     * @return le stade de croissance actuel, ou null si non trouvée
     */
    public StageCroissance afficherStageCroissance(String idCulture) {
        for (Culture c : cultures) {
            if (c.getId().equals(idCulture)) {
                return c.getStageActuel();
            }
        }
        return null;
    }

    /**
     * Génère un rapport complet de l'état de toutes les cultures de la zone.
     * Inclut : type, famille, stade, dates, exigences, relevés courants.
     *
     * @return rapport formaté
     */
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

    /**
     * Retourne le nombre de cultures dans la zone.
     *
     * @return nombre de cultures
     */
    @Override
    public int getNombreEntites() {
        return cultures.size();
    }

    /**
     * Enregistre la production de la zone culture.
     * Crée un HistoriqueProductionCulture avec le rendement calculé
     * et le nom de la culture principale.
     */
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

    /**
     * Calcule le rendement moyen des cultures (simulation).
     * En production réelle, ce serait basé sur les capteurs et la surface.
     *
     * @return rendement en kg/hectare
     */
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

    /**
     * Retourne les noms des cultures principales de la zone.
     *
     * @return chaîne des types de cultures
     */
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
