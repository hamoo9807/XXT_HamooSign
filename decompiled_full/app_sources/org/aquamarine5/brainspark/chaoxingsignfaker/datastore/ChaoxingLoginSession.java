package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class ChaoxingLoginSession extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSessionOrBuilder {
    public static final int COOKIES_FIELD_NUMBER = 1;
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession DEFAULT_INSTANCE = None;
    public static final int FACEIMAGEOBJECTID_FIELD_NUMBER = 4;
    private static volatile ua3 PARSER = None;
    public static final int PASSWORD_FIELD_NUMBER = 3;
    public static final int PHONENUMBER_FIELD_NUMBER = 2;
    private sz1 cookies_;
    private String faceImageObjectId_;
    private String password_;
    private String phoneNumber_;

    static ChaoxingLoginSession()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession, v0_1);
        return;
    }

    private ChaoxingLoginSession()
    {
        this.cookies_ = pk1.emptyProtobufList();
        this.phoneNumber_ = "";
        this.password_ = "";
        this.faceImageObjectId_ = "";
        return;
    }

    private void addAllCookies(Iterable p1)
    {
        this.ensureCookiesIsMutable();
        l0.addAll(p1, this.cookies_);
        return;
    }

    private void addCookies(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p2)
    {
        p2.getClass();
        this.ensureCookiesIsMutable();
        this.cookies_.add(p1, p2);
        return;
    }

    private void addCookies(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p1)
    {
        p1.getClass();
        this.ensureCookiesIsMutable();
        this.cookies_.add(p1);
        return;
    }

    private void clearCookies()
    {
        this.cookies_ = pk1.emptyProtobufList();
        return;
    }

    private void clearFaceImageObjectId()
    {
        this.faceImageObjectId_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.getDefaultInstance().getFaceImageObjectId();
        return;
    }

    private void clearPassword()
    {
        this.password_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.getDefaultInstance().getPassword();
        return;
    }

    private void clearPhoneNumber()
    {
        this.phoneNumber_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.getDefaultInstance().getPhoneNumber();
        return;
    }

    private void ensureCookiesIsMutable()
    {
        sz1 v0_0 = this.cookies_;
        if (!((r0) v0_0).a) {
            this.cookies_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0, Iterable p1)
    {
        p0.addAllCookies(p1);
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p2)
    {
        p0.addCookies(p1, p2);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p1)
    {
        p0.addCookies(p1);
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0)
    {
        p0.clearCookies();
        return;
    }

    public static bridge synthetic void j(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0)
    {
        p0.clearFaceImageObjectId();
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0)
    {
        p0.clearPassword();
        return;
    }

    public static bridge synthetic void l(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0)
    {
        p0.clearPhoneNumber();
        return;
    }

    public static bridge synthetic void m(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0, int p1)
    {
        p0.removeCookies(p1);
        return;
    }

    public static bridge synthetic void n(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p2)
    {
        p0.setCookies(p1, p2);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static bridge synthetic void o(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0, String p1)
    {
        p0.setFaceImageObjectId(p1);
        return;
    }

    public static bridge synthetic void p(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0, du p1)
    {
        p0.setFaceImageObjectIdBytes(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE.getParserForType();
    }

    public static bridge synthetic void q(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0, String p1)
    {
        p0.setPassword(p1);
        return;
    }

    public static bridge synthetic void r(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0, du p1)
    {
        p0.setPasswordBytes(p1);
        return;
    }

    private void removeCookies(int p1)
    {
        this.ensureCookiesIsMutable();
        this.cookies_.remove(p1);
        return;
    }

    public static bridge synthetic void s(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0, String p1)
    {
        p0.setPhoneNumber(p1);
        return;
    }

    private void setCookies(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p2)
    {
        p2.getClass();
        this.ensureCookiesIsMutable();
        this.cookies_.set(p1, p2);
        return;
    }

    private void setFaceImageObjectId(String p1)
    {
        p1.getClass();
        this.faceImageObjectId_ = p1;
        return;
    }

    private void setFaceImageObjectIdBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.faceImageObjectId_ = p1.q();
        return;
    }

    private void setPassword(String p1)
    {
        p1.getClass();
        this.password_ = p1;
        return;
    }

    private void setPasswordBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.password_ = p1.q();
        return;
    }

    private void setPhoneNumber(String p1)
    {
        p1.getClass();
        this.phoneNumber_ = p1;
        return;
    }

    private void setPhoneNumberBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.phoneNumber_ = p1.q();
        return;
    }

    public static bridge synthetic void t(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p0, du p1)
    {
        p0.setPhoneNumberBytes(p1);
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession u()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE;
    }

    public final Object dynamicMethod(ok1 p2, Object p3, Object p4)
    {
        lk1 v1_0 = p2.ordinal();
        if (v1_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v1_0 == 2) {
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0001\u0000\u0001\u001b\u0002\u0208\u0003\u0208\u0004\u0208", new Object[] {"cookies_", org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie, "phoneNumber_", "password_", "faceImageObjectId_"}));
            } else {
                if (v1_0 == 3) {
                    lk1 v1_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession;
                    v1_3();
                    return v1_3;
                } else {
                    if (v1_0 == 4) {
                        lk1 v1_4 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder;
                        v1_4(0);
                        return v1_4;
                    } else {
                        if (v1_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE;
                        } else {
                            if (v1_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v1_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.PARSER;
                                if (v1_8 != null) {
                                    return v1_8;
                                } else {
                                    lk1 v1_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.PARSER;
                                    if (v1_9 == null) {
                                        v1_9 = new lk1;
                                        v1_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.PARSER = v1_9;
                                    }
                                    return v1_9;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie getCookies(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) this.cookies_.get(p1));
    }

    public int getCookiesCount()
    {
        return this.cookies_.size();
    }

    public java.util.List getCookiesList()
    {
        return this.cookies_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookieOrBuilder getCookiesOrBuilder(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookieOrBuilder) this.cookies_.get(p1));
    }

    public java.util.List getCookiesOrBuilderList()
    {
        return this.cookies_;
    }

    public String getFaceImageObjectId()
    {
        return this.faceImageObjectId_;
    }

    public du getFaceImageObjectIdBytes()
    {
        return du.g(this.faceImageObjectId_);
    }

    public String getPassword()
    {
        return this.password_;
    }

    public du getPasswordBytes()
    {
        return du.g(this.password_);
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber_;
    }

    public du getPhoneNumberBytes()
    {
        return du.g(this.phoneNumber_);
    }
}
