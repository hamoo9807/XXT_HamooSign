package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class RecommendHabit extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabitOrBuilder {
    public static final int CLASSID_FIELD_NUMBER = 3;
    public static final int CLASSNAME_FIELD_NUMBER = 6;
    public static final int COURSEID_FIELD_NUMBER = 5;
    public static final int DAYOFWEEK_FIELD_NUMBER = 1;
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit DEFAULT_INSTANCE = None;
    public static final int MINUTEOFDAY_FIELD_NUMBER = 2;
    private static volatile ua3 PARSER = None;
    public static final int RECORDCOUNT_FIELD_NUMBER = 4;
    private int classId_;
    private String className_;
    private int courseId_;
    private int dayOfWeek_;
    private int minuteOfDay_;
    private int recordCount_;

    static RecommendHabit()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit, v0_1);
        return;
    }

    private RecommendHabit()
    {
        this.className_ = "";
        return;
    }

    private void clearClassId()
    {
        this.classId_ = 0;
        return;
    }

    private void clearClassName()
    {
        this.className_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.getDefaultInstance().getClassName();
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

    private void clearRecordCount()
    {
        this.recordCount_ = 0;
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p0)
    {
        p0.clearClassId();
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p0)
    {
        p0.clearClassName();
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p0)
    {
        p0.clearCourseId();
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p0)
    {
        p0.clearDayOfWeek();
        return;
    }

    public static bridge synthetic void j(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p0)
    {
        p0.clearMinuteOfDay();
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p0)
    {
        p0.clearRecordCount();
        return;
    }

    public static bridge synthetic void l(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p1)
    {
        p1.setClassId(p0);
        return;
    }

    public static bridge synthetic void m(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p0, String p1)
    {
        p0.setClassName(p1);
        return;
    }

    public static bridge synthetic void n(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p0, du p1)
    {
        p0.setClassNameBytes(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static bridge synthetic void o(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p1)
    {
        p1.setCourseId(p0);
        return;
    }

    public static bridge synthetic void p(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p1)
    {
        p1.setDayOfWeek(p0);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE.getParserForType();
    }

    public static bridge synthetic void q(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p1)
    {
        p1.setMinuteOfDay(p0);
        return;
    }

    public static bridge synthetic void r(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p1)
    {
        p1.setRecordCount(p0);
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit s()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE;
    }

    private void setClassId(int p1)
    {
        this.classId_ = p1;
        return;
    }

    private void setClassName(String p1)
    {
        p1.getClass();
        this.className_ = p1;
        return;
    }

    private void setClassNameBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.className_ = p1.q();
        return;
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

    private void setRecordCount(int p1)
    {
        this.recordCount_ = p1;
        return;
    }

    public final Object dynamicMethod(ok1 p7, Object p8, Object p9)
    {
        lk1 v6_0 = p7.ordinal();
        if (v6_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v6_0 == 2) {
                String v2 = "classId_";
                String v4 = "courseId_";
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE, "\u0000\u0006\u0000\u0000\u0001\u0006\u0006\u0000\u0000\u0000\u0001\u000b\u0002\u000b\u0003\u000b\u0004\u000b\u0005\u000b\u0006\u0208", new Object[] {"dayOfWeek_", "className_"}));
            } else {
                if (v6_0 == 3) {
                    lk1 v6_3 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit;
                    v6_3();
                    return v6_3;
                } else {
                    if (v6_0 == 4) {
                        lk1 v6_4 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit$Builder;
                        v6_4(0);
                        return v6_4;
                    } else {
                        if (v6_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE;
                        } else {
                            if (v6_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v6_8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.PARSER;
                                if (v6_8 != null) {
                                    return v6_8;
                                } else {
                                    lk1 v6_9 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.PARSER;
                                    if (v6_9 == null) {
                                        v6_9 = new lk1;
                                        v6_9(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit.PARSER = v6_9;
                                    }
                                    return v6_9;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int getClassId()
    {
        return this.classId_;
    }

    public String getClassName()
    {
        return this.className_;
    }

    public du getClassNameBytes()
    {
        return du.g(this.className_);
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

    public int getRecordCount()
    {
        return this.recordCount_;
    }
}
