package org.aquamarine5.brainspark.chaoxingsignfaker;
public final class MainActivity extends wh0 {
    public static final synthetic int u;

    public MainActivity()
    {
        return;
    }

    public final void onCreate(android.os.Bundle p11)
    {
        super.onCreate(p11);
        li0 v11_2 = this.getPackageManager().getPackageInfo(this.getPackageName(), 128);
        io.sentry.android.core.m1.b(this, new io.sentry.android.core.v(3), new ro0(13, v11_2));
        com.umeng.commonsdk.UMConfigure.preInit(this, "67d42c1c48ac1b4f87e7edae", "Stackbricks");
        rq v9 = 0;
        rj4 v4_1 = new rj4(0, 0, new ui4(1));
        rj4 v5_1 = new rj4(l21.a, l21.b, new ui4(1));
        android.view.ViewGroup v7_0 = this.getWindow().getDecorView();
        v7_0.getClass();
        m21 v0_6 = l21.c;
        if (v0_6 == null) {
            m21 v0_7 = android.os.Build$VERSION.SDK_INT;
            if (v0_7 < 35) {
                if (v0_7 < 30) {
                    if (v0_7 < 29) {
                        if (v0_7 < 28) {
                            v0_6 = new m21();
                        } else {
                            v0_6 = new n21();
                        }
                    } else {
                        v0_6 = new p21();
                    }
                } else {
                    v0_6 = new q21();
                }
            } else {
                v0_6 = new r21();
            }
            l21.c = v0_6;
        }
        int v3_2 = v0_6;
        int v2_7 = new yn(v3_2, v4_1, v5_1, this, v7_0, 1);
        android.view.ViewGroup v7_1 = ((android.view.ViewGroup) v7_0);
        while (v9 < v7_1.getChildCount()) {
            rq v10_4 = (v9 + 1);
            m21 v0_16 = v7_1.getChildAt(v9);
            if (v0_16 == null) {
                rq v10_5 = new IndexOutOfBoundsException;
                v10_5();
                throw v10_5;
            } else {
                if (!(v0_16.getTag() instanceof m21)) {
                    v9 = v10_4;
                }
            }
            v2_7.run();
            rq v10_6 = this.getWindow();
            v10_6.getClass();
            v3_2.a(v10_6);
            rq v10_7 = new rq;
            v10_7(14, this, v11_2);
            xh0.a(this, new li0(-219308635, v10_7, 1));
            return;
        }
        m21 v0_15 = new k21(v2_7, v7_1.getContext());
        v0_15.setTag(v3_2);
        v0_15.setVisibility(8);
        v0_15.setWillNotDraw(1);
        v7_1.addView(v0_15);
        v2_7.run();
        v10_6 = this.getWindow();
        v10_6.getClass();
        v3_2.a(v10_6);
        v10_7 = new rq;
        v10_7(14, this, v11_2);
        xh0.a(this, new li0(-219308635, v10_7, 1));
        return;
    }

    public final void onDestroy()
    {
        com.umeng.analytics.MobclickAgent.onKillProcess(this);
        super.onDestroy();
        return;
    }

    public final void onNewIntent(android.content.Intent p3)
    {
        p3.getClass();
        if (p3.getBooleanExtra("intent_extra_exit_flag", 0)) {
            this.finish();
        }
        super.onNewIntent(p3);
        return;
    }

    public final void onPause()
    {
        com.umeng.analytics.MobclickAgent.onPause(this);
        super.onPause();
        return;
    }

    public final void onResume()
    {
        com.umeng.analytics.MobclickAgent.onResume(this);
        super.onResume();
        return;
    }

    public final void onStop()
    {
        com.umeng.analytics.MobclickAgent.onKillProcess(this);
        super.onStop();
        return;
    }
}
