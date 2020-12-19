package api;

import org.jetbrains.annotations.NotNull;

import javax.xml.soap.Node;
import java.util.HashMap;

public class NodeData implements node_data, Comparable<NodeData> {

    static int count = 0;
    int key, tag;
    double weight;
    HashMap<Integer,EdgeData> neigh;
    GeoLocation gl;
    String info;

    public NodeData() {
        this.key = count++;
        this.neigh = new HashMap<Integer, EdgeData>();
        this.gl = new GeoLocation();
        this.info = new String();
    }
    public NodeData(int id,GeoLocation gl) {
        this.key = id;
        this.gl = gl;
        this.neigh = new HashMap<Integer,EdgeData>();
        this.info = new String();
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public geo_location getLocation() {
        return this.gl;
    }

    @Override
    public void setLocation(geo_location p) {
        this.gl = (GeoLocation) p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
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

    @Override
    public int compareTo(@NotNull NodeData nd) {
        if(this.weight<nd.weight)
            return -1;
        else
            return 1;
    }
    public String toString(){
        return "" + this.getKey();
    }
}
