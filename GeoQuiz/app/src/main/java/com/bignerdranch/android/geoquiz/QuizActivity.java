package com.bignerdranch.android.geoquiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private Button mNextButton;
    private Button mCheatButton;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "Index";
    private static final String KEY_CHEAT = "cheat";
    private static final int QUEST_CODE_CHEAT = 0;


    private Question[] mQuestionBank = {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: onCreate(Bundle) called");

        if ( savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEAT);
        }

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
//                toast.setGravity(Gravity.TOP, 0, 0);
//                toast.show();
            }
        });

        mQuestionTextView = findViewById(R.id.question_text_view);
        updateQuestion();

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CheatActivity.newIntent(QuizActivity.this, mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(intent, QUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == QUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answer = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageId = 0;
        if (mIsCheater) {
            messageId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answer) {
                messageId = R.string.correct_toast;
            } else {
                messageId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBoolean(KEY_CHEAT, mIsCheater);
    }
}
