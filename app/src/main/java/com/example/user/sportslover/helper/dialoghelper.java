package com.example.user.sportslover.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.example.user.sportslover.R;

/**
 * Created by user on 17-9-25.
 */

public class dialoghelper extends AlertDialog {
    public dialoghelper(Context context) {
        super(context);
        final View view = getLayoutInflater().inflate(R.layout.about,null);
        setButton(context.getText(R.string.close), (OnClickListener) null);
        setIcon(R.drawable.ic_launcher);
        setTitle("SportsLover   v1.0.0" );
        setView(view);
    }
}