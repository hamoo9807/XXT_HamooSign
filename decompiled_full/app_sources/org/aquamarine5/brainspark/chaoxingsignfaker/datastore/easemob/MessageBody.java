package org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob;
public final class MessageBody extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBodyOrBuilder {
    public static final int ACKCONTENT_FIELD_NUMBER = 8;
    public static final int ACKMESSAGEID_FIELD_NUMBER = 6;
    public static final int CONTENTS_FIELD_NUMBER = 4;
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody DEFAULT_INSTANCE = None;
    public static final int EXT_FIELD_NUMBER = 5;
    public static final int FROM_FIELD_NUMBER = 2;
    public static final int MSGCONFIG_FIELD_NUMBER = 7;
    private static volatile ua3 PARSER = None;
    public static final int TO_FIELD_NUMBER = 3;
    public static final int TYPE_FIELD_NUMBER = 1;
    private String ackContent_;
    private long ackMessageId_;
    private int bitField0_;
    private sz1 contents_;
    private sz1 ext_;
    private org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID from_;
    private org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig msgConfig_;
    private org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID to_;
    private int type_;

    static MessageBody()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody, v0_1);
        return;
    }

    private MessageBody()
    {
        this.contents_ = pk1.emptyProtobufList();
        this.ext_ = pk1.emptyProtobufList();
        this.ackContent_ = "";
        return;
    }

    public static bridge synthetic void A(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, long p1)
    {
        p0.setAckMessageId(p1);
        return;
    }

    public static bridge synthetic void B(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p2)
    {
        p0.setContents(p1, p2);
        return;
    }

    public static bridge synthetic void C(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p2)
    {
        p0.setExt(p1, p2);
        return;
    }

    public static bridge synthetic void D(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        p0.setFrom(p1);
        return;
    }

    public static bridge synthetic void E(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig p1)
    {
        p0.setMsgConfig(p1);
        return;
    }

    public static bridge synthetic void F(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        p0.setTo(p1);
        return;
    }

    public static bridge synthetic void G(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, int p1)
    {
        p0.setType(p1);
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody H()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE;
    }

    private void addAllContents(Iterable p1)
    {
        this.ensureContentsIsMutable();
        l0.addAll(p1, this.contents_);
        return;
    }

    private void addAllExt(Iterable p1)
    {
        this.ensureExtIsMutable();
        l0.addAll(p1, this.ext_);
        return;
    }

    private void addContents(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p2)
    {
        p2.getClass();
        this.ensureContentsIsMutable();
        this.contents_.add(p1, p2);
        return;
    }

    private void addContents(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p1)
    {
        p1.getClass();
        this.ensureContentsIsMutable();
        this.contents_.add(p1);
        return;
    }

    private void addExt(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p2)
    {
        p2.getClass();
        this.ensureExtIsMutable();
        this.ext_.add(p1, p2);
        return;
    }

    private void addExt(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p1)
    {
        p1.getClass();
        this.ensureExtIsMutable();
        this.ext_.add(p1);
        return;
    }

    private void clearAckContent()
    {
        this.ackContent_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.getDefaultInstance().getAckContent();
        return;
    }

    private void clearAckMessageId()
    {
        this.ackMessageId_ = 0;
        return;
    }

    private void clearContents()
    {
        this.contents_ = pk1.emptyProtobufList();
        return;
    }

    private void clearExt()
    {
        this.ext_ = pk1.emptyProtobufList();
        return;
    }

    private void clearFrom()
    {
        this.from_ = 0;
        this.bitField0_ = (this.bitField0_ & -2);
        return;
    }

    private void clearMsgConfig()
    {
        this.msgConfig_ = 0;
        this.bitField0_ = (this.bitField0_ & -5);
        return;
    }

    private void clearTo()
    {
        this.to_ = 0;
        this.bitField0_ = (this.bitField0_ & -3);
        return;
    }

    private void clearType()
    {
        this.type_ = 0;
        return;
    }

    private void ensureContentsIsMutable()
    {
        sz1 v0_0 = this.contents_;
        if (!((r0) v0_0).a) {
            this.contents_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    private void ensureExtIsMutable()
    {
        sz1 v0_0 = this.ext_;
        if (!((r0) v0_0).a) {
            this.ext_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, Iterable p1)
    {
        p0.addAllContents(p1);
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, Iterable p1)
    {
        p0.addAllExt(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p2)
    {
        p0.addContents(p1, p2);
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p1)
    {
        p0.addContents(p1);
        return;
    }

    public static bridge synthetic void j(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p2)
    {
        p0.addExt(p1, p2);
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p1)
    {
        p0.addExt(p1);
        return;
    }

    public static bridge synthetic void l(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0)
    {
        p0.clearAckContent();
        return;
    }

    public static bridge synthetic void m(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0)
    {
        p0.clearAckMessageId();
        return;
    }

    private void mergeFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p3)
    {
        p3.getClass();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder v0_0 = this.from_;
        if ((v0_0 == null) || (v0_0 == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.getDefaultInstance())) {
            this.from_ = p3;
        } else {
            this.from_ = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.newBuilder(this.from_).mergeFrom(p3)).buildPartial());
        }
        this.bitField0_ = (this.bitField0_ | 1);
        return;
    }

    private void mergeMsgConfig(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig p3)
    {
        p3.getClass();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig$Builder v0_0 = this.msgConfig_;
        if ((v0_0 == null) || (v0_0 == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig.getDefaultInstance())) {
            this.msgConfig_ = p3;
        } else {
            this.msgConfig_ = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig) ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig.newBuilder(this.msgConfig_).mergeFrom(p3)).buildPartial());
        }
        this.bitField0_ = (this.bitField0_ | 4);
        return;
    }

    private void mergeTo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p3)
    {
        p3.getClass();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder v0_0 = this.to_;
        if ((v0_0 == null) || (v0_0 == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.getDefaultInstance())) {
            this.to_ = p3;
        } else {
            this.to_ = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID) ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.newBuilder(this.to_).mergeFrom(p3)).buildPartial());
        }
        this.bitField0_ = (this.bitField0_ | 2);
        return;
    }

    public static bridge synthetic void n(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0)
    {
        p0.clearContents();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static bridge synthetic void o(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0)
    {
        p0.clearExt();
        return;
    }

    public static bridge synthetic void p(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0)
    {
        p0.clearFrom();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE.getParserForType();
    }

    public static bridge synthetic void q(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0)
    {
        p0.clearMsgConfig();
        return;
    }

    public static bridge synthetic void r(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0)
    {
        p0.clearTo();
        return;
    }

    private void removeContents(int p1)
    {
        this.ensureContentsIsMutable();
        this.contents_.remove(p1);
        return;
    }

    private void removeExt(int p1)
    {
        this.ensureExtIsMutable();
        this.ext_.remove(p1);
        return;
    }

    public static bridge synthetic void s(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0)
    {
        p0.clearType();
        return;
    }

    private void setAckContent(String p1)
    {
        p1.getClass();
        this.ackContent_ = p1;
        return;
    }

    private void setAckContentBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.ackContent_ = p1.q();
        return;
    }

    private void setAckMessageId(long p1)
    {
        this.ackMessageId_ = p1;
        return;
    }

    private void setContents(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content p2)
    {
        p2.getClass();
        this.ensureContentsIsMutable();
        this.contents_.set(p1, p2);
        return;
    }

    private void setExt(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue p2)
    {
        p2.getClass();
        this.ensureExtIsMutable();
        this.ext_.set(p1, p2);
        return;
    }

    private void setFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        p1.getClass();
        this.from_ = p1;
        this.bitField0_ = (this.bitField0_ | 1);
        return;
    }

    private void setMsgConfig(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig p1)
    {
        p1.getClass();
        this.msgConfig_ = p1;
        this.bitField0_ = (this.bitField0_ | 4);
        return;
    }

    private void setTo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        p1.getClass();
        this.to_ = p1;
        this.bitField0_ = (this.bitField0_ | 2);
        return;
    }

    private void setType(int p1)
    {
        this.type_ = p1;
        return;
    }

    public static bridge synthetic void t(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        p0.mergeFrom(p1);
        return;
    }

    public static bridge synthetic void u(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig p1)
    {
        p0.mergeMsgConfig(p1);
        return;
    }

    public static bridge synthetic void v(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID p1)
    {
        p0.mergeTo(p1);
        return;
    }

    public static bridge synthetic void w(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, int p1)
    {
        p0.removeContents(p1);
        return;
    }

    public static bridge synthetic void x(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, int p1)
    {
        p0.removeExt(p1);
        return;
    }

    public static bridge synthetic void y(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, String p1)
    {
        p0.setAckContent(p1);
        return;
    }

    public static bridge synthetic void z(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody p0, du p1)
    {
        p0.setAckContentBytes(p1);
        return;
    }

    public final Object dynamicMethod(ok1 p12, Object p13, Object p14)
    {
        lk1 v11_0 = p12.ordinal();
        if (v11_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v11_0 == 2) {
                String v2 = "from_";
                String v4 = "contents_";
                String v6 = "ext_";
                String v8 = "ackMessageId_";
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE, "\u0000\u0008\u0000\u0001\u0001\u0008\u0008\u0000\u0002\u0000\u0001\u0004\u0002\u1009\u0000\u0003\u1009\u0001\u0004\u001b\u0005\u001b\u0006\u0003\u0007\u1009\u0002\u0008\u0208", new Object[] {"bitField0_", "ackContent_"}));
            } else {
                if (v11_0 == 3) {
                    lk1 v11_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody;
                    v11_3();
                    return v11_3;
                } else {
                    if (v11_0 == 4) {
                        lk1 v11_5 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody$Builder;
                        v11_5(0);
                        return v11_5;
                    } else {
                        if (v11_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE;
                        } else {
                            if (v11_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v11_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.PARSER;
                                if (v11_8 != null) {
                                    return v11_8;
                                } else {
                                    lk1 v11_10 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.PARSER;
                                    if (v11_10 == null) {
                                        v11_10 = new lk1;
                                        v11_10(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageBody.PARSER = v11_10;
                                    }
                                    return v11_10;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String getAckContent()
    {
        return this.ackContent_;
    }

    public du getAckContentBytes()
    {
        return du.g(this.ackContent_);
    }

    public long getAckMessageId()
    {
        return this.ackMessageId_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content getContents(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.Content) this.contents_.get(p1));
    }

    public int getContentsCount()
    {
        return this.contents_.size();
    }

    public java.util.List getContentsList()
    {
        return this.contents_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.ContentOrBuilder getContentsOrBuilder(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.ContentOrBuilder) this.contents_.get(p1));
    }

    public java.util.List getContentsOrBuilderList()
    {
        return this.contents_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue getExt(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValue) this.ext_.get(p1));
    }

    public int getExtCount()
    {
        return this.ext_.size();
    }

    public java.util.List getExtList()
    {
        return this.ext_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValueOrBuilder getExtOrBuilder(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.KeyValueOrBuilder) this.ext_.get(p1));
    }

    public java.util.List getExtOrBuilderList()
    {
        return this.ext_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID getFrom()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID v0_1 = this.from_;
        if (v0_1 == null) {
            v0_1 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.getDefaultInstance();
        }
        return v0_1;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig getMsgConfig()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig v0_1 = this.msgConfig_;
        if (v0_1 == null) {
            v0_1 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.MessageConfig.getDefaultInstance();
        }
        return v0_1;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID getTo()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID v0_1 = this.to_;
        if (v0_1 == null) {
            v0_1 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.JID.getDefaultInstance();
        }
        return v0_1;
    }

    public int getType()
    {
        return this.type_;
    }

    public boolean hasFrom()
    {
        if ((this.bitField0_ & 1) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean hasMsgConfig()
    {
        if ((this.bitField0_ & 4) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean hasTo()
    {
        if ((this.bitField0_ & 2) == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
