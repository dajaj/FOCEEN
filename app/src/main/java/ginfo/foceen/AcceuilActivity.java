package ginfo.foceen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by pierre on 23/09/15.
 */
public class AcceuilActivity extends AppCompatActivity
{
    private Button buttonCentrale;
    private Button buttonAutres;
    private Button buttonChoix;
    private Spinner spin;
    private TextView textViewAcceuil;
    private static boolean eventChoisi = false;
    private static Event eventEnCours = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);
        this.Initialize();
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
        action1.setTitle("Gestion Evenements");
        if(eventChoisi == true)
        {
            action1.setVisible(false);
        }

        MenuItem action2 = menu.getItem(1);
        action2.setTitle("Gestion Presences");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if(id == R.id.action_1)
        {
            Intent intent = new Intent(AcceuilActivity.this, GestionEventActivity.class);
            startActivity(intent);
        }
        if(id == R.id.action_2)
        {
            Intent intent = new Intent(AcceuilActivity.this, PresenceActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        Toast.makeText(this,"Vous n'avez pas le droit de faire cela",Toast.LENGTH_SHORT).show();
    }

    private void Initialize()
    {
        buttonCentrale = (Button) findViewById(R.id.buttonCentrale);
        buttonAutres = (Button)findViewById(R.id.buttonAutres);
        buttonChoix = (Button)findViewById(R.id.buttonChoixEvent);
        spin = (Spinner)findViewById(R.id.spinner);
        textViewAcceuil = (TextView)findViewById(R.id.textViewAcceuil);

        if(eventChoisi == false)
        {
            buttonCentrale.setVisibility(View.INVISIBLE);
            buttonAutres.setVisibility(View.INVISIBLE);
            textViewAcceuil.setVisibility(View.INVISIBLE);

            EventSql eventSql = new EventSql(this);
            ArrayList<Event> events = eventSql.getEventAll();

            EventAdapter eventAdapter = new EventAdapter(this,events);
            spin.setAdapter(eventAdapter);

            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
                {
                    buttonChoix.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v)
                        {
                            Event event = (Event)spin.getSelectedItem();
                            AcceuilActivity.setEventEnCours(event);
                            eventChoisi = true;
                            recreate();
                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0)
                {

                }
            });
        }
        else
        {
            spin.setVisibility(View.INVISIBLE);
            buttonChoix.setVisibility(View.INVISIBLE);

            textViewAcceuil.setText("Evenement sélectionné : \n" + AcceuilActivity.getEventEnCours().getNom());

            buttonCentrale.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AcceuilActivity.this, CentraleActivity.class);
                    startActivity(intent);
                }
            });


            buttonAutres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Ici onClick boutonAutre", "Ici eh ho");
                    Intent intent = new Intent(AcceuilActivity.this, ExterneActivity.class);
                    startActivity(intent);
                }
            });
        }
    }



    public static void setEventEnCours(Event eventEnCours)
    {
        AcceuilActivity.eventEnCours = new Event(eventEnCours);
    }

    public static Event getEventEnCours() {
        return eventEnCours;
    }
}
