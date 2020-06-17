package com.garehn.bloodteacher.graphics;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.TextView;

import com.garehn.bloodteacher.R;
import com.garehn.bloodteacher.characters.Student;

    public class PlayerCard {

        protected TextView textTitle;
        protected TextView text1;

        public void showDialog(Activity activity, Student student){
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.player_card);
            dialog.show();
        }
    }

