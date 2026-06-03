package org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob;
public final class JID extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JIDOrBuilder {
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID DEFAULT_INSTANCE = None;
    public static final int FIELD2_FIELD_NUMBER = 2;
    public static final int FIELD3_FIELD_NUMBER = 3;
    public static final int FIELD4_FIELD_NUMBER = 4;
    public static final int NAME_FIELD_NUMBER = 1;
    private static volatile ua3 PARSER;
    private String field2_;
    private String field3_;
    private String field4_;
    private String name_;

    static JID()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID, v0_1);
        return;
    }

    private JID()
    {
        this.name_ = "";
        this.field2_ = "";
        this.field3_ = "";
        this.field4_ = "";
        return;
    }

    private void clearField2()
    {
        this.field2_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.getDefaultInstance().getField2();
        return;
    }

    private void clearField3()
    {
        this.field3_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.getDefaultInstance().getField3();
        return;
    }

    private void clearField4()
    {
        this.field4_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.getDefaultInstance().getField4();
        return;
    }

    private void clearName()
    {
        this.name_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.getDefaultInstance().getName();
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p0)
    {
        p0.clearField2();
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p0)
    {
        p0.clearField3();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p0)
    {
        p0.clearField4();
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p0)
    {
        p0.clearName();
        return;
    }

    public static bridge synthetic void j(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p0, String p1)
    {
        p0.setField2(p1);
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p0, du p1)
    {
        p0.setField2Bytes(p1);
        return;
    }

    public static bridge synthetic void l(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p0, String p1)
    {
        p0.setField3(p1);
        return;
    }

    public static bridge synthetic void m(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p0, du p1)
    {
        p0.setField3Bytes(p1);
        return;
    }

    public static bridge synthetic void n(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p0, String p1)
    {
        p0.setField4(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static bridge synthetic void o(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p0, du p1)
    {
        p0.setField4Bytes(p1);
        return;
    }

    public static bridge synthetic void p(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p0, String p1)
    {
        p0.setName(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE.getParserForType();
    }

    public static bridge synthetic void q(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p0, du p1)
    {
        p0.setNameBytes(p1);
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID r()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE;
    }

    private void setField2(String p1)
    {
        p1.getClass();
        this.field2_ = p1;
        return;
    }

    private void setField2Bytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.field2_ = p1.q();
        return;
    }

    private void setField3(String p1)
    {
        p1.getClass();
        this.field3_ = p1;
        return;
    }

    private void setField3Bytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.field3_ = p1.q();
        return;
    }

    private void setField4(String p1)
    {
        p1.getClass();
        this.field4_ = p1;
        return;
    }

    private void setField4Bytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.field4_ = p1.q();
        return;
    }

    private void setName(String p1)
    {
        p1.getClass();
        this.name_ = p1;
        return;
    }

    private void setNameBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.name_ = p1.q();
        return;
    }

    public final Object dynamicMethod(ok1 p1, Object p2, Object p3)
    {
        lk1 v0_0 = p1.ordinal();
        if (v0_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v0_0 == 2) {
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0208\u0002\u0208\u0003\u0208\u0004\u0208", new Object[] {"name_", "field2_", "field3_", "field4_"}));
            } else {
                if (v0_0 == 3) {
                    lk1 v0_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID;
                    v0_3();
                    return v0_3;
                } else {
                    if (v0_0 == 4) {
                        lk1 v0_4 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder;
                        v0_4(0);
                        return v0_4;
                    } else {
                        if (v0_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE;
                        } else {
                            if (v0_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v0_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.PARSER;
                                if (v0_8 != null) {
                                    return v0_8;
                                } else {
                                    lk1 v0_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.PARSER;
                                    if (v0_9 == null) {
                                        v0_9 = new lk1;
                                        v0_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.PARSER = v0_9;
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

    public String getField2()
    {
        return this.field2_;
    }

    public du getField2Bytes()
    {
        return du.g(this.field2_);
    }

    public String getField3()
    {
        return this.field3_;
    }

    public du getField3Bytes()
    {
        return du.g(this.field3_);
    }

    public String getField4()
    {
        return this.field4_;
    }

    public du getField4Bytes()
    {
        return du.g(this.field4_);
    }

    public String getName()
    {
        return this.name_;
    }

    public du getNameBytes()
    {
        return du.g(this.name_);
    }
}
