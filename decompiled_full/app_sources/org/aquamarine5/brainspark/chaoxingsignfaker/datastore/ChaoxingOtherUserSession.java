package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class ChaoxingOtherUserSession extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSessionOrBuilder {
    public static final int COOKIES_FIELD_NUMBER = 1;
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession DEFAULT_INSTANCE = None;
    public static final int FACEIMAGEOBJECTID_FIELD_NUMBER = 6;
    public static final int ISOBSOLETESESSION_FIELD_NUMBER = 7;
    public static final int NAME_FIELD_NUMBER = 4;
    private static volatile ua3 PARSER = None;
    public static final int PASSWORD_FIELD_NUMBER = 3;
    public static final int PHONENUMBER_FIELD_NUMBER = 2;
    public static final int TAGS_FIELD_NUMBER = 5;
    private sz1 cookies_;
    private String faceImageObjectId_;
    private boolean isObsoleteSession_;
    private String name_;
    private String password_;
    private String phoneNumber_;
    private int tagsMemoizedSerializedSize;
    private qz1 tags_;

    static ChaoxingOtherUserSession()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession, v0_1);
        return;
    }

    private ChaoxingOtherUserSession()
    {
        this.tagsMemoizedSerializedSize = -1;
        this.cookies_ = pk1.emptyProtobufList();
        this.phoneNumber_ = "";
        this.password_ = "";
        this.name_ = "";
        this.tags_ = pk1.emptyIntList();
        this.faceImageObjectId_ = "";
        return;
    }

    public static bridge synthetic void A(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, String p1)
    {
        p0.setPhoneNumber(p1);
        return;
    }

    public static bridge synthetic void B(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, du p1)
    {
        p0.setPhoneNumberBytes(p1);
        return;
    }

    public static bridge synthetic void C(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, int p1, int p2)
    {
        p0.setTags(p1, p2);
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession D()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE;
    }

    private void addAllCookies(Iterable p1)
    {
        this.ensureCookiesIsMutable();
        l0.addAll(p1, this.cookies_);
        return;
    }

    private void addAllTags(Iterable p1)
    {
        this.ensureTagsIsMutable();
        l0.addAll(p1, this.tags_);
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

    private void addTags(int p1)
    {
        this.ensureTagsIsMutable();
        ((ly1) this.tags_).c(p1);
        return;
    }

    private void clearCookies()
    {
        this.cookies_ = pk1.emptyProtobufList();
        return;
    }

    private void clearFaceImageObjectId()
    {
        this.faceImageObjectId_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.getDefaultInstance().getFaceImageObjectId();
        return;
    }

    private void clearIsObsoleteSession()
    {
        this.isObsoleteSession_ = 0;
        return;
    }

    private void clearName()
    {
        this.name_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.getDefaultInstance().getName();
        return;
    }

    private void clearPassword()
    {
        this.password_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.getDefaultInstance().getPassword();
        return;
    }

    private void clearPhoneNumber()
    {
        this.phoneNumber_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.getDefaultInstance().getPhoneNumber();
        return;
    }

    private void clearTags()
    {
        this.tags_ = pk1.emptyIntList();
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

    private void ensureTagsIsMutable()
    {
        qz1 v0_0 = this.tags_;
        if (!((r0) v0_0).a) {
            this.tags_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, Iterable p1)
    {
        p0.addAllCookies(p1);
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, Iterable p1)
    {
        p0.addAllTags(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p2)
    {
        p0.addCookies(p1, p2);
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p1)
    {
        p0.addCookies(p1);
        return;
    }

    public static bridge synthetic void j(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p1)
    {
        p1.addTags(p0);
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0)
    {
        p0.clearCookies();
        return;
    }

    public static bridge synthetic void l(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0)
    {
        p0.clearFaceImageObjectId();
        return;
    }

    public static bridge synthetic void m(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0)
    {
        p0.clearIsObsoleteSession();
        return;
    }

    public static bridge synthetic void n(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0)
    {
        p0.clearName();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static bridge synthetic void o(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0)
    {
        p0.clearPassword();
        return;
    }

    public static bridge synthetic void p(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0)
    {
        p0.clearPhoneNumber();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE.getParserForType();
    }

    public static bridge synthetic void q(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0)
    {
        p0.clearTags();
        return;
    }

    public static bridge synthetic void r(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p1)
    {
        p1.removeCookies(p0);
        return;
    }

    private void removeCookies(int p1)
    {
        this.ensureCookiesIsMutable();
        this.cookies_.remove(p1);
        return;
    }

    public static bridge synthetic void s(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p2)
    {
        p0.setCookies(p1, p2);
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

    private void setIsObsoleteSession(boolean p1)
    {
        this.isObsoleteSession_ = p1;
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

    private void setTags(int p1, int p2)
    {
        this.ensureTagsIsMutable();
        ((ly1) this.tags_).h(p1, p2);
        return;
    }

    public static bridge synthetic void t(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, String p1)
    {
        p0.setFaceImageObjectId(p1);
        return;
    }

    public static bridge synthetic void u(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, du p1)
    {
        p0.setFaceImageObjectIdBytes(p1);
        return;
    }

    public static bridge synthetic void v(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, boolean p1)
    {
        p0.setIsObsoleteSession(p1);
        return;
    }

    public static bridge synthetic void w(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, String p1)
    {
        p0.setName(p1);
        return;
    }

    public static bridge synthetic void x(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, du p1)
    {
        p0.setNameBytes(p1);
        return;
    }

    public static bridge synthetic void y(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, String p1)
    {
        p0.setPassword(p1);
        return;
    }

    public static bridge synthetic void z(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p0, du p1)
    {
        p0.setPasswordBytes(p1);
        return;
    }

    public final Object dynamicMethod(ok1 p9, Object p10, Object p11)
    {
        lk1 v8_0 = p9.ordinal();
        if (v8_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v8_0 == 2) {
                String v2 = "phoneNumber_";
                String v4 = "name_";
                String v6 = "faceImageObjectId_";
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE, "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0002\u0000\u0001\u001b\u0002\u0208\u0003\u0208\u0004\u0208\u0005+\u0006\u0208\u0007\u0007", new Object[] {"cookies_", "isObsoleteSession_"}));
            } else {
                if (v8_0 == 3) {
                    lk1 v8_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession;
                    v8_3();
                    return v8_3;
                } else {
                    if (v8_0 == 4) {
                        lk1 v8_5 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession$Builder;
                        v8_5(0);
                        return v8_5;
                    } else {
                        if (v8_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE;
                        } else {
                            if (v8_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v8_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.PARSER;
                                if (v8_8 != null) {
                                    return v8_8;
                                } else {
                                    lk1 v8_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.PARSER;
                                    if (v8_9 == null) {
                                        v8_9 = new lk1;
                                        v8_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession.PARSER = v8_9;
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

    public boolean getIsObsoleteSession()
    {
        return this.isObsoleteSession_;
    }

    public String getName()
    {
        return this.name_;
    }

    public du getNameBytes()
    {
        return du.g(this.name_);
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

    public int getTags(int p1)
    {
        return ((ly1) this.tags_).f(p1);
    }

    public int getTagsCount()
    {
        return this.tags_.size();
    }

    public java.util.List getTagsList()
    {
        return this.tags_;
    }
}
