package tb.heigvd.tb_userapp.bluetooth;

/**
 * Created by anthony on 11.12.2015.
 */
public enum CustomProximity {
    IMMEDIATE,
    NEAR,
    FAR,
    UNKNOWN;

    private static final double DISTANCE_THRESHOLD_UNKNOWN = 0.0;
    private static final double DISTANCE_THRESHOLD_IMMEDIATE = 3.0;
    private static final double DISTANCE_THRESHOLD_NEAR = 10.0;

    /**
     * Categorizes accuracy to Proximity.
     *
     * @param accuracy the accuracy
     * @return the proximity
     */
    public static CustomProximity fromDistance(final double accuracy) {
        if(accuracy < DISTANCE_THRESHOLD_UNKNOWN) {
            return UNKNOWN;
        }

        if(accuracy < DISTANCE_THRESHOLD_IMMEDIATE){
            return IMMEDIATE;
        }

        if(accuracy < DISTANCE_THRESHOLD_NEAR){
            return NEAR;
        }

        return FAR;
    }
}
