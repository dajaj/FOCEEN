package ginfo.foceen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by pierre on 01/10/15.
 */
public class LoginActivity extends AppCompatActivity
{
    private SettingsSql settingsSql;
    private String password;
    private EditText passwordTape;
    private Button buttonValidation;
    private static final String versionBdd = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        settingsSql = new SettingsSql(this);
        passwordTape = (EditText)findViewById(R.id.editTextPassword);
        buttonValidation = (Button)findViewById(R.id.buttonConnexion);

        buttonValidation.setText("Connexion");

        final Context context = this;

        this.verifierExistenceMDP();

        buttonValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingsSql.getPasswordInBDD().equals(passwordTape.getText().toString()))
                {
                    Intent intent = new Intent(LoginActivity.this,AcceuilActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(context,"Erreur le mot de passe est faux",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action1 = menu.getItem(0);
        action1.setTitle("Changer mot de passe");

        MenuItem action2 = menu.getItem(1);
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


        if(id == R.id.action_1)
        {
            Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void verifierExistenceMDP()
    {
        if(settingsSql.getPasswordInBDD().equals("ERREUR"))
        {
            settingsSql.setPasswordInBDD("TeSt1!");
        }
    }

    private void miseAJourBDD()
    {
        if(!settingsSql.getVersionBDD().equals(versionBdd))
        {
            settingsSql.changeVersionBDD(versionBdd);
        }
    }

    public static String getVersionBdd() {
        return versionBdd;
    }
}
