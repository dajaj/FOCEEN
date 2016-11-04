package ginfo.foceen;

/**
 * Created by pierre on 21/09/15.
 */
public class Event
{
    private int id;
    private String nom;
    private String date;

    public Event()
    {

    }

    public Event(String n, String d){
        nom = n;
        date = d;
    }

    public Event(int i,String n,String d)
    {
        id = i;
        nom = n;
        date = d;
    }

    public Event(Event e)
    {
        this.id = e.getId();
        this.nom = e.getNom();
        this.date = e.getDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

}
