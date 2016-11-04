package ginfo.foceen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by pierre on 01/10/15.
 */
public class SettingsSql extends SqlAbstract
{
    private static final String TABLE_SETTINGS = DefinitionSql.getTableSettings();
    private static final String COL_NOM_SETTINGS = DefinitionSql.getColNomSettings();
    private static final String COL_VALUE_SETTINGS = DefinitionSql.getColValueSettings();
    private static final int NUM_COL_NOM = DefinitionSql.getNumColNomSettings();
    private static final int NUM_COL_VALUE = DefinitionSql.getNumColValueSettings();

    public SettingsSql(Context context)
    {
        super(context);
        super.open();
    }

    public String getPasswordInBDD()
    {
        Cursor c = super.getBDD().query(TABLE_SETTINGS, new String[]{COL_NOM_SETTINGS, COL_VALUE_SETTINGS}, COL_NOM_SETTINGS + " = 'password'", null, null, null, null);
        ArrayList<String> test = cursorTo(c);
        String retour = "ERREUR";
        if(test.isEmpty() == false)
        {
            retour = test.get(0);
        }

        return retour;
    }

    public int setPasswordInBDD(String password)
    {
        long retour = 255;
        try {
            ContentValues values = new ContentValues();

            if(!getPasswordInBDD().isEmpty() && !getPasswordInBDD().equals("ERREUR"))
            {
                values.put(COL_VALUE_SETTINGS, password);

                retour = super.getBDD().update(TABLE_SETTINGS, values, COL_NOM_SETTINGS + " = 'password'", null);
            }
            else
            {
                values.put(COL_NOM_SETTINGS, "password");
                values.put(COL_VALUE_SETTINGS, password);

                retour = super.getBDD().insert(TABLE_SETTINGS, null, values);
            }
        }
        catch (Exception e)
        {
            Log.e("create PersonneSql : ", e.toString());
        }

        return (int)retour;
    }

    public String getVersionBDD()
    {
        Cursor c = super.getBDD().query(TABLE_SETTINGS, new String[]{COL_NOM_SETTINGS, COL_VALUE_SETTINGS}, COL_NOM_SETTINGS + " = 'version'", null, null, null, null);
        ArrayList<String> test = cursorTo(c);
        String retour = "ERREUR";
        if(!test.isEmpty())
        {
            retour = test.get(1);
        }

        return retour;
    }

    public int changeVersionBDD(String version)
    {
        long retour = 255;
        try {
            ContentValues values = new ContentValues();

            if(!getVersionBDD().isEmpty() && !getVersionBDD().equals("ERREUR"))
            {
                values.put(COL_VALUE_SETTINGS, version);

                retour = super.getBDD().update(TABLE_SETTINGS, values, COL_NOM_SETTINGS + " ='version'", null);
            }
            else
            {
                values.put(COL_NOM_SETTINGS, "'version'");
                values.put(COL_VALUE_SETTINGS, version);

                retour = super.getBDD().insert(TABLE_SETTINGS, null, values);
            }

            super.miseAJourBDD(Integer.parseInt(getVersionBDD()),Integer.parseInt(version));
        }
        catch (Exception e)
        {
            Log.e("create PersonneSql : ", e.toString());
        }

        return (int)retour;
    }

    private ArrayList<String> cursorTo(Cursor c)
    {
        ArrayList<String> settingsArray = new ArrayList<String>();
        if(c.getCount() != 0)
        {

            for(int i = 0;i<c.getCount();i++)
            {
                c.moveToPosition(i);

                settingsArray.add(c.getString(NUM_COL_VALUE));
            }

            c.close();
        }

        return settingsArray;
    }
}
