package org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob;
public final class Meta extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MetaOrBuilder {
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta DEFAULT_INSTANCE = None;
    public static final int FIELD1_FIELD_NUMBER = 1;
    public static final int FIELD2_FIELD_NUMBER = 2;
    public static final int FIELD3_FIELD_NUMBER = 3;
    public static final int FIELD4_FIELD_NUMBER = 4;
    public static final int FIELD5_FIELD_NUMBER = 5;
    public static final int FIELD6_FIELD_NUMBER = 6;
    public static final int FIELD7_FIELD_NUMBER = 7;
    private static volatile ua3 PARSER;
    private int bitField0_;
    private long field1_;
    private org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID field2_;
    private org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID field3_;
    private long field4_;
    private int field5_;
    private du field6_;
    private int field7_;

    static Meta()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta, v0_1);
        return;
    }

    private Meta()
    {
        this.field6_ = du.b;
        return;
    }

    private void clearField1()
    {
        this.field1_ = 0;
        return;
    }

    private void clearField2()
    {
        this.field2_ = 0;
        this.bitField0_ = (this.bitField0_ & -2);
        return;
    }

    private void clearField3()
    {
        this.field3_ = 0;
        this.bitField0_ = (this.bitField0_ & -3);
        return;
    }

    private void clearField4()
    {
        this.field4_ = 0;
        return;
    }

    private void clearField5()
    {
        this.field5_ = 0;
        return;
    }

    private void clearField6()
    {
        this.field6_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.getDefaultInstance().getField6();
        return;
    }

    private void clearField7()
    {
        this.field7_ = 0;
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0)
    {
        p0.clearField1();
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0)
    {
        p0.clearField2();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0)
    {
        p0.clearField3();
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0)
    {
        p0.clearField4();
        return;
    }

    public static bridge synthetic void j(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0)
    {
        p0.clearField5();
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0)
    {
        p0.clearField6();
        return;
    }

    public static bridge synthetic void l(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0)
    {
        p0.clearField7();
        return;
    }

    public static bridge synthetic void m(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        p0.mergeField2(p1);
        return;
    }

    private void mergeField2(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p3)
    {
        p3.getClass();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder v0_0 = this.field2_;
        if ((v0_0 == null) || (v0_0 == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.getDefaultInstance())) {
            this.field2_ = p3;
        } else {
            this.field2_ = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.newBuilder(this.field2_).mergeFrom(p3)).buildPartial());
        }
        this.bitField0_ = (this.bitField0_ | 1);
        return;
    }

    private void mergeField3(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p3)
    {
        p3.getClass();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder v0_0 = this.field3_;
        if ((v0_0 == null) || (v0_0 == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.getDefaultInstance())) {
            this.field3_ = p3;
        } else {
            this.field3_ = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.newBuilder(this.field3_).mergeFrom(p3)).buildPartial());
        }
        this.bitField0_ = (this.bitField0_ | 2);
        return;
    }

    public static bridge synthetic void n(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        p0.mergeField3(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static bridge synthetic void o(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0, long p1)
    {
        p0.setField1(p1);
        return;
    }

    public static bridge synthetic void p(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        p0.setField2(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE.getParserForType();
    }

    public static bridge synthetic void q(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        p0.setField3(p1);
        return;
    }

    public static bridge synthetic void r(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0, long p1)
    {
        p0.setField4(p1);
        return;
    }

    public static bridge synthetic void s(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace p1)
    {
        p0.setField5(p1);
        return;
    }

    private void setField1(long p1)
    {
        this.field1_ = p1;
        return;
    }

    private void setField2(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        p1.getClass();
        this.field2_ = p1;
        this.bitField0_ = (this.bitField0_ | 1);
        return;
    }

    private void setField3(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        p1.getClass();
        this.field3_ = p1;
        this.bitField0_ = (this.bitField0_ | 2);
        return;
    }

    private void setField4(long p1)
    {
        this.field4_ = p1;
        return;
    }

    private void setField5(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace p1)
    {
        this.field5_ = p1.getNumber();
        return;
    }

    private void setField5Value(int p1)
    {
        this.field5_ = p1;
        return;
    }

    private void setField6(du p1)
    {
        p1.getClass();
        this.field6_ = p1;
        return;
    }

    private void setField7(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType p1)
    {
        this.field7_ = p1.getNumber();
        return;
    }

    private void setField7Value(int p1)
    {
        this.field7_ = p1;
        return;
    }

    public static bridge synthetic void t(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0, int p1)
    {
        p0.setField5Value(p1);
        return;
    }

    public static bridge synthetic void u(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0, du p1)
    {
        p0.setField6(p1);
        return;
    }

    public static bridge synthetic void v(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType p1)
    {
        p0.setField7(p1);
        return;
    }

    public static bridge synthetic void w(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta p0, int p1)
    {
        p0.setField7Value(p1);
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta x()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE;
    }

    public final Object dynamicMethod(ok1 p9, Object p10, Object p11)
    {
        lk1 v8_0 = p9.ordinal();
        if (v8_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v8_0 == 2) {
                String v2 = "field2_";
                String v4 = "field4_";
                String v6 = "field6_";
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE, "\u0000\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001\u0003\u0002\u1009\u0000\u0003\u1009\u0001\u0004\u0003\u0005\u000c\u0006\n\u0007\u000c", new Object[] {"bitField0_", "field7_"}));
            } else {
                if (v8_0 == 3) {
                    lk1 v8_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta;
                    v8_3();
                    return v8_3;
                } else {
                    if (v8_0 == 4) {
                        lk1 v8_5 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta$Builder;
                        v8_5(0);
                        return v8_5;
                    } else {
                        if (v8_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE;
                        } else {
                            if (v8_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v8_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.PARSER;
                                if (v8_8 != null) {
                                    return v8_8;
                                } else {
                                    lk1 v8_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.PARSER;
                                    if (v8_9 == null) {
                                        v8_9 = new lk1;
                                        v8_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Meta.PARSER = v8_9;
                                    }
                                    return v8_9;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public long getField1()
    {
        return this.field1_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID getField2()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID v0_1 = this.field2_;
        if (v0_1 == null) {
            v0_1 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.getDefaultInstance();
        }
        return v0_1;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID getField3()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID v0_1 = this.field3_;
        if (v0_1 == null) {
            v0_1 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.getDefaultInstance();
        }
        return v0_1;
    }

    public long getField4()
    {
        return this.field4_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace getField5()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace v0_2 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.forNumber(this.field5_);
        if (v0_2 == null) {
            v0_2 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.UNRECOGNIZED;
        }
        return v0_2;
    }

    public int getField5Value()
    {
        return this.field5_;
    }

    public du getField6()
    {
        return this.field6_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType getField7()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType v0_2 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.forNumber(this.field7_);
        if (v0_2 == null) {
            v0_2 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.UNRECOGNIZED;
        }
        return v0_2;
    }

    public int getField7Value()
    {
        return this.field7_;
    }

    public boolean hasField2()
    {
        if ((this.bitField0_ & 1) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean hasField3()
    {
        if ((this.bitField0_ & 2) == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
