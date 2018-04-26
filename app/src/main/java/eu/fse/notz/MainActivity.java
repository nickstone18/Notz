package eu.fse.notz;

import android.content.DialogInterface;
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
    private RecyclerView mRecyclerView;
    private NotesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Note> myDataset;
    private FloatingActionButton addNoteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new StaggeredGridLayoutManager(2, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<>();
        Note pinPalazzo = new Note("pin", "234");
        myDataset.add(pinPalazzo);

        myDataset = new ArrayList<>();
        Note spesa = new Note("nota", "fai la spesa");
        myDataset.add(spesa);

        mAdapter = new NotesAdapter(myDataset,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showDialog() {

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        final View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_add_note, null);

        alertBuilder.setView(R.layout.dialog_add_note);
        alertBuilder.setTitle(R.string.dialog_add_note_title);

        final EditText titleEt = (EditText) dialogView.findViewById(R.id.dialog_title_et);
        final EditText descriptionEt = (EditText) dialogView.findViewById(R.id.dialog_description_et);

        alertBuilder.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String insertedTitle = titleEt.getText().toString();
                String insertedDescription = descriptionEt.getText().toString();

                Note note = new Note("Titolo della nota",
                        "contenuto nota dsaasd");
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
}