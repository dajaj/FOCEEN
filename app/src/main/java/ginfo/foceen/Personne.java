package ginfo.foceen;

/**
 * Created by pierre on 21/09/15.
 */
public class Personne
{
    private int id;
    private String nom;
    private String prenom;
    private String dateNaissance;

    public Personne()
    {

    }

    public Personne(int i,String n,String p,String d)
    {
        id = i;
        nom = n;
        prenom = p;
        dateNaissance = d;
    }

    public Personne(String n,String p,String d)
    {
        nom = n;
        prenom = p;
        dateNaissance = d;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                '}';
    }
}
