package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import api.*;

public class NodeData_test {

    @Test
    void getKey() {
        NodeData node = new NodeData(1,new GeoLocation());
        assertEquals(1,node.getKey());
    }

    @Test
    void setGetLocation() {
        NodeData node = new NodeData();
        GeoLocation gl = new GeoLocation(10,10,10);
        assertEquals(new GeoLocation(0,0,0).toString(),node.getLocation().toString());
        node.setLocation(gl);
        assertEquals(gl,node.getLocation());
    }

    @Test
    void setGetWeight() {
        NodeData node = new NodeData();
        assertEquals(0,node.getWeight());
        double weight = 10.123456;
        node.setWeight(weight);
        assertEquals(weight,node.getWeight());
    }

}
