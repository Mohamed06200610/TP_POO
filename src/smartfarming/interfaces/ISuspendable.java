package TP_POO.src.smartfarming.interfaces;

/**
 * Interface ISuspendable — Contrat pour tout élément pouvant être activé ou
 * suspendu.
 *
 * Rôle : Définit le contrat commun d'activation / suspension pour les zones
 * et les capteurs. Permet le polymorphisme via un type commun.
 *
 * Concept POO : INTERFACE — impose un comportement uniforme (activer/suspendre)
 * sans imposer d'implémentation. Utilisée par Zone et Capteur.
 */
public interface ISuspendable {

    /**
     * Active l'élément (zone ou capteur).
     */
    void activer();

    /**
     * Suspend l'élément (zone ou capteur).
     */
    void suspendre();

    /**
     * Retourne le statut actuel sous forme de chaîne.
     *
     * @return le statut (ex: "ACTIVE", "SUSPENDUE", "ACTIF", "SUSPENDU")
     */
    String getStatut();
}
