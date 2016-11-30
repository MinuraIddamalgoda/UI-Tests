package com.minura.uitests;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Declaring views and view assets
    private Button mBtnTrue;
    private Button mBtnFalse;
    private Button mBtnNext;
    private TextView mQuestionsTextView;
    private Button mCheatButton;
    private Bitmap mBitmap;
    private Drawable background;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private FloatingActionButton mFloatingActionButton;

    // Initialising variables
    private int mCurrentIndex = 0;
    private static final String logTag = "MainActivity";
    private static final String keyIndex = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;

    private Questions[] mQuestionBank = new Questions[] {
            new Questions(R.string.question_oceans, true),
            new Questions(R.string.question_mideast, false),
            new Questions(R.string.question_africa, false),
            new Questions(R.string.question_americas, true),
            new Questions(R.string.question_asia, true),
    };

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct;
            } else {
                messageResId = R.string.incorrect;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();

    }

    private void updateQuestion(int index) {
        Log.d(logTag, "updateQuest index=" + index);

        int question = mQuestionBank[index].getTextResId();
        mQuestionsTextView.setText(question);

        switch (index){
            case 0:
                background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.pac);
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pac);
                break;
            case 1:
                background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.suez);
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.suez);
                break;
            case 2:
                background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.nile);
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nile);
                break;
            case 3:
                background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.amazon);
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.amazon);
                break;
            case 4:
                background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.baikal);
                mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.baikal);
                break;
        }

        mCollapsingToolbarLayout.setBackground(background);

        userInterface.updatePalette(
                getApplicationContext(),
                mBitmap,
                mCollapsingToolbarLayout,
                mFloatingActionButton
        );

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(logTag, "onSaveInstanceState");
        savedInstanceState.putInt(keyIndex, mCurrentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up views and bitmap
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.toolbar_layout);
        mFloatingActionButton = (FloatingActionButton)
                findViewById(R.id.fab);
        mQuestionsTextView = (TextView) findViewById(R.id.txtQuestions);
        mBtnTrue = (Button) findViewById(R.id.btnTrue);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mBtnFalse = (Button) findViewById(R.id.btnFalse);
        mBtnNext = (Button) findViewById(R.id.btnNext);

//        userInterface.updatePalette(
//                getApplicationContext(),
//                mBitmap,
//                mCollapsingToolbarLayout,
//                mFloatingActionButton);

        if (savedInstanceState != null) {
            //mCurrentIndex = savedInstanceState.getInt(keyIndex);
        }

        updateQuestion(mCurrentIndex);

        // Setting up OnClickListeners
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion(mCurrentIndex);
            }
        });

        mBtnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                mCurrentIndex++;
                updateQuestion(mCurrentIndex);
            }
        });

        mBtnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                mCurrentIndex++;
                updateQuestion(mCurrentIndex);
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(logTag, "onStart called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(logTag, "onPause called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(logTag, "onResume called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(logTag, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(logTag, "onDestroy called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

//                  A Minura Iddamalgoda production                 //