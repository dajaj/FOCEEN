package ginfo.foceen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by pierre on 22/09/15.
 */
public class PresenceExterneSql extends SqlAbstract
{
    private static final String TABLE_PRESENCE_EXTERNE = DefinitionSql.getTablePresenceExterne();
    private static final String COL_ID_ECOLE = DefinitionSql.getColIdEcolePresenceExterne();
    private static final String COL_ID_EVENT = DefinitionSql.getColIdEventPresenceExterne();
    private static final String COL_NB = DefinitionSql.getColNbPresenceExterne();
    private static final int NUM_COL_ID_ECOLE = DefinitionSql.getNumColIdEcolePresenceExterne();
    private static final int NUM_COL_ID_EVENT = DefinitionSql.getNumColIdEventPresenceExterne();
    private static final int NUM_COL_NB = DefinitionSql.getNumColNbPresenceExterne();

    public PresenceExterneSql(Context context)
    {
        super(context);
        super.open();
    }

    private long create(PresenceExterne presenceExterne)
    {
        long retour = 255;
        try {
            ContentValues values = new ContentValues();

            values.put(COL_ID_ECOLE, presenceExterne.getIdEcole());
            values.put(COL_ID_EVENT, presenceExterne.getIdEvent());
            values.put(COL_NB, presenceExterne.getNb());

            retour = super.getBDD().insert(TABLE_PRESENCE_EXTERNE, null, values);
        }
        catch (Exception e)
        {
            Log.e("creaPresenceExterneSql", e.toString());
        }

        return retour;
    }

    private int update(PresenceExterne presenceExterne)
    {
        int retour = 255;
        try
        {
            ContentValues values = new ContentValues();

            values.put(COL_NB, presenceExterne.getNb() + getPresenceExterneWithIdEcoleAndIdEvent(presenceExterne.getIdEcole(),presenceExterne.getIdEvent()).get(0).getNb());

            retour = super.getBDD().update(TABLE_PRESENCE_EXTERNE, values, COL_ID_ECOLE + " = " + presenceExterne.getIdEcole() + " AND " + COL_ID_EVENT + " = " + presenceExterne.getIdEvent(), null);
        }
        catch (Exception e)
        {
            Log.e("updPresenceExterneSql", e.toString());
        }

        return retour;
    }

    public int createOrUpdate(PresenceExterne presenceExterne)
    {
        int retour;
        Log.d("PresenceExterneSqlCoU", String.valueOf(getPresenceExterneWithIdEcoleAndIdEvent(presenceExterne.getIdEcole(), presenceExterne.getIdEvent())));
        if(getPresenceExterneWithIdEcoleAndIdEvent(presenceExterne.getIdEcole(),presenceExterne.getIdEvent()).size() == 0)
        {
            retour = (int)this.create(presenceExterne);
        }
        else
        {
            retour = this.update(presenceExterne);
        }

        return retour;
    }

    public int removeWithIdEcole(int iE)
    {
        int retour = 255;
        try
        {
            retour = super.getBDD().delete(TABLE_PRESENCE_EXTERNE, COL_ID_ECOLE + " = " + iE, null);
        }
        catch (Exception e)
        {
            Log.e("remove", e.toString());
        }

        return retour;
    }

    public int removeWithIdEvent(int iE)
    {
        int retour = 255;
        try
        {
            retour = super.getBDD().delete(TABLE_PRESENCE_EXTERNE, COL_ID_EVENT + " = " + iE, null);
        }
        catch (Exception e)
        {
            Log.e("remove", e.toString());
        }

        return retour;
    }

    public int removeWithID(int id, int iE)
    {
        int retour = 255;
        try
        {
            retour = super.getBDD().delete(TABLE_PRESENCE_EXTERNE, COL_ID_ECOLE + " = " + id + " AND " + COL_ID_EVENT + " = " + iE, null);
        }
        catch (Exception e)
        {
            Log.e("remove", e.toString());
        }

        return retour;
    }

    public ArrayList<PresenceExterne> getPresenceExterneWithIdEcole(int iEc)
    {
        ArrayList<PresenceExterne> presenceExterneArray = new ArrayList<>();
        try
        {
            presenceExterneArray = cursorTo(super.getBDD().query(TABLE_PRESENCE_EXTERNE, new String[]{COL_ID_ECOLE, COL_ID_EVENT, COL_NB}, COL_ID_ECOLE + " = \"" + iEc + "\"", null, null, null, null));
        }
        catch (Exception e)
        {
            Log.e("PresenceExterneWithPresenceExterneSql", e.toString());
        }

        return presenceExterneArray;
    }

    public ArrayList<PresenceExterne> getPresenceExterneWithIdEcoleAndIdEvent(int iEc, int iEv)
    {
        ArrayList<PresenceExterne> presenceExterneArray = new ArrayList<>();
        try
        {
            presenceExterneArray = cursorTo(super.getBDD().query(TABLE_PRESENCE_EXTERNE, new String[]{COL_ID_ECOLE, COL_ID_EVENT, COL_NB}, COL_ID_ECOLE + " = \"" + iEc + "\" AND " + COL_ID_EVENT + " = \"" + iEv + "\"", null, null, null, null));
        }
        catch (Exception e)
        {
            Log.e("PresenceExterneWithPresenceExterneSql", e.toString());
        }

        return presenceExterneArray;
    }

    public ArrayList<PresenceExterne> getPresenceExterneAll()
    {
        ArrayList<PresenceExterne> presenceExterneArray = new ArrayList<>();
        try
        {
            presenceExterneArray = cursorTo(super.getBDD().rawQuery("SELECT * FROM " + TABLE_PRESENCE_EXTERNE, null));
        }
        catch(Exception e)
        {
            Log.e("PresenceExterneAllPresenceExterneSql", e.toString());
        }

        return presenceExterneArray;
    }

    private ArrayList<PresenceExterne> cursorTo(Cursor c)
    {
        ArrayList<PresenceExterne> presenceExterneArray = new ArrayList<>();
        if(c.getCount() != 0)
        {

            for(int i = 0;i<c.getCount();i++)
            {
                c.moveToPosition(i);

                presenceExterneArray.add(new PresenceExterne(c.getInt(NUM_COL_ID_ECOLE),c.getInt(NUM_COL_ID_EVENT),c.getInt(NUM_COL_NB)));
            }

            c.close();
        }

        return presenceExterneArray;
    }
}
