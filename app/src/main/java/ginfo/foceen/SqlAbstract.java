package ginfo.foceen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import java.io.File;

/**
 * Created by pierre on 21/09/15.
 */
public class SqlAbstract
{
    private static final int VERSION_BDD = 1;
    private static final String PATH_BDD = "/data/data/ginfo.foceen/";
    private static final String NOM_BDD = "foceen.db";

    private static SQLiteDatabase bdd=null;
    private SqlAccess maBaseSQLite;

    private static Context myContext;

    public SqlAbstract(Context context)
    {
        maBaseSQLite = new SqlAccess(context, NOM_BDD, null, VERSION_BDD);
        this.myContext = context;
    }

    public boolean verifierExistenceBDD()
    {
        File dbFile = myContext.getDatabasePath(PATH_BDD + NOM_BDD);
        return dbFile.exists();
    }

    public void open()
    {
        if(bdd==null)
        {
            try{
                File dbFile = myContext.getDatabasePath(PATH_BDD + NOM_BDD);
                if(verifierExistenceBDD() == true)
                {
                    Log.d("Coucou ici : ", "Le fichier existe");
                    bdd = SQLiteDatabase.openDatabase(dbFile.toString(), null, SQLiteDatabase.OPEN_READWRITE);
                    Log.d("Coucou ici : ","La base c'est bien ouverte");
                }
                else
                {
                    bdd = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
                    maBaseSQLite.onCreate(bdd);
                    this.close();
                    Log.e("Coucou ici : ", "La base n'existe pas création de la base");
                    this.open();
                }

            } catch (Exception e)
            {
                Log.e("Coucou ici : ", e.toString());
            }
        }
        else
        {
            //Log.d("Coucou ici : ","La base existe déjà "+bdd.toString());
        }
    }
    public void miseAJourBDD(int oldVersion, int newVersion)
    {
        maBaseSQLite.onUpgrade(bdd,oldVersion,newVersion);
    }

    public void close()
    {
        bdd.close();
        bdd=null;
    }

    public SQLiteDatabase getBDD()
    {
        return bdd;
    }
}
