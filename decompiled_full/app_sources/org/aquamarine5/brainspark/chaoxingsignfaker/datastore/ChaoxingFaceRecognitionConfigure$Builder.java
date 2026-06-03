package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class ChaoxingFaceRecognitionConfigure$Builder extends kk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigureOrBuilder {

    private ChaoxingFaceRecognitionConfigure$Builder()
    {
        super(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.l());
        return;
    }

    public synthetic ChaoxingFaceRecognitionConfigure$Builder(int p1)
    {
        return;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder addAllImages(Iterable p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.f(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.instance), p2);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder addImages(int p2, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage$Builder p3)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.g(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.instance), p2, ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) p3.build()));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder addImages(int p2, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p3)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.g(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.instance), p2, p3);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder addImages(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage$Builder p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.h(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.instance), ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) p2.build()));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder addImages(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.h(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.instance), p2);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder clearImages()
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.i(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.instance));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage getImages(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.instance).getImages(p1);
    }

    public int getImagesCount()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.instance).getImagesCount();
    }

    public java.util.List getImagesList()
    {
        return java.util.Collections.unmodifiableList(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.instance).getImagesList());
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder removeImages(int p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.j(p2, ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.instance));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder setImages(int p2, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage$Builder p3)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.k(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.instance), p2, ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage) p3.build()));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure$Builder setImages(int p2, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionImage p3)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure.k(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.instance), p2, p3);
        return this;
    }
}
