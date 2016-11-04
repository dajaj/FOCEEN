package ginfo.foceen;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by pierre on 25/09/15.
 */
public class JointureSql extends SqlAbstract
{
    private static final String TABLE_PERSONNE = DefinitionSql.getTablePersonne();
    private static final String COL_PERSONNE_ID_PERSONNE = DefinitionSql.getColIdPersonne();
    private static final String COL_PERSONNE_NOM_PERSONNE = DefinitionSql.getColNomPersonne();
    private static final String COL_PERSONNE_PRENOM_PERSONNE = DefinitionSql.getColPrenomPersonne();
    private static final String COL_PERSONNE_PROMO_PERSONNE = DefinitionSql.getColPromoPersonne();
    private static final int NUM_COL_ID_PERSONNE = DefinitionSql.getNumColIdPersonne();
    private static final int NUM_COL_NOM_PERSONNE = DefinitionSql.getNumColNomPersonne();
    private static final int NUM_COL_PRENOM_PERSONNE = DefinitionSql.getNumColPrenomPersonne();
    private static final int NUM_COL_PROMO_PERSONNE = DefinitionSql.getNumColPromoPersonne();

    private static final String TABLE_EVENT = DefinitionSql.getTableEvent();
    private static final String COL_ID_EVENT = DefinitionSql.getColIdEvent();
    private static final String COL_NOM_EVENT = DefinitionSql.getColNomEvent();
    private static final String COL_DATE_EVENT = DefinitionSql.getColDateEvent();
    private static final int NUM_COL_ID_EVENT = DefinitionSql.getNumColIdEvent();
    private static final int NUM_COL_NOM_EVENT = DefinitionSql.getNumColNomEvent();
    private static final int NUM_COL_DATE_EVENT = DefinitionSql.getNumColDateEvent();

    private static final String TABLE_PRESENCE = DefinitionSql.getTablePresence();
    private static final String COL_PRESENCE_ID_PERSONNE_PRESENCE = DefinitionSql.getColIdPersonnePresence();
    private static final String COL_PRESENCE_ID_EVENT_PRESENCE = DefinitionSql.getColIdEventPresence();
    private static final int NUM_COL_ID_PERSONNE_PRESENCE = DefinitionSql.getNumColIdPersonnePresence();
    private static final int NUM_COL_ID_EVENT_PRESENCE = DefinitionSql.getNumColIdEventPresence();

    private static final String TABLE_PRESENCE_EXTERNE = DefinitionSql.getTablePresenceExterne();
    private static final String COL_PRESENCE_EXTERNE_ID_ECOLE = DefinitionSql.getColIdEcolePresenceExterne();
    private static final String COL_PRESENCE_EXTERNE_ID_EVENT = DefinitionSql.getColIdEventPresenceExterne();
    private static final String COL_PRESENCE_EXTERNE_NB_ELEVES = DefinitionSql.getColNbPresenceExterne();
    private static final int NUM_COL_ID_ECOLE_PRESENCE_EXTERNE = DefinitionSql.getNumColIdEcolePresenceExterne();
    private static final int NUM_COL_ID_EVENT_PRESENCE_EXTERNE = DefinitionSql.getNumColIdEventPresenceExterne();
    private static final int NUM_COL_NB_PRESENCE_EXTERNE = DefinitionSql.getNumColNbPresenceExterne();

    private static final String TABLE_EXTERNE = DefinitionSql.getTableExterne();
    private static final String COL_ID_ECOLE_EXTERNE = DefinitionSql.getColIdEcoleExterne();
    private static final String COL_NOM_ECOLE_EXTERNE = DefinitionSql.getColNomEcoleExterne();
    private static final int NUM_COL_ID_ECOLE_EXTERNE = DefinitionSql.getNumColIdEcoleExterne();
    private static final int NUM_COL_NOM_ECOLE_EXTERNE = DefinitionSql.getNumColNomEcoleExterne();

    public JointureSql(Context context)
    {
        super(context);
        super.open();
    }

    public ArrayList<Personne> getPersonneByEvent(Event event)
    {
        ArrayList<Personne> personneArray = new ArrayList<>();
        //Log.d("JointureSqlgetPersonneEv", event.toString());
        //Log.d("getBdd()JointureSql", super.getBDD().toString());
        try
        {
            personneArray = cursorToPersonne(super.getBDD().rawQuery("SELECT * FROM " + TABLE_PERSONNE + " WHERE id IN (SELECT idPersonne FROM " + TABLE_PRESENCE + " WHERE idEvent='" + event.getId()+"');" ,null));
        }
        catch(Exception e)
        {
            Log.e("JointureSqlEventPersonne", e.toString());
        }

        return personneArray;
    }

    public ArrayList<Personne> getPersonneByEventAndName(Event event,String recherche)
    {
        ArrayList<Personne> personneArray = new ArrayList<>();
        Log.d("JointureSqlgetPersonneAll", event.toString());
        Log.d("JointureSqlgetPersonneAll", recherche);
        Cursor cursor = null;
        try
        {
            String requete = "SELECT * FROM " + TABLE_PERSONNE + " WHERE id IN (SELECT idPersonne FROM " + TABLE_PRESENCE + " WHERE idEvent='" + event.getId()+"') AND nom LIKE '%"+recherche+"%';";

            cursor = super.getBDD().rawQuery(requete,null);
            personneArray = cursorToPersonne(cursor);
        }
        catch(Exception e)
        {
            Log.d("Cursor : ",(cursor != null) ? cursor.toString():"null");
            Log.e("JointureSqlEventNamePersonne", e.toString());
        }

        return personneArray;
    }

    private ArrayList<Personne> cursorToPersonne(Cursor c)
    {
        ArrayList<Personne> personneArray = new ArrayList<>();
        if(c.getCount() != 0)
        {

            for(int i = 0;i<c.getCount();i++)
            {
                c.moveToPosition(i);
                Personne personne = new Personne(c.getInt(NUM_COL_ID_PERSONNE), c.getString(NUM_COL_NOM_PERSONNE), c.getString(NUM_COL_PRENOM_PERSONNE), c.getString(NUM_COL_PROMO_PERSONNE));
                personneArray.add(personne);
                Log.d("PersonneJointureSql", personne.toString());
                Log.d("PersonneArrayJointureSql",personneArray.get(i).toString());
            }

            c.close();
        }

        return personneArray;
    }

    private ArrayList<Externe> cursorToExterne(Cursor c)
    {
        ArrayList<Externe> externeArray = new ArrayList<>();
        if(c.getCount() != 0)
        {

            for(int i = 0;i<c.getCount();i++)
            {
                c.moveToPosition(i);

                externeArray.add(new Externe(c.getInt(NUM_COL_ID_ECOLE_EXTERNE),c.getString(NUM_COL_NOM_ECOLE_EXTERNE)));
            }

            c.close();
        }

        return externeArray;
    }

    public ArrayList<Externe> getExterneByEvent(Event event)
    {
        ArrayList<Externe> externeArray = new ArrayList<>();
        try
        {
            externeArray = cursorToExterne(super.getBDD().rawQuery("SELECT * FROM " + TABLE_EXTERNE + " WHERE idEcole IN (SELECT idEcole FROM " + TABLE_PRESENCE_EXTERNE + " WHERE idEvent=" + event.getId()+");" ,null));
        }
        catch(Exception e)
        {
            Log.e("EventAllJointureSql", e.toString());
        }

        return externeArray;
    }

    public ArrayList<Externe> getExterneByEventAndName(Event event,String recherche)
    {
        ArrayList<Externe> externeArray = new ArrayList<>();
        try
        {
            externeArray = cursorToExterne(super.getBDD().rawQuery("SELECT * FROM " + TABLE_EXTERNE + " WHERE idEcole IN (SELECT idEcole FROM " + TABLE_PRESENCE_EXTERNE + " WHERE idEvent=" + event.getId()+") AND nomEcole LIKE '%"+recherche+"%';" ,null));
        }
        catch(Exception e)
        {
            Log.e("EventAllNameJointureSql", e.toString());
        }

        return externeArray;
    }
}

