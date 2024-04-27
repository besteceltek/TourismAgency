package Entity;

public class HotelFeatures {
    private int featueId, hotelId;
    private String featureName;

    public int getFeatueId() {
        return featueId;
    }

    public void setFeatueId(int featueId) {
        this.featueId = featueId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }
}
