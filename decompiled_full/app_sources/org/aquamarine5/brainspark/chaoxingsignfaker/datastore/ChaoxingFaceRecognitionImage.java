package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class ChaoxingFaceRecognitionImage extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImageOrBuilder {
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage DEFAULT_INSTANCE = None;
    public static final int OBJECTID_FIELD_NUMBER = 1;
    private static volatile ua3 PARSER = None;
    public static final int USECOUNT_FIELD_NUMBER = 2;
    private String objectId_;
    private int useCount_;

    static ChaoxingFaceRecognitionImage()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage, v0_1);
        return;
    }

    private ChaoxingFaceRecognitionImage()
    {
        this.objectId_ = "";
        return;
    }

    private void clearObjectId()
    {
        this.objectId_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.getDefaultInstance().getObjectId();
        return;
    }

    private void clearUseCount()
    {
        this.useCount_ = 0;
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p0)
    {
        p0.clearObjectId();
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p0)
    {
        p0.clearUseCount();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p0, String p1)
    {
        p0.setObjectId(p1);
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p0, du p1)
    {
        p0.setObjectIdBytes(p1);
        return;
    }

    public static bridge synthetic void j(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p1)
    {
        p1.setUseCount(p0);
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage k()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE.getParserForType();
    }

    private void setObjectId(String p1)
    {
        p1.getClass();
        this.objectId_ = p1;
        return;
    }

    private void setObjectIdBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.objectId_ = p1.q();
        return;
    }

    private void setUseCount(int p1)
    {
        this.useCount_ = p1;
        return;
    }

    public final Object dynamicMethod(ok1 p1, Object p2, Object p3)
    {
        lk1 v0_0 = p1.ordinal();
        if (v0_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v0_0 == 2) {
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0208\u0002\u000b", new Object[] {"objectId_", "useCount_"}));
            } else {
                if (v0_0 == 3) {
                    lk1 v0_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage;
                    v0_3();
                    return v0_3;
                } else {
                    if (v0_0 == 4) {
                        lk1 v0_4 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage$Builder;
                        v0_4(0);
                        return v0_4;
                    } else {
                        if (v0_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE;
                        } else {
                            if (v0_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v0_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.PARSER;
                                if (v0_8 != null) {
                                    return v0_8;
                                } else {
                                    lk1 v0_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.PARSER;
                                    if (v0_9 == null) {
                                        v0_9 = new lk1;
                                        v0_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage.PARSER = v0_9;
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

    public String getObjectId()
    {
        return this.objectId_;
    }

    public du getObjectIdBytes()
    {
        return du.g(this.objectId_);
    }

    public int getUseCount()
    {
        return this.useCount_;
    }
}
