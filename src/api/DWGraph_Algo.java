package api;

import java.io.*;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class DWGraph_Algo implements dw_graph_algorithms{

    DWGraph_DS dwg;
    int component;
    static int counter;

    @Override
    public void init(directed_weighted_graph g) {
        this.dwg = (DWGraph_DS) g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.dwg;
    }

    @Override
    public directed_weighted_graph copy() {
        DWGraph_DS g = new DWGraph_DS();
        g.nodes = new HashMap<Integer,NodeData>(this.dwg.nodes);
        g.edges = new HashMap<Double,EdgeData>(this.dwg.edges);
        g.mc = this.dwg.mc;
        return g;
    }

    @Override
    public boolean isConnected() {

        Stack<Integer> s = new Stack<Integer>();
        int size = this.dwg.nodeSize();
        this.counter = 0;
        this.component = 0;
        Object[] arr = this.dwg.nodes.keySet().toArray();
        for(int i=0 ; i<arr.length ; i++)
            if((int)arr[i]>size)
                size = (int)arr[i];
        int[] DFSlow = new int[size+1];
        for(int i=0 ; i<DFSlow.length ; i++)
        {
            DFSlow[i] = Integer.MAX_VALUE;
        }
        for(int i=0 ; i<arr.length ; i++)
        {

            if(DFSlow[i]==Integer.MAX_VALUE)
                counter++;
                DFS((int)arr[i],DFSlow,s);
        }
        return component==1;
    }

    @Override
    public double shortestPathDist(int src, int dest) {

        PriorityQueue<NodeData> pq = new PriorityQueue<NodeData>();
        Object[] arr = this.dwg.nodes.keySet().toArray();
        double weight;
        int size = this.dwg.nodeSize();
        for(int i=0 ; i<arr.length ; i++)
        {
            this.dwg.nodes.get((int)arr[i]).setWeight(Double.POSITIVE_INFINITY);
            pq.add((NodeData)this.dwg.nodes.get((int)arr[i]));
            if(size<(int)arr[i])
                size = (int)arr[i];
        }
        this.dwg.nodes.get(src).setWeight(0);
        pq.remove(this.dwg.nodes.get(src));
        pq.add(this.dwg.nodes.get(src));
        int[] parent = new int[size+1];
        boolean[] visited = new boolean[size+1];
        for(int i=0 ; i<parent.length ; i++)
        {
            parent[i] = -1;
            visited[i] = false;
        }
        while(!pq.isEmpty())
        {
            NodeData nd = pq.poll();
            arr = nd.neigh.keySet().toArray();
            for(int i=0 ; i< arr.length ; i++)
            {
                EdgeData ed = nd.neigh.get((int)arr[i]);
                NodeData u = this.dwg.nodes.get(ed.dstKey);
                if(!visited[u.getKey()])
                {
                    weight = nd.getWeight() + ed.getWeight();
                    if(u.getWeight()>weight)
                    {
                        this.dwg.nodes.get(u.getKey()).setWeight(weight);
                        parent[u.getKey()] = nd.getKey();
                        pq.remove(u);
                        pq.add(u);
                    }
                }
            }
            visited[nd.getKey()] = true;
        }
        return this.dwg.nodes.get(dest).getWeight();
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        if(!this.dwg.nodes.containsKey(src) || !this.dwg.nodes.containsKey(dest))
            return null;
        Stack<node_data> s = new Stack<node_data>();
        if(src==dest)
            return s;
        PriorityQueue<NodeData> pq = new PriorityQueue<NodeData>();
        Object[] arr = this.dwg.nodes.keySet().toArray();
        double weight;
        int size = this.dwg.nodeSize();
        for(int i=0 ; i<arr.length ; i++)
        {
            this.dwg.nodes.get((int)arr[i]).setWeight(Double.POSITIVE_INFINITY);
            pq.add((NodeData)this.dwg.nodes.get((int)arr[i]));
            if(size<(int)arr[i])
                size = (int)arr[i];
        }
        this.dwg.nodes.get(src).setWeight(0);
        pq.remove(this.dwg.nodes.get(src));
        pq.add(this.dwg.nodes.get(src));
        int[] parent = new int[size+1];
        boolean[] visited = new boolean[size+1];
        for(int i=0 ; i<parent.length ; i++)
        {
            parent[i] = -1;
            visited[i] = false;
        }
        while(!pq.isEmpty())
        {
            NodeData nd = pq.poll();
            arr = nd.neigh.keySet().toArray();
            for(int i=0 ; i< arr.length ; i++)
            {
                EdgeData ed = nd.neigh.get((int)arr[i]);
                NodeData u = this.dwg.nodes.get(ed.dstKey);
                if(!visited[u.getKey()])
                {
                    weight = nd.getWeight() + ed.getWeight();
                    if(u.getWeight()>weight)
                    {
                        this.dwg.nodes.get(u.getKey()).setWeight(weight);
                        parent[u.getKey()] = nd.getKey();
                        pq.remove(u);
                        pq.add(u);
                    }
                }
            }
            visited[nd.getKey()] = true;
        }
        s.push(this.dwg.nodes.get(dest));
        int i=dest;
        while((int)parent[i]!=src)
        {
            s.push(this.dwg.nodes.get(parent[i]));
            i = parent[i];
        }
        List<node_data> l = new Vector<node_data>();
        //s.push(this.dwg.nodes.get(src));
        while(!s.empty())
            l.add(s.pop());
        return l;
    }

    @Override
    public boolean save(String file) {
        try
        {
            JSONArray jarr = new JSONArray();
            Object[] arr = this.dwg.nodes.keySet().toArray();
            for (int i = 0; i < arr.length; i++)
            {
                JSONObject json = new JSONObject();
                json.put("id", (int) arr[i]);
                json.put("pos",this.dwg.nodes.get((int)arr[i]).gl.toString());
                jarr.put(json);
            }
            JSONObject j = new JSONObject();
            j.put("Nodes",jarr);
            arr = this.dwg.edges.keySet().toArray();
            jarr = new JSONArray();
            for(int i=0 ; i<arr.length ; i++)
            {
                JSONObject json = new JSONObject();
                json.put("src",this.dwg.edges.get((double)arr[i]).getSrc());
                json.put("w",this.dwg.edges.get((double)arr[i]).getWeight());
                json.put("dest",this.dwg.edges.get((double)arr[i]).getDest());
                jarr.put(json);
            }
            j.put("Edges",jarr);
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(j);
            pw.close();
            fw.close();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
            return false;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        try
        {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            DWGraph_DS graph = new DWGraph_DS();
            JSONObject json = new JSONObject(br.readLine());
            JSONArray n = new JSONArray(json.get("Nodes").toString());
            JSONArray e = new JSONArray(json.get("Edges").toString());
            for(int i=0 ; i<n.length() ; i++)
            {
                JSONObject jo = new JSONObject(n.get(i).toString());
                String s = jo.getString("pos");
                double x,y,z;
                x =  Double.parseDouble(s.substring(0,s.indexOf(',')));
                s = s.substring(s.indexOf(',')+1);
                y = Double.parseDouble(s.substring(0,s.indexOf(',')));
                s = s.substring(s.indexOf(',')+1);
                z = Double.parseDouble(s);
                GeoLocation gl = new GeoLocation(x,y,z);
                graph.addNode(new NodeData(jo.getInt("id"),gl));
            }
            for(int i=0 ; i<e.length() ; i++)
            {
                JSONObject jo = new JSONObject(e.get(i).toString());
                graph.connect(jo.getInt("src"),jo.getInt("dest"),jo.getDouble("w"));
            }
            init(graph);
            br.close();
            fr.close();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
            return false;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void DFS(int v,int[] low,Stack<Integer> s)
    {
        Object[] arr = this.dwg.nodes.get(v).neigh.keySet().toArray();
        boolean root = true;
        low[v] = counter;
        s.push(v);
        for(int i=0 ; i<arr.length ; i++)
        {
            if(low[(int)arr[i]]==Integer.MAX_VALUE)
            {
                counter++;
                DFS((int)arr[i], low, s);
            }
            if(low[(int)arr[i]]<low[v])
            {
                low[v] = low[(int)arr[i]];
                root = false;
            }
        }
        if(root)
        {
            this.component++;
            while(v!=s.peek())
            {
                s.pop();
            }
            s.pop();
        }
    }
    public void initJson(String g) {
        try
        {
            DWGraph_DS graph = new DWGraph_DS();
            JSONObject json = new JSONObject(g);
            JSONArray n = new JSONArray(json.get("Nodes").toString());
            JSONArray e = new JSONArray(json.get("Edges").toString());
            for(int i=0 ; i<n.length() ; i++)
            {
                JSONObject jo = new JSONObject(n.get(i).toString());
                String s = jo.getString("pos");
                double x,y,z;
                x =  Double.parseDouble(s.substring(0,s.indexOf(',')));
                s = s.substring(s.indexOf(',')+1);
                y = Double.parseDouble(s.substring(0,s.indexOf(',')));
                s = s.substring(s.indexOf(',')+1);
                z = Double.parseDouble(s);
                GeoLocation gl = new GeoLocation(x,y,z);
                graph.addNode(new NodeData(jo.getInt("id"),gl));
            }
            for(int i=0 ; i<e.length() ; i++)
            {
                JSONObject jo = new JSONObject(e.get(i).toString());
                graph.connect(jo.getInt("src"),jo.getInt("dest"),jo.getDouble("w"));
            }
            this.init(graph);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
}