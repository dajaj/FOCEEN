package ginfo.foceen;

/**
 * Created by pierre on 25/09/15.
 */
public class DefinitionSql
{
    private static final String TABLE_PERSONNE = "personne";
    private static final String COL_ID_PERSONNE = "id";
    private static final String COL_NOM_PERSONNE = "nom";
    private static final String COL_PRENOM_PERSONNE = "prenom";
    private static final String COL_PROMO_PERSONNE = "promo";
    private static final int NUM_COL_ID_PERSONNE = 0;
    private static final int NUM_COL_NOM_PERSONNE = 1;
    private static final int NUM_COL_PRENOM_PERSONNE = 2;
    private static final int NUM_COL_PROMO_PERSONNE = 3;

    private static final String TABLE_PRESENCE = "presence";
    private static final String COL_ID_PERSONNE_PRESENCE = "idPersonne";
    private static final String COL_ID_EVENT_PRESENCE = "idEvent";
    private static final int NUM_COL_ID_PERSONNE_PRESENCE = 0;
    private static final int NUM_COL_ID_EVENT_PRESENCE = 1;

    private static final String TABLE_EVENT = "event";
    private static final String COL_ID_EVENT = "id";
    private static final String COL_NOM_EVENT = "nom";
    private static final String COL_DATE_EVENT = "date";
    private static final int NUM_COL_ID_EVENT = 0;
    private static final int NUM_COL_NOM_EVENT = 1;
    private static final int NUM_COL_DATE_EVENT = 2;

    private static final String TABLE_EXTERNE = "ecoleExterne";
    private static final String COL_ID_ECOLE_EXTERNE = "idEcole";
    private static final String COL_NOM_ECOLE_EXTERNE = "nomEcole";
    private static final int NUM_COL_ID_ECOLE_EXTERNE = 0;
    private static final int NUM_COL_NOM_ECOLE_EXTERNE = 1;

    private static final String TABLE_PRESENCE_EXTERNE = "presenceExterne";
    private static final String COL_ID_ECOLE_PRESENCE_EXTERNE = "idEcole";
    private static final String COL_ID_EVENT_PRESENCE_EXTERNE = "idEvent";
    private static final String COL_NB_PRESENCE_EXTERNE = "nbEleves";
    private static final int NUM_COL_ID_ECOLE_PRESENCE_EXTERNE = 0;
    private static final int NUM_COL_ID_EVENT_PRESENCE_EXTERNE = 1;
    private static final int NUM_COL_NB_PRESENCE_EXTERNE = 2;

    private static final String TABLE_SETTINGS = "settings";
    private static final String COL_NOM_SETTINGS = "nomSettings";
    private static final String COL_VALUE_SETTINGS = "valueSettings";
    private static final int NUM_COL_NOM_SETTINGS = 0;
    private static final int NUM_COL_VALUE_SETTINGS = 1;

    public static String getTablePersonne() {
        return TABLE_PERSONNE;
    }

    public static String getColIdPersonne() {
        return COL_ID_PERSONNE;
    }

    public static String getColNomPersonne() {
        return COL_NOM_PERSONNE;
    }

    public static String getColPrenomPersonne() {
        return COL_PRENOM_PERSONNE;
    }

    public static String getColPromoPersonne() {
        return COL_PROMO_PERSONNE;
    }

    public static int getNumColIdPersonne() {
        return NUM_COL_ID_PERSONNE;
    }

    public static int getNumColNomPersonne() {
        return NUM_COL_NOM_PERSONNE;
    }

    public static int getNumColPrenomPersonne() {
        return NUM_COL_PRENOM_PERSONNE;
    }

    public static int getNumColPromoPersonne() {
        return NUM_COL_PROMO_PERSONNE;
    }

    public static String getTablePresence() {
        return TABLE_PRESENCE;
    }

    public static String getColIdPersonnePresence() {
        return COL_ID_PERSONNE_PRESENCE;
    }

    public static String getColIdEventPresence() {
        return COL_ID_EVENT_PRESENCE;
    }

    public static int getNumColIdPersonnePresence() {
        return NUM_COL_ID_PERSONNE_PRESENCE;
    }

    public static int getNumColIdEventPresence() {
        return NUM_COL_ID_EVENT_PRESENCE;
    }

    public static String getTableEvent() {
        return TABLE_EVENT;
    }

    public static String getColIdEvent() {
        return COL_ID_EVENT;
    }

    public static String getColNomEvent() {
        return COL_NOM_EVENT;
    }

    public static String getColDateEvent() {
        return COL_DATE_EVENT;
    }

    public static int getNumColIdEvent() {
        return NUM_COL_ID_EVENT;
    }

    public static int getNumColNomEvent() {
        return NUM_COL_NOM_EVENT;
    }

    public static int getNumColDateEvent() {
        return NUM_COL_DATE_EVENT;
    }

    public static String getTableExterne() {
        return TABLE_EXTERNE;
    }

    public static String getColIdEcoleExterne() {
        return COL_ID_ECOLE_EXTERNE;
    }

    public static String getColNomEcoleExterne() {
        return COL_NOM_ECOLE_EXTERNE;
    }

    public static int getNumColIdEcoleExterne() {
        return NUM_COL_ID_ECOLE_EXTERNE;
    }

    public static int getNumColNomEcoleExterne() {
        return NUM_COL_NOM_ECOLE_EXTERNE;
    }

    public static String getTablePresenceExterne() {
        return TABLE_PRESENCE_EXTERNE;
    }

    public static String getColIdEcolePresenceExterne() {
        return COL_ID_ECOLE_PRESENCE_EXTERNE;
    }

    public static String getColIdEventPresenceExterne() {
        return COL_ID_EVENT_PRESENCE_EXTERNE;
    }

    public static String getColNbPresenceExterne() {
        return COL_NB_PRESENCE_EXTERNE;
    }

    public static int getNumColIdEcolePresenceExterne() {
        return NUM_COL_ID_ECOLE_PRESENCE_EXTERNE;
    }

    public static int getNumColIdEventPresenceExterne() {
        return NUM_COL_ID_EVENT_PRESENCE_EXTERNE;
    }

    public static int getNumColNbPresenceExterne() {
        return NUM_COL_NB_PRESENCE_EXTERNE;
    }

    public static String getTableSettings() {
        return TABLE_SETTINGS;
    }

    public static String getColNomSettings() {
        return COL_NOM_SETTINGS;
    }

    public static String getColValueSettings() {
        return COL_VALUE_SETTINGS;
    }

    public static int getNumColNomSettings() {
        return NUM_COL_NOM_SETTINGS;
    }

    public static int getNumColValueSettings() {
        return NUM_COL_VALUE_SETTINGS;
    }
}
