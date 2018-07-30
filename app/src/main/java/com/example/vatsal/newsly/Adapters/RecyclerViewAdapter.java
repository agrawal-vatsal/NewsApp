package com.example.vatsal.newsly.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vatsal.newsly.DatabaseOperations.AppDatabase;
import com.example.vatsal.newsly.DatabaseOperations.DatabaseInstance;
import com.example.vatsal.newsly.Models.ArticleDB;
import com.example.vatsal.newsly.Models.ArticleInterface;
import com.example.vatsal.newsly.R;
import com.example.vatsal.newsly.WebPageActivity;

import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter<T extends ArticleInterface> extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    AppDatabase db;
    List<T> dataset;
    private TextToSpeech tts;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        CardView cardView;
        TextView description;
        Button readFull;
        View view;
        Button speakButton;
        boolean isFront;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            textView = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.imageView);
            cardView = view.findViewById(R.id.card_view);
            description = view.findViewById(R.id.description);
            readFull = view.findViewById(R.id.button);
            speakButton = view.findViewById(R.id.speak);
            isFront = true;
        }
    }

    void setDb() {
        db = DatabaseInstance.getInstance(context);
    }

    private void showRear(ViewHolder holder) {
        holder.isFront = false;
        holder.textView.setAlpha(0);
        holder.imageView.setAlpha(0f);
        holder.description.setAlpha(1f);
        holder.readFull.setAlpha(1f);
        holder.readFull.setClickable(true);
        holder.speakButton.setClickable(true);
        holder.speakButton.setAlpha(1f);
    }

    private void showFront(ViewHolder holder) {
        holder.isFront = true;
        holder.textView.setAlpha(1f);
        holder.imageView.setAlpha(1f);
        holder.description.setAlpha(0);
        holder.readFull.setAlpha(0);
        holder.readFull.setClickable(false);
        holder.speakButton.setClickable(false);
        holder.speakButton.setAlpha(0);
    }

    private void puttingDataOnCard(ViewHolder holder, T item) {
        holder.textView.setText(item.getTitle());
        Glide.with(context)
                .load(item.getUrlToImage())
                .into(holder.imageView);
        showFront(holder);
        holder.description.setText(item.getDescription());
    }

    public void handleReadFull(ViewHolder holder, T item) {
        holder.readFull.setOnClickListener((View view) -> {
            handleClickOnReadFull(item);
        });
    }

    public void handleClickOnReadFull(T item) {
        Intent intent = new Intent(context, WebPageActivity.class);
        intent.putExtra("webPage", item.getUrl());
        context.startActivity(intent);
    }

    private void handleCardFlip(ViewHolder holder) {
        holder.cardView.setOnClickListener((View view) -> {
            if (holder.isFront) {
                holder.cardView.animate().rotationY(360f).setDuration(500);
                showRear(holder);
            } else {
                holder.cardView.animate().rotationY(360f).setDuration(500);
                showFront(holder);
            }
        });
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        T article = dataset.get(position);
        puttingDataOnCard(holder, article);
        handleReadFull(holder, article);
        handleCardFlip(holder);
        handleCardViewLongClick(holder, position);
        handleSpeakButton(holder, article);
    }

    private void playNextChunk(String text) {
        String utteranceId = this.hashCode() + "";
        Bundle params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, params, utteranceId);
    }

    private void handleSpeakButton(ViewHolder holder, T article) {
        holder.speakButton.setOnClickListener((View view) -> {
            if (tts != null) {
                tts.stop();
                tts.shutdown();
                tts = null;
            }
            tts = new TextToSpeech(context, (int i) -> {
                if (i == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(context, "Language not supported", Toast.LENGTH_LONG).show();
                    }
                    playNextChunk(article.getDescription());
                }
            });
        });
    }

    public void handleCardViewLongClick(ViewHolder holder, int position) {
        T item = dataset.get(position);
        holder.cardView.setOnLongClickListener((View view) -> {
            boolean isInDb = checkItem(item.getTitle());
            String message = "Are you sure you want to " + (isInDb ? "delete" : "save") + " this post?";
            new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage(message)
                    .setPositiveButton("Yes", (DialogInterface dialogInterface, int i) -> {
                        ArticleDB article = new ArticleDB(item.getTitle(), item.getDescription(), item.getUrl(), item.getUrlToImage());
                        if (isInDb)
                            db.articleDao().deleteArticle(article);
                        else
                            db.articleDao().insertArticle(article);
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    private boolean checkItem(String title) {
        return db.articleDao().getArticle(title).length != 0;
    }

    public RecyclerViewAdapter(List<T> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
        setDb();
    }

    RecyclerViewAdapter(Context context) {
        this.context = context;
    }
}