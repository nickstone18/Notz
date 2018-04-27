package eu.fse.notz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Amministratore on 12/04/2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>  {
    private ArrayList<Note> mDataset;
    private Context context;




    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTv;
        public TextView descriptionTv;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.title_tv);
            descriptionTv = (TextView) itemView.findViewById(R.id.description_tv);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View view){
                    Intent intent = new Intent(context, NoteActivity.class);


                    String title = mDataset.get(getAdapterPosition()).getTitle();
                    String description = mDataset.get(getAdapterPosition()).getDescription();


                    intent.putExtra("title", title);
                    intent.putExtra("description", description);
                    intent.putExtra("position", getAdapterPosition());

                    ((MainActivity)context).startActivityForResult(intent, 1001);
                }
            });
        }
    }

    public NotesAdapter(ArrayList<Note> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_note, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotesAdapter.ViewHolder notevh= (NotesAdapter.ViewHolder)holder;
        Note currentNote= mDataset.get(position);
        notevh.titleTv.setText(currentNote.getTitle());
        notevh.descriptionTv.setText(currentNote.getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public Note getNote(int index) {
        return mDataset.get(index);
    }

    public void addNote(Note note) {
        this.mDataset.add(0, note);
        notifyItemInserted(0);


    }

    public void addNote(int index, Note note) {
        mDataset.add(index, note);
        notifyItemInserted(index);

    }

    public void updateNote(int index,Note note){
        mDataset.set(index,note);
        notifyItemChanged(index);
    }

    public void updateNote(int index,String title, String description){

        Note note = mDataset.get(index);

        note.setTitle(title);
        note.setDescription(description);
        notifyItemChanged(index);
    }

    public void removeNote(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }
}