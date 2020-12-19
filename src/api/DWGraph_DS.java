package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class DWGraph_DS implements directed_weighted_graph{

    HashMap<Integer, NodeData> nodes;
    HashMap<Double,EdgeData> edges;
    int mc;

    public DWGraph_DS() {
        nodes = new HashMap<Integer,NodeData>();
        edges = new HashMap<Double,EdgeData>();
        mc = 0;
    }

    @Override
    public node_data getNode(int key) {
        return this.nodes.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(!this.nodes.containsKey(src) || !this.nodes.containsKey(dest))
            return null;
        if(nodes.get(src).neigh.get(dest)==null)
            return null;
        return nodes.get(src).neigh.get(dest);
    }

    @Override
    public void addNode(node_data n) {
        if(nodes.containsKey(n.getKey()))
            return;
        this.nodes.put(n.getKey(),(NodeData) n);
        this.mc++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(!this.nodes.containsKey(src) || !this.nodes.containsKey(dest))
            return;
        if(this.nodes.containsKey(dest));
            removeEdge(src,dest);
        EdgeData ed = new EdgeData(src,dest,w);
        this.nodes.get(src).neigh.put(dest,ed);
        this.edges.put(getc(src,dest,w),new EdgeData(src,dest,w));
        this.mc++;
    }

    @Override
    public Collection<node_data> getV() {
        Collection<node_data> list = new Vector<node_data>();
        Object arr[] = this.nodes.keySet().toArray();
        for(int i=0 ; i<arr.length ; i++)
            list.add(this.nodes.get((int)arr[i]));
        return list;
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        Collection list = new Vector<edge_data>();
        Object arr[] = this.nodes.get(node_id).neigh.keySet().toArray();
        for(int i=0 ; i<arr.length ; i++)
            list.add(this.nodes.get(node_id).neigh.get((int)arr[i]));
        return list;
    }

    @Override
    public node_data removeNode(int key) {
        if(!nodes.containsKey(key))
            return null;
        NodeData nd = this.nodes.get(key);
        Object[] arr = this.nodes.keySet().toArray();
        for(int i=0 ; i<arr.length ; i++)
        {
            if(this.nodes.get((int)arr[i]).neigh.containsKey(key))
            {
                this.nodes.get((int)arr[i]).neigh.remove(key);
                this.edges.remove(nd.neigh.get((int)arr[i]).weight);
            }
        }
        this.nodes.remove(key);
        mc++;
        return nd;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if(!this.nodes.containsKey(src) || !this.nodes.containsKey(dest) || !this.nodes.get(src).neigh.containsKey(dest))
            return null;
        EdgeData ed = this.nodes.get(src).neigh.get(dest);
        this.nodes.get(src).neigh.remove(dest);
        this.edges.remove(getc(ed.getSrc(),ed.getDest(),ed.getWeight()));
        mc++;
        return ed;
    }

    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.edges.size();
    }

    @Override
    public int getMC() {
        return this.mc;
    }
    public double getc(int src, int dest, double w) {
        return (src*17.17 + dest*19.19 + w*23.23);
    }
}
