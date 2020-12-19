package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import api.*;

import javax.xml.soap.Node;

public class DWGraphAlgo_test {

    @Test
    void initGetGraph() {
        DWGraph_DS g = new DWGraph_DS();
        DWGraph_Algo graph = new DWGraph_Algo();
        assertEquals(null,graph.getGraph());
        graph.init(g);
        assertEquals(g,graph.getGraph());
    }

    @Test
    void copy() {
        DWGraph_DS g1 = new DWGraph_DS();
        NodeData a = new NodeData();
        NodeData b = new NodeData();
        NodeData c = new NodeData();
        NodeData d = new NodeData();
        g1.addNode(a);
        g1.addNode(b);
        g1.addNode(c);
        g1.addNode(d);
        g1.connect(a.getKey(),b.getKey(),10);
        g1.connect(b.getKey(),d.getKey(),40);
        g1.connect(c.getKey(),a.getKey(),70);
        DWGraph_Algo graph = new DWGraph_Algo();
        graph.init(g1);
        DWGraph_DS g2 = (DWGraph_DS) graph.copy();
        assertNotEquals(g1,g2);
    }

    @Test
    void isConnected() {
        DWGraph_DS dwg = new DWGraph_DS();
        DWGraph_Algo graph = new DWGraph_Algo();
        NodeData a = new NodeData();
        NodeData b = new NodeData();
        NodeData c = new NodeData();
        NodeData d = new NodeData();
        NodeData e = new NodeData();
        NodeData f = new NodeData();
        NodeData g = new NodeData();
        dwg.addNode(a);
        dwg.addNode(b);
        dwg.addNode(c);
        dwg.addNode(d);
        dwg.addNode(e);
        dwg.addNode(f);
        dwg.addNode(g);
        dwg.connect(a.getKey(),b.getKey(),1);
        dwg.connect(b.getKey(),c.getKey(),4);
        dwg.connect(c.getKey(),d.getKey(),1);
        dwg.connect(d.getKey(),e.getKey(),1); // not connected
        graph.init(dwg);
        assertEquals(false,graph.isConnected());
        dwg.connect(e.getKey(),f.getKey(),1);
        dwg.connect(f.getKey(),g.getKey(),1);
        dwg.connect(g.getKey(),a.getKey(),1);   // simple circle
        graph.init(dwg);
        assertEquals(true, graph.isConnected());
    }

    @Test
    void shortestPathDist() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData a = new NodeData();
        NodeData b = new NodeData();
        NodeData c = new NodeData();
        NodeData d = new NodeData();
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.connect(a.getKey(),b.getKey(),9);
        g.connect(b.getKey(),c.getKey(),10);
        g.connect(c.getKey(),d.getKey(),11);
        g.connect(a.getKey(),d.getKey(),100);
        DWGraph_Algo graph = new DWGraph_Algo();
        graph.init(g);
        assertEquals(30,graph.shortestPathDist(a.getKey(),d.getKey()));
    }

    @Test
    void saveLoad() {
        DWGraph_DS g = new DWGraph_DS();
        NodeData a = new NodeData();
        NodeData b = new NodeData();
        g.addNode(a);
        g.addNode(b);
        g.connect(a.getKey(),b.getKey(),10);
        DWGraph_Algo graph = new DWGraph_Algo();
        graph.init(g);
        assertEquals(true,graph.save("alon.json"));
        DWGraph_Algo gra = new DWGraph_Algo();
        gra.load("alon.json");
        assertEquals(gra.getGraph().getV().toString(),graph.getGraph().getV().toString());
        assertEquals(gra.getGraph().edgeSize(),graph.getGraph().edgeSize());
    }

}
