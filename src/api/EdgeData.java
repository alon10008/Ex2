package api;

public class EdgeData implements edge_data {
    int srcKey, dstKey, tag;
    double weight;
    String info;

    public EdgeData(int src, int dst , double w) {
        this.srcKey = src;
        this.dstKey = dst;
        this.weight = w;
        this.info = new String();

    }

    @Override
    public int getSrc() {
        return this.srcKey;
    }

    @Override
    public int getDest() {
        return this.dstKey;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
    public String toString()
    {
        String s = "src:" + this.srcKey + ",w:" + this.weight + ",dst:" + this.dstKey;
        return s;
    }
}
