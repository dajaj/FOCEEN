package ginfo.foceen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by pierre on 21/09/15.
 */

public class PersonneSql extends SqlAbstract
{
    private static final String TABLE_PERSONNE = DefinitionSql.getTablePersonne();
    private static final String COL_ID = DefinitionSql.getColIdPersonne();
    private static final String COL_NOM = DefinitionSql.getColNomPersonne();
    private static final String COL_PRENOM = DefinitionSql.getColPrenomPersonne();
    private static final String COL_PROMO = DefinitionSql.getColPromoPersonne();
    private static final int NUM_COL_ID = DefinitionSql.getNumColIdPersonne();
    private static final int NUM_COL_NOM = DefinitionSql.getNumColNomPersonne();
    private static final int NUM_COL_PRENOM = DefinitionSql.getNumColPrenomPersonne();
    private static final int NUM_COL_PROMO = DefinitionSql.getNumColPromoPersonne();

    public PersonneSql(Context context)
    {
        super(context);
        super.open();
    }

    public long create(Personne personne)
    {
        long retour = 255;
        try {
            ContentValues values = new ContentValues();

            values.put(COL_NOM, personne.getNom());
            values.put(COL_PRENOM, personne.getPrenom());
            values.put(COL_PROMO, personne.getDateNaissance());

            retour = super.getBDD().insert(TABLE_PERSONNE, null, values);
        }
        catch (Exception e)
        {
            Log.e("create PersonneSql : ", e.toString());
        }

        return retour;
    }

    public int update(int id, Personne personne)
    {
        int retour = 255;
        try
        {
            ContentValues values = new ContentValues();

            values.put(COL_NOM, personne.getNom());
            values.put(COL_PRENOM, personne.getPrenom());
            values.put(COL_PROMO, personne.getDateNaissance());

            retour = super.getBDD().update(TABLE_PERSONNE, values, COL_ID + " = " + id, null);
        }
        catch (Exception e)
        {
            Log.e("update PersonneSql : ", e.toString());
        }

        return retour;
    }

    public int removeWithID(int id)
    {
        int retour = 255;
        try
        {
            retour = super.getBDD().delete(TABLE_PERSONNE, COL_ID + " = " + id, null);
        }
        catch (Exception e)
        {
            Log.e("removeWithIDPersonneSql", e.toString());
        }

        return retour;
    }

    public int removeAll()
    {
        int retour = 255;
        try
        {
            retour = super.getBDD().delete(TABLE_PERSONNE, "1",null);
        }
        catch (Exception e)
        {
            Log.e("removeAllPersonne", e.toString());
        }
        return retour;
    }

    public ArrayList<Personne> getPersonneWithNom(String nom)
    {
        ArrayList<Personne> personneArray = new ArrayList<>();
        try
        {
            personneArray = cursorTo(super.getBDD().query(TABLE_PERSONNE, new String[]{COL_ID, COL_NOM, COL_PRENOM, COL_PROMO}, COL_NOM + " LIKE \"%" + nom + "%\" OR " + COL_PRENOM + " LIKE \"%" + nom + "%\"", null, null, null, null));
        }
        catch (Exception e)
        {
            Log.e("PersonneWithPersonneSql", e.toString());
        }

        return personneArray;
    }

    public ArrayList<Personne> getPersonneAll()
    {
        ArrayList<Personne> personneArray = new ArrayList<>();
        try
        {
            personneArray = cursorTo(super.getBDD().rawQuery("SELECT * FROM " + TABLE_PERSONNE,null));
        }
        catch(Exception e)
        {
            Log.e("PersonneAllPersonneSql", e.toString());
        }

        return personneArray;
    }

    private ArrayList<Personne> cursorTo(Cursor c)
    {
        ArrayList<Personne> personneArray = new ArrayList<>();
        if(c.getCount() != 0)
        {

            for(int i = 0;i<c.getCount();i++)
            {
                c.moveToPosition(i);

                personneArray.add(new Personne(c.getInt(NUM_COL_ID),c.getString(NUM_COL_NOM),c.getString(NUM_COL_PRENOM), c.getString(NUM_COL_PROMO)));
            }

            c.close();
        }

        return personneArray;
    }
}
