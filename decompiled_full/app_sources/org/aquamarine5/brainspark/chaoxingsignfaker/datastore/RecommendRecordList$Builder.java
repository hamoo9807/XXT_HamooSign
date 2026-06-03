package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class RecommendRecordList$Builder extends kk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordListOrBuilder {

    private RecommendRecordList$Builder()
    {
        super(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.l());
        return;
    }

    public synthetic RecommendRecordList$Builder(int p1)
    {
        return;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder addAllRecords(Iterable p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.f(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.instance), p2);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder addRecords(int p2, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord$Builder p3)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.g(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.instance), p2, ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) p3.build()));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder addRecords(int p2, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p3)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.g(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.instance), p2, p3);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder addRecords(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord$Builder p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.h(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.instance), ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) p2.build()));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder addRecords(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.h(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.instance), p2);
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder clearRecords()
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.i(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.instance));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord getRecords(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.instance).getRecords(p1);
    }

    public int getRecordsCount()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.instance).getRecordsCount();
    }

    public java.util.List getRecordsList()
    {
        return java.util.Collections.unmodifiableList(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.instance).getRecordsList());
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder removeRecords(int p2)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.j(p2, ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.instance));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder setRecords(int p2, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord$Builder p3)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.k(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.instance), p2, ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) p3.build()));
        return this;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder setRecords(int p2, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p3)
    {
        this.copyOnWrite();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.k(((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.instance), p2, p3);
        return this;
    }
}
