package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import api.*;
import

public class DWGraph_test {

    @Test
    void getAddNode() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData a = new NodeData();
        g.addNode(a);
        assertEquals(a,g.getNode(a.getKey()));
    }

    @Test
    void getEdge() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData a = new NodeData();
        NodeData b = new NodeData();
        g.addNode(a);
        g.addNode(b);
        g.connect(a.getKey(),b.getKey(),15);
        assertEquals(new EdgeData(a.getKey(),b.getKey(),15).toString(),g.getEdge(a.getKey(),b.getKey()).toString());
    }

    @Test
    void connect() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData a = new NodeData();
        NodeData b = new NodeData();
        g.addNode(a);
        g.addNode(b);
        assertEquals(null,g.getEdge(a.getKey(),b.getKey()));
        g.connect(a.getKey(),b.getKey(),18);
        assertEquals(new EdgeData(a.getKey(),b.getKey(),18).toString(),g.getEdge(a.getKey(),b.getKey()).toString());
    }

    @Test
    void removeNode() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData a = new NodeData();
        NodeData b = new NodeData();
        NodeData c = new NodeData();
        NodeData d = new NodeData();
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        assertEquals(4,g.nodeSize());
        g.removeNode(a.getKey());
        g.removeNode(d.getKey());
        assertEquals(2,g.nodeSize());
    }

    @Test
    void removeEdge() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData a = new NodeData();
        NodeData b = new NodeData();
        NodeData c = new NodeData();
        NodeData d = new NodeData();
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.connect(a.getKey(),b.getKey(),10);
        g.connect(b.getKey(),c.getKey(),4);
        g.connect(c.getKey(),d.getKey(),5);
        g.connect(d.getKey(),a.getKey(),8);
        assertEquals(4,g.edgeSize());
        g.removeEdge(a.getKey(),b.getKey());
        g.removeEdge(c.getKey(),d.getKey());
        assertEquals(2,g.edgeSize());
    }

    @Test
    void nodeSize() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData a = new NodeData();
        NodeData b = new NodeData();
        g.addNode(a);
        g.addNode(b);
        assertEquals(2,g.nodeSize());
    }

    @Test
    void edgeSize() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData a = new NodeData();
        NodeData b = new NodeData();
        NodeData c = new NodeData();
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.connect(a.getKey(),b.getKey(),11);
        g.connect(b.getKey(),c.getKey(),12);
        g.connect(c.getKey(),a.getKey(),13);
        assertEquals(3,g.edgeSize());
    }

    @Test
    void getMC() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData a = new NodeData();
        NodeData b = new NodeData();
        g.addNode(a);
        g.addNode(b);
        g.connect(a.getKey(),b.getKey(),14);
        g.connect(b.getKey(),a.getKey(),17);
        assertEquals(4,g.getMC());
    }
}