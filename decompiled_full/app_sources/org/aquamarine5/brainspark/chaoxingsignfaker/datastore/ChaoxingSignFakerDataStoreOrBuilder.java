package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public interface ChaoxingSignFakerDataStoreOrBuilder implements kl2 {

    public abstract boolean containsFaceRecognitionConfigures(int p0);

    public abstract boolean containsRecommendRecords(int p0);

    public abstract boolean getAgreeTerms();

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis getAnalysis();

    public abstract String getAnalysisRankName();

    public abstract du getAnalysisRankNameBytes();

    public abstract String getAnalysisUUID();

    public abstract du getAnalysisUUIDBytes();

    public abstract boolean getBypassBlockedChecking();

    public abstract synthetic jl2 getDefaultInstanceForType();

    public abstract String getDeviceCode();

    public abstract du getDeviceCodeBytes();

    public abstract boolean getDisableAnalysisRank();

    public abstract boolean getDisableRecommend();

    public abstract java.util.Map getFaceRecognitionConfigures();

    public abstract int getFaceRecognitionConfiguresCount();

    public abstract java.util.Map getFaceRecognitionConfiguresMap();

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure getFaceRecognitionConfiguresOrDefault(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure p1);

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure getFaceRecognitionConfiguresOrThrow(int p0);

    public abstract boolean getHideAnalysisRankSchoolName();

    public abstract int getLastUploadAnalysisDate();

    public abstract long getLastUploadAnalysisTimestamp();

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip getLearntTooltips();

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation getLocations(int p0);

    public abstract int getLocationsCount();

    public abstract java.util.List getLocationsList();

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession getLoginSession();

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession getOtherUsers(int p0);

    public abstract int getOtherUsersCount();

    public abstract java.util.List getOtherUsersList();

    public abstract int getPreferClassId(int p0);

    public abstract int getPreferClassIdCount();

    public abstract java.util.List getPreferClassIdList();

    public abstract int getPreferCourse(int p0);

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingCourseClass getPreferCourseClass(int p0);

    public abstract int getPreferCourseClassCount();

    public abstract java.util.List getPreferCourseClassList();

    public abstract int getPreferCourseCount();

    public abstract java.util.List getPreferCourseList();

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences getPreferences();

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit getRecommendHabits(int p0);

    public abstract int getRecommendHabitsCount();

    public abstract java.util.List getRecommendHabitsList();

    public abstract java.util.Map getRecommendRecords();

    public abstract int getRecommendRecordsCount();

    public abstract java.util.Map getRecommendRecordsMap();

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList getRecommendRecordsOrDefault(int p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList p1);

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList getRecommendRecordsOrThrow(int p0);

    public abstract int getTagsIncreaseId();

    public abstract org.aquamarine5.brainspark.chaoxingsignfaker.datastore.OtherUserTagType getTagsLibrary(int p0);

    public abstract int getTagsLibraryCount();

    public abstract java.util.List getTagsLibraryList();

    public abstract int getVersion();

    public abstract boolean hasAnalysis();

    public abstract boolean hasLearntTooltips();

    public abstract boolean hasLoginSession();

    public abstract boolean hasPreferences();

    public abstract synthetic boolean isInitialized();
}
