package org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob;
public final class Content extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.ContentOrBuilder {
    public static final int ACTION_FIELD_NUMBER = 16;
    public static final int ADDRESS_FIELD_NUMBER = 5;
    public static final int CUSTOMEVENT_FIELD_NUMBER = 19;
    public static final int CUSTOMEXTS_FIELD_NUMBER = 11;
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content DEFAULT_INSTANCE = None;
    public static final int DISPLAYNAME_FIELD_NUMBER = 8;
    public static final int DURATION_FIELD_NUMBER = 12;
    public static final int FIELD17_FIELD_NUMBER = 17;
    public static final int FIELD18_FIELD_NUMBER = 18;
    public static final int FILELENGTH_FIELD_NUMBER = 9;
    public static final int FILETYPE_FIELD_NUMBER = 14;
    public static final int LATITUDE_FIELD_NUMBER = 3;
    public static final int LONGITUDE_FIELD_NUMBER = 4;
    public static final int PARAMS_FIELD_NUMBER = 20;
    private static volatile ua3 PARSER = None;
    public static final int REMOTEPATH_FIELD_NUMBER = 6;
    public static final int SECRETKEY_FIELD_NUMBER = 7;
    public static final int SIZE_FIELD_NUMBER = 13;
    public static final int TEXT_FIELD_NUMBER = 2;
    public static final int THUMBNAILREMOTEPATH_FIELD_NUMBER = 10;
    public static final int THUMBNAILSECRETKEY_FIELD_NUMBER = 15;
    public static final int TYPE_FIELD_NUMBER = 1;
    private String action_;
    private String address_;
    private int bitField0_;
    private String customEvent_;
    private sz1 customExts_;
    private String displayName_;
    private int duration_;
    private int field17_;
    private org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size field18_;
    private int fileLength_;
    private String filetype_;
    private double latitude_;
    private double longitude_;
    private sz1 params_;
    private String remotePath_;
    private String secretKey_;
    private org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size size_;
    private String text_;
    private String thumbnailRemotePath_;
    private String thumbnailSecretKey_;
    private int type_;

    static Content()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content, v0_1);
        return;
    }

    private Content()
    {
        this.text_ = "";
        this.address_ = "";
        this.remotePath_ = "";
        this.secretKey_ = "";
        this.displayName_ = "";
        this.thumbnailRemotePath_ = "";
        this.customExts_ = pk1.emptyProtobufList();
        this.filetype_ = "";
        this.thumbnailSecretKey_ = "";
        this.action_ = "";
        this.customEvent_ = "";
        this.params_ = pk1.emptyProtobufList();
        return;
    }

    public static bridge synthetic void A(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearSize();
        return;
    }

    public static bridge synthetic void B(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearText();
        return;
    }

    public static bridge synthetic void C(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearThumbnailRemotePath();
        return;
    }

    public static bridge synthetic void D(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearThumbnailSecretKey();
        return;
    }

    public static bridge synthetic void E(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearType();
        return;
    }

    public static bridge synthetic void F(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size p1)
    {
        p0.mergeField18(p1);
        return;
    }

    public static bridge synthetic void G(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size p1)
    {
        p0.mergeSize(p1);
        return;
    }

    public static bridge synthetic void H(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p1)
    {
        p1.removeCustomExts(p0);
        return;
    }

    public static bridge synthetic void I(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p1)
    {
        p1.removeParams(p0);
        return;
    }

    public static bridge synthetic void J(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, String p1)
    {
        p0.setAction(p1);
        return;
    }

    public static bridge synthetic void K(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, du p1)
    {
        p0.setActionBytes(p1);
        return;
    }

    public static bridge synthetic void L(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, String p1)
    {
        p0.setAddress(p1);
        return;
    }

    public static bridge synthetic void M(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, du p1)
    {
        p0.setAddressBytes(p1);
        return;
    }

    public static bridge synthetic void N(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, String p1)
    {
        p0.setCustomEvent(p1);
        return;
    }

    public static bridge synthetic void O(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, du p1)
    {
        p0.setCustomEventBytes(p1);
        return;
    }

    public static bridge synthetic void P(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p2)
    {
        p0.setCustomExts(p1, p2);
        return;
    }

    public static bridge synthetic void Q(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, String p1)
    {
        p0.setDisplayName(p1);
        return;
    }

    public static bridge synthetic void R(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, du p1)
    {
        p0.setDisplayNameBytes(p1);
        return;
    }

    public static bridge synthetic void S(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p1)
    {
        p1.setDuration(p0);
        return;
    }

    public static bridge synthetic void T(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p1)
    {
        p1.setField17(p0);
        return;
    }

    public static bridge synthetic void U(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size p1)
    {
        p0.setField18(p1);
        return;
    }

    public static bridge synthetic void V(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p1)
    {
        p1.setFileLength(p0);
        return;
    }

    public static bridge synthetic void W(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, String p1)
    {
        p0.setFiletype(p1);
        return;
    }

    public static bridge synthetic void X(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, du p1)
    {
        p0.setFiletypeBytes(p1);
        return;
    }

    public static bridge synthetic void Y(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, double p1)
    {
        p0.setLatitude(p1);
        return;
    }

    public static bridge synthetic void Z(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, double p1)
    {
        p0.setLongitude(p1);
        return;
    }

    public static bridge synthetic void a0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p2)
    {
        p0.setParams(p1, p2);
        return;
    }

    private void addAllCustomExts(Iterable p1)
    {
        this.ensureCustomExtsIsMutable();
        l0.addAll(p1, this.customExts_);
        return;
    }

    private void addAllParams(Iterable p1)
    {
        this.ensureParamsIsMutable();
        l0.addAll(p1, this.params_);
        return;
    }

    private void addCustomExts(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p2)
    {
        p2.getClass();
        this.ensureCustomExtsIsMutable();
        this.customExts_.add(p1, p2);
        return;
    }

    private void addCustomExts(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p1)
    {
        p1.getClass();
        this.ensureCustomExtsIsMutable();
        this.customExts_.add(p1);
        return;
    }

    private void addParams(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p2)
    {
        p2.getClass();
        this.ensureParamsIsMutable();
        this.params_.add(p1, p2);
        return;
    }

    private void addParams(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p1)
    {
        p1.getClass();
        this.ensureParamsIsMutable();
        this.params_.add(p1);
        return;
    }

    public static bridge synthetic void b0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, String p1)
    {
        p0.setRemotePath(p1);
        return;
    }

    public static bridge synthetic void c0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, du p1)
    {
        p0.setRemotePathBytes(p1);
        return;
    }

    private void clearAction()
    {
        this.action_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.getDefaultInstance().getAction();
        return;
    }

    private void clearAddress()
    {
        this.address_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.getDefaultInstance().getAddress();
        return;
    }

    private void clearCustomEvent()
    {
        this.customEvent_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.getDefaultInstance().getCustomEvent();
        return;
    }

    private void clearCustomExts()
    {
        this.customExts_ = pk1.emptyProtobufList();
        return;
    }

    private void clearDisplayName()
    {
        this.displayName_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.getDefaultInstance().getDisplayName();
        return;
    }

    private void clearDuration()
    {
        this.duration_ = 0;
        return;
    }

    private void clearField17()
    {
        this.field17_ = 0;
        return;
    }

    private void clearField18()
    {
        this.field18_ = 0;
        this.bitField0_ = (this.bitField0_ & -3);
        return;
    }

    private void clearFileLength()
    {
        this.fileLength_ = 0;
        return;
    }

    private void clearFiletype()
    {
        this.filetype_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.getDefaultInstance().getFiletype();
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

    private void clearParams()
    {
        this.params_ = pk1.emptyProtobufList();
        return;
    }

    private void clearRemotePath()
    {
        this.remotePath_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.getDefaultInstance().getRemotePath();
        return;
    }

    private void clearSecretKey()
    {
        this.secretKey_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.getDefaultInstance().getSecretKey();
        return;
    }

    private void clearSize()
    {
        this.size_ = 0;
        this.bitField0_ = (this.bitField0_ & -2);
        return;
    }

    private void clearText()
    {
        this.text_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.getDefaultInstance().getText();
        return;
    }

    private void clearThumbnailRemotePath()
    {
        this.thumbnailRemotePath_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.getDefaultInstance().getThumbnailRemotePath();
        return;
    }

    private void clearThumbnailSecretKey()
    {
        this.thumbnailSecretKey_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.getDefaultInstance().getThumbnailSecretKey();
        return;
    }

    private void clearType()
    {
        this.type_ = 0;
        return;
    }

    public static bridge synthetic void d0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, String p1)
    {
        p0.setSecretKey(p1);
        return;
    }

    public static bridge synthetic void e0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, du p1)
    {
        p0.setSecretKeyBytes(p1);
        return;
    }

    private void ensureCustomExtsIsMutable()
    {
        sz1 v0_0 = this.customExts_;
        if (!((r0) v0_0).a) {
            this.customExts_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    private void ensureParamsIsMutable()
    {
        sz1 v0_0 = this.params_;
        if (!((r0) v0_0).a) {
            this.params_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, Iterable p1)
    {
        p0.addAllCustomExts(p1);
        return;
    }

    public static bridge synthetic void f0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size p1)
    {
        p0.setSize(p1);
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, Iterable p1)
    {
        p0.addAllParams(p1);
        return;
    }

    public static bridge synthetic void g0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, String p1)
    {
        p0.setText(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p2)
    {
        p0.addCustomExts(p1, p2);
        return;
    }

    public static bridge synthetic void h0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, du p1)
    {
        p0.setTextBytes(p1);
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p1)
    {
        p0.addCustomExts(p1);
        return;
    }

    public static bridge synthetic void i0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, String p1)
    {
        p0.setThumbnailRemotePath(p1);
        return;
    }

    public static bridge synthetic void j(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p2)
    {
        p0.addParams(p1, p2);
        return;
    }

    public static bridge synthetic void j0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, du p1)
    {
        p0.setThumbnailRemotePathBytes(p1);
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p1)
    {
        p0.addParams(p1);
        return;
    }

    public static bridge synthetic void k0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, String p1)
    {
        p0.setThumbnailSecretKey(p1);
        return;
    }

    public static bridge synthetic void l(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearAction();
        return;
    }

    public static bridge synthetic void l0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0, du p1)
    {
        p0.setThumbnailSecretKeyBytes(p1);
        return;
    }

    public static bridge synthetic void m(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearAddress();
        return;
    }

    public static bridge synthetic void m0(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p1)
    {
        p1.setType(p0);
        return;
    }

    private void mergeField18(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size p3)
    {
        p3.getClass();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size$Builder v0_0 = this.field18_;
        if ((v0_0 == null) || (v0_0 == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size.getDefaultInstance())) {
            this.field18_ = p3;
        } else {
            this.field18_ = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size) ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size.newBuilder(this.field18_).mergeFrom(p3)).buildPartial());
        }
        this.bitField0_ = (this.bitField0_ | 2);
        return;
    }

    private void mergeSize(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size p3)
    {
        p3.getClass();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size$Builder v0_0 = this.size_;
        if ((v0_0 == null) || (v0_0 == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size.getDefaultInstance())) {
            this.size_ = p3;
        } else {
            this.size_ = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size) ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size.newBuilder(this.size_).mergeFrom(p3)).buildPartial());
        }
        this.bitField0_ = (this.bitField0_ | 1);
        return;
    }

    public static bridge synthetic void n(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearCustomEvent();
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content n0()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static bridge synthetic void o(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearCustomExts();
        return;
    }

    public static bridge synthetic void p(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearDisplayName();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE.getParserForType();
    }

    public static bridge synthetic void q(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearDuration();
        return;
    }

    public static bridge synthetic void r(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearField17();
        return;
    }

    private void removeCustomExts(int p1)
    {
        this.ensureCustomExtsIsMutable();
        this.customExts_.remove(p1);
        return;
    }

    private void removeParams(int p1)
    {
        this.ensureParamsIsMutable();
        this.params_.remove(p1);
        return;
    }

    public static bridge synthetic void s(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearField18();
        return;
    }

    private void setAction(String p1)
    {
        p1.getClass();
        this.action_ = p1;
        return;
    }

    private void setActionBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.action_ = p1.q();
        return;
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

    private void setCustomEvent(String p1)
    {
        p1.getClass();
        this.customEvent_ = p1;
        return;
    }

    private void setCustomEventBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.customEvent_ = p1.q();
        return;
    }

    private void setCustomExts(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p2)
    {
        p2.getClass();
        this.ensureCustomExtsIsMutable();
        this.customExts_.set(p1, p2);
        return;
    }

    private void setDisplayName(String p1)
    {
        p1.getClass();
        this.displayName_ = p1;
        return;
    }

    private void setDisplayNameBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.displayName_ = p1.q();
        return;
    }

    private void setDuration(int p1)
    {
        this.duration_ = p1;
        return;
    }

    private void setField17(int p1)
    {
        this.field17_ = p1;
        return;
    }

    private void setField18(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size p1)
    {
        p1.getClass();
        this.field18_ = p1;
        this.bitField0_ = (this.bitField0_ | 2);
        return;
    }

    private void setFileLength(int p1)
    {
        this.fileLength_ = p1;
        return;
    }

    private void setFiletype(String p1)
    {
        p1.getClass();
        this.filetype_ = p1;
        return;
    }

    private void setFiletypeBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.filetype_ = p1.q();
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

    private void setParams(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p2)
    {
        p2.getClass();
        this.ensureParamsIsMutable();
        this.params_.set(p1, p2);
        return;
    }

    private void setRemotePath(String p1)
    {
        p1.getClass();
        this.remotePath_ = p1;
        return;
    }

    private void setRemotePathBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.remotePath_ = p1.q();
        return;
    }

    private void setSecretKey(String p1)
    {
        p1.getClass();
        this.secretKey_ = p1;
        return;
    }

    private void setSecretKeyBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.secretKey_ = p1.q();
        return;
    }

    private void setSize(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size p1)
    {
        p1.getClass();
        this.size_ = p1;
        this.bitField0_ = (this.bitField0_ | 1);
        return;
    }

    private void setText(String p1)
    {
        p1.getClass();
        this.text_ = p1;
        return;
    }

    private void setTextBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.text_ = p1.q();
        return;
    }

    private void setThumbnailRemotePath(String p1)
    {
        p1.getClass();
        this.thumbnailRemotePath_ = p1;
        return;
    }

    private void setThumbnailRemotePathBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.thumbnailRemotePath_ = p1.q();
        return;
    }

    private void setThumbnailSecretKey(String p1)
    {
        p1.getClass();
        this.thumbnailSecretKey_ = p1;
        return;
    }

    private void setThumbnailSecretKeyBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.thumbnailSecretKey_ = p1.q();
        return;
    }

    private void setType(int p1)
    {
        this.type_ = p1;
        return;
    }

    public static bridge synthetic void t(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearFileLength();
        return;
    }

    public static bridge synthetic void u(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearFiletype();
        return;
    }

    public static bridge synthetic void v(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearLatitude();
        return;
    }

    public static bridge synthetic void w(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearLongitude();
        return;
    }

    public static bridge synthetic void x(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearParams();
        return;
    }

    public static bridge synthetic void y(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearRemotePath();
        return;
    }

    public static bridge synthetic void z(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p0)
    {
        p0.clearSecretKey();
        return;
    }

    public final Object dynamicMethod(ok1 p26, Object p27, Object p28)
    {
        lk1 v0_0 = p26.ordinal();
        if (v0_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v0_0 == 2) {
                String v4 = "text_";
                String v6 = "longitude_";
                String v8 = "remotePath_";
                String v10 = "displayName_";
                String v12 = "thumbnailRemotePath_";
                Class v14 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue;
                String v16 = "size_";
                String v18 = "thumbnailSecretKey_";
                String v20 = "field17_";
                String v22 = "customEvent_";
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE, "\u0000\u0014\u0000\u0001\u0001\u0014\u0014\u0000\u0002\u0000\u0001\u0004\u0002\u0208\u0003\u0000\u0004\u0000\u0005\u0208\u0006\u0208\u0007\u0208\u0008\u0208\t\u0004\n\u0208\u000b\u001b\u000c\u0004\r\u1009\u0000\u000e\u0208\u000f\u0208\u0010\u0208\u0011\u0004\u0012\u1009\u0001\u0013\u0208\u0014\u001b", new Object[] {"bitField0_", org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue}));
            } else {
                if (v0_0 == 3) {
                    return new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content();
                } else {
                    if (v0_0 == 4) {
                        return new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content$Builder(0);
                    } else {
                        if (v0_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE;
                        } else {
                            if (v0_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v0_11 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.PARSER;
                                if (v0_11 != null) {
                                    return v0_11;
                                } else {
                                    lk1 v0_12 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.PARSER;
                                    if (v0_12 == null) {
                                        v0_12 = new lk1(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content.PARSER = v0_12;
                                    }
                                    return v0_12;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String getAction()
    {
        return this.action_;
    }

    public du getActionBytes()
    {
        return du.g(this.action_);
    }

    public String getAddress()
    {
        return this.address_;
    }

    public du getAddressBytes()
    {
        return du.g(this.address_);
    }

    public String getCustomEvent()
    {
        return this.customEvent_;
    }

    public du getCustomEventBytes()
    {
        return du.g(this.customEvent_);
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue getCustomExts(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) this.customExts_.get(p1));
    }

    public int getCustomExtsCount()
    {
        return this.customExts_.size();
    }

    public java.util.List getCustomExtsList()
    {
        return this.customExts_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValueOrBuilder getCustomExtsOrBuilder(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValueOrBuilder) this.customExts_.get(p1));
    }

    public java.util.List getCustomExtsOrBuilderList()
    {
        return this.customExts_;
    }

    public String getDisplayName()
    {
        return this.displayName_;
    }

    public du getDisplayNameBytes()
    {
        return du.g(this.displayName_);
    }

    public int getDuration()
    {
        return this.duration_;
    }

    public int getField17()
    {
        return this.field17_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size getField18()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size v0_1 = this.field18_;
        if (v0_1 == null) {
            v0_1 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size.getDefaultInstance();
        }
        return v0_1;
    }

    public int getFileLength()
    {
        return this.fileLength_;
    }

    public String getFiletype()
    {
        return this.filetype_;
    }

    public du getFiletypeBytes()
    {
        return du.g(this.filetype_);
    }

    public double getLatitude()
    {
        return this.latitude_;
    }

    public double getLongitude()
    {
        return this.longitude_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue getParams(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) this.params_.get(p1));
    }

    public int getParamsCount()
    {
        return this.params_.size();
    }

    public java.util.List getParamsList()
    {
        return this.params_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValueOrBuilder getParamsOrBuilder(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValueOrBuilder) this.params_.get(p1));
    }

    public java.util.List getParamsOrBuilderList()
    {
        return this.params_;
    }

    public String getRemotePath()
    {
        return this.remotePath_;
    }

    public du getRemotePathBytes()
    {
        return du.g(this.remotePath_);
    }

    public String getSecretKey()
    {
        return this.secretKey_;
    }

    public du getSecretKeyBytes()
    {
        return du.g(this.secretKey_);
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size getSize()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size v0_1 = this.size_;
        if (v0_1 == null) {
            v0_1 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Size.getDefaultInstance();
        }
        return v0_1;
    }

    public String getText()
    {
        return this.text_;
    }

    public du getTextBytes()
    {
        return du.g(this.text_);
    }

    public String getThumbnailRemotePath()
    {
        return this.thumbnailRemotePath_;
    }

    public du getThumbnailRemotePathBytes()
    {
        return du.g(this.thumbnailRemotePath_);
    }

    public String getThumbnailSecretKey()
    {
        return this.thumbnailSecretKey_;
    }

    public du getThumbnailSecretKeyBytes()
    {
        return du.g(this.thumbnailSecretKey_);
    }

    public int getType()
    {
        return this.type_;
    }

    public boolean hasField18()
    {
        if ((this.bitField0_ & 2) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean hasSize()
    {
        if ((this.bitField0_ & 1) == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
