package com.example.vatsal.newsly;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.SynchronizedRandomGenerator;

import java.util.Arrays;
import java.util.List;

public class ThompsonSampling {
    private SharedPreferences sharedPreferences;
    static List<String> categories;
    private static final RandomGenerator THREADSAFE_RANDOMNUMBER_GENERATOR = new SynchronizedRandomGenerator(new MersenneTwister());
    private Context context;

    public ThompsonSampling(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("app_data", Context.MODE_PRIVATE);
        if (categories == null)
            categories = Arrays.asList(context.getResources().getStringArray(R.array.categories));
    }

    public void clicked(String title) {
        int currentZero = sharedPreferences.getInt(title + "_zero", 0);
        int currentOne = sharedPreferences.getInt(title + "_one", 0);
        Toast.makeText(context, "clicked " + title, Toast.LENGTH_SHORT).show();
        if (currentZero == 0)
            return;
        sharedPreferences.edit().putInt(title + "_zero", currentZero - 1).apply();
        sharedPreferences.edit().putInt(title + "_one", currentOne + 1).apply();
    }

    public void addZeroes(String title, int pageSize) {
        sharedPreferences.edit().putInt(title + "_zero", sharedPreferences.getInt(title + "_zero", 0) + pageSize).apply();
        Toast.makeText(context, "zeroes added" + title, Toast.LENGTH_SHORT).show();

    }

    private int getZeroes(String title) {
        return sharedPreferences.getInt(title + "_zero", 0);
    }

    private int getOnes(String title) {
        return sharedPreferences.getInt(title + "_one", 0);
    }

    public String getBest() {
        String bestTitle = categories.get(0);
        double max = 0.0;
        for (String title : categories) {
            int currentZero = getZeroes(title);
            int currentOne = getOnes(title);
            currentOne = currentOne > 1 ? currentOne : 1;
            currentZero = currentZero > 1 ? currentZero : 1;
            double num = new BetaDistribution(THREADSAFE_RANDOMNUMBER_GENERATOR, currentOne, currentZero).sample();
            if (num > max) {
                max = num;
                bestTitle = title;
            }
        }
        Toast.makeText(context, "Best category is" + bestTitle, Toast.LENGTH_SHORT).show();
        return bestTitle;
    }
}
