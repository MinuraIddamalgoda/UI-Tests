package com.minura.uitests;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class userInterface  {

    String logTag = "userInterface.class";

    public userInterface() {


    }



    public static void updatePalette(
            final Context context,
            Bitmap bitmap,
            final CollapsingToolbarLayout collapsingToolbarLayout,
            final FloatingActionButton floatingActionButton) {

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int mutedColour = palette.getMutedColor(ContextCompat.getColor(context, R.color.colorPrimary));
                int mutedDarkColour = palette.getDarkMutedColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                int vibrantColour = palette.getVibrantColor(ContextCompat.getColor(context, R.color.colorAccent));

                collapsingToolbarLayout.setContentScrimColor(mutedColour);
                collapsingToolbarLayout.setStatusBarScrimColor(mutedDarkColour);
                floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(vibrantColour));
            }
        });

    }
}
 //                     A Minura Iddamalgoda production                 //
