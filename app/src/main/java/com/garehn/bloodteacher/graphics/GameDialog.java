package com.garehn.bloodteacher.graphics;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.garehn.bloodteacher.R;

public class GameDialog extends AppCompatDialogFragment  {

    private String REROLL_TITLE = "Do you want to reroll ?";
    private String REROLL_MESSAGE = "you have %s rerolls left";
    private Button buttonYES;
    private Button buttonNO;
    boolean answer = false;
    //private GameDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String message = String.format(REROLL_MESSAGE, "???");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.game_dialog, null);

        builder.setView(view)
                .setTitle(String.format(REROLL_TITLE))
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // listener.applyYES(true);
                    }
                });

        return builder.create();
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (GameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement GameDialogListener");
        }
    }

    public void applyYES(boolean b) {

    }


    public interface GameDialogListener {
        void applyYES(boolean b);
    }*/


}

