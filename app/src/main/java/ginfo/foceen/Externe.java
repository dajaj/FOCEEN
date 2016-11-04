package ginfo.foceen;

/**
 * Created by pierre on 22/09/15.
 */
public class Externe
{


    private int idEcole;
    private String nomEcole;

    public Externe()
    {

    }

    public Externe(int i,String n)
    {
        idEcole = i;
        nomEcole = n;
    }

    public Externe(String n)
    {
        nomEcole = n;
    }

    public int getIdEcole() {
        return idEcole;
    }

    public void setIdEcole(int idEcole) {
        this.idEcole = idEcole;
    }

    public String getNomEcole() {
        return nomEcole;
    }

    public void setNomEcole(String nomEcole) {
        this.nomEcole = nomEcole;
    }

    @Override
    public String toString() {
        return "Externe{" +
                "idEcole=" + idEcole +
                ", nomEcole='" + nomEcole + '\'' +
                '}';
    }
}
