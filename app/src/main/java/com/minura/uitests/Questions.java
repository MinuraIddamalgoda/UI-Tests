package com.minura.uitests;

/**
 * Created by minura on 28/07/2016.
 */
public class Questions {

    // Initialising variables
    private int mTextResId;
    private boolean mAnswerTrue;

    // Getters and setters
    public int getTextResId() {return mTextResId;}

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }


    public Questions(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

}

//                  A Minura Iddamalgoda production                 //
