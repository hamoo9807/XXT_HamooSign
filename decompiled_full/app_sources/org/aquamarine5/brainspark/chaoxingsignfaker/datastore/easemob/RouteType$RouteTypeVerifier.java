package org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob;
final class RouteType$RouteTypeVerifier implements oz1 {
    static final oz1 INSTANCE;

    static RouteType$RouteTypeVerifier()
    {
        org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType$RouteTypeVerifier.INSTANCE = new org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType$RouteTypeVerifier();
        return;
    }

    private RouteType$RouteTypeVerifier()
    {
        return;
    }

    public boolean isInRange(int p1)
    {
        if (org.aquamarine5.brainspark.chaoxingsignfaker.datastore.easemob.RouteType.forNumber(p1) == null) {
            return 0;
        } else {
            return 1;
        }
    }
}
