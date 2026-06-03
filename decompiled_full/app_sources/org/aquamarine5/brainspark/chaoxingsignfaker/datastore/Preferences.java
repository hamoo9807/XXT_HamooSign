package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class Preferences extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.PreferencesOrBuilder {
    public static final int CUSTOMIZEDPACKAGENAME_FIELD_NUMBER = 5;
    public static final int CUSTOMIZEDUSERAGENT_FIELD_NUMBER = 4;
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences DEFAULT_INSTANCE = None;
    public static final int ISDEVELOPEDMODE_FIELD_NUMBER = 2;
    public static final int LASTSIGNEDLOCATION_FIELD_NUMBER = 3;
    public static final int NEVERASKSAVEFAVORITELOCATION_FIELD_NUMBER = 1;
    private static volatile ua3 PARSER;
    private int bitField0_;
    private String customizedPackageName_;
    private String customizedUserAgent_;
    private boolean isDevelopedMode_;
    private org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation lastSignedLocation_;
    private boolean neverAskSaveFavoriteLocation_;

    static Preferences()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences, v0_1);
        return;
    }

    private Preferences()
    {
        this.customizedUserAgent_ = "";
        this.customizedPackageName_ = "";
        return;
    }

    private void clearCustomizedPackageName()
    {
        this.customizedPackageName_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.getDefaultInstance().getCustomizedPackageName();
        return;
    }

    private void clearCustomizedUserAgent()
    {
        this.customizedUserAgent_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.getDefaultInstance().getCustomizedUserAgent();
        return;
    }

    private void clearIsDevelopedMode()
    {
        this.isDevelopedMode_ = 0;
        return;
    }

    private void clearLastSignedLocation()
    {
        this.lastSignedLocation_ = 0;
        this.bitField0_ = (this.bitField0_ & -2);
        return;
    }

    private void clearNeverAskSaveFavoriteLocation()
    {
        this.neverAskSaveFavoriteLocation_ = 0;
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0)
    {
        p0.clearCustomizedPackageName();
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0)
    {
        p0.clearCustomizedUserAgent();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0)
    {
        p0.clearIsDevelopedMode();
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0)
    {
        p0.clearLastSignedLocation();
        return;
    }

    public static bridge synthetic void j(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0)
    {
        p0.clearNeverAskSaveFavoriteLocation();
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p1)
    {
        p0.mergeLastSignedLocation(p1);
        return;
    }

    public static bridge synthetic void l(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0, String p1)
    {
        p0.setCustomizedPackageName(p1);
        return;
    }

    public static bridge synthetic void m(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0, du p1)
    {
        p0.setCustomizedPackageNameBytes(p1);
        return;
    }

    private void mergeLastSignedLocation(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p3)
    {
        p3.getClass();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation$Builder v0_0 = this.lastSignedLocation_;
        if ((v0_0 == null) || (v0_0 == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.getDefaultInstance())) {
            this.lastSignedLocation_ = p3;
        } else {
            this.lastSignedLocation_ = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.newBuilder(this.lastSignedLocation_).mergeFrom(p3)).buildPartial());
        }
        this.bitField0_ = (this.bitField0_ | 1);
        return;
    }

    public static bridge synthetic void n(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0, String p1)
    {
        p0.setCustomizedUserAgent(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static bridge synthetic void o(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0, du p1)
    {
        p0.setCustomizedUserAgentBytes(p1);
        return;
    }

    public static bridge synthetic void p(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0, boolean p1)
    {
        p0.setIsDevelopedMode(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE.getParserForType();
    }

    public static bridge synthetic void q(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p1)
    {
        p0.setLastSignedLocation(p1);
        return;
    }

    public static bridge synthetic void r(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p0, boolean p1)
    {
        p0.setNeverAskSaveFavoriteLocation(p1);
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences s()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE;
    }

    private void setCustomizedPackageName(String p1)
    {
        p1.getClass();
        this.customizedPackageName_ = p1;
        return;
    }

    private void setCustomizedPackageNameBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.customizedPackageName_ = p1.q();
        return;
    }

    private void setCustomizedUserAgent(String p1)
    {
        p1.getClass();
        this.customizedUserAgent_ = p1;
        return;
    }

    private void setCustomizedUserAgentBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.customizedUserAgent_ = p1.q();
        return;
    }

    private void setIsDevelopedMode(boolean p1)
    {
        this.isDevelopedMode_ = p1;
        return;
    }

    private void setLastSignedLocation(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p1)
    {
        p1.getClass();
        this.lastSignedLocation_ = p1;
        this.bitField0_ = (this.bitField0_ | 1);
        return;
    }

    private void setNeverAskSaveFavoriteLocation(boolean p1)
    {
        this.neverAskSaveFavoriteLocation_ = p1;
        return;
    }

    public final Object dynamicMethod(ok1 p7, Object p8, Object p9)
    {
        lk1 v6_0 = p7.ordinal();
        if (v6_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v6_0 == 2) {
                String v2 = "isDevelopedMode_";
                String v4 = "customizedUserAgent_";
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE, "\u0000\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001\u0007\u0002\u0007\u0003\u1009\u0000\u0004\u0208\u0005\u0208", new Object[] {"bitField0_", "customizedPackageName_"}));
            } else {
                if (v6_0 == 3) {
                    lk1 v6_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences;
                    v6_3();
                    return v6_3;
                } else {
                    if (v6_0 == 4) {
                        lk1 v6_4 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences$Builder;
                        v6_4(0);
                        return v6_4;
                    } else {
                        if (v6_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE;
                        } else {
                            if (v6_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v6_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.PARSER;
                                if (v6_8 != null) {
                                    return v6_8;
                                } else {
                                    lk1 v6_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.PARSER;
                                    if (v6_9 == null) {
                                        v6_9 = new lk1;
                                        v6_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.PARSER = v6_9;
                                    }
                                    return v6_9;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String getCustomizedPackageName()
    {
        return this.customizedPackageName_;
    }

    public du getCustomizedPackageNameBytes()
    {
        return du.g(this.customizedPackageName_);
    }

    public String getCustomizedUserAgent()
    {
        return this.customizedUserAgent_;
    }

    public du getCustomizedUserAgentBytes()
    {
        return du.g(this.customizedUserAgent_);
    }

    public boolean getIsDevelopedMode()
    {
        return this.isDevelopedMode_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation getLastSignedLocation()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation v0_1 = this.lastSignedLocation_;
        if (v0_1 == null) {
            v0_1 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.getDefaultInstance();
        }
        return v0_1;
    }

    public boolean getNeverAskSaveFavoriteLocation()
    {
        return this.neverAskSaveFavoriteLocation_;
    }

    public boolean hasLastSignedLocation()
    {
        if ((this.bitField0_ & 1) == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
