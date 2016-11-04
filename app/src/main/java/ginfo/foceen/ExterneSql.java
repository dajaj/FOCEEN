package ginfo.foceen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by pierre on 22/09/15.
 */
public class ExterneSql extends SqlAbstract
{
    private static final String TABLE_EXTERNE = DefinitionSql.getTableExterne();
    private static final String COL_ID_ECOLE = DefinitionSql.getColIdEcoleExterne();
    private static final String COL_NOM_ECOLE = DefinitionSql.getColNomEcoleExterne();
    private static final int NUM_COL_ID_ECOLE = DefinitionSql.getNumColIdEcoleExterne();
    private static final int NUM_COL_NOM_ECOLE = DefinitionSql.getNumColNomEcoleExterne();

    public ExterneSql(Context context)
    {
        super(context);
        super.open();
    }

    public long create(Externe externe)
    {
        long retour = 255;
        try {
            ContentValues values = new ContentValues();

            values.put(COL_NOM_ECOLE, externe.getNomEcole());

            retour = super.getBDD().insert(TABLE_EXTERNE, null, values);
        }
        catch (Exception e)
        {
            Log.e("creaPresenceExterneSql", e.toString());
        }

        return retour;
    }

    public int update(int id, Externe externe)
    {
        int retour = 255;
        try
        {
            ContentValues values = new ContentValues();

            values.put(COL_ID_ECOLE, externe.getIdEcole());
            values.put(COL_NOM_ECOLE, externe.getNomEcole());

            retour = super.getBDD().update(TABLE_EXTERNE, values, COL_ID_ECOLE + " = " + id, null);
        }
        catch (Exception e) {
            Log.e("updPresenceExterneSql", e.toString());
        }

        return retour;
    }

    public int removeWithID(int id)
    {
        int retour = 255;
        try
        {
            retour = super.getBDD().delete(TABLE_EXTERNE, COL_ID_ECOLE + " = " + id, null);
        }
        catch (Exception e)
        {
            Log.e("remove", e.toString());
        }

        return retour;
    }

    public int removeAll()
    {
        int retour = 255;
        try
        {
            retour = super.getBDD().delete(TABLE_EXTERNE, "1", null);
        }
        catch (Exception e)
        {
            Log.e("remove", e.toString());
        }

        return retour;
    }

    public ArrayList<Externe> getExterneWithNom(String nom)
    {
        ArrayList<Externe> externeArray = new ArrayList<>();
        try
        {
            externeArray = cursorTo(super.getBDD().query(TABLE_EXTERNE, new String[]{COL_ID_ECOLE, COL_NOM_ECOLE}, COL_NOM_ECOLE + " = \"" + nom + "\"", null, null, null, null));
        }
        catch (Exception e)
        {
            Log.e("geExterneWithExterneSql", e.toString());
        }

        return externeArray;
    }

    public ArrayList<Externe> getExterneAll()
    {
        ArrayList<Externe> externeArray = new ArrayList<>();
        try
        {
            externeArray = cursorTo(super.getBDD().rawQuery("SELECT * FROM " + TABLE_EXTERNE,null));
        }
        catch(Exception e)
        {
            Log.e("ExterneAllExterneSql", e.toString());
        }

        return externeArray;
    }

    private ArrayList<Externe> cursorTo(Cursor c)
    {
        ArrayList<Externe> externeArray = new ArrayList<>();
        if(c.getCount() != 0)
        {

            for(int i = 0;i<c.getCount();i++)
            {
                c.moveToPosition(i);

                externeArray.add(new Externe(c.getInt(NUM_COL_ID_ECOLE),c.getString(NUM_COL_NOM_ECOLE)));
            }

            c.close();
        }

        return externeArray;
    }
}
