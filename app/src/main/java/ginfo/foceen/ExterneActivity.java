package ginfo.foceen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pierre on 23/09/15.
 */
public class ExterneActivity extends AppCompatActivity
{
    private Spinner spin;
    private EditText editTextExterne;
    private Button buttonExterne;

    private static boolean activiteAFermer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_externe);

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
        MenuItem action1 = menu.getItem(0);
        MenuItem action2 = menu.getItem(1);
        action1.setTitle(R.string.accueil);
        action2.setTitle(R.string.gestionExterne);
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
            Intent intent = new Intent(ExterneActivity.this, GestionExterneActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void Initialize()
    {
        spin = (Spinner)findViewById(R.id.spinnerExterne);
        editTextExterne = (EditText)findViewById(R.id.editTextExterne);
        buttonExterne = (Button)findViewById(R.id.buttonExterneValidation);

        final Context context = this;
        final ExterneSql externeSql = new ExterneSql(this);
        ArrayList<Externe> externes = externeSql.getExterneAll();

        ExterneAdapter externeAdapter = new ExterneAdapter(context, externes);
        spin.setAdapter(externeAdapter);

        final ExterneActivity t_h_i_s = this;

        buttonExterne.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int id;
                if (!editTextExterne.getText().toString().equals(""))
                {
                    long create = externeSql.create(new Externe(editTextExterne.getText().toString()));
                    if (create == 0) {
                        Toast.makeText(context, "Externe non crée erreur", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Externe crée avec succès", Toast.LENGTH_SHORT).show();
                    }
                    id = externeSql.getExterneWithNom(editTextExterne.getText().toString()).get(0).getIdEcole();
                }
                else
                {
                    Externe externe = (Externe)spin.getSelectedItem();
                    id = externe.getIdEcole();
                }

                PresenceExterne presenceExterne = new PresenceExterne(id,AcceuilActivity.getEventEnCours().getId(),1);
                PresenceExterneSql presenceExterneSql = new PresenceExterneSql(context);
                int create = presenceExterneSql.createOrUpdate(presenceExterne);

                if(create != 0)
                {
                    Log.d("ExterneActivityPresence","Présence externe correctement validé");
                    Toast.makeText(context, "Présence externe correctement validé",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.d("ExterneActivityPresence","Problème présence externe non validé");
                    Toast.makeText(context, "Problème présence externe non validé", Toast.LENGTH_SHORT).show();
                }

                t_h_i_s.finish();
            }
        });
    }

    public static void fermerActivite(){
        activiteAFermer = true;
    }


}
