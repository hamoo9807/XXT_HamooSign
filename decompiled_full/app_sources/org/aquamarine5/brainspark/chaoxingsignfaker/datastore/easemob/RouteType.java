package org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob;
public final enum class RouteType extends java.lang.Enum implements mz1 {
    private static final synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType[] $VALUES;
    public static final enum org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType ROUTETYPE_UNKNOWN;
    public static final int ROUTETYPE_UNKNOWN_VALUE;
    public static final enum org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType UNRECOGNIZED;
    private static final nz1 internalValueMap;
    private final int value;

    private static synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType[] $values()
    {
        return new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType[] {org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.ROUTETYPE_UNKNOWN, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.UNRECOGNIZED});
    }

    static RouteType()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.ROUTETYPE_UNKNOWN = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType("ROUTETYPE_UNKNOWN", 0, 0);
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.UNRECOGNIZED = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType("UNRECOGNIZED", 1, -1);
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.$VALUES = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.$values();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.internalValueMap = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType$1();
        return;
    }

    private RouteType(String p1, int p2, int p3)
    {
        super(p1, p2);
        super.value = p3;
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType forNumber(int p0)
    {
        if (p0 == null) {
            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.ROUTETYPE_UNKNOWN;
        } else {
            return 0;
        }
    }

    public static nz1 internalGetValueMap()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.internalValueMap;
    }

    public static oz1 internalGetVerifier()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType$RouteTypeVerifier.INSTANCE;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType valueOf(int p0)
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.forNumber(p0);
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType valueOf(String p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType) Enum.valueOf(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType[] values()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType[]) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.$VALUES.clone());
    }

    public final int getNumber()
    {
        if (this == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.UNRECOGNIZED) {
            xv2.j("Can\'t get the number of an unknown enum value.");
            return 0;
        } else {
            return this.value;
        }
    }
}
