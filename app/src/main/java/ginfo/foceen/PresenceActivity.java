package ginfo.foceen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by dajaj on 25/09/15.
 */
public class PresenceActivity extends AppCompatActivity {

    private EditText editTextRecherche;
    private ListView listViewRecherche;
    private Spinner spin;

    private static boolean externeEtNonCentrale = false;
    private static Event evenement = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        this.Initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action1 = menu.getItem(0);
        MenuItem action2 = menu.getItem(1);
        action1.setTitle(R.string.retour);
        if(externeEtNonCentrale){action2.setTitle(R.string.presence_centrale);}else{action2.setTitle(R.string.presence_externe);}
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
            if(externeEtNonCentrale){
                item.setTitle(R.string.presence_externe);
                changeToCentrale();
            }else{
                item.setTitle(R.string.presence_centrale);
                changeToExterne();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void Initialize()
    {
        setContentView(R.layout.activity_presence);

        Log.d("PresenceActivity","Initializing");
        final Context context = this;
        editTextRecherche = (EditText)findViewById(R.id.editTextPresence);
        listViewRecherche = (ListView) findViewById(R.id.listViewPresence);
        spin = (Spinner)findViewById(R.id.spinnerPresence);

        if(evenement == null)
            evenement = AcceuilActivity.getEventEnCours();

        EventSql eventSql = new EventSql(this);
        ArrayList<Event> events = eventSql.getEventAll();

        EventAdapter eventAdapter = new EventAdapter(this,events);
        spin.setAdapter(eventAdapter);

        if(evenement != null)
            spin.setSelection(events.indexOf(evenement));

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                evenement = (Event) spin.getSelectedItem();
                editTextRecherche.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        editTextRecherche.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                //Toast.makeText(context, editTextRecherche.getText().toString(), Toast.LENGTH_SHORT).show();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                JointureSql jointureSql = new JointureSql(context);
                String recherche = editTextRecherche.getText().toString();
                if (externeEtNonCentrale) {
                    ArrayList<Externe> externes;
                    if (recherche.equals("")) {
                        externes = jointureSql.getExterneByEvent(evenement);
                    } else {
                        externes = jointureSql.getExterneByEventAndName(evenement, editTextRecherche.getText().toString());
                    }

                    ExterneAdapter externeAdapter = new ExterneAdapter(context, externes, evenement);
                    listViewRecherche.setAdapter(externeAdapter);
                } else {
                    ArrayList<Personne> personnes;
                    if (recherche.equals("")) {
                        personnes = jointureSql.getPersonneByEvent(evenement);
                    } else {
                        personnes = jointureSql.getPersonneByEventAndName(evenement, editTextRecherche.getText().toString());
                    }

                    PersonneAdapter personneAdapter = new PersonneAdapter(context, personnes);
                    listViewRecherche.setAdapter(personneAdapter);
                }

            }
        });

        editTextRecherche.setText("");

        if(externeEtNonCentrale) {
            this.setTitle(R.string.presence_externe);
            Toast.makeText(context, R.string.presence_externe, Toast.LENGTH_LONG).show();
        }else {
            this.setTitle(R.string.presence_centrale);
            Toast.makeText(context, R.string.presence_centrale, Toast.LENGTH_LONG).show();
        }

        Log.d("PresenceActivity","Initialized");
    }

    private void changeToExterne(){
        externeEtNonCentrale=true;
        editTextRecherche.setText("");
    }

    private void changeToCentrale(){
        externeEtNonCentrale=false;
        editTextRecherche.setText("");
    }

}
