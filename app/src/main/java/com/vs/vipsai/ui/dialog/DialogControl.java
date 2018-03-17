package com.vs.vipsai.ui.dialog;

import android.app.ProgressDialog;

/**
 * Author: cynid
 * Created on 3/17/18 10:53 AM
 * Description:
 */

public interface DialogControl {

    public abstract void hideWaitDialog();

    public abstract ProgressDialog showWaitDialog();

    public abstract ProgressDialog showWaitDialog(int resid);

    public abstract ProgressDialog showWaitDialog(String text);

}
