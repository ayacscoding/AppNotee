package com.example.appnote;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private List<AppNote> notesList;

    public NotesAdapter(List<AppNote> notesList) {
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        AppNote note = notesList.get(position);
        holder.title.setText(note.title);
        holder.content.setText(note.content);

        // 1. برمجة الضغط العادي (للتعديل والعرض)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NewnoteActivity.class);
            intent.putExtra("note_id", note.id);
            intent.putExtra("title", note.title);
            intent.putExtra("content", note.content);
            intent.putExtra("isUpdate", true);
            v.getContext().startActivity(intent);
        });

        // 2. برمجة الضغط المطول (للحذف)
        holder.itemView.setOnLongClickListener(v -> {
            new android.app.AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Note")
                    .setMessage("Do you want to delete this note?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        new Thread(() -> {
                            MyDatabase db = Room.databaseBuilder(v.getContext(), MyDatabase.class, "notes_db").build();
                            db.noteDo().delete(note);

                            ((android.app.Activity) v.getContext()).runOnUiThread(() -> {
                                notesList.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(v.getContext(), "Note deleted", Toast.LENGTH_SHORT).show();
                            });
                        }).start();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            content = itemView.findViewById(R.id.textContent);
        }
    }
}