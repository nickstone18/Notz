package eu.fse.notz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        String title = intent.getStringExtra("title");
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
}
