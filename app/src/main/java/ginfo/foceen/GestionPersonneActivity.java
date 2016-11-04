package ginfo.foceen;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierre on 22/09/15.
 */
public class GestionPersonneActivity extends AppCompatActivity {
    private Button buttonCreerPersonne = null;
    private EditText nom = null;
    private EditText prenom = null;
    private EditText dateNaissance = null;
    private EditText recherchePersonne = null;
    private ListView listViewPersonne = null;
    private Button buttonModifier = null;
    private Button buttonSupprimer = null;
    private Button buttonSupprimerTous = null;
    private Button buttonImporter = null;
    private boolean modificationEtNonCreation = false;
    private boolean selectionEnCours = false;
    private static boolean rechercheEnCours = false;
    private static final int REQUEST_PATH = 1;

    private TextView personneSelectionneeAffichage = null;
    private Personne personneSelectionnee = null;

    private static List<Personne> listPersonneAffichage = null;
    private PersonneAdapter adapterList = null;

    private static String rechercheTexte = null;
    private static int positionCurseurRecherche = 0;
    private static boolean reinitList = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_personne_2);

        final PersonneSql personneSql = new PersonneSql(this);
        final Context context = this;

        modificationEtNonCreation = false;
        selectionEnCours = false;

        buttonCreerPersonne = (Button) findViewById(R.id.buttonCreerPersonne);
        nom = (EditText) findViewById(R.id.editTextNom);
        prenom = (EditText) findViewById(R.id.editTextPrenom);
        dateNaissance = (EditText) findViewById(R.id.editTextDateNaissance);
        recherchePersonne = (EditText) findViewById(R.id.editTextRecherchePersonnePourSupprimer);
        listViewPersonne = (ListView) findViewById(R.id.listViewPersonnePourSupprimer);
        buttonModifier = (Button) findViewById(R.id.buttonModifierPersonne);
        buttonSupprimer = (Button) findViewById(R.id.buttonSupprimerPersonne);
        buttonSupprimerTous = (Button) findViewById(R.id.buttonSupprimerTous);
        buttonImporter = (Button) findViewById(R.id.buttonImporterListe);
        personneSelectionneeAffichage = (TextView) findViewById(R.id.textViewPersonneSelectionnee);

        if (listPersonneAffichage == null) listPersonneAffichage = personneSql.getPersonneAll();

        //remplirBase(personneSql);

        adapterList = new PersonneAdapter(this, listPersonneAffichage);

        listViewPersonne.setAdapter(adapterList);
        listViewPersonne.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listViewPersonne.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Click", "List@" + position);
                selectionEnCours = true;
                personneSelectionnee = (Personne) listViewPersonne.getItemAtPosition(position);
                personneSelectionneeAffichage.setText(personneSelectionnee.getPrenom() + " " + personneSelectionnee.getNom() + " : " + personneSelectionnee.getDateNaissance());
            }
        });

        Log.d("GestionPersonneActivity", "Built until setOnItemClickListener");

        buttonCreerPersonne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Click", "Creer");
                if (!nom.getText().toString().equals("") && !prenom.getText().toString().equals("") && !dateNaissance.getText().toString().equals("")) {
                    if (modificationEtNonCreation) {
                        personneSql.update(personneSelectionnee.getId(), new Personne(nom.getText().toString(), prenom.getText().toString(), dateNaissance.getText().toString()));
                        modificationEtNonCreation = false;
                        buttonCreerPersonne.setText("Créer");
                        buttonModifier.setText("Modifier");
                        buttonSupprimer.setVisibility(View.VISIBLE);
                        nom.setText("");
                        prenom.setText("");
                        dateNaissance.setText("");
                        Toast.makeText(context, "Personne modifiée avec succès", Toast.LENGTH_LONG).show();
                        Log.d("Modifier personne : ", "Personne modifiée");
                    } else {
                        personneSql.create(new Personne(nom.getText().toString(), prenom.getText().toString(), dateNaissance.getText().toString()));
                        nom.setText("");
                        prenom.setText("");
                        dateNaissance.setText("");
                        Toast.makeText(context, "Personne créée avec succès", Toast.LENGTH_LONG).show();
                        Log.d("Créer personne : ", "Personne créée");
                    }
                    listPersonneAffichage = personneSql.getPersonneAll();
                    miseAJourDeLaListe();
                } else {
                    Toast.makeText(context, "Un des champs au moins est vide", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonModifier.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Click", "Modifier");
                if (modificationEtNonCreation) {
                    modificationEtNonCreation = false;
                    buttonCreerPersonne.setText("Créer");
                    buttonModifier.setText("Modifier");
                    buttonSupprimer.setVisibility(View.VISIBLE);
                    buttonImporter.setVisibility(View.VISIBLE);
                    nom.setText("");
                    prenom.setText("");
                    dateNaissance.setText("");
                    Toast.makeText(context, "Modifications annulées", Toast.LENGTH_LONG).show();
                } else {
                    if (selectionEnCours) {
                        modificationEtNonCreation = true;
                        buttonCreerPersonne.setText("Valider les modifications");
                        buttonModifier.setText("Annuler les modifications");
                        buttonSupprimer.setVisibility(View.INVISIBLE);
                        buttonImporter.setVisibility(View.INVISIBLE);
                        nom.setText(personneSelectionnee.getNom());
                        prenom.setText(personneSelectionnee.getPrenom());
                        dateNaissance.setText(personneSelectionnee.getDateNaissance());
                        Toast.makeText(context, "Vous pouvez modifier cette personne", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Veuillez sélectionner quelqu'un", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        buttonSupprimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Click", "Supprimer");
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("Voulez-vous valider la suppression de : " + personneSelectionnee.getNom() + " " + personneSelectionnee.getPrenom() + " ?");
                dlgAlert.setTitle("Validation");

                dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (selectionEnCours) {
                            PresenceSql presenceSql = new PresenceSql(context);
                            presenceSql.removeWithIdPersonne(personneSelectionnee.getId());
                            personneSql.removeWithID(personneSelectionnee.getId());
                            Toast.makeText(context, "Personne supprimée avec succès", Toast.LENGTH_LONG).show();
                            Log.d("Supprimer personne : ", "Personne supprimée");
                            listPersonneAffichage = personneSql.getPersonneAll();
                            miseAJourDeLaListe();
                        } else {
                            Toast.makeText(context, "Veuillez sélectionner quelqu'un", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dlgAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dlgAlert.create().show();
            }
        });

        buttonImporter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*String path = "/sdcard/foceen";
                File repertoire = new File(path);
                File[] files=repertoire.listFiles();
                for(File f:files)
                {
                    RecuperationBddDepuisCsv recuperationBddDepuisCsv = new RecuperationBddDepuisCsv(path + f.toString(),context);
                    recuperationBddDepuisCsv.recuperationPuisEnregistrement();
                }*/
                try {
                    Intent intent = new Intent(GestionPersonneActivity.this, FileBrowserActivity.class);
                    startActivityForResult(intent, REQUEST_PATH);
                } catch (Exception ex) {
                    Log.e("GestionPersonne", "Erreur : " + ex.toString());
                }
            }
        });

        recherchePersonne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<Personne> personnes;
                String recherche = recherchePersonne.getText().toString();
                if (recherche.equals(""))
                    personnes = personneSql.getPersonneAll();
                else
                    personnes = personneSql.getPersonneWithNom(recherchePersonne.getText().toString());

                PersonneAdapter personneAdapter = new PersonneAdapter(context, personnes, AcceuilActivity.getEventEnCours());
                listViewPersonne.setAdapter(personneAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonSupprimerTous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("Click", "Supprimer Tous");
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("Voulez-vous valider la suppression de toutes les personnes ?");
                dlgAlert.setTitle("Validation");

                dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                            PresenceSql presenceSql = new PresenceSql(context);
                            presenceSql.removeAll();
                            personneSql.removeAll();
                            Toast.makeText(context, "Personnes supprimées avec succès", Toast.LENGTH_LONG).show();
                            Log.d("Supprimer personnes : ", "Personnes supprimées");
                            listPersonneAffichage = personneSql.getPersonneAll();
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
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("GestionPersonneActivity", "onResume : rechercheEnCours = " + rechercheEnCours);

        nom.setText("");
        prenom.setText("");
        dateNaissance.setText("");
        if (rechercheEnCours) {
            recherchePersonne.setText(rechercheTexte);
            recherchePersonne.setSelection(positionCurseurRecherche);
        } else {
            recherchePersonne.setText("");
        }
        ;
        buttonCreerPersonne.setText("Créer");
        buttonSupprimer.setText("Supprimer");
        buttonModifier.setText("Modifier");
        personneSelectionneeAffichage.setText("");

        rechercheEnCours = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action1 = menu.getItem(0);
        MenuItem action2 = menu.getItem(1);
        action1.setTitle(R.string.accueil);
        action2.setTitle(R.string.centrale);
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
            CentraleActivity.fermerActivite();
            this.finish();
        }
        if (id == R.id.action_2) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void miseAJourDeLaListe() {
        Log.d("GestionPersonneActivity", "MaJ Liste");
        adapterList.notifyDataSetChanged();
        this.recreate();
    }

    private void recherche(PersonneSql personneSql) {
        String texte = recherchePersonne.getText().toString();
        if (!texte.equals("") && !rechercheEnCours) {
            reinitList = true;
            listPersonneAffichage = personneSql.getPersonneWithNom(texte);
            rechercheEnCours = true;
            rechercheTexte = texte;
            positionCurseurRecherche = texte.length();
            miseAJourDeLaListe();
        } else if (texte.equals("") && reinitList) {
            reinitList = false;
            listPersonneAffichage = personneSql.getPersonneAll();
            miseAJourDeLaListe();
        }
    }

    private void remplirBase(PersonneSql p) {
        if (listPersonneAffichage.isEmpty()) {
            p.create(new Personne("Dousselin", "Aurele", "16/10/1993"));
            p.create(new Personne("Delmas", "Pierre", "18/11/1991"));
            listPersonneAffichage = p.getPersonneAll();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH) {
            if (resultCode == RESULT_OK) {
                String completePath = data.getStringExtra("GetPath") + "//";
                String fileName = data.getStringExtra("GetFileName");
                this.Traitement(completePath,fileName);
                miseAJourDeLaListe();
            }
        }
    }

    private void Traitement(String completePath, String fileName)
    {
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        final Context context = this;
        final String path = completePath;
        final String curFileName = fileName;
        final RecuperationBddDepuisCsv recuperationBddDepuisCsv = new RecuperationBddDepuisCsv(path + curFileName,context);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Import en cours de " + curFileName);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Test", "Test");
                recuperationBddDepuisCsv.recuperationPuisEnregistrement();
                progressDialog.dismiss();
            }
        }).start();

        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
