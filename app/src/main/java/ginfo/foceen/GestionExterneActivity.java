package ginfo.foceen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dajaj on 24/09/15.
 */
public class GestionExterneActivity extends AppCompatActivity
{
    private Button buttonCreerExterne = null;
    private EditText nom = null;
    private EditText prenom = null;
    private EditText date = null;
    private EditText rechercheExterne = null;
    private ListView listViewExterne = null;
    private Button buttonModifier = null;
    private Button buttonSupprimer = null;
    private Button buttonImporter = null;
    private boolean modificationEtNonCreation = false;
    private boolean selectionEnCours = false;
    private static boolean rechercheEnCours = false;

    private TextView externeSelectionneeAffichage = null;
    private Externe externeSelectionnee = null;

    private List<Externe> listExterneAffichage = null;
    private ExterneAdapter adapterList = null;

    private static String rechercheTexte = null;
    private static int positionCurseurRecherche = 0;
    private static boolean reinitList = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_personne_2);
        RelativeLayout rel = (RelativeLayout)findViewById(R.id.relativeCreationPersonne);
        rel.setBackground(getResources().getDrawable(R.drawable.partenaires));
        final ExterneSql externeSql = new ExterneSql(this);
        final Context context = this;

        modificationEtNonCreation = false;
        selectionEnCours = false;

        buttonCreerExterne = (Button)findViewById(R.id.buttonCreerPersonne);
        nom = (EditText)findViewById(R.id.editTextNom);
        prenom = (EditText)findViewById(R.id.editTextPrenom);
        date = (EditText)findViewById(R.id.editTextDateNaissance);
        rechercheExterne = (EditText)findViewById(R.id.editTextRecherchePersonnePourSupprimer);
        listViewExterne = (ListView)findViewById(R.id.listViewPersonnePourSupprimer);
        buttonModifier = (Button)findViewById(R.id.buttonModifierPersonne);
        buttonSupprimer = (Button)findViewById(R.id.buttonSupprimerPersonne);
        buttonImporter = (Button)findViewById(R.id.buttonImporterListe);
        externeSelectionneeAffichage = (TextView)findViewById(R.id.textViewPersonneSelectionnee);

        if(listExterneAffichage==null)listExterneAffichage = externeSql.getExterneAll();

        //remplirBase(externeSql);

        adapterList = new ExterneAdapter(this,listExterneAffichage);

        listViewExterne.setAdapter(adapterList);
        listViewExterne.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listViewExterne.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Click", "List@" + position);
                selectionEnCours = true;
                externeSelectionnee = (Externe) listViewExterne.getItemAtPosition(position);
                externeSelectionneeAffichage.setText(externeSelectionnee.getNomEcole());
            }
        });

        Log.d("GestionExterneActivity", "Built until setOnItemClickListener");

        buttonCreerExterne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Click", "Creer");
                if (!nom.getText().toString().equals("")) {
                    if (modificationEtNonCreation) {
                        externeSql.update(externeSelectionnee.getIdEcole(), new Externe(nom.getText().toString()));
                        modificationEtNonCreation = false;
                        buttonCreerExterne.setText("Créer");
                        buttonModifier.setText("Modifier");
                        buttonSupprimer.setVisibility(View.VISIBLE);
                        nom.setText("");
                        Toast.makeText(context, "Ecole modifiée avec succès", Toast.LENGTH_LONG).show();
                        Log.d("Modifier école : ", "Ecole modifiée");
                    } else {
                        Externe externe = new Externe(nom.getText().toString());
                        externeSql.create(externe);
                        Externe externe2 = externeSql.getExterneWithNom(externe.getNomEcole()).get(0);
                        nom.setText("");

                        PresenceExterne presenceExterne = new PresenceExterne(externe2.getIdEcole(),AcceuilActivity.getEventEnCours().getId(),0);
                        PresenceExterneSql presenceExterneSql = new PresenceExterneSql(context);
                        int create = presenceExterneSql.createOrUpdate(presenceExterne);

                        Toast.makeText(context, "Ecole créée avec succès", Toast.LENGTH_LONG).show();
                        Log.d("Créer école : ", "Ecole créée");
                    }
                    listExterneAffichage = externeSql.getExterneAll();
                    miseAJourDeLaListe();
                } else {
                    Toast.makeText(context, "Veuillez saisir un nom", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonModifier.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Log.d("Click", "Modifier");
                if(modificationEtNonCreation){
                    modificationEtNonCreation = false;
                    buttonCreerExterne.setText("Créer");
                    buttonModifier.setText("Modifier");
                    buttonSupprimer.setVisibility(View.VISIBLE);
                    nom.setText("");
                    Toast.makeText(context, "Modifications annulées", Toast.LENGTH_LONG).show();
                }else{
                    if(selectionEnCours){
                        modificationEtNonCreation = true;
                        buttonCreerExterne.setText("Valider les modifications");
                        buttonModifier.setText("Annuler les modifications");
                        buttonSupprimer.setVisibility(View.INVISIBLE);
                        nom.setText(externeSelectionnee.getNomEcole());
                        Toast.makeText(context, "Vous pouvez modifier cet évènement", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(context, "Veuillez sélectionner un évènement", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        buttonSupprimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Click", "Supprimer");
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                dlgAlert.setMessage("Voulez-vous valider la suppression de : " + externeSelectionnee.getNomEcole() + " ?");
                dlgAlert.setTitle("Validation");

                dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (selectionEnCours) {
                            PresenceExterneSql presenceExterneSql = new PresenceExterneSql(context);
                            presenceExterneSql.removeWithIdEcole(externeSelectionnee.getIdEcole());
                            externeSql.removeWithID(externeSelectionnee.getIdEcole());
                            Toast.makeText(context, "Ecole supprimée avec succès", Toast.LENGTH_LONG).show();
                            Log.d("Supprimer école : ", "Ecole supprimée");
                            listExterneAffichage = externeSql.getExterneAll();
                            miseAJourDeLaListe();
                        } else {
                            Toast.makeText(context, "Veuillez sélectionner une école", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dlgAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                dlgAlert.create().show();
            }
        });

        rechercheExterne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<Externe> externes;
                String recherche = rechercheExterne.getText().toString();
                if (recherche.equals(""))
                    externes = externeSql.getExterneAll();
                else
                    externes = externeSql.getExterneWithNom(rechercheExterne.getText().toString());
                Log.d("GestionExterneActivity", externes.toString());

                ExterneAdapter externeAdapter = new ExterneAdapter(context, externes, AcceuilActivity.getEventEnCours());
                listViewExterne.setAdapter(externeAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("GestionExterneActivity", "onResume : rechercheEnCours = " + rechercheEnCours);

        nom.setText("");
        prenom.setVisibility(View.INVISIBLE);
        date.setVisibility(View.INVISIBLE);
        buttonImporter.setVisibility(View.INVISIBLE);
        if(rechercheEnCours){
            rechercheExterne.setText(rechercheTexte);
            rechercheExterne.setSelection(positionCurseurRecherche);
        }else{rechercheExterne.setText("");}
        buttonCreerExterne.setText("Créer");
        buttonSupprimer.setText("Supprimer");
        buttonModifier.setText("Modifier");
        externeSelectionneeAffichage.setText("");

        rechercheEnCours=false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action1 = menu.getItem(0);
        MenuItem action2 = menu.getItem(1);
        action1.setTitle(R.string.accueil);
        action2.setTitle(R.string.externe);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_1) {
            ExterneActivity.fermerActivite();
            this.finish();
        }
        if(id == R.id.action_2)
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void miseAJourDeLaListe(){
        Log.d("GestionExterneActivity","MaJ Liste");
        adapterList.notifyDataSetChanged();
        this.recreate();
    }

    private void recherche(ExterneSql externeSql){
        String texte = rechercheExterne.getText().toString();
        if(!texte.equals("") && !rechercheEnCours)
        {
            reinitList=true;
            listExterneAffichage = externeSql.getExterneWithNom(texte);
            Log.d("CreerPersonneactivity","MaJ Liste");
            rechercheEnCours=true;
            rechercheTexte=texte;
            positionCurseurRecherche=texte.length();
            miseAJourDeLaListe();
        } else if(texte.equals("")&&reinitList){
            reinitList=false;
            listExterneAffichage = externeSql.getExterneAll();
            miseAJourDeLaListe();
        }
    }

    private void remplirBase(ExterneSql p){
        if(listExterneAffichage.isEmpty()){
            p.create(new Externe("Centrale Lyon"));
            p.create(new Externe("Centrale Paris"));
            listExterneAffichage = p.getExterneAll();
        }
    }
}
