package com.example.vatsal.newsly.ThompsonSampling;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vatsal.newsly.R;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.SynchronizedRandomGenerator;

import java.util.Arrays;
import java.util.List;

public class ThompsonSampling {
    private SharedPreferences sharedPreferences;
    private static List<String> categories;
    private static final RandomGenerator THREADSAFE_RANDOMNUMBER_GENERATOR = new SynchronizedRandomGenerator(new MersenneTwister());

    public ThompsonSampling(Context context) {
        sharedPreferences = context.getSharedPreferences("app_data", Context.MODE_PRIVATE);
        if (categories == null)
            categories = Arrays.asList(context.getResources().getStringArray(R.array.categories));
    }

    public void clicked(String title) {
        int currentZero = sharedPreferences.getInt(title + "_zero", 0);
        int currentOne = sharedPreferences.getInt(title + "_one", 0);
        if (currentZero == 0)
            return;
        sharedPreferences.edit().putInt(title + "_zero", currentZero - 1).apply();
        sharedPreferences.edit().putInt(title + "_one", currentOne + 1).apply();
    }

    public void addZeroes(String title, int pageSize) {
        sharedPreferences.edit().putInt(title + "_zero", sharedPreferences.getInt(title + "_zero", 0) + pageSize).apply();

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
        return bestTitle;
    }
}
