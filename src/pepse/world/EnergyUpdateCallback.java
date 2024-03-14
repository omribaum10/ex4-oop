package pepse.world;

/***
 * @author omri baum and ayelet hashhar halevy
 * interface to
 */
@FunctionalInterface
public interface EnergyUpdateCallback {
    /**
     *
     * @return energy rate of singelto Avatar instance
     */
    public float GetEnergyRate();
}
