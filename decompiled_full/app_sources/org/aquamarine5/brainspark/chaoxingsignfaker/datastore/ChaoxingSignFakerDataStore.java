package org.aquamarine5.brainspark.chaoxingsignfaker.datastore;
public final class ChaoxingSignFakerDataStore extends pk1 implements org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStoreOrBuilder {
    public static final int AGREETERMS_FIELD_NUMBER = 3;
    public static final int ANALYSISRANKNAME_FIELD_NUMBER = 22;
    public static final int ANALYSISUUID_FIELD_NUMBER = 21;
    public static final int ANALYSIS_FIELD_NUMBER = 7;
    public static final int BYPASSBLOCKEDCHECKING_FIELD_NUMBER = 14;
    private static final org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore DEFAULT_INSTANCE = None;
    public static final int DEVICECODE_FIELD_NUMBER = 5;
    public static final int DISABLEANALYSISRANK_FIELD_NUMBER = 23;
    public static final int DISABLERECOMMEND_FIELD_NUMBER = 11;
    public static final int FACERECOGNITIONCONFIGURES_FIELD_NUMBER = 19;
    public static final int HIDEANALYSISRANKSCHOOLNAME_FIELD_NUMBER = 24;
    public static final int LASTUPLOADANALYSISDATE_FIELD_NUMBER = 20;
    public static final int LASTUPLOADANALYSISTIMESTAMP_FIELD_NUMBER = 25;
    public static final int LEARNTTOOLTIPS_FIELD_NUMBER = 17;
    public static final int LOCATIONS_FIELD_NUMBER = 1;
    public static final int LOGINSESSION_FIELD_NUMBER = 2;
    public static final int OTHERUSERS_FIELD_NUMBER = 4;
    private static volatile ua3 PARSER = None;
    public static final int PREFERCLASSID_FIELD_NUMBER = 8;
    public static final int PREFERCOURSECLASS_FIELD_NUMBER = 13;
    public static final int PREFERCOURSE_FIELD_NUMBER = 6;
    public static final int PREFERENCES_FIELD_NUMBER = 18;
    public static final int RECOMMENDHABITS_FIELD_NUMBER = 10;
    public static final int RECOMMENDRECORDS_FIELD_NUMBER = 9;
    public static final int TAGSINCREASEID_FIELD_NUMBER = 16;
    public static final int TAGSLIBRARY_FIELD_NUMBER = 15;
    public static final int VERSION_FIELD_NUMBER = 12;
    private boolean agreeTerms_;
    private String analysisRankName_;
    private String analysisUUID_;
    private org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis analysis_;
    private int bitField0_;
    private boolean bypassBlockedChecking_;
    private String deviceCode_;
    private boolean disableAnalysisRank_;
    private boolean disableRecommend_;
    private uj2 faceRecognitionConfigures_;
    private boolean hideAnalysisRankSchoolName_;
    private int lastUploadAnalysisDate_;
    private long lastUploadAnalysisTimestamp_;
    private org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip learntTooltips_;
    private sz1 locations_;
    private org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession loginSession_;
    private sz1 otherUsers_;
    private int preferClassIdMemoizedSerializedSize;
    private qz1 preferClassId_;
    private sz1 preferCourseClass_;
    private int preferCourseMemoizedSerializedSize;
    private qz1 preferCourse_;
    private org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences preferences_;
    private sz1 recommendHabits_;
    private uj2 recommendRecords_;
    private int tagsIncreaseId_;
    private sz1 tagsLibrary_;
    private int version_;

    static ChaoxingSignFakerDataStore()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore v0_1 = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE = v0_1;
        pk1.registerDefaultInstance(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore, v0_1);
        return;
    }

    private ChaoxingSignFakerDataStore()
    {
        this.preferCourseMemoizedSerializedSize = -1;
        this.preferClassIdMemoizedSerializedSize = -1;
        String v0_3 = uj2.b;
        this.recommendRecords_ = v0_3;
        this.faceRecognitionConfigures_ = v0_3;
        this.locations_ = pk1.emptyProtobufList();
        this.otherUsers_ = pk1.emptyProtobufList();
        this.deviceCode_ = "";
        this.preferCourse_ = pk1.emptyIntList();
        this.preferClassId_ = pk1.emptyIntList();
        this.recommendHabits_ = pk1.emptyProtobufList();
        this.preferCourseClass_ = pk1.emptyProtobufList();
        this.tagsLibrary_ = pk1.emptyProtobufList();
        this.analysisUUID_ = "";
        this.analysisRankName_ = "";
        return;
    }

    public static bridge synthetic void A(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearAnalysisRankName();
        return;
    }

    public static bridge synthetic void A0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingCourseClass p2)
    {
        p0.setPreferCourseClass(p1, p2);
        return;
    }

    public static bridge synthetic void B(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearAnalysisUUID();
        return;
    }

    public static bridge synthetic void B0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p1)
    {
        p0.setPreferences(p1);
        return;
    }

    public static bridge synthetic void C(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearBypassBlockedChecking();
        return;
    }

    public static bridge synthetic void C0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p2)
    {
        p0.setRecommendHabits(p1, p2);
        return;
    }

    public static bridge synthetic void D(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearDeviceCode();
        return;
    }

    public static bridge synthetic void D0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1)
    {
        p0.setTagsIncreaseId(p1);
        return;
    }

    public static bridge synthetic void E(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearDisableAnalysisRank();
        return;
    }

    public static bridge synthetic void E0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.OtherUserTagType p2)
    {
        p0.setTagsLibrary(p1, p2);
        return;
    }

    public static bridge synthetic void F(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearDisableRecommend();
        return;
    }

    public static bridge synthetic void F0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1)
    {
        p0.setVersion(p1);
        return;
    }

    public static bridge synthetic void G(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearHideAnalysisRankSchoolName();
        return;
    }

    public static bridge synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore G0()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE;
    }

    public static bridge synthetic void H(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearLastUploadAnalysisDate();
        return;
    }

    public static bridge synthetic void I(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearLastUploadAnalysisTimestamp();
        return;
    }

    public static bridge synthetic void J(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearLearntTooltips();
        return;
    }

    public static bridge synthetic void K(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearLocations();
        return;
    }

    public static bridge synthetic void L(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearLoginSession();
        return;
    }

    public static bridge synthetic void M(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearOtherUsers();
        return;
    }

    public static bridge synthetic void N(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearPreferClassId();
        return;
    }

    public static bridge synthetic void O(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearPreferCourse();
        return;
    }

    public static bridge synthetic void P(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearPreferCourseClass();
        return;
    }

    public static bridge synthetic void Q(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearPreferences();
        return;
    }

    public static bridge synthetic void R(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearRecommendHabits();
        return;
    }

    public static bridge synthetic void S(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearTagsIncreaseId();
        return;
    }

    public static bridge synthetic void T(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearTagsLibrary();
        return;
    }

    public static bridge synthetic void U(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearVersion();
        return;
    }

    public static bridge synthetic java.util.Map V(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        return p0.getMutableFaceRecognitionConfiguresMap();
    }

    public static bridge synthetic java.util.Map W(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        return p0.getMutableRecommendRecordsMap();
    }

    public static bridge synthetic void X(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis p1)
    {
        p0.mergeAnalysis(p1);
        return;
    }

    public static bridge synthetic void Y(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip p1)
    {
        p0.mergeLearntTooltips(p1);
        return;
    }

    public static bridge synthetic void Z(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p1)
    {
        p0.mergeLoginSession(p1);
        return;
    }

    public static bridge synthetic void a0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p1)
    {
        p0.mergePreferences(p1);
        return;
    }

    private void addAllLocations(Iterable p1)
    {
        this.ensureLocationsIsMutable();
        l0.addAll(p1, this.locations_);
        return;
    }

    private void addAllOtherUsers(Iterable p1)
    {
        this.ensureOtherUsersIsMutable();
        l0.addAll(p1, this.otherUsers_);
        return;
    }

    private void addAllPreferClassId(Iterable p1)
    {
        this.ensurePreferClassIdIsMutable();
        l0.addAll(p1, this.preferClassId_);
        return;
    }

    private void addAllPreferCourse(Iterable p1)
    {
        this.ensurePreferCourseIsMutable();
        l0.addAll(p1, this.preferCourse_);
        return;
    }

    private void addAllPreferCourseClass(Iterable p1)
    {
        this.ensurePreferCourseClassIsMutable();
        l0.addAll(p1, this.preferCourseClass_);
        return;
    }

    private void addAllRecommendHabits(Iterable p1)
    {
        this.ensureRecommendHabitsIsMutable();
        l0.addAll(p1, this.recommendHabits_);
        return;
    }

    private void addAllTagsLibrary(Iterable p1)
    {
        this.ensureTagsLibraryIsMutable();
        l0.addAll(p1, this.tagsLibrary_);
        return;
    }

    private void addLocations(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p2)
    {
        p2.getClass();
        this.ensureLocationsIsMutable();
        this.locations_.add(p1, p2);
        return;
    }

    private void addLocations(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p1)
    {
        p1.getClass();
        this.ensureLocationsIsMutable();
        this.locations_.add(p1);
        return;
    }

    private void addOtherUsers(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p2)
    {
        p2.getClass();
        this.ensureOtherUsersIsMutable();
        this.otherUsers_.add(p1, p2);
        return;
    }

    private void addOtherUsers(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p1)
    {
        p1.getClass();
        this.ensureOtherUsersIsMutable();
        this.otherUsers_.add(p1);
        return;
    }

    private void addPreferClassId(int p1)
    {
        this.ensurePreferClassIdIsMutable();
        ((ly1) this.preferClassId_).c(p1);
        return;
    }

    private void addPreferCourse(int p1)
    {
        this.ensurePreferCourseIsMutable();
        ((ly1) this.preferCourse_).c(p1);
        return;
    }

    private void addPreferCourseClass(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingCourseClass p2)
    {
        p2.getClass();
        this.ensurePreferCourseClassIsMutable();
        this.preferCourseClass_.add(p1, p2);
        return;
    }

    private void addPreferCourseClass(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingCourseClass p1)
    {
        p1.getClass();
        this.ensurePreferCourseClassIsMutable();
        this.preferCourseClass_.add(p1);
        return;
    }

    private void addRecommendHabits(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p2)
    {
        p2.getClass();
        this.ensureRecommendHabitsIsMutable();
        this.recommendHabits_.add(p1, p2);
        return;
    }

    private void addRecommendHabits(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p1)
    {
        p1.getClass();
        this.ensureRecommendHabitsIsMutable();
        this.recommendHabits_.add(p1);
        return;
    }

    private void addTagsLibrary(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.OtherUserTagType p2)
    {
        p2.getClass();
        this.ensureTagsLibraryIsMutable();
        this.tagsLibrary_.add(p1, p2);
        return;
    }

    private void addTagsLibrary(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.OtherUserTagType p1)
    {
        p1.getClass();
        this.ensureTagsLibraryIsMutable();
        this.tagsLibrary_.add(p1);
        return;
    }

    public static bridge synthetic void b0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1)
    {
        p0.removeLocations(p1);
        return;
    }

    public static bridge synthetic void c0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1)
    {
        p0.removeOtherUsers(p1);
        return;
    }

    private void clearAgreeTerms()
    {
        this.agreeTerms_ = 0;
        return;
    }

    private void clearAnalysis()
    {
        this.analysis_ = 0;
        this.bitField0_ = (this.bitField0_ & -3);
        return;
    }

    private void clearAnalysisRankName()
    {
        this.analysisRankName_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.getDefaultInstance().getAnalysisRankName();
        return;
    }

    private void clearAnalysisUUID()
    {
        this.analysisUUID_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.getDefaultInstance().getAnalysisUUID();
        return;
    }

    private void clearBypassBlockedChecking()
    {
        this.bypassBlockedChecking_ = 0;
        return;
    }

    private void clearDeviceCode()
    {
        this.deviceCode_ = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.getDefaultInstance().getDeviceCode();
        return;
    }

    private void clearDisableAnalysisRank()
    {
        this.disableAnalysisRank_ = 0;
        return;
    }

    private void clearDisableRecommend()
    {
        this.disableRecommend_ = 0;
        return;
    }

    private void clearHideAnalysisRankSchoolName()
    {
        this.hideAnalysisRankSchoolName_ = 0;
        return;
    }

    private void clearLastUploadAnalysisDate()
    {
        this.lastUploadAnalysisDate_ = 0;
        return;
    }

    private void clearLastUploadAnalysisTimestamp()
    {
        this.lastUploadAnalysisTimestamp_ = 0;
        return;
    }

    private void clearLearntTooltips()
    {
        this.learntTooltips_ = 0;
        this.bitField0_ = (this.bitField0_ & -5);
        return;
    }

    private void clearLocations()
    {
        this.locations_ = pk1.emptyProtobufList();
        return;
    }

    private void clearLoginSession()
    {
        this.loginSession_ = 0;
        this.bitField0_ = (this.bitField0_ & -2);
        return;
    }

    private void clearOtherUsers()
    {
        this.otherUsers_ = pk1.emptyProtobufList();
        return;
    }

    private void clearPreferClassId()
    {
        this.preferClassId_ = pk1.emptyIntList();
        return;
    }

    private void clearPreferCourse()
    {
        this.preferCourse_ = pk1.emptyIntList();
        return;
    }

    private void clearPreferCourseClass()
    {
        this.preferCourseClass_ = pk1.emptyProtobufList();
        return;
    }

    private void clearPreferences()
    {
        this.preferences_ = 0;
        this.bitField0_ = (this.bitField0_ & -9);
        return;
    }

    private void clearRecommendHabits()
    {
        this.recommendHabits_ = pk1.emptyProtobufList();
        return;
    }

    private void clearTagsIncreaseId()
    {
        this.tagsIncreaseId_ = 0;
        return;
    }

    private void clearTagsLibrary()
    {
        this.tagsLibrary_ = pk1.emptyProtobufList();
        return;
    }

    private void clearVersion()
    {
        this.version_ = 0;
        return;
    }

    public static bridge synthetic void d0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1)
    {
        p0.removePreferCourseClass(p1);
        return;
    }

    public static bridge synthetic void e0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1)
    {
        p0.removeRecommendHabits(p1);
        return;
    }

    private void ensureLocationsIsMutable()
    {
        sz1 v0_0 = this.locations_;
        if (!((r0) v0_0).a) {
            this.locations_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    private void ensureOtherUsersIsMutable()
    {
        sz1 v0_0 = this.otherUsers_;
        if (!((r0) v0_0).a) {
            this.otherUsers_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    private void ensurePreferClassIdIsMutable()
    {
        qz1 v0_0 = this.preferClassId_;
        if (!((r0) v0_0).a) {
            this.preferClassId_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    private void ensurePreferCourseClassIsMutable()
    {
        sz1 v0_0 = this.preferCourseClass_;
        if (!((r0) v0_0).a) {
            this.preferCourseClass_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    private void ensurePreferCourseIsMutable()
    {
        qz1 v0_0 = this.preferCourse_;
        if (!((r0) v0_0).a) {
            this.preferCourse_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    private void ensureRecommendHabitsIsMutable()
    {
        sz1 v0_0 = this.recommendHabits_;
        if (!((r0) v0_0).a) {
            this.recommendHabits_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    private void ensureTagsLibraryIsMutable()
    {
        sz1 v0_0 = this.tagsLibrary_;
        if (!((r0) v0_0).a) {
            this.tagsLibrary_ = pk1.mutableCopy(v0_0);
        }
        return;
    }

    public static bridge synthetic void f(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, Iterable p1)
    {
        p0.addAllLocations(p1);
        return;
    }

    public static bridge synthetic void f0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1)
    {
        p0.removeTagsLibrary(p1);
        return;
    }

    public static bridge synthetic void g(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, Iterable p1)
    {
        p0.addAllOtherUsers(p1);
        return;
    }

    public static bridge synthetic void g0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, boolean p1)
    {
        p0.setAgreeTerms(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore getDefaultInstance()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE;
    }

    private java.util.Map getMutableFaceRecognitionConfiguresMap()
    {
        return this.internalGetMutableFaceRecognitionConfigures();
    }

    private java.util.Map getMutableRecommendRecordsMap()
    {
        return this.internalGetMutableRecommendRecords();
    }

    public static bridge synthetic void h(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, Iterable p1)
    {
        p0.addAllPreferClassId(p1);
        return;
    }

    public static bridge synthetic void h0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis p1)
    {
        p0.setAnalysis(p1);
        return;
    }

    public static bridge synthetic void i(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, Iterable p1)
    {
        p0.addAllPreferCourse(p1);
        return;
    }

    public static bridge synthetic void i0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, String p1)
    {
        p0.setAnalysisRankName(p1);
        return;
    }

    private uj2 internalGetFaceRecognitionConfigures()
    {
        return this.faceRecognitionConfigures_;
    }

    private uj2 internalGetMutableFaceRecognitionConfigures()
    {
        uj2 v0_0 = this.faceRecognitionConfigures_;
        if (!v0_0.a) {
            this.faceRecognitionConfigures_ = v0_0.c();
        }
        return this.faceRecognitionConfigures_;
    }

    private uj2 internalGetMutableRecommendRecords()
    {
        uj2 v0_0 = this.recommendRecords_;
        if (!v0_0.a) {
            this.recommendRecords_ = v0_0.c();
        }
        return this.recommendRecords_;
    }

    private uj2 internalGetRecommendRecords()
    {
        return this.recommendRecords_;
    }

    public static bridge synthetic void j(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, Iterable p1)
    {
        p0.addAllPreferCourseClass(p1);
        return;
    }

    public static bridge synthetic void j0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, du p1)
    {
        p0.setAnalysisRankNameBytes(p1);
        return;
    }

    public static bridge synthetic void k(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, Iterable p1)
    {
        p0.addAllRecommendHabits(p1);
        return;
    }

    public static bridge synthetic void k0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, String p1)
    {
        p0.setAnalysisUUID(p1);
        return;
    }

    public static bridge synthetic void l(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, Iterable p1)
    {
        p0.addAllTagsLibrary(p1);
        return;
    }

    public static bridge synthetic void l0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, du p1)
    {
        p0.setAnalysisUUIDBytes(p1);
        return;
    }

    public static bridge synthetic void m(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p2)
    {
        p0.addLocations(p1, p2);
        return;
    }

    public static bridge synthetic void m0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, boolean p1)
    {
        p0.setBypassBlockedChecking(p1);
        return;
    }

    private void mergeAnalysis(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis p3)
    {
        p3.getClass();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis$Builder v0_0 = this.analysis_;
        if ((v0_0 == null) || (v0_0 == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis.getDefaultInstance())) {
            this.analysis_ = p3;
        } else {
            this.analysis_ = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis) ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis.newBuilder(this.analysis_).mergeFrom(p3)).buildPartial());
        }
        this.bitField0_ = (this.bitField0_ | 2);
        return;
    }

    private void mergeLearntTooltips(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip p3)
    {
        p3.getClass();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip$Builder v0_0 = this.learntTooltips_;
        if ((v0_0 == null) || (v0_0 == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip.getDefaultInstance())) {
            this.learntTooltips_ = p3;
        } else {
            this.learntTooltips_ = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip) ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip.newBuilder(this.learntTooltips_).mergeFrom(p3)).buildPartial());
        }
        this.bitField0_ = (this.bitField0_ | 4);
        return;
    }

    private void mergeLoginSession(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p3)
    {
        p3.getClass();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder v0_0 = this.loginSession_;
        if ((v0_0 == null) || (v0_0 == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.getDefaultInstance())) {
            this.loginSession_ = p3;
        } else {
            this.loginSession_ = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession) ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.newBuilder(this.loginSession_).mergeFrom(p3)).buildPartial());
        }
        this.bitField0_ = (this.bitField0_ | 1);
        return;
    }

    private void mergePreferences(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p3)
    {
        p3.getClass();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences$Builder v0_0 = this.preferences_;
        if ((v0_0 == null) || (v0_0 == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.getDefaultInstance())) {
            this.preferences_ = p3;
        } else {
            this.preferences_ = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences) ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.newBuilder(this.preferences_).mergeFrom(p3)).buildPartial());
        }
        this.bitField0_ = (this.bitField0_ | 8);
        return;
    }

    public static bridge synthetic void n(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p1)
    {
        p0.addLocations(p1);
        return;
    }

    public static bridge synthetic void n0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, String p1)
    {
        p0.setDeviceCode(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore$Builder newBuilder()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE.createBuilder());
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore$Builder newBuilder(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore$Builder) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE.createBuilder(p1));
    }

    public static bridge synthetic void o(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p2)
    {
        p0.addOtherUsers(p1, p2);
        return;
    }

    public static bridge synthetic void o0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, du p1)
    {
        p0.setDeviceCodeBytes(p1);
        return;
    }

    public static bridge synthetic void p(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p1)
    {
        p0.addOtherUsers(p1);
        return;
    }

    public static bridge synthetic void p0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, boolean p1)
    {
        p0.setDisableAnalysisRank(p1);
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore parseDelimitedFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore parseDelimitedFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore) pk1.parseDelimitedFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore parseFrom(du p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore parseFrom(du p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore parseFrom(java.io.InputStream p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore parseFrom(java.io.InputStream p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore parseFrom(java.nio.ByteBuffer p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore parseFrom(java.nio.ByteBuffer p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore parseFrom(ye0 p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore parseFrom(ye0 p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, p1, p2));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore parseFrom(byte[] p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore parseFrom(byte[] p1, b81 p2)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore) pk1.parseFrom(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, p1, p2));
    }

    public static ua3 parser()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE.getParserForType();
    }

    public static bridge synthetic void q(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1)
    {
        p0.addPreferClassId(p1);
        return;
    }

    public static bridge synthetic void q0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, boolean p1)
    {
        p0.setDisableRecommend(p1);
        return;
    }

    public static bridge synthetic void r(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1)
    {
        p0.addPreferCourse(p1);
        return;
    }

    public static bridge synthetic void r0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, boolean p1)
    {
        p0.setHideAnalysisRankSchoolName(p1);
        return;
    }

    private void removeLocations(int p1)
    {
        this.ensureLocationsIsMutable();
        this.locations_.remove(p1);
        return;
    }

    private void removeOtherUsers(int p1)
    {
        this.ensureOtherUsersIsMutable();
        this.otherUsers_.remove(p1);
        return;
    }

    private void removePreferCourseClass(int p1)
    {
        this.ensurePreferCourseClassIsMutable();
        this.preferCourseClass_.remove(p1);
        return;
    }

    private void removeRecommendHabits(int p1)
    {
        this.ensureRecommendHabitsIsMutable();
        this.recommendHabits_.remove(p1);
        return;
    }

    private void removeTagsLibrary(int p1)
    {
        this.ensureTagsLibraryIsMutable();
        this.tagsLibrary_.remove(p1);
        return;
    }

    public static bridge synthetic void s(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingCourseClass p2)
    {
        p0.addPreferCourseClass(p1, p2);
        return;
    }

    public static bridge synthetic void s0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1)
    {
        p0.setLastUploadAnalysisDate(p1);
        return;
    }

    private void setAgreeTerms(boolean p1)
    {
        this.agreeTerms_ = p1;
        return;
    }

    private void setAnalysis(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis p1)
    {
        p1.getClass();
        this.analysis_ = p1;
        this.bitField0_ = (this.bitField0_ | 2);
        return;
    }

    private void setAnalysisRankName(String p1)
    {
        p1.getClass();
        this.analysisRankName_ = p1;
        return;
    }

    private void setAnalysisRankNameBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.analysisRankName_ = p1.q();
        return;
    }

    private void setAnalysisUUID(String p1)
    {
        p1.getClass();
        this.analysisUUID_ = p1;
        return;
    }

    private void setAnalysisUUIDBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.analysisUUID_ = p1.q();
        return;
    }

    private void setBypassBlockedChecking(boolean p1)
    {
        this.bypassBlockedChecking_ = p1;
        return;
    }

    private void setDeviceCode(String p1)
    {
        p1.getClass();
        this.deviceCode_ = p1;
        return;
    }

    private void setDeviceCodeBytes(du p1)
    {
        l0.checkByteStringIsUtf8(p1);
        this.deviceCode_ = p1.q();
        return;
    }

    private void setDisableAnalysisRank(boolean p1)
    {
        this.disableAnalysisRank_ = p1;
        return;
    }

    private void setDisableRecommend(boolean p1)
    {
        this.disableRecommend_ = p1;
        return;
    }

    private void setHideAnalysisRankSchoolName(boolean p1)
    {
        this.hideAnalysisRankSchoolName_ = p1;
        return;
    }

    private void setLastUploadAnalysisDate(int p1)
    {
        this.lastUploadAnalysisDate_ = p1;
        return;
    }

    private void setLastUploadAnalysisTimestamp(long p1)
    {
        this.lastUploadAnalysisTimestamp_ = p1;
        return;
    }

    private void setLearntTooltips(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip p1)
    {
        p1.getClass();
        this.learntTooltips_ = p1;
        this.bitField0_ = (this.bitField0_ | 4);
        return;
    }

    private void setLocations(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p2)
    {
        p2.getClass();
        this.ensureLocationsIsMutable();
        this.locations_.set(p1, p2);
        return;
    }

    private void setLoginSession(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p1)
    {
        p1.getClass();
        this.loginSession_ = p1;
        this.bitField0_ = (this.bitField0_ | 1);
        return;
    }

    private void setOtherUsers(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p2)
    {
        p2.getClass();
        this.ensureOtherUsersIsMutable();
        this.otherUsers_.set(p1, p2);
        return;
    }

    private void setPreferClassId(int p1, int p2)
    {
        this.ensurePreferClassIdIsMutable();
        ((ly1) this.preferClassId_).h(p1, p2);
        return;
    }

    private void setPreferCourse(int p1, int p2)
    {
        this.ensurePreferCourseIsMutable();
        ((ly1) this.preferCourse_).h(p1, p2);
        return;
    }

    private void setPreferCourseClass(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingCourseClass p2)
    {
        p2.getClass();
        this.ensurePreferCourseClassIsMutable();
        this.preferCourseClass_.set(p1, p2);
        return;
    }

    private void setPreferences(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences p1)
    {
        p1.getClass();
        this.preferences_ = p1;
        this.bitField0_ = (this.bitField0_ | 8);
        return;
    }

    private void setRecommendHabits(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p2)
    {
        p2.getClass();
        this.ensureRecommendHabitsIsMutable();
        this.recommendHabits_.set(p1, p2);
        return;
    }

    private void setTagsIncreaseId(int p1)
    {
        this.tagsIncreaseId_ = p1;
        return;
    }

    private void setTagsLibrary(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.OtherUserTagType p2)
    {
        p2.getClass();
        this.ensureTagsLibraryIsMutable();
        this.tagsLibrary_.set(p1, p2);
        return;
    }

    private void setVersion(int p1)
    {
        this.version_ = p1;
        return;
    }

    public static bridge synthetic void t(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingCourseClass p1)
    {
        p0.addPreferCourseClass(p1);
        return;
    }

    public static bridge synthetic void t0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, long p1)
    {
        p0.setLastUploadAnalysisTimestamp(p1);
        return;
    }

    public static bridge synthetic void u(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p2)
    {
        p0.addRecommendHabits(p1, p2);
        return;
    }

    public static bridge synthetic void u0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip p1)
    {
        p0.setLearntTooltips(p1);
        return;
    }

    public static bridge synthetic void v(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit p1)
    {
        p0.addRecommendHabits(p1);
        return;
    }

    public static bridge synthetic void v0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation p2)
    {
        p0.setLocations(p1, p2);
        return;
    }

    public static bridge synthetic void w(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.OtherUserTagType p2)
    {
        p0.addTagsLibrary(p1, p2);
        return;
    }

    public static bridge synthetic void w0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession p1)
    {
        p0.setLoginSession(p1);
        return;
    }

    public static bridge synthetic void x(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.OtherUserTagType p1)
    {
        p0.addTagsLibrary(p1);
        return;
    }

    public static bridge synthetic void x0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession p2)
    {
        p0.setOtherUsers(p1, p2);
        return;
    }

    public static bridge synthetic void y(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearAgreeTerms();
        return;
    }

    public static bridge synthetic void y0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1, int p2)
    {
        p0.setPreferClassId(p1, p2);
        return;
    }

    public static bridge synthetic void z(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0)
    {
        p0.clearAnalysis();
        return;
    }

    public static bridge synthetic void z0(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore p0, int p1, int p2)
    {
        p0.setPreferCourse(p1, p2);
        return;
    }

    public boolean containsFaceRecognitionConfigures(int p1)
    {
        return this.internalGetFaceRecognitionConfigures().containsKey(Integer.valueOf(p1));
    }

    public boolean containsRecommendRecords(int p1)
    {
        return this.internalGetRecommendRecords().containsKey(Integer.valueOf(p1));
    }

    public final Object dynamicMethod(ok1 p36, Object p37, Object p38)
    {
        lk1 v0_0 = p36.ordinal();
        if (v0_0 == null) {
            return Byte.valueOf(1);
        } else {
            if (v0_0 == 2) {
                Class v4 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation;
                String v6 = "agreeTerms_";
                Class v8 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession;
                String v10 = "preferCourse_";
                String v12 = "preferClassId_";
                rj2 v14 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore$RecommendRecordsDefaultEntryHolder.defaultEntry;
                Class v16 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit;
                String v18 = "version_";
                Class v20 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingCourseClass;
                String v22 = "tagsLibrary_";
                String v24 = "tagsIncreaseId_";
                String v26 = "preferences_";
                rj2 v28 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore$FaceRecognitionConfiguresDefaultEntryHolder.defaultEntry;
                String v30 = "analysisUUID_";
                String v32 = "disableAnalysisRank_";
                return pk1.newMessageInfo(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE, "\u0000\u0019\u0000\u0001\u0001\u0019\u0019\u0002\u0007\u0000\u0001\u001b\u0002\u1009\u0000\u0003\u0007\u0004\u001b\u0005\u0208\u0006+\u0007\u1009\u0001\u0008+\t2\n\u001b\u000b\u0007\u000c\u000b\r\u001b\u000e\u0007\u000f\u001b\u0010\u000b\u0011\u1009\u0002\u0012\u1009\u0003\u00132\u0014\u000b\u0015\u0208\u0016\u0208\u0017\u0007\u0018\u0007\u0019\u0003", new Object[] {"bitField0_", "lastUploadAnalysisTimestamp_"}));
            } else {
                if (v0_0 == 3) {
                    return new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore();
                } else {
                    if (v0_0 == 4) {
                        return new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore$Builder(0);
                    } else {
                        if (v0_0 == 5) {
                            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE;
                        } else {
                            if (v0_0 != 6) {
                                throw 0;
                            } else {
                                lk1 v0_11 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.PARSER;
                                if (v0_11 != null) {
                                    return v0_11;
                                } else {
                                    lk1 v0_12 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.PARSER;
                                    if (v0_12 == null) {
                                        v0_12 = new lk1(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.DEFAULT_INSTANCE);
                                        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingSignFakerDataStore.PARSER = v0_12;
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

    public boolean getAgreeTerms()
    {
        return this.agreeTerms_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis getAnalysis()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis v0_1 = this.analysis_;
        if (v0_1 == null) {
            v0_1 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingAnalysis.getDefaultInstance();
        }
        return v0_1;
    }

    public String getAnalysisRankName()
    {
        return this.analysisRankName_;
    }

    public du getAnalysisRankNameBytes()
    {
        return du.g(this.analysisRankName_);
    }

    public String getAnalysisUUID()
    {
        return this.analysisUUID_;
    }

    public du getAnalysisUUIDBytes()
    {
        return du.g(this.analysisUUID_);
    }

    public boolean getBypassBlockedChecking()
    {
        return this.bypassBlockedChecking_;
    }

    public String getDeviceCode()
    {
        return this.deviceCode_;
    }

    public du getDeviceCodeBytes()
    {
        return du.g(this.deviceCode_);
    }

    public boolean getDisableAnalysisRank()
    {
        return this.disableAnalysisRank_;
    }

    public boolean getDisableRecommend()
    {
        return this.disableRecommend_;
    }

    public java.util.Map getFaceRecognitionConfigures()
    {
        return this.getFaceRecognitionConfiguresMap();
    }

    public int getFaceRecognitionConfiguresCount()
    {
        return this.internalGetFaceRecognitionConfigures().size();
    }

    public java.util.Map getFaceRecognitionConfiguresMap()
    {
        return java.util.Collections.unmodifiableMap(this.internalGetFaceRecognitionConfigures());
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure getFaceRecognitionConfiguresOrDefault(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure p2)
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure v0_3 = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.internalGetFaceRecognitionConfigures().get(Integer.valueOf(p1)));
        if (v0_3 == null) {
            return p2;
        } else {
            return v0_3;
        }
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure getFaceRecognitionConfiguresOrThrow(int p1)
    {
        int v0_3 = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingFaceRecognitionConfigure) this.internalGetFaceRecognitionConfigures().get(Integer.valueOf(p1)));
        if (v0_3 == 0) {
            hh3.b();
            return 0;
        } else {
            return v0_3;
        }
    }

    public boolean getHideAnalysisRankSchoolName()
    {
        return this.hideAnalysisRankSchoolName_;
    }

    public int getLastUploadAnalysisDate()
    {
        return this.lastUploadAnalysisDate_;
    }

    public long getLastUploadAnalysisTimestamp()
    {
        return this.lastUploadAnalysisTimestamp_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip getLearntTooltips()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip v0_1 = this.learntTooltips_;
        if (v0_1 == null) {
            v0_1 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.LearntTooltip.getDefaultInstance();
        }
        return v0_1;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation getLocations(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocation) this.locations_.get(p1));
    }

    public int getLocationsCount()
    {
        return this.locations_.size();
    }

    public java.util.List getLocationsList()
    {
        return this.locations_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocationOrBuilder getLocationsOrBuilder(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLocationOrBuilder) this.locations_.get(p1));
    }

    public java.util.List getLocationsOrBuilderList()
    {
        return this.locations_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession getLoginSession()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession v0_1 = this.loginSession_;
        if (v0_1 == null) {
            v0_1 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingLoginSession.getDefaultInstance();
        }
        return v0_1;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession getOtherUsers(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSession) this.otherUsers_.get(p1));
    }

    public int getOtherUsersCount()
    {
        return this.otherUsers_.size();
    }

    public java.util.List getOtherUsersList()
    {
        return this.otherUsers_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSessionOrBuilder getOtherUsersOrBuilder(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingOtherUserSessionOrBuilder) this.otherUsers_.get(p1));
    }

    public java.util.List getOtherUsersOrBuilderList()
    {
        return this.otherUsers_;
    }

    public int getPreferClassId(int p1)
    {
        return ((ly1) this.preferClassId_).f(p1);
    }

    public int getPreferClassIdCount()
    {
        return this.preferClassId_.size();
    }

    public java.util.List getPreferClassIdList()
    {
        return this.preferClassId_;
    }

    public int getPreferCourse(int p1)
    {
        return ((ly1) this.preferCourse_).f(p1);
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingCourseClass getPreferCourseClass(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingCourseClass) this.preferCourseClass_.get(p1));
    }

    public int getPreferCourseClassCount()
    {
        return this.preferCourseClass_.size();
    }

    public java.util.List getPreferCourseClassList()
    {
        return this.preferCourseClass_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingCourseClassOrBuilder getPreferCourseClassOrBuilder(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.ChaoxingCourseClassOrBuilder) this.preferCourseClass_.get(p1));
    }

    public java.util.List getPreferCourseClassOrBuilderList()
    {
        return this.preferCourseClass_;
    }

    public int getPreferCourseCount()
    {
        return this.preferCourse_.size();
    }

    public java.util.List getPreferCourseList()
    {
        return this.preferCourse_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences getPreferences()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences v0_1 = this.preferences_;
        if (v0_1 == null) {
            v0_1 = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.Preferences.getDefaultInstance();
        }
        return v0_1;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit getRecommendHabits(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabit) this.recommendHabits_.get(p1));
    }

    public int getRecommendHabitsCount()
    {
        return this.recommendHabits_.size();
    }

    public java.util.List getRecommendHabitsList()
    {
        return this.recommendHabits_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabitOrBuilder getRecommendHabitsOrBuilder(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendHabitOrBuilder) this.recommendHabits_.get(p1));
    }

    public java.util.List getRecommendHabitsOrBuilderList()
    {
        return this.recommendHabits_;
    }

    public java.util.Map getRecommendRecords()
    {
        return this.getRecommendRecordsMap();
    }

    public int getRecommendRecordsCount()
    {
        return this.internalGetRecommendRecords().size();
    }

    public java.util.Map getRecommendRecordsMap()
    {
        return java.util.Collections.unmodifiableMap(this.internalGetRecommendRecords());
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList getRecommendRecordsOrDefault(int p1, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList p2)
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList v0_3 = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.internalGetRecommendRecords().get(Integer.valueOf(p1)));
        if (v0_3 == null) {
            return p2;
        } else {
            return v0_3;
        }
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList getRecommendRecordsOrThrow(int p1)
    {
        int v0_3 = ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.RecommendRecordList) this.internalGetRecommendRecords().get(Integer.valueOf(p1)));
        if (v0_3 == 0) {
            hh3.b();
            return 0;
        } else {
            return v0_3;
        }
    }

    public int getTagsIncreaseId()
    {
        return this.tagsIncreaseId_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.OtherUserTagType getTagsLibrary(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.OtherUserTagType) this.tagsLibrary_.get(p1));
    }

    public int getTagsLibraryCount()
    {
        return this.tagsLibrary_.size();
    }

    public java.util.List getTagsLibraryList()
    {
        return this.tagsLibrary_;
    }

    public org.aquamarine5.brainspark.chaoxingsignfaker.datastore.OtherUserTagTypeOrBuilder getTagsLibraryOrBuilder(int p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.OtherUserTagTypeOrBuilder) this.tagsLibrary_.get(p1));
    }

    public java.util.List getTagsLibraryOrBuilderList()
    {
        return this.tagsLibrary_;
    }

    public int getVersion()
    {
        return this.version_;
    }

    public boolean hasAnalysis()
    {
        if ((this.bitField0_ & 2) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean hasLearntTooltips()
    {
        if ((this.bitField0_ & 4) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean hasLoginSession()
    {
        if ((this.bitField0_ & 1) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean hasPreferences()
    {
        if ((this.bitField0_ & 8) == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
