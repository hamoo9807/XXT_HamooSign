package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class RecommendRecordList extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordListOrBuilder {
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList DEFAULT_INSTANCE = None;
    private static volatile ua3 PARSER = None;
    public static final int RECORDS_FIELD_NUMBER = 1;
    private sz1 records_;

    static RecommendRecordList()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList, v0_1);
        return;
    }

    private RecommendRecordList()
    {
        this.records_ = pk1.emptyProtobufList();
        return;
    }

    private void addAllRecords(Iterable p1)
    {
        this.ensureRecordsIsMutable();
        l0.addAll(p1, this.records_);
        return;
    }

    private void addRecords(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p2)
    {
        p2.getClass();
        this.ensureRecordsIsMutable();
        this.records_.add(p1, p2);
        return;
    }

    private void addRecords(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p1)
    {
        p1.getClass();
        this.ensureRecordsIsMutable();
        this.records_.add(p1);
        return;
    }

    private void clearRecords()
    {
        this.records_ = pk1.emptyProtobufList();
        return;
    }

    private void ensureRecordsIsMutable()
    {
        sz1 v0_0 = this.records_;
        if (!((r0) v0_0).a) {
            this.records_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList p0, Iterable p1)
    {
        p0.addAllRecords(p1);
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p2)
    {
        p0.addRecords(p1, p2);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p1)
    {
        p0.addRecords(p1);
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList p0)
    {
        p0.clearRecords();
        return;
    }

    public static bridge synthetic void j(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList p1)
    {
        p1.removeRecords(p0);
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p2)
    {
        p0.setRecords(p1, p2);
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList l()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE.getParserForType();
    }

    private void removeRecords(int p1)
    {
        this.ensureRecordsIsMutable();
        this.records_.remove(p1);
        return;
    }

    private void setRecords(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p2)
    {
        p2.getClass();
        this.ensureRecordsIsMutable();
        this.records_.set(p1, p2);
        return;
    }

    public final Object dynamicMethod(ok1 p1, Object p2, Object p3)
    {
        lk1 v0_0 = p1.ordinal();
        if (v0_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v0_0 == 2) {
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[] {"records_", org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord}));
            } else {
                if (v0_0 == 3) {
                    lk1 v0_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList;
                    v0_3();
                    return v0_3;
                } else {
                    if (v0_0 == 4) {
                        lk1 v0_4 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList$Builder;
                        v0_4(0);
                        return v0_4;
                    } else {
                        if (v0_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE;
                        } else {
                            if (v0_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v0_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.PARSER;
                                if (v0_8 != null) {
                                    return v0_8;
                                } else {
                                    lk1 v0_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.PARSER;
                                    if (v0_9 == null) {
                                        v0_9 = new lk1;
                                        v0_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList.PARSER = v0_9;
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

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord getRecords(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) this.records_.get(p1));
    }

    public int getRecordsCount()
    {
        return this.records_.size();
    }

    public java.util.List getRecordsList()
    {
        return this.records_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordOrBuilder getRecordsOrBuilder(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordOrBuilder) this.records_.get(p1));
    }

    public java.util.List getRecordsOrBuilderList()
    {
        return this.records_;
    }
}
