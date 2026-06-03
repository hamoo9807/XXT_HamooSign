package org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob;
public final enum class NameSpace extends java.lang.Enum implements mz1 {
    private static final synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace[] $VALUES;
    public static final enum org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace NAMESPACE_UNKNOWN;
    public static final int NAMESPACE_UNKNOWN_VALUE;
    public static final enum org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace UNRECOGNIZED;
    private static final nz1 internalValueMap;
    private final int value;

    private static synthetic org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace[] $values()
    {
        return new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace[] {org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.NAMESPACE_UNKNOWN, org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.UNRECOGNIZED});
    }

    static NameSpace()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.NAMESPACE_UNKNOWN = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace("NAMESPACE_UNKNOWN", 0, 0);
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.UNRECOGNIZED = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace("UNRECOGNIZED", 1, -1);
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.$VALUES = org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.$values();
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.internalValueMap = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace$1();
        return;
    }

    private NameSpace(String p1, int p2, int p3)
    {
        super(p1, p2);
        super.value = p3;
        return;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace forNumber(int p0)
    {
        if (p0 == null) {
            return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.NAMESPACE_UNKNOWN;
        } else {
            return 0;
        }
    }

    public static nz1 internalGetValueMap()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.internalValueMap;
    }

    public static oz1 internalGetVerifier()
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace$NameSpaceVerifier.INSTANCE;
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace valueOf(int p0)
    {
        return org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.forNumber(p0);
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace valueOf(String p1)
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace) Enum.valueOf(org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace, p1));
    }

    public static org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace[] values()
    {
        return ((org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace[]) org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.$VALUES.clone());
    }

    public final int getNumber()
    {
        if (this == org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.UNRECOGNIZED) {
            xv2.j("Can\'t get the number of an unknown enum value.");
            return 0;
        } else {
            return this.value;
        }
    }
}
