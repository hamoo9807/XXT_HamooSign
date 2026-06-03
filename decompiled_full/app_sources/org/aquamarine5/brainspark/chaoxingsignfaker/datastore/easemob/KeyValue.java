package org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob;
public final class KeyValue extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValueOrBuilder {
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue DEFAULT_INSTANCE = None;
    public static final int DVALUE_FIELD_NUMBER = 5;
    public static final int FVALUE_FIELD_NUMBER = 4;
    public static final int KEY_FIELD_NUMBER = 1;
    public static final int LVALUE_FIELD_NUMBER = 3;
    private static volatile ua3 PARSER = None;
    public static final int STRINGVALUE_FIELD_NUMBER = 6;
    public static final int TYPE_FIELD_NUMBER = 2;
    private double dValue_;
    private float fValue_;
    private String key_;
    private long lValue_;
    private String stringValue_;
    private int type_;

    static KeyValue()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue, v0_1);
        return;
    }

    private KeyValue()
    {
        this.key_ = "";
        this.stringValue_ = "";
        return;
    }

    private void clearDValue()
    {
        this.dValue_ = 0;
        return;
    }

    private void clearFValue()
    {
        this.fValue_ = 0;
        return;
    }

    private void clearKey()
    {
        this.key_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.getDefaultInstance().getKey();
        return;
    }

    private void clearLValue()
    {
        this.lValue_ = 0;
        return;
    }

    private void clearStringValue()
    {
        this.stringValue_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.getDefaultInstance().getStringValue();
        return;
    }

    private void clearType()
    {
        this.type_ = 0;
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0)
    {
        p0.clearDValue();
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0)
    {
        p0.clearFValue();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0)
    {
        p0.clearKey();
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0)
    {
        p0.clearLValue();
        return;
    }

    public static bridge synthetic void j(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0)
    {
        p0.clearStringValue();
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0)
    {
        p0.clearType();
        return;
    }

    public static bridge synthetic void l(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0, double p1)
    {
        p0.setDValue(p1);
        return;
    }

    public static bridge synthetic void m(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0, float p1)
    {
        p0.setFValue(p1);
        return;
    }

    public static bridge synthetic void n(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0, String p1)
    {
        p0.setKey(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static bridge synthetic void o(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0, du p1)
    {
        p0.setKeyBytes(p1);
        return;
    }

    public static bridge synthetic void p(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0, long p1)
    {
        p0.setLValue(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE.getParserForType();
    }

    public static bridge synthetic void q(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0, String p1)
    {
        p0.setStringValue(p1);
        return;
    }

    public static bridge synthetic void r(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p0, du p1)
    {
        p0.setStringValueBytes(p1);
        return;
    }

    public static bridge synthetic void s(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p1)
    {
        p1.setType(p0);
        return;
    }

    private void setDValue(double p1)
    {
        this.dValue_ = p1;
        return;
    }

    private void setFValue(float p1)
    {
        this.fValue_ = p1;
        return;
    }

    private void setKey(String p1)
    {
        p1.getClass();
        this.key_ = p1;
        return;
    }

    private void setKeyBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.key_ = p1.q();
        return;
    }

    private void setLValue(long p1)
    {
        this.lValue_ = p1;
        return;
    }

    private void setStringValue(String p1)
    {
        p1.getClass();
        this.stringValue_ = p1;
        return;
    }

    private void setStringValueBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.stringValue_ = p1.q();
        return;
    }

    private void setType(int p1)
    {
        this.type_ = p1;
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue t()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE;
    }

    public final Object dynamicMethod(ok1 p7, Object p8, Object p9)
    {
        lk1 v6_0 = p7.ordinal();
        if (v6_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v6_0 == 2) {
                String v2 = "lValue_";
                String v4 = "dValue_";
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE, "\u0000\u0006\u0000\u0000\u0001\u0006\u0006\u0000\u0000\u0000\u0001\u0208\u0002\u0004\u0003\u0002\u0004\u0001\u0005\u0000\u0006\u0208", new Object[] {"key_", "stringValue_"}));
            } else {
                if (v6_0 == 3) {
                    lk1 v6_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue;
                    v6_3();
                    return v6_3;
                } else {
                    if (v6_0 == 4) {
                        lk1 v6_4 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue$Builder;
                        v6_4(0);
                        return v6_4;
                    } else {
                        if (v6_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE;
                        } else {
                            if (v6_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v6_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.PARSER;
                                if (v6_8 != null) {
                                    return v6_8;
                                } else {
                                    lk1 v6_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.PARSER;
                                    if (v6_9 == null) {
                                        v6_9 = new lk1;
                                        v6_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue.PARSER = v6_9;
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

    public double getDValue()
    {
        return this.dValue_;
    }

    public float getFValue()
    {
        return this.fValue_;
    }

    public String getKey()
    {
        return this.key_;
    }

    public du getKeyBytes()
    {
        return du.g(this.key_);
    }

    public long getLValue()
    {
        return this.lValue_;
    }

    public String getStringValue()
    {
        return this.stringValue_;
    }

    public du getStringValueBytes()
    {
        return du.g(this.stringValue_);
    }

    public int getType()
    {
        return this.type_;
    }
}
