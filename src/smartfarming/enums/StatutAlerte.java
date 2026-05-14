package TP_POO.src.smartfarming.enums;

/**
 * Énumération StatutAlerte — Cycle de vie d'une alerte.
 *
 * Valeurs : ACTIVE (non traitée, affichée dans le panneau),
 *           ACQUITTEE (prise en compte par l'opérateur),
 *           SUPPRIMEE (retirée du panneau, conservée dans l'historique).
 *
 * Concept POO : ENUMERATION — gestion type-safe du workflow des alertes.
 */
public enum StatutAlerte {
    ACTIVE,
    ACQUITTEE,
    SUPPRIMEE
}
