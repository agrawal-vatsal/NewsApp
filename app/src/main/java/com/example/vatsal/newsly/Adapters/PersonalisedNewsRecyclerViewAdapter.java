package com.example.vatsal.newsly.Adapters;

import android.content.Context;

import com.example.vatsal.newsly.Models.PersonalisedArticle;
import com.example.vatsal.newsly.ThompsonSampling;

import java.util.List;

public class PersonalisedNewsRecyclerViewAdapter extends RecyclerViewAdapter<PersonalisedArticle> {

    private ThompsonSampling sampling;

    public PersonalisedNewsRecyclerViewAdapter(List<PersonalisedArticle> dataset, Context context, ThompsonSampling sampling) {
        super(dataset, context);
        this.sampling = sampling;
    }

    @Override
    public void handleClickOnReadFull(PersonalisedArticle article) {
        super.handleClickOnReadFull(article);
        sampling.clicked(article.getCategory());
    }
}

