package com.example.vatsal.newsly;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vatsal.newsly.Fragments.DatePickerFragment;

import java.util.Arrays;
import java.util.List;


public class CustomSearchFormActivity extends AppCompatActivity {
    public static class fromDatePickerFragment extends DatePickerFragment {
        @SuppressLint("DefaultLocale")
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int date) {
            fromDate = String.format("%d-%d-%d", year, month, date);
        }
    }

    public static class toDatePickerFragment extends DatePickerFragment {
        @SuppressLint("DefaultLocale")
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int date) {
            toDate = String.format("%d-%d-%d", year, month, date);
        }
    }

    Spinner languageSpinner;
    List<String> languages;
    List<String> language_codes;
    List<String> sortByList;
    List<String> sortByCodes;
    public static String language;
    public static String fromDate;
    public static String toDate;
    Button fromDatePicker;
    Button toDatePicker;
    public static String query;
    EditText editText;
    Spinner sortBySpinner;
    public static String sortBy;
    Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_search_form);
        setVariables();
        setLanguageSpinner();
        setFromDatePicker();
        setToDatePicker();
        setSortBySpinner();
        setSubmitButton();
    }

    private void setSubmitButton() {
        submitButton.setOnClickListener((View v) -> {
            setQuery();
            Intent intent = new Intent(CustomSearchFormActivity.this, CustomNewsActivity.class);
            startActivity(intent);
        });
    }

    private void setSortBySpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortByList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(dataAdapter);
        sortBySpinner.setSelection(2);
        sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sortBy = sortByCodes.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setQuery() {
        query = editText.getText().toString();
    }

    private void setVariables() {
        editText = findViewById(R.id.editText);
        toDatePicker = findViewById(R.id.toDate);
        fromDatePicker = findViewById(R.id.fromDate);
        languageSpinner = findViewById(R.id.language);
        sortBySpinner = findViewById(R.id.sortBy);
        submitButton = findViewById(R.id.submit);
        languages = Arrays.asList(getResources().getStringArray(R.array.languages));
        language_codes = Arrays.asList(getResources().getStringArray(R.array.language_code));
        sortByList = Arrays.asList(getResources().getStringArray(R.array.sortBy));
        sortByCodes = Arrays.asList(getResources().getStringArray(R.array.sortByCodes));
        query = "";
        fromDate = "";
        toDate = "";
        sortBy = "publishedAt";
        language = "en";
    }

    private void setToDatePicker() {
        toDatePicker.setOnClickListener((View v) -> {
            DialogFragment fragment = new toDatePickerFragment();
            fragment.show(getSupportFragmentManager(), "toDatePicker");
        });
    }

    private void setFromDatePicker() {
        fromDatePicker.setOnClickListener((View v) -> {
            DialogFragment fragment = new fromDatePickerFragment();
            fragment.show(getSupportFragmentManager(), "fromDatePicker");
        });
    }


    private void setLanguageSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(dataAdapter);
        languageSpinner.setSelection(2);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                language = language_codes.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}
