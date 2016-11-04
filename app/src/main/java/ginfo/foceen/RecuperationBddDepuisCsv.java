package ginfo.foceen;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import java.io.FileReader;
import java.util.ArrayList;


/**
 * Created by dajaj on 06/10/15.
 */
public class RecuperationBddDepuisCsv {
    private Context context;
    private String fileName;

    private boolean fini;

    public RecuperationBddDepuisCsv(String fichierCsv, Context c) {
        fileName = fichierCsv;
        context=c;
        fini = false;
    }

    public ArrayList<Personne> recuperation() {
        String[] valeurs;
        ArrayList<Personne> retour = new ArrayList<Personne>();
        FileReader fileReader;
        boolean nouvelleEntree = true;

        Log.d("RecupBddDepuisCsv","Debut récup");
        try{
            fileReader = new FileReader(fileName);
            while(nouvelleEntree){
                valeurs = readNext(fileReader);
                if (valeurs == null){
                    nouvelleEntree = false;
                }
                else{
                    retour.add(new Personne(valeurs[0],valeurs[1],valeurs[2]));
                }
            }
            fileReader.close();
        }
        catch (Exception e){
            Log.d("RecupBDDdeCSV","Problème d'ouverture de " + fileName + "!");
        }
        Log.d("RecupBddDepuisCsv","Fin récup");

        return retour;
    }

    private String[] readNext(FileReader fr){
        Log.d("RecupBddDepuisCsv","Debut récup 1 personne");
        String[] retour = {"","",""};//nom,prénom,promo
        int pos=-1;
        char separateur = ';';
        char textDelimiter = '\n';
        try {
            int i = fr.read();
            while (i != -1 && i != (int)textDelimiter){
                if (pos==-1)pos=0;
                if(i == (int)separateur)
                    pos += 1;
                else
                    retour[pos] += (char)i;
                Log.d("RecupBddDepuisCsv", retour[pos]);
                i = fr.read();
            }
        }catch (Exception e){
            Log.d("RecupBDDdeCSV","Problème de lecture de "+ fileName + "!");
        }
        if(pos==-1)retour=null;
        Log.d("RecupBddDepuisCsv","Fin récup 1 personne");
        return retour;
    }

    public void enregistrement(ArrayList<Personne> persos){
        PersonneSql personneSql = new PersonneSql(context);
        for (Personne p:persos) personneSql.create(p);
        fini = true;
    }

    public void recuperationPuisEnregistrement(){
        enregistrement(recuperation());
    }

    public boolean isFini(){
        return fini;
    }
}
