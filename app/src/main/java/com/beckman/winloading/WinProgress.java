package com.beckman.winloading;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * O(∩_∩)O 无BUG 好开心
 * Created by Beckman on 2016/10/3.
 */
public class WinProgress {

    private static WinProgressDialog dialog;
    private static Context context;
    private static WinProgressCallback callback;

    public static int dismissDelay = WinProgress.DISMISS_DELAY_SHORT;
    public static final int DISMISS_DELAY_SHORT = 2000;


    public static void showLoadingMessage(Context context, String msg, boolean cancelable, WinProgressCallback callback) {
        WinProgress.callback = callback;
        showLoadingMessage(context, msg, cancelable);
    }

    public static void showLoadingMessage(Context context, String msg, boolean cancelable, final int time) {
        dismiss();
        setDialog(context, msg, cancelable);
        if (dialog != null) {
            dialog.show();
            dismissAfterSeconds(time);
        }
    }

    public static void showLoadingMessage(Context context, String msg, boolean cancelable) {
        dismiss();
        setDialog(context, msg, cancelable);
        if (dialog != null) dialog.show();
    }


    public static void showInfoMessage(Context context, String msg, WinProgressCallback callback) {
        WinProgress.callback = callback;
        showInfoMessage(context, msg);
    }

    public static void showInfoMessage(Context context, String msg) {
        dismiss();
        setDialog(context, msg, true);
        if (dialog != null) {
            dialog.show();
            dismissAfterSeconds();
        }
    }


    private static void setDialog(Context ctx, String msg, boolean cancelable) {
        context = ctx;

        if (!isContextValid())
            return;

        dialog = WinProgressDialog.createDialog(ctx);
        dialog.setMessage(msg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(cancelable);        // back键是否可dimiss对话框
    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        dialog = null;
    }


    /**
     * 计时关闭对话框
     */
    private static void dismissAfterSeconds() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(dismissDelay);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void dismissAfterSeconds(final int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                dismiss();
                if (WinProgress.callback != null) {
                    callback.onWinProgressDismissed();
                    callback = null;
                }
            }
        }

        ;
    };


    /**
     * 判断parent view是否还存在
     * 若不存在不能调用dismis，或setDialog等方法
     *
     * @return
     */
    private static boolean isContextValid() {
        if (context == null)
            return false;
        if (context instanceof Activity) {
            Activity act = (Activity) context;
            if (act.isFinishing())
                return false;
        }
        return true;
    }


    public static interface WinProgressCallback {
        public void onWinProgressDismissed();
    }


}
