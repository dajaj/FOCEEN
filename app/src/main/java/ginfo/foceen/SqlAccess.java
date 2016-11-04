package ginfo.foceen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
/**
 * Created by pierre on 21/09/15.
 */

public class SqlAccess extends SQLiteOpenHelper
{
    private static final String TABLE_PERSONNE = DefinitionSql.getTablePersonne();
    private static final String COL_PERSONNE_ID = DefinitionSql.getColIdPersonne();
    private static final String COL_PERSONNE_NOM = DefinitionSql.getColNomPersonne();
    private static final String COL_PERSONNE_PRENOM = DefinitionSql.getColPrenomPersonne();
    private static final String COL_PERSONNE_PROMO = DefinitionSql.getColPromoPersonne();
    private static final String CREATE_TABLE_PERSONNE = "CREATE TABLE IF NOT EXISTS " + TABLE_PERSONNE + " ("
            + COL_PERSONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_PERSONNE_NOM + " TEXT NOT NULL, "
            + COL_PERSONNE_PRENOM + " TEXT NOT NULL, "
            + COL_PERSONNE_PROMO + " TEXT NOT NULL);";

    private static final String TABLE_EVENT = DefinitionSql.getTableEvent();
    private static final String COL_EVENT_ID = DefinitionSql.getColIdEvent();
    private static final String COL_EVENT_NOM = DefinitionSql.getColNomEvent();
    private static final String COL_EVENT_DATE = DefinitionSql.getColDateEvent();
    private static final String CREATE_TABLE_EVENT = "CREATE TABLE IF NOT EXISTS " + TABLE_EVENT + " ("
            + COL_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_EVENT_NOM + " TEXT NOT NULL, "
            + COL_EVENT_DATE + " TEXT NOT NULL);";

    private static final String TABLE_PRESENCE = DefinitionSql.getTablePresence();
    private static final String COL_PRESENCE_ID_PERSONNE = DefinitionSql.getColIdPersonnePresence();
    private static final String COL_PRESENCE_ID_EVENT = DefinitionSql.getColIdEventPresence();
    private static final String CREATE_TABLE_PRESENCE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRESENCE + " ("
            + COL_PRESENCE_ID_PERSONNE+ " INTEGER NOT NULL, "
            + COL_PRESENCE_ID_EVENT+ " INTEGER NOT NULL);";

    private static final String TABLE_EXTERNE = DefinitionSql.getTableExterne();
    private static final String COL_EXTERNE_ID = DefinitionSql.getColIdEcoleExterne();
    private static final String COL_EXTERNE_NOM = DefinitionSql.getColNomEcoleExterne();
    private static final String CREATE_TABLE_EXTERNE = "CREATE TABLE IF NOT EXISTS " + TABLE_EXTERNE + " ("
            + COL_EXTERNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_EXTERNE_NOM + " TEXT NOT NULL);";

    private static final String TABLE_PRESENCE_EXTERNE = DefinitionSql.getTablePresenceExterne();
    private static final String COL_PRESENCE_EXTERNE_ID_ECOLE = DefinitionSql.getColIdEcolePresenceExterne();
    private static final String COL_PRESENCE_EXTERNE_ID_EVENT = DefinitionSql.getColIdEventPresenceExterne();
    private static final String COL_PRESENCE_EXTERNE_NB_ELEVES = DefinitionSql.getColNbPresenceExterne();
    private static final String CREATE_TABLE_PRESENCE_EXTERNE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRESENCE_EXTERNE + " ("
            + COL_PRESENCE_EXTERNE_ID_ECOLE + " INTEGER NOT NULL, "
            + COL_PRESENCE_EXTERNE_ID_EVENT + " INTEGER NOT NULL, "
            + COL_PRESENCE_EXTERNE_NB_ELEVES + " INTEGER NOT NULL);";

    private static final String TABLE_SETTINGS = DefinitionSql.getTableSettings();
    private static final String COL_NOM_SETTINGS = DefinitionSql.getColNomSettings();
    private static final String COL_VALUE_SETTINGS = DefinitionSql.getColValueSettings();
    private static final String CREATE_TABLE_SETTINGS = "CREATE TABLE IF NOT EXISTS " + TABLE_SETTINGS + " ("
            + COL_NOM_SETTINGS + " TEXT NOT NULL, "
            + COL_VALUE_SETTINGS + " TEXT NOT NULL);";

    public SqlAccess(Context context, String name, CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db)
    {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        try
        {
            db.execSQL(CREATE_TABLE_PERSONNE);
            db.execSQL(CREATE_TABLE_EVENT);
            db.execSQL(CREATE_TABLE_PRESENCE);
            db.execSQL(CREATE_TABLE_EXTERNE);
            db.execSQL(CREATE_TABLE_PRESENCE_EXTERNE);
            db.execSQL(CREATE_TABLE_SETTINGS);
        }
        catch(Exception e)
        {
            Log.e("ErreuronCreateSqlAccess", e.toString());
        }

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        //db.execSQL("DROP TABLE " + TABLE_LIVRES + ";");
        try
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONNE + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT  + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESENCE + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXTERNE + ";");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESENCE_EXTERNE);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
            onCreate(db);
        }
        catch (Exception e)
        {
            Log.e("ErronUpgradeSqlAccess", e.toString());
        }

    }
}
