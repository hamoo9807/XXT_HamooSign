package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class ChaoxingLocation extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocationOrBuilder {
    public static final int ADDRESS_FIELD_NUMBER = 3;
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation DEFAULT_INSTANCE = None;
    public static final int LABEL_FIELD_NUMBER = 6;
    public static final int LATITUDE_FIELD_NUMBER = 4;
    public static final int LONGITUDE_FIELD_NUMBER = 5;
    private static volatile ua3 PARSER;
    private String address_;
    private String label_;
    private double latitude_;
    private double longitude_;

    static ChaoxingLocation()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation, v0_1);
        return;
    }

    private ChaoxingLocation()
    {
        this.address_ = "";
        this.label_ = "";
        return;
    }

    private void clearAddress()
    {
        this.address_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.getDefaultInstance().getAddress();
        return;
    }

    private void clearLabel()
    {
        this.label_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.getDefaultInstance().getLabel();
        return;
    }

    private void clearLatitude()
    {
        this.latitude_ = 0;
        return;
    }

    private void clearLongitude()
    {
        this.longitude_ = 0;
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p0)
    {
        p0.clearAddress();
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p0)
    {
        p0.clearLabel();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p0)
    {
        p0.clearLatitude();
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p0)
    {
        p0.clearLongitude();
        return;
    }

    public static bridge synthetic void j(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p0, String p1)
    {
        p0.setAddress(p1);
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p0, du p1)
    {
        p0.setAddressBytes(p1);
        return;
    }

    public static bridge synthetic void l(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p0, String p1)
    {
        p0.setLabel(p1);
        return;
    }

    public static bridge synthetic void m(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p0, du p1)
    {
        p0.setLabelBytes(p1);
        return;
    }

    public static bridge synthetic void n(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p0, double p1)
    {
        p0.setLatitude(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static bridge synthetic void o(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p0, double p1)
    {
        p0.setLongitude(p1);
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE.getParserForType();
    }

    private void setAddress(String p1)
    {
        p1.getClass();
        this.address_ = p1;
        return;
    }

    private void setAddressBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.address_ = p1.q();
        return;
    }

    private void setLabel(String p1)
    {
        p1.getClass();
        this.label_ = p1;
        return;
    }

    private void setLabelBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.label_ = p1.q();
        return;
    }

    private void setLatitude(double p1)
    {
        this.latitude_ = p1;
        return;
    }

    private void setLongitude(double p1)
    {
        this.longitude_ = p1;
        return;
    }

    public final Object dynamicMethod(ok1 p1, Object p2, Object p3)
    {
        lk1 v0_0 = p1.ordinal();
        if (v0_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v0_0 == 2) {
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0000\u0003\u0006\u0004\u0000\u0000\u0000\u0003\u0208\u0004\u0000\u0005\u0000\u0006\u0208", new Object[] {"address_", "latitude_", "longitude_", "label_"}));
            } else {
                if (v0_0 == 3) {
                    lk1 v0_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation;
                    v0_3();
                    return v0_3;
                } else {
                    if (v0_0 == 4) {
                        lk1 v0_4 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation$Builder;
                        v0_4(0);
                        return v0_4;
                    } else {
                        if (v0_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE;
                        } else {
                            if (v0_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v0_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.PARSER;
                                if (v0_8 != null) {
                                    return v0_8;
                                } else {
                                    lk1 v0_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.PARSER;
                                    if (v0_9 == null) {
                                        v0_9 = new lk1;
                                        v0_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation.PARSER = v0_9;
                                    }
                                    return v0_9;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String getAddress()
    {
        return this.address_;
    }

    public du getAddressBytes()
    {
        return du.g(this.address_);
    }

    public String getLabel()
    {
        return this.label_;
    }

    public du getLabelBytes()
    {
        return du.g(this.label_);
    }

    public double getLatitude()
    {
        return this.latitude_;
    }

    public double getLongitude()
    {
        return this.longitude_;
    }
}
