package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class RecommendRecord extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordOrBuilder {
    public static final int COURSEID_FIELD_NUMBER = 3;
    public static final int DAYOFWEEK_FIELD_NUMBER = 1;
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord DEFAULT_INSTANCE = None;
    public static final int MINUTEOFDAY_FIELD_NUMBER = 2;
    private static volatile ua3 PARSER;
    private int courseId_;
    private int dayOfWeek_;
    private int minuteOfDay_;

    static RecommendRecord()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord, v0_1);
        return;
    }

    private RecommendRecord()
    {
        return;
    }

    private void clearCourseId()
    {
        this.courseId_ = 0;
        return;
    }

    private void clearDayOfWeek()
    {
        this.dayOfWeek_ = 0;
        return;
    }

    private void clearMinuteOfDay()
    {
        this.minuteOfDay_ = 0;
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p0)
    {
        p0.clearCourseId();
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p0)
    {
        p0.clearDayOfWeek();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p0)
    {
        p0.clearMinuteOfDay();
        return;
    }

    public static bridge synthetic void i(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p1)
    {
        p1.setCourseId(p0);
        return;
    }

    public static bridge synthetic void j(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p1)
    {
        p1.setDayOfWeek(p0);
        return;
    }

    public static bridge synthetic void k(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p1)
    {
        p1.setMinuteOfDay(p0);
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord l()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE.getParserForType();
    }

    private void setCourseId(int p1)
    {
        this.courseId_ = p1;
        return;
    }

    private void setDayOfWeek(int p1)
    {
        this.dayOfWeek_ = p1;
        return;
    }

    private void setMinuteOfDay(int p1)
    {
        this.minuteOfDay_ = p1;
        return;
    }

    public final Object dynamicMethod(ok1 p1, Object p2, Object p3)
    {
        lk1 v0_0 = p1.ordinal();
        if (v0_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v0_0 == 2) {
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u000b\u0002\u000b\u0003\u000b", new Object[] {"dayOfWeek_", "minuteOfDay_", "courseId_"}));
            } else {
                if (v0_0 == 3) {
                    lk1 v0_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord;
                    v0_3();
                    return v0_3;
                } else {
                    if (v0_0 == 4) {
                        lk1 v0_4 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord$Builder;
                        v0_4(0);
                        return v0_4;
                    } else {
                        if (v0_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE;
                        } else {
                            if (v0_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v0_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.PARSER;
                                if (v0_8 != null) {
                                    return v0_8;
                                } else {
                                    lk1 v0_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.PARSER;
                                    if (v0_9 == null) {
                                        v0_9 = new lk1;
                                        v0_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecord.PARSER = v0_9;
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

    public int getCourseId()
    {
        return this.courseId_;
    }

    public int getDayOfWeek()
    {
        return this.dayOfWeek_;
    }

    public int getMinuteOfDay()
    {
        return this.minuteOfDay_;
    }
}
