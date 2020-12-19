package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.*;
import java.util.List;


public class Ex2  implements Runnable {

    static int lvl;
    static long id;
    game_service game;
    double xMin,xMax,yMin,yMax;
    List<CL_Pokemon> pokemons;
    List<CL_Agent> agents = new ArrayList<>();
    directed_weighted_graph graph;
    Frame frame = new Frame();

    public static void main(String[] args) {
        try
        {
            lvl = Integer.parseInt(args[1]);
        }
        catch(Exception e)
        {
            System.out.println("Wrong input!\nSecond arg represent the level of the game, must be an integer[0,23]!\nDefault level: 0");
            lvl = 0;
        }
        try
        {
            id = Integer.parseInt(args[0]);
        }
        catch(Exception e)
        {
            System.out.println("Wrong input!\nFirst arg represent id number, must be an integer!\nDefault id: 0\n");
            id = 0;
        }
        if(lvl<0 || lvl>23)
        {
            lvl = 0;
            System.out.println("Wrong input!\nSecond arg represent the level of the game, must be an integer of [0,23]!\nDefault level: 0");
        }
        Thread thread = new Thread(new Ex2());
        thread.start();
    }

    public void run() {
        this.game = Game_Server_Ex2.getServer(lvl);
        this.game.login(id);
        this.graph = game.getJava_Graph_Not_to_be_used();
        frame.setSize(700,400);
        setRange();
        setPokemons();
        addAgents(1);
        frame.setRange(this.xMin,this.xMax,this.yMin,this.yMax);
        frame.init(this.graph,this.pokemons);
        frame.setTitle("Ex2");
        frame.setBackground(Color.darkGray);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.show();
        this.game.startGame();
        int nd = 1;
        while(this.game.isRunning())
        {
            try
            {
                setPokemons();
                moveAgent();
                addAgents(0);
                frame.setPokemons(Arena.json2Pokemons(this.game.getPokemons()));
                frame.setAgents(agentsLoc());
                if(nd%3==0)
                {
                    frame.repaint();
                }
                for(int i=0 ; i<this.agents.size() ; i++)
                {
                    for(int j=0 ; j<this.pokemons.size() ; j++)
                    {
                        if(this.agents.get(i).getSrcNode()==this.pokemons.get(j).get_edge().getSrc())
                            this.game.move();
                    }
                }
                if(nd%5==0)
                    this.game.move();
                Thread.sleep(100);
                nd++;
            }
            catch(InterruptedException e)
           {
                e.printStackTrace();
           }
        }
        System.out.println(this.game.toString());
        System.exit(0);
    }

    public void setRange() {
        Collection<node_data> list = this.graph.getV();
        Iterator<node_data> iter = list.iterator();
        boolean first = true;
        this.xMin = this.xMax = this.yMin = this.yMax = 0;
        while(iter.hasNext())
        {
            node_data n = iter.next();
            if(first)
            {
                this.xMax = this.xMin = n.getLocation().x();
                this.yMax = this.yMin = n.getLocation().y();
                first = false;
            }
            else
            {
                if(this.xMin>n.getLocation().x())
                    this.xMin = n.getLocation().x();
                if(this.xMax<n.getLocation().x())
                    this.xMax = n.getLocation().x();
                if(this.yMin>n.getLocation().y())
                    this.yMin = n.getLocation().y();
                if(this.yMax<n.getLocation().y())
                    this.yMax = n.getLocation().y();
            }
        }
    }

    public void setPokemons() {
        this.pokemons = Arena.json2Pokemons(this.game.getPokemons());
        Iterator<CL_Pokemon> iter = this.pokemons.iterator();
        while(iter.hasNext())
        {
            CL_Pokemon poke = iter.next();
            edge_data edge = findEdge(poke.getLocation());
                if(poke.getType()==-1 && edge.getSrc()<edge.getDest())
                    poke.set_edge(this.graph.getEdge(edge.getDest(),edge.getSrc()));
                else if(poke.getType()==-1)
                    poke.set_edge(edge);
                if(poke.getType()==1 && edge.getDest()<edge.getSrc())
                    poke.set_edge(this.graph.getEdge(edge.getDest(),edge.getSrc()));
                else
                    poke.set_edge(edge);
        }
    }
    public void addAgents(int condition) {
        try
        {
            JSONObject json = new JSONObject(this.game.toString());
            JSONObject j = json.getJSONObject("GameServer");
            int num = j.getInt("agents");
            if(condition==1)
            {
                for(int i=0 ; i<num ; i++)
                {
                    if(i<(this.pokemons.size()))
                        this.game.addAgent(this.pokemons.get(i).get_edge().getSrc());
                    else
                        this.game.addAgent(new Random().nextInt(this.graph.nodeSize()));
                }
            }
            JSONObject a = new JSONObject(this.game.getAgents());
            JSONArray b = a.getJSONArray("Agents");
            this.agents = new ArrayList<CL_Agent>();
            for(int i=0 ; i<b.length() ;i++)
            {
                JSONObject c = b.getJSONObject(i);
                JSONObject d = c.getJSONObject("Agent");
                CL_Agent ag = new CL_Agent(this.graph,d.getInt("src"),d.getInt("id"));
                ag.setNextNode(d.getInt("dest"));
                ag.setSpeed(d.getDouble("speed"));
                this.agents.add(ag);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void moveAgent() {
        Iterator<CL_Agent> iter = this.agents.iterator();
        DWGraph_Algo ga = new DWGraph_Algo();
        ga.initJson(this.game.getGraph());
        boolean flag = true;
        while(iter.hasNext())
        {
            CL_Agent ag = iter.next();
            double shortest = Double.POSITIVE_INFINITY;
            int nodeKey = -1,index=-1;
            if(ag.getNextNode()==-1)
            {
                for (int i = 0; i < this.pokemons.size(); i++)
                {
                    if (ga.shortestPathDist(ag.getSrcNode(),this.pokemons.get(i).get_edge().getSrc()) < shortest)
                    {
                        flag = true;
                        Iterator<CL_Agent> itr = this.agents.iterator();
                        while(itr.hasNext())
                        {
                            CL_Agent agent = itr.next();
                            if((ga.shortestPathDist(ag.getSrcNode(),this.pokemons.get(i).get_edge().getSrc())/ag.getSpeed())>(ga.shortestPathDist(agent.getSrcNode(),this.pokemons.get(i).get_edge().getSrc()))/agent.getSpeed())
                            {
                                flag = false;
                                index = i;
                                nodeKey = this.pokemons.get(i).get_edge().getSrc();
                            }
                        }
                        if(flag)
                        {
                            shortest = ga.shortestPathDist(ag.getSrcNode(), this.pokemons.get(i).get_edge().getSrc());
                            nodeKey = this.pokemons.get(i).get_edge().getSrc();
                            index = i;
                        }
                    }
                }
                if(true)
                {
                    if(ga.shortestPath(ag.getSrcNode(), nodeKey).size() != 0)
                        this.game.chooseNextEdge(ag.getID(), ga.shortestPath(ag.getSrcNode(), nodeKey).get(0).getKey());
                    else
                        this.game.chooseNextEdge(ag.getID(), this.pokemons.get(index).get_edge().getDest());
                }

            }
        }

    }
    public List<Point3D> agentsLoc(){
        List<Point3D> l = new ArrayList<Point3D>();
        try
        {
            JSONObject a = new JSONObject(this.game.getAgents());
            JSONArray b = a.getJSONArray("Agents");
            for(int i=0 ; i<b.length() ;i++)
            {
                JSONObject c = b.getJSONObject(i);
                JSONObject d = c.getJSONObject("Agent");
                Point3D p = new Point3D(d.getString("pos"));
                l.add(p);
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return l;
    }
    public edge_data findEdge(Point3D p){
        try
        {
            Point3D pp = frameLocation(p);
            double eps = 0.0000001;
            edge_data edge = null;
            JSONObject json = new JSONObject(this.game.getGraph());
            JSONArray arr = json.getJSONArray("Edges");
            boolean flag = true;
            for(int i=0 ; i<arr.length() ; i++)
            {
                JSONObject j = arr.getJSONObject(i);
                node_data a = this.graph.getNode(j.getInt("src"));
                node_data b = this.graph.getNode(j.getInt("dest"));
                Line line = new Line(frameLocation(a.getLocation()),frameLocation(b.getLocation()));
                if(Math.abs(line.getx(pp.y())-pp.x())<eps && Math.abs(line.gety(pp.x())-pp.y())<eps)
                {
                    edge = this.graph.getEdge(a.getKey(),b.getKey());
                    flag = false;
                }
            }
            if(flag)
            for(int i=0 ; i<arr.length() ; i++)
            {
                JSONObject j = arr.getJSONObject(i);
                node_data a = this.graph.getNode(j.getInt("src"));
                node_data b = this.graph.getNode(j.getInt("dest"));
                Line line = new Line(frameLocation(a.getLocation()),frameLocation(b.getLocation()));
                if(pp.x()<=line.maxx() && pp.x()>=line.minx() && pp.y()<=line.maxy() && pp.y()>=line.miny())
                    return this.graph.getEdge(a.getKey(),b.getKey());
            }
            return edge;
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public Point3D frameLocation(geo_location p) {
        double x = (((p.x() - this.xMin) / (this.xMax - this.xMin)) * (frame.getWidth() * 0.75) + frame.getWidth() * 0.1);
        double y = (((p.y() - this.yMin) / (this.yMax - this.yMin)) * (frame.getHeight() * 0.75) + frame.getHeight() * 0.15);
        return new Point3D(x, y, 0);
    }
}
