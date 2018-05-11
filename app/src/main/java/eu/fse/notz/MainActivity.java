package eu.fse.notz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by Amministratore on 12/04/2018.
 */

public class MainActivity extends AppCompatActivity {

    public static final int EDIT_REQUEST = 1001;
    public static final int RESULT_REMOVE_NOTE = RESULT_FIRST_USER + 1;

    private RecyclerView mRecyclerView;
    private NotesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Note> myDataset;
    private FloatingActionButton addNoteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.notes_rv);
        mRecyclerView.setHasFixedSize(true);
        addNoteButton = (FloatingActionButton) findViewById(R.id.add_note_fab);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);


        myDataset = new ArrayList<>();
        Note pinPalazzo = new Note("pin", "234");
        myDataset.add(pinPalazzo);

        myDataset = new ArrayList<>();
        Note spesa = new Note("nota", "fai la spesa");
        myDataset.add(spesa);

        mAdapter = new NotesAdapter(myDataset,this);
        mRecyclerView.setAdapter(mAdapter);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_note, null);

        alertBuilder.setView(dialogView);
        alertBuilder.setTitle(R.string.dialog_add_note_title);

        final EditText titleEt = (EditText) dialogView.findViewById(R.id.dialog_title_et);
        final EditText descriptionEt = (EditText) dialogView.findViewById(R.id.dialog_description_et);

        alertBuilder.setPositiveButton(R.string.dialog_positive_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //

                        String insertedTitle = titleEt.getText().toString();
                        String insertedDescription = descriptionEt.getText().toString();

                        Note note = new Note(insertedTitle, insertedDescription);
                        mAdapter.addNote(note);

                    }
                });

        alertBuilder.setNegativeButton(R.string.dialog_negative_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        alertBuilder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_REQUEST) {

            if (resultCode == RESULT_OK) {

                //getPosition from returnIntent
                int editedNotePosition = data.getIntExtra("position", -1);

                mAdapter.updateNote(editedNotePosition,
                        data.getStringExtra("title"),
                        data.getStringExtra("description"));

            }
        }
    }
}

              /*setTitle(R.string.dialog_add_note_title)



            if (resultCode == RESULT_REMOVE_NOTE) {
                final int editedNotePosition = data.getIntExtra("position", -1);
                mAdapter.removeNote(editedNotePosition);


                Snackbar.make(mRecyclerView, getString(R.string.note_removed), Snackbar.LENGTH_LONG)
                        .setAction(R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Note note = new Note(data.getStringExtra("title"),
                                        data.getStringExtra("description"));

                                mAdapter.addNote(editedNotePosition, note);
                            }
                        })
                        .show();
            }
        }
    }*/