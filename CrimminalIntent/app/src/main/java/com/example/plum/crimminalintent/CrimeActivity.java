package com.example.plum.crimminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_ID = "com.plum.android.crime_id";
    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID)getIntent().getSerializableExtra(EXTRA_ID);
        return CrimeFragment.newInstance(crimeId);
    }

    public static Intent newIntent(Context pakageContext, UUID crimeId) {
        Intent intent = new Intent(pakageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_ID, crimeId);
        return intent;
    }
}
