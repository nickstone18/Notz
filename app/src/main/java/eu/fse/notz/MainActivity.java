package eu.fse.notz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
    public static final int RESUL_REMOVE_NOTE = RESULT_FIRST_USER + 1;

    private RecyclerView mRecyclerView;
    private NotesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Note> myDataset;
    private FloatingActionButton addNoteButton;
    private DatabaseHandler dbHandler;
    //private ProgressBar loading;
    //String title = getResources().getString(R.string.titolo_dialog);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.notes_rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        mRecyclerView.setHasFixedSize(true);

        addNoteButton = (FloatingActionButton) findViewById(R.id.add_note_fab);
        //loading = (ProgressBar) findViewById(R.id.loading);

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);


        /* myDataset = new ArrayList<>();
        Note pinPalazzo = new Note("pin", "234");
        myDataset.add(pinPalazzo);

        myDataset = new ArrayList<>();
        Note spesa = new Note("nota", "fai la spesa");
        myDataset.add(spesa);*/


        myDataset = new ArrayList<>();
        dbHandler = new DatabaseHandler(this);
        myDataset.addAll(dbHandler.getAllNotes());

        mAdapter = new NotesAdapter(myDataset, this);
        mRecyclerView.setAdapter(mAdapter);


        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
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
                        data.getStringExtra("description"),
                        data.getBooleanExtra("favourite",false));

                dbHandler.updateNote(mAdapter.getNote(editedNotePosition));


            }

            if (resultCode == RESUL_REMOVE_NOTE) {
                final int editedNotePosition = data.getIntExtra("position", -1);

                dbHandler.deleteNote(mAdapter.getNote(editedNotePosition));
                mAdapter.removeNote(editedNotePosition);

                //final int finalEditedNotePosition = editedNotePosition;
                Snackbar.make(mRecyclerView, getString(R.string.note_removed), Snackbar.LENGTH_LONG)
                        .setAction(R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Note note = new Note(data.getStringExtra("title"),
                                        data.getStringExtra("description"));

                                mAdapter.addNote(editedNotePosition, note);
                                dbHandler.addNote(note);
                            }
                        })
                        .show();
            }


        }

    }

    private void showDialog() {

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_note, null);


        final EditText titleEt = (EditText) dialogView.findViewById(R.id.dialog_title_et);
        final EditText descriptionEt = (EditText) dialogView.findViewById(R.id.dialog_description_et);

        alertBuilder.setView(dialogView)
                .setTitle(R.string.dialog_add_note_title)
                .setPositiveButton(R.string.dialog_positive_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //

                        String insertedTitle = titleEt.getText().toString();
                        String insertedDescription = descriptionEt.getText().toString();

                        Note.NoteBuilder noteBuilder = new Note.NoteBuilder();
                        noteBuilder
                                .setTitle(insertedTitle)
                                .setDescription(insertedDescription)
                                .setId(12)
                                .setShownOnTop(true);

                        mAdapter.addNote(noteBuilder.build());
                        dbHandler.addNote(noteBuilder.build());

                    }
                });

        alertBuilder.setNegativeButton(R.string.dialog_negative_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                alertBuilder.create()
                .show();
    }

    /*private void getNotesFromURL() {

        //Make HTTP call

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        //String url = "http://5af1bf8530f9490014ead894.mockapi.io/api/v1/notes";
        String url = "http://www.mocky.io/v2/5af4468d55000062007a5239";

        // Request a string response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(
                Request.Method.GET, // METHOD
                url, // URL
                null, // Body parameters
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // TODO manage success

                        loading.setVisibility(View.GONE);
                        Log.d("jsonRequest", response.toString());
                        ArrayList<Note> noteListFromResponse = Note.getNotesList(response);
                        mAdapter.addNotesList(noteListFromResponse);
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.setVisibility(View.GONE);
                        error.printStackTrace();

                        Toast.makeText(MainActivity.this,
                                "Si è riscontrato un errore " + error.networkResponse.statusCode,
                                Toast.LENGTH_LONG).show();

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);

    }*/

}



