package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<Crime>();
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTittle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public int index(UUID crimeId) {
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                return i;
            }
        }
        return -1;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }
}

