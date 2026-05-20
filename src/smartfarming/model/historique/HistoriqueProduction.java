package TP_POO.src.smartfarming.model.historique;

import java.util.Date;
import TP_POO.src.smartfarming.enums.TypeZone;

/**
 * Classe abstraite HistoriqueProduction — Base de l'historique de production.
 *
 * Rôle : Chaque type de zone mesure la production différemment :
 * culture (kg/ha), élevage (litres ou œufs), aquacole (kg).
 * Cette classe abstraite impose un contrat uniforme (enregistrer, getResume)
 * tout en permettant à chaque sous-classe de stocker ses propres métriques.
 *
 * Concept POO : ABSTRACTION — impose enregistrer() et getResume() abstraits.
 * HÉRITAGE — trois sous-classes spécialisées.
 * POLYMORPHISME — getResume() produit un format différent
 * selon le type de zone.
 *
 * Pourquoi pas une classe générique unique ? Chaque zone mesure des métriques
 * fondamentalement différentes. L'héritage reflète cette spécialisation réelle.
 */
public abstract class HistoriqueProduction {

    private String id;
    private Date date;
    private String codeZone;
    private TypeZone typeZone;

    private static int compteur = 0;

    public HistoriqueProduction(String codeZone, TypeZone typeZone) {
        this.id = "HIST-" + (++compteur);
        this.date = new Date();
        this.codeZone = codeZone;
        this.typeZone = typeZone;
    }

    // ── Getters / Setters ──────────────────────────────────

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCodeZone() {
        return codeZone;
    }

    public void setCodeZone(String codeZone) {
        this.codeZone = codeZone;
    }

    public TypeZone getTypeZone() {
        return typeZone;
    }

    public void setTypeZone(TypeZone typeZone) {
        this.typeZone = typeZone;
    }

    // ── Méthodes abstraites ────────────────────────────────

    public abstract void enregistrer();

    public abstract String getResume();
}
