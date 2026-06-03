package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class ChaoxingLoginSession$Builder extends kk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSessionOrBuilder {

    private ChaoxingLoginSession$Builder()
    {
        super(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.u());
        return;
    }

    public synthetic ChaoxingLoginSession$Builder(int p1)
    {
        return;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder addAllCookies(Iterable p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.f(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder addCookies(int p2, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie$Builder p3)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.g(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2, ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) p3.build()));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder addCookies(int p2, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p3)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.g(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2, p3);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder addCookies(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie$Builder p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.h(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) p2.build()));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder addCookies(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.h(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder clearCookies()
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.i(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder clearFaceImageObjectId()
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.j(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder clearPassword()
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.k(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder clearPhoneNumber()
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.l(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie getCookies(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance).getCookies(p1);
    }

    public int getCookiesCount()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance).getCookiesCount();
    }

    public java.util.List getCookiesList()
    {
        return java.util.Collections.unmodifiableList(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance).getCookiesList());
    }

    public String getFaceImageObjectId()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance).getFaceImageObjectId();
    }

    public du getFaceImageObjectIdBytes()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance).getFaceImageObjectIdBytes();
    }

    public String getPassword()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance).getPassword();
    }

    public du getPasswordBytes()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance).getPasswordBytes();
    }

    public String getPhoneNumber()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance).getPhoneNumber();
    }

    public du getPhoneNumberBytes()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance).getPhoneNumberBytes();
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder removeCookies(int p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.m(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder setCookies(int p2, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie$Builder p3)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.n(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2, ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie) p3.build()));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder setCookies(int p2, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.HttpCookie p3)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.n(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2, p3);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder setFaceImageObjectId(String p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.o(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder setFaceImageObjectIdBytes(du p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.p(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder setPassword(String p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.q(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder setPasswordBytes(du p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.r(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder setPhoneNumber(String p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.s(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder setPhoneNumberBytes(du p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.t(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) this.instance), p2);
        return this;
    }
}
