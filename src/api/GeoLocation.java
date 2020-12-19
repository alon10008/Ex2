package api;

public class GeoLocation implements geo_location {

            double x,y,z;

    public GeoLocation(double x, double y, double z) {
                this.x = x;
                this.y = y;
                this.z = z;
            }
    public GeoLocation() {
                return;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(geo_location g) {
        return Math.sqrt(Math.pow(this.x()-g.x(),2) + Math.pow(this.y()-g.y(),2) + Math.pow(this.z()-g.z(),2));
    }
    public String toString() {
        return this.x()+","+this.y()+","+this.z();
    }
}
