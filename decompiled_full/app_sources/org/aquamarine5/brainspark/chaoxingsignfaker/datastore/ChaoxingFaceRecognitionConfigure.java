package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class ChaoxingFaceRecognitionConfigure extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigureOrBuilder {
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure DEFAULT_INSTANCE = None;
    public static final int IMAGES_FIELD_NUMBER = 1;
    private static volatile ua3 PARSER;
    private sz1 images_;

    static ChaoxingFaceRecognitionConfigure()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure, v0_1);
        return;
    }

    private ChaoxingFaceRecognitionConfigure()
    {
        this.images_ = pk1.emptyProtobufList();
        return;
    }

    private void addAllImages(Iterable p1)
    {
        this.ensureImagesIsMutable();
        l0.addAll(p1, this.images_);
        return;
    }

    private void addImages(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p2)
    {
        p2.getClass();
        this.ensureImagesIsMutable();
        this.images_.add(p1, p2);
        return;
    }

    private void addImages(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p1)
    {
        p1.getClass();
        this.ensureImagesIsMutable();
        this.images_.add(p1);
        return;
    }

    private void clearImages()
    {
        this.images_ = pk1.emptyProtobufList();
        return;
    }

    private void ensureImagesIsMutable()
    {
        sz1 v0_0 = this.images_;
        if (!((r0) v0_0).a) {
            this.images_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure p0, Iterable p1)
    {
        p0.addAllImages(p1);
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p2)
    {
        p0.addImages(p1, p2);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p1)
    {
        p0.addImages(p1);
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure p0)
    {
        p0.clearImages();
        return;
    }

    public static bridge synthetic void j(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure p1)
    {
        p1.removeImages(p0);
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p2)
    {
        p0.setImages(p1, p2);
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure l()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE.getParserForType();
    }

    private void removeImages(int p1)
    {
        this.ensureImagesIsMutable();
        this.images_.remove(p1);
        return;
    }

    private void setImages(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p2)
    {
        p2.getClass();
        this.ensureImagesIsMutable();
        this.images_.set(p1, p2);
        return;
    }

    public final Object dynamicMethod(ok1 p1, Object p2, Object p3)
    {
        lk1 v0_0 = p1.ordinal();
        if (v0_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v0_0 == 2) {
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[] {"images_", org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage}));
            } else {
                if (v0_0 == 3) {
                    lk1 v0_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure;
                    v0_3();
                    return v0_3;
                } else {
                    if (v0_0 == 4) {
                        lk1 v0_4 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder;
                        v0_4(0);
                        return v0_4;
                    } else {
                        if (v0_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE;
                        } else {
                            if (v0_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v0_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.PARSER;
                                if (v0_8 != null) {
                                    return v0_8;
                                } else {
                                    lk1 v0_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.PARSER;
                                    if (v0_9 == null) {
                                        v0_9 = new lk1;
                                        v0_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.PARSER = v0_9;
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

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage getImages(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) this.images_.get(p1));
    }

    public int getImagesCount()
    {
        return this.images_.size();
    }

    public java.util.List getImagesList()
    {
        return this.images_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImageOrBuilder getImagesOrBuilder(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImageOrBuilder) this.images_.get(p1));
    }

    public java.util.List getImagesOrBuilderList()
    {
        return this.images_;
    }
}
