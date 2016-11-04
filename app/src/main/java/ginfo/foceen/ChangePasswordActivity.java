package ginfo.foceen;

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
 * Created by pierre on 03/10/15.
 */
public class ChangePasswordActivity extends AppCompatActivity
{
    private EditText passwordOld;
    private EditText passwordNew;
    private EditText passwordNewConfirm;
    private Button validation;
    private SettingsSql settingsSql;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        settingsSql = new SettingsSql(this);
        passwordOld = (EditText)findViewById(R.id.editTextPasswordAncient);
        passwordNew = (EditText)findViewById(R.id.editTextPasswordNew);
        passwordNewConfirm = (EditText)findViewById(R.id.editTextPasswordNewConfirm);
        validation = (Button)findViewById(R.id.buttonValidationChangePassword);

        final Context context = this;
        final ChangePasswordActivity t_h_i_s = this;

        validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String passwordOldString = passwordOld.getText().toString();
                String passwordNewString = passwordNew.getText().toString();
                String passwordNewConfirmString = passwordNewConfirm.getText().toString();
                if(settingsSql.getPasswordInBDD().equals(passwordOldString))
                {
                    if(passwordNewConfirmString.equals(passwordNewString))
                    {
                        settingsSql.setPasswordInBDD(passwordNewConfirmString);
                        Toast.makeText(context,"Le mot de passe à bien été mis à jour",Toast.LENGTH_SHORT).show();
                        t_h_i_s.finish();
                    }
                    else
                    {
                        Toast.makeText(context,"Les mots de passe ne correspondent pas",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context,"Le mot de passe ancien ne correspond pas à celui enregistré",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action1 = menu.getItem(0);
        action1.setTitle("Retour au login");

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
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
