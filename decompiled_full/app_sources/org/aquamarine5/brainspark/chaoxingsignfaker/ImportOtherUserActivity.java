package org.aquamarine5.brainspark.chaoxingsignfaker;
public final class ImportOtherUserActivity extends wh0 {
    public static final synthetic int u;

    public ImportOtherUserActivity()
    {
        return;
    }

    public final void onCreate(android.os.Bundle p4)
    {
        super.onCreate(p4);
        na v4_4 = this.getPackageManager().getPackageInfo(this.getPackageName(), 128);
        li0 v0_8 = this.getPackageManager();
        int v1_0 = v4_4.applicationInfo;
        v1_0.getClass();
        if (!s21.q(v0_8.getApplicationLabel(v1_0).toString()).equals("181b23fb3bfa29181fcde41f72757e97")) {
            s21.s(this, v4_4);
            sa0 v3_1 = new sa0;
            v3_1();
            throw v3_1;
        } else {
            xh0.a(this, new li0(-1159341863, new na(8, this), 1));
            return;
        }
    }
}
