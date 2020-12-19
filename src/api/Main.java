package api;


public class Main {

    public static void main(String[] args) {

        api.DWGraph_DS dwg = new api.DWGraph_DS();
        api.NodeData a = new NodeData();
        NodeData b = new NodeData();
        NodeData c = new NodeData();
        NodeData d = new NodeData();
        dwg.addNode(a);
        dwg.addNode(b);
        dwg.addNode(c);
        dwg.addNode(d);
        dwg.connect(a.getKey(),b.getKey(),1);
        dwg.connect(b.getKey(),c.getKey(),1);
        dwg.connect(c.getKey(),d.getKey(),1);
        dwg.connect(d.getKey(),a.getKey(),1);
        DWGraph_Algo graph = new DWGraph_Algo();
        graph.init(dwg);
        System.out.println(graph.isConnected());

    }

}