package com.example.zombiehunt.zombiehunt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

//description: this class is for the setup of the popup dialog box that will pop up after user finishes 1 trial of game.
public class WinAlertFragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        //View

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.win_alert, null);


        //Listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();

            }
        };

        //Dialog Build

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .setCancelable(false)
                .create();

    }
}
