package com.beckman.winloading;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;

/**
 * O(∩_∩)O 无BUG 好开心
 * Created by Beckman on 2016/10/3.
 */
public class WinProgressDialog extends Dialog {


    public WinProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static WinProgressDialog createDialog(Context context) {
        WinProgressDialog dialog = new WinProgressDialog(context, R.style.winDialog);
        dialog.setContentView(R.layout.dialog_win);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return dialog;
    }

    public  void setMessage(String msg){
        if (TextUtils.isEmpty(msg))return;
        TextView tvMessage= (TextView) findViewById(R.id.tv_progress_info);
        tvMessage.setText(msg);
    }

}
