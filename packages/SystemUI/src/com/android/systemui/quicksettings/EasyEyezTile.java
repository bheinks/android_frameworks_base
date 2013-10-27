package com.android.systemui.quicksettings;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

import com.android.systemui.R;
import com.android.systemui.statusbar.phone.QuickSettingsController;

public class EasyEyezTile extends QuickSettingsTile {
    private boolean mActive = false;
    private static final String SERVICE_INTENT = "com.palmerin.easyeyezfree.EasyEyezService";

    public EasyEyezTile(Context c, QuickSettingsController qsc) {
        super(c, qsc);

        final Context context = c;
        final QuickSettingsController quickSettingsController = qsc;
        final Intent intent = new Intent(SERVICE_INTENT);

        mOnClick = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mActive) {
                    context.startService(intent);
                    mActive = true;
                } else {
                    context.stopService(intent);
                    mActive = false;
                }

                updateResources();
            }
        };

        mOnLongClick = new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                context.startActivity(context.getPackageManager().getLaunchIntentForPackage("com.palmerin.easyeyezfree"));
                quickSettingsController.mStatusBarService.animateCollapsePanels();

                return true;
            }
        };
    }

    @Override
    void onPostCreate() {
        updateTile();
        super.onPostCreate();
    }

    @Override
    public void updateResources() {
        updateTile();
        super.updateResources();
    }

    private synchronized void updateTile() {
        if(mActive) {
            mDrawable = R.drawable.ic_qs_easyeyez_on;
            mLabel = mContext.getString(R.string.quick_settings_easyeyez_on);
        } else {
            mDrawable = R.drawable.ic_qs_easyeyez_off;
            mLabel = mContext.getString(R.string.quick_settings_easyeyez_off);
        }
    }
}


