package org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob;
final class NameSpace$NameSpaceVerifier implements oz1 {
    static final oz1 INSTANCE;

    static NameSpace$NameSpaceVerifier()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace$NameSpaceVerifier.INSTANCE = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace$NameSpaceVerifier();
        return;
    }

    private NameSpace$NameSpaceVerifier()
    {
        return;
    }

    public boolean isInRange(int p1)
    {
        if (org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.NameSpace.forNumber(p1) == null) {
            return 0;
        } else {
            return 1;
        }
    }
}
