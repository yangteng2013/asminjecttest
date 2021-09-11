package com.tyty.asminjecttest.hook;

import android.util.Log;
import android.view.View;

public class ProxyOnClickListener implements View.OnClickListener {
    View.OnClickListener ocl;

    public ProxyOnClickListener(View.OnClickListener ocl) {
        this.ocl = ocl;
    }

    @Override
    public void onClick(View v) {
        Log.d("ProxyOnClickListener","点击事件被hook到了");
        if (ocl!=null) {
            ocl.onClick(v);
        }

    }

}
