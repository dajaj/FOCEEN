package ginfo.foceen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dajaj on 24/09/15.
 */
public class GestionEventActivity extends AppCompatActivity
{
    private Button buttonCreerEvent = null;
    private EditText nom = null;
    private EditText prenom = null;
    private EditText date = null;
    private EditText rechercheEvent = null;
    private ListView listViewEvent = null;
    private Button buttonModifier = null;
    private Button buttonSupprimer = null;
    private Button buttonImporter = null;
    private Button buttonSupprimerAll = null;
    private boolean modificationEtNonCreation = false;
    private boolean selectionEnCours = false;
    private static boolean rechercheEnCours = false;

    private TextView eventSelectionneeAffichage = null;
    private Event eventSelectionnee = null;

    private List<Event> listEventAffichage = null;
    private EventAdapter adapterList = null;

    private static String rechercheTexte = null;
    private static int positionCurseurRecherche = 0;
    private static boolean reinitList = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_personne_2);
        RelativeLayout rel = (RelativeLayout)findViewById(R.id.relativeCreationPersonne);
        rel.setBackground(getResources().getDrawable(R.drawable.foceen));

        final EventSql eventSql = new EventSql(this);
        final Context context = this;

        modificationEtNonCreation = false;
        selectionEnCours = false;

        buttonCreerEvent = (Button)findViewById(R.id.buttonCreerPersonne);
        nom = (EditText)findViewById(R.id.editTextNom);
        prenom = (EditText)findViewById(R.id.editTextPrenom);
        date = (EditText)findViewById(R.id.editTextDateNaissance);
        rechercheEvent = (EditText)findViewById(R.id.editTextRecherchePersonnePourSupprimer);
        listViewEvent = (ListView)findViewById(R.id.listViewPersonnePourSupprimer);
        buttonModifier = (Button)findViewById(R.id.buttonModifierPersonne);
        buttonSupprimer = (Button)findViewById(R.id.buttonSupprimerPersonne);
        buttonImporter = (Button)findViewById(R.id.buttonImporterListe);
        buttonSupprimerAll = (Button)findViewById(R.id.buttonSupprimerTous);
        eventSelectionneeAffichage = (TextView)findViewById(R.id.textViewPersonneSelectionnee);

        buttonSupprimerAll.setVisibility(View.VISIBLE);
        
        if(listEventAffichage==null)listEventAffichage = eventSql.getEventAll();

        //remplirBase(eventSql);

        adapterList = new EventAdapter(this,listEventAffichage);

        listViewEvent.setAdapter(adapterList);
        listViewEvent.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listViewEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Click", "List@" + position);
                selectionEnCours = true;
                eventSelectionnee = (Event) listViewEvent.getItemAtPosition(position);
                eventSelectionneeAffichage.setText(eventSelectionnee.getNom() + " : " + eventSelectionnee.getDate());
            }
        });

        Log.d("GestionEventActivity", "Built until setOnItemClickListener");

        buttonCreerEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Click", "Creer");
                if (!nom.getText().toString().equals("") && !date.getText().toString().equals("")) {
                    if (modificationEtNonCreation) {
                        eventSql.update(eventSelectionnee.getId(), new Event(nom.getText().toString(), date.getText().toString()));
                        modificationEtNonCreation = false;
                        buttonCreerEvent.setText("Créer");
                        buttonModifier.setText("Modifier");
                        buttonSupprimer.setVisibility(View.VISIBLE);
                        nom.setText("");
                        date.setText("");
                        Toast.makeText(context, "Evènement modifié avec succès", Toast.LENGTH_LONG).show();
                        Log.d("Modifier évènement : ", "Evènement modifié");
                    } else {
                        eventSql.create(new Event(nom.getText().toString(), date.getText().toString()));
                        nom.setText("");
                        date.setText("");
                        Toast.makeText(context, "Evènement créé avec succès", Toast.LENGTH_LONG).show();
                        Log.d("Créer évènement : ", "Evènement créé");
                    }
                    listEventAffichage = eventSql.getEventAll();
                    miseAJourDeLaListe();
                } else {
                    Toast.makeText(context, "Un des champs au moins est vide", Toast.LENGTH_LONG).show();
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
                    buttonCreerEvent.setText("Créer");
                    buttonModifier.setText("Modifier");
                    buttonSupprimer.setVisibility(View.VISIBLE);
                    nom.setText("");
                    date.setText("");
                    Toast.makeText(context, "Modifications annulées", Toast.LENGTH_LONG).show();
                }else{
                    if(selectionEnCours){
                        modificationEtNonCreation = true;
                        buttonCreerEvent.setText("Valider les modifications");
                        buttonModifier.setText("Annuler les modifications");
                        buttonSupprimer.setVisibility(View.INVISIBLE);
                        nom.setText(eventSelectionnee.getNom());
                        date.setText(eventSelectionnee.getDate());
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
                dlgAlert.setMessage("Voulez-vous valider la suppression de : " + eventSelectionnee.getNom() + " ?");
                dlgAlert.setTitle("Validation");

                dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (selectionEnCours) {
                            PresenceExterneSql presenceExterneSql = new PresenceExterneSql(context);
                            PresenceSql presenceSql = new PresenceSql(context);
                            presenceExterneSql.removeWithIdEvent(eventSelectionnee.getId());
                            presenceSql.removeWithIdEvent(eventSelectionnee.getId());
                            eventSql.removeWithID(eventSelectionnee.getId());
                            Toast.makeText(context, "Evènement supprimé avec succès", Toast.LENGTH_LONG).show();
                            Log.d("Supprimer évènement : ", "Evènement supprimé");
                            listEventAffichage = eventSql.getEventAll();
                            miseAJourDeLaListe();
                        } else {
                            Toast.makeText(context, "Veuillez sélectionner un évènement", Toast.LENGTH_LONG).show();
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

        buttonSupprimerAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("Click", "Supprimer Tous");
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("Voulez-vous valider la suppression de toutes les personnes, tous les événements et tous les externes ?");
                dlgAlert.setTitle("Validation");

                dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PresenceSql presenceSql = new PresenceSql(context);
                        presenceSql.removeAll();
                        PersonneSql personneSql = new PersonneSql(context);
                        personneSql.removeAll();
                        ExterneSql externeSql = new ExterneSql(context);
                        externeSql.removeAll();

                        eventSql.removeAll();
                        Toast.makeText(context, "Tout a été supprimé correctement...mouahahah je ne blague pas", Toast.LENGTH_LONG).show();
                        Log.d("Supprimer personnes : ", "Personnes supprimées");
                        miseAJourDeLaListe();
                    }
                });
                dlgAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dlgAlert.create().show();
            }
        });
        rechercheEvent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<Event> events;
                String recherche = rechercheEvent.getText().toString();
                if (recherche.equals(""))
                    events = eventSql.getEventAll();
                else
                    events = eventSql.getEventWithNom(rechercheEvent.getText().toString());

                EventAdapter eventAdapter = new EventAdapter(context, events);
                listViewEvent.setAdapter(eventAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("GestionEventActivity","onResume : rechercheEnCours = "+rechercheEnCours);

        nom.setText("");
        prenom.setVisibility(View.INVISIBLE);
        date.setText("");
        if(rechercheEnCours){
            rechercheEvent.setText(rechercheTexte);
            rechercheEvent.setSelection(positionCurseurRecherche);
        }else{rechercheEvent.setText("");};
        buttonCreerEvent.setText("Créer");
        buttonSupprimer.setText("Supprimer");
        buttonModifier.setText("Modifier");
        eventSelectionneeAffichage.setText("");

        rechercheEnCours=false;

        date.setHint(R.string.date);
        buttonImporter.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action1 = menu.getItem(0);
        MenuItem action2 = menu.getItem(1);
        action1.setTitle(R.string.accueil);
        action2.setVisible(false);
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
            this.finish();
        }
        if(id == R.id.action_2)
        {
            //ne rien faire, ce bouton n'existe pas
        }

        return super.onOptionsItemSelected(item);
    }

    private void miseAJourDeLaListe(){
        Log.d("GestionEventActivity","MaJ Liste");
        adapterList.notifyDataSetChanged();
        this.recreate();
    }

    private void recherche(EventSql eventSql){
        String texte = rechercheEvent.getText().toString();
        if(!texte.equals("") && !rechercheEnCours)
        {
            reinitList=true;
            listEventAffichage = eventSql.getEventWithNom(texte);
            Log.d("CreerPersonneactivity","MaJ Liste");
            rechercheEnCours=true;
            rechercheTexte=texte;
            positionCurseurRecherche=texte.length();
            miseAJourDeLaListe();
        } else if(texte.equals("")&&reinitList){
            reinitList=false;
            listEventAffichage = eventSql.getEventAll();
            miseAJourDeLaListe();
        }
    }

    private void remplirBase(EventSql p){
        if(listEventAffichage.isEmpty()){
            p.create(new Event("Foceen", "16/10/1993"));
            p.create(new Event("Japan Expo", "18/11/1991"));
            listEventAffichage = p.getEventAll();
        }
    }
}
