package ginfo.foceen;

/**
 * Created by pierre on 21/09/15.
 */
public class Presence
{
    private int idPersonne;
    private int idEvent;

    public Presence()
    {

    }

    public Presence(int iP, int iE)
    {
        idPersonne = iP;
        idEvent = iE;
    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    @Override
    public String toString() {
        return "Presence{" +
                "idPersonne=" + idPersonne +
                ", idEvent=" + idEvent +
                '}';
    }
}
