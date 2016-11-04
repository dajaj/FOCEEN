package ginfo.foceen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class CentraleActivity extends AppCompatActivity {

    EditText editTextRecherche;
    ListView listViewRecherche;

    private static boolean activiteAFermer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.Initialize();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if(activiteAFermer)
        {
            activiteAFermer = false;
            this.finish();
        }

        this.Initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(CentraleActivity.this, GestionPersonneActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void Initialize()
    {
        setContentView(R.layout.activity_main);

        final Context context = this;
        editTextRecherche = (EditText)findViewById(R.id.editText);
        listViewRecherche = (ListView) findViewById(R.id.listView);

        editTextRecherche.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //Toast.makeText(context, editTextRecherche.getText().toString(), Toast.LENGTH_SHORT).show();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PersonneSql personneSql = new PersonneSql(context);
                ArrayList<Personne> personnes;
                String recherche = editTextRecherche.getText().toString();
                if (recherche.equals(""))
                    personnes = personneSql.getPersonneAll();
                else
                    personnes = personneSql.getPersonneWithNom(editTextRecherche.getText().toString());

                Log.d("CentraleActivity","MaJ listView");
                PersonneAdapter personneAdapter = new PersonneAdapter(context, personnes, AcceuilActivity.getEventEnCours());
                listViewRecherche.setAdapter(personneAdapter);
                Log.i("listViewRecherche",listViewRecherche.toString());
                Log.i("listViewRechercheAdapter",listViewRecherche.getAdapter().toString());
            }
        });

        editTextRecherche.setText("");

        final CentraleActivity t_h_i_s = this;

        listViewRecherche.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                final Personne personne = (Personne) listViewRecherche.getItemAtPosition(position);

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                dlgAlert.setMessage("Voulez-vous valider la présence de : " + personne.getNom() + " " + personne.getPrenom() + " ?");
                dlgAlert.setTitle("Validation");

                dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Presence presence = new Presence(personne.getId(), AcceuilActivity.getEventEnCours().getId());
                        PresenceSql presenceSql = new PresenceSql(context);
                        long create = presenceSql.create(presence);
                        Log.d("CentraleActivity : ", String.valueOf(create));

                        if (create != 0) {
                            if (create == 255)
                                Toast.makeText(context, personne.getNom() + " " + personne.getPrenom() + " deja enregistre", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(context, "Presence de " + personne.getNom() + " " + personne.getPrenom() + " enregistre", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Probleme presence de " + personne.getNom() + " " + personne.getPrenom() + " non enregistre.\nVeuillez recommencer", Toast.LENGTH_SHORT).show();
                        }
                        //editTextRecherche.setText("");
                        t_h_i_s.finish();
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
    }

    public static void fermerActivite(){
        activiteAFermer = true;
    }
}
