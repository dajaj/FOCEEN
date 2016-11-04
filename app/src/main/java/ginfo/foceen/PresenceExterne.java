package ginfo.foceen;

/**
 * Created by pierre on 22/09/15.
 */
public class PresenceExterne
{
    private int idEcole;
    private int idEvent;
    private int nb;

    public PresenceExterne()
    {

    }

    public PresenceExterne(int iEc, int iEv, int n)
    {
        idEcole = iEc;
        idEvent = iEv;
        nb = n;
    }

    public int getIdEcole() {
        return idEcole;
    }

    public void setIdEcole(int idEcole) {
        this.idEcole = idEcole;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    @Override
    public String toString() {
        return "PresenceExterne{" +
                "idEcole=" + idEcole +
                ", idEvent=" + idEvent +
                ", nb=" + nb +
                '}';
    }
}
