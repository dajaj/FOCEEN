package ginfo.foceen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by pierre on 21/09/15.
 */
public class PresenceSql extends SqlAbstract
{
    private static final String TABLE_PRESENCE = DefinitionSql.getTablePresence();
    private static final String COL_ID_PERSONNE = DefinitionSql.getColIdPersonnePresence();
    private static final String COL_ID_EVENT = DefinitionSql.getColIdEventPresence();
    private static final int NUM_COL_ID_PERSONNE = DefinitionSql.getNumColIdPersonnePresence();
    private static final int NUM_COL_ID_EVENT = DefinitionSql.getNumColIdEventPresence();

    public PresenceSql(Context context)
    {
        super(context);
        super.open();
    }

    public long create(Presence presence)
    {
        ContentValues values = new ContentValues();

        values.put(COL_ID_PERSONNE, presence.getIdPersonne());
        values.put(COL_ID_EVENT, presence.getIdEvent());

        long retour = 255;
        if(getWithIdPersonneAndIdEvent(presence.getIdPersonne(),presence.getIdEvent()).size() == 0)
        {
             retour = super.getBDD().insert(TABLE_PRESENCE, null, values);
        }

        return retour;
    }

    public int removeWithIdPersonne(int iP)
    {
        int delete = super.getBDD().delete(TABLE_PRESENCE, COL_ID_PERSONNE + " = " + iP, null);

        return delete;
    }

    public int removeWithIdEvent(int iE)
    {
        int delete = super.getBDD().delete(TABLE_PRESENCE, COL_ID_EVENT + " = " + iE, null);

        return delete;
    }

    public int removeWithID(int iP, int iE)
    {
        int delete = super.getBDD().delete(TABLE_PRESENCE, COL_ID_PERSONNE + " = " + iP + " AND "+ COL_ID_EVENT + " = " + iE, null);

        return delete;
    }

    public int removeAll()
    {
        int delete = super.getBDD().delete(TABLE_PRESENCE, "1",null);

        return delete;
    }

    public ArrayList<Presence> getWithIdPersonne(int iP)
    {
        Cursor c = super.getBDD().query(TABLE_PRESENCE, new String[]{COL_ID_PERSONNE, COL_ID_EVENT}, COL_ID_PERSONNE + " = " + iP, null, null, null, null);

        return cursorTo(c);
    }

    public ArrayList<Presence> getWithIdEvent(int iE)
    {
        Cursor c = super.getBDD().query(TABLE_PRESENCE, new String[] {COL_ID_PERSONNE, COL_ID_EVENT}, COL_ID_EVENT + " = " + iE,null,null,null,null);
        ArrayList<Presence> presences = cursorTo(c);
        return presences;
    }

    public ArrayList<Presence> getWithIdPersonneAndIdEvent(int iP, int iE)
    {
        Cursor c = super.getBDD().query(TABLE_PRESENCE, new String[] {COL_ID_PERSONNE, COL_ID_EVENT}, COL_ID_PERSONNE +" = " + iP + " AND " + COL_ID_EVENT + " = " + iE ,null,null,null,null);
        ArrayList<Presence> presences = cursorTo(c);

        return presences;
    }

    private ArrayList<Presence> cursorTo(Cursor c)
    {
        ArrayList<Presence> presenceArray = new ArrayList<>();
        if(c.getCount() != 0)
        {

            for(int i = 0;i<c.getCount();i++)
            {
                c.moveToPosition(i);

                presenceArray.add(new Presence(c.getInt(NUM_COL_ID_PERSONNE),c.getInt(NUM_COL_ID_EVENT)));
            }

            c.close();
        }

        return presenceArray;
    }
}
