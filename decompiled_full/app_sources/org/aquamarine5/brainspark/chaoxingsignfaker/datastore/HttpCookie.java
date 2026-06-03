package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class HttpCookie extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookieOrBuilder {
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie DEFAULT_INSTANCE = None;
    public static final int HOST_FIELD_NUMBER = 1;
    public static final int NAME_FIELD_NUMBER = 2;
    private static volatile ua3 PARSER = None;
    public static final int VALUE_FIELD_NUMBER = 3;
    private String host_;
    private String name_;
    private String value_;

    static HttpCookie()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie, v0_1);
        return;
    }

    private HttpCookie()
    {
        this.host_ = "";
        this.name_ = "";
        this.value_ = "";
        return;
    }

    private void clearHost()
    {
        this.host_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.getDefaultInstance().getHost();
        return;
    }

    private void clearName()
    {
        this.name_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.getDefaultInstance().getName();
        return;
    }

    private void clearValue()
    {
        this.value_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.getDefaultInstance().getValue();
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p0)
    {
        p0.clearHost();
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p0)
    {
        p0.clearName();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p0)
    {
        p0.clearValue();
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p0, String p1)
    {
        p0.setHost(p1);
        return;
    }

    public static bridge synthetic void j(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p0, du p1)
    {
        p0.setHostBytes(p1);
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p0, String p1)
    {
        p0.setName(p1);
        return;
    }

    public static bridge synthetic void l(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p0, du p1)
    {
        p0.setNameBytes(p1);
        return;
    }

    public static bridge synthetic void m(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p0, String p1)
    {
        p0.setValue(p1);
        return;
    }

    public static bridge synthetic void n(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p0, du p1)
    {
        p0.setValueBytes(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie o()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE.getParserForType();
    }

    private void setHost(String p1)
    {
        p1.getClass();
        this.host_ = p1;
        return;
    }

    private void setHostBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.host_ = p1.q();
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

    private void setValue(String p1)
    {
        p1.getClass();
        this.value_ = p1;
        return;
    }

    private void setValueBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.value_ = p1.q();
        return;
    }

    public final Object dynamicMethod(ok1 p1, Object p2, Object p3)
    {
        lk1 v0_0 = p1.ordinal();
        if (v0_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v0_0 == 2) {
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0208\u0002\u0208\u0003\u0208", new Object[] {"host_", "name_", "value_"}));
            } else {
                if (v0_0 == 3) {
                    lk1 v0_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie;
                    v0_3();
                    return v0_3;
                } else {
                    if (v0_0 == 4) {
                        lk1 v0_4 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie$Builder;
                        v0_4(0);
                        return v0_4;
                    } else {
                        if (v0_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE;
                        } else {
                            if (v0_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v0_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.PARSER;
                                if (v0_8 != null) {
                                    return v0_8;
                                } else {
                                    lk1 v0_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.PARSER;
                                    if (v0_9 == null) {
                                        v0_9 = new lk1;
                                        v0_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie.PARSER = v0_9;
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

    public String getHost()
    {
        return this.host_;
    }

    public du getHostBytes()
    {
        return du.g(this.host_);
    }

    public String getName()
    {
        return this.name_;
    }

    public du getNameBytes()
    {
        return du.g(this.name_);
    }

    public String getValue()
    {
        return this.value_;
    }

    public du getValueBytes()
    {
        return du.g(this.value_);
    }
}
