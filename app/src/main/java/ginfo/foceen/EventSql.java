package ginfo.foceen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by pierre on 21/09/15.
 */
public class EventSql extends SqlAbstract
{
    private static final String TABLE_EVENT = DefinitionSql.getTableEvent();
    private static final String COL_ID = DefinitionSql.getColIdEvent();
    private static final String COL_NOM = DefinitionSql.getColNomEvent();
    private static final String COL_DATE = DefinitionSql.getColDateEvent();
    private static final int NUM_COL_ID = DefinitionSql.getNumColIdEvent();
    private static final int NUM_COL_NOM = DefinitionSql.getNumColNomEvent();
    private static final int NUM_COL_DATE = DefinitionSql.getNumColDateEvent();

    public EventSql(Context context)
    {
        super(context);
    }

    public long create(Event event)
    {
        super.open();
        ContentValues values = new ContentValues();

        values.put(COL_NOM, event.getNom());
        values.put(COL_DATE, event.getDate());

        long retour = 255;
        try {
            retour = super.getBDD().insert(TABLE_EVENT, null, values);
        }
        catch(Exception e)
        {
            Log.e("EventSql", e.toString());
        }
            this.close();

        return retour;
    }

    public int update(int id, Event event)
    {
        this.open();
        ContentValues values = new ContentValues();

        values.put(COL_NOM, event.getNom());
        values.put(COL_DATE, event.getDate());

        int retour = 255;
        try {
            retour = super.getBDD().update(TABLE_EVENT, values, COL_ID + " = " + id, null);
        }
        catch (Exception e)
        {
            Log.e("EventSql", e.toString());
        }
        this.close();

        return retour;
    }

    public int removeWithID(int id)
    {
        this.open();
        int delete = 255;
        try {
            delete = super.getBDD().delete(TABLE_EVENT, COL_ID + " = " + id, null);
        }
        catch(Exception e)
        {
            Log.e("EventSql", e.toString());
        }
            this.close();

        return delete;

    }

    public int removeAll()
    {
        this.open();
        int delete = 255;
        try {
            delete = super.getBDD().delete(TABLE_EVENT, "1", null);
        }
        catch(Exception e)
        {
            Log.e("EventSql", e.toString());
        }
        this.close();

        return delete;

    }

    public ArrayList<Event> getEventWithNom(String nom)
    {
        this.open();
        ArrayList<Event> events = null;
        try
        {
            Cursor c = super.getBDD().query(TABLE_EVENT, new String[]{COL_ID, COL_NOM, COL_DATE}, COL_NOM + " LIKE \"" + nom + "\"", null, null, null, null);
            events = cursorTo(c);
        }
        catch(Exception e)
        {
            Log.e("EventSql",e.toString());
        }

        this.close();
        return events;
    }

    public ArrayList<Event> getEventAll()
    {
        this.open();
        ArrayList<Event> eventArray = new ArrayList<>();
        try
        {
            eventArray = cursorTo(super.getBDD().rawQuery("SELECT * FROM " + TABLE_EVENT,null));
        }
        catch(Exception e)
        {
            Log.e("EventAllEventSql", e.toString());
        }
        this.close();

        return eventArray;
    }

    private ArrayList<Event> cursorTo(Cursor c)
    {
        ArrayList<Event> eventArray = new ArrayList<>();
        if(c.getCount() != 0)
        {
            for(int i = 0;i<c.getCount();i++)
            {
                c.moveToPosition(i);

                eventArray.add(new Event(c.getInt(NUM_COL_ID),c.getString(NUM_COL_NOM),c.getString(NUM_COL_DATE)));
            }

            c.close();
        }

        return eventArray;
    }
}
