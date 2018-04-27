package eu.fse.notz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.description;
import static eu.fse.notz.R.attr.title;

/**
 * Created by Amministratore on 12/04/2018.
 */

public class NoteActivity extends AppCompatActivity {

    EditText titleEt;
    EditText descriptionEt;
    Button   editConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        titleEt = (EditText) findViewById(R.id.edit_title);
        descriptionEt = (EditText) findViewById(R.id.edit_description);
        editConfirmButton = (Button) findViewById(R.id.confrim_button);

        final Intent intent = getIntent();
        final String title = intent.getStringExtra("title");
        String description =  intent.getStringExtra ("description");

        titleEt.setText(title);
        descriptionEt.setText(description);

        editConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editedTitle = titleEt.getText().toString();
                String editedDescription = descriptionEt.getText().toString();
                int position = intent.getIntExtra ("position", -1);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("title", editedTitle);
                returnIntent.putExtra("description", editedDescription);
                returnIntent.putExtra("position", position);
                setResult(Activity.RESULT_OK, returnIntent);

                finish();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_note,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.edit_delete){


            int position = intent.getIntExtra("position", -1);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("title", title);
            returnIntent.putExtra("description", description);
            returnIntent.putExtra("position", position);
            setResult(MainActivity.RESUL_REMOVE_NOTE, returnIntent);

            finish();
            return true;
        }
        if(item.getItemId() == R.id.ciao){

            Toast.makeText(this,"Ciao",Toast.LENGTH_LONG).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
