package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Frame extends JFrame {

    double xMin,xMax,yMin,yMax;
    directed_weighted_graph graph;
    Collection<CL_Pokemon>  pokemons;
    List<Point3D> agents;


    public void init(directed_weighted_graph graph,Collection<CL_Pokemon> pokemons){
        this.graph = graph;
        this.pokemons = pokemons;
        this.agents = new ArrayList<Point3D>();
    }

    public void paint(Graphics g) {
        g.clearRect(0,0,this.getWidth(),this.getHeight());
        drawNodes(g);
        drawPokemons(g);
        drawAgent(g);
    }

    public void drawNodes(Graphics g) {
        Collection<node_data> node = this.graph.getV();
        Iterator<node_data> iter = node.iterator();
        while (iter.hasNext()) {
            node_data n = iter.next();
            Point3D p = frameLocation(n.getLocation());
            int r = 10;
            g.setColor(Color.green);
            g.fillOval((int) p.x() - r / 2, (int) p.y() - r / 2, r, r);
            g.setColor(Color.black);
            g.drawOval((int) p.x() - r / 2, (int) p.y() - r / 2, r, r);
            g.setColor(Color.green);
            g.drawString("" + n.getKey(), (int) p.x(), (int) p.y() - r);
            drawEdges(g, this.graph.getE(n.getKey()));
        }
    }

    public void drawEdges(Graphics g, Collection<edge_data> edges) {
        Iterator<edge_data> iter = edges.iterator();
        while (iter.hasNext()) {
            edge_data edge = iter.next();
            Point3D src = frameLocation((Point3D) graph.getNode(edge.getSrc()).getLocation());
            Point3D dest = frameLocation((Point3D) graph.getNode(edge.getDest()).getLocation());
            g.setColor(Color.blue);
            g.drawLine((int) src.x(), (int) src.y(), (int) dest.x(), (int) dest.y());
        }
    }
    public void drawAgent(Graphics g) {
        Iterator<Point3D> iter = this.agents.iterator();
        while(iter.hasNext())
        {
            Point3D p = frameLocation(iter.next());
            g.setColor(Color.red);
            g.fillOval((int)p.x()-5,(int)p.y()-5,10,10);
            g.setColor(Color.black);
            g.drawOval((int)p.x()-5,(int)p.y()-5,10,10);
        }

    }
    public void drawPokemons(Graphics g){
        Iterator<CL_Pokemon> iter = this.pokemons.iterator();
        while(iter.hasNext())
        {
            CL_Pokemon poke = iter.next();
            geo_location p = frameLocation((Point3D)poke.getLocation());
            if(poke.getType()==-1)
                g.setColor(Color.yellow);
            else
                g.setColor(Color.orange);
            int r = 15;
            g.fillOval((int)p.x()-r/2,(int)p.y()-r/2,r,r);
            g.setColor(Color.black);
            g.drawOval((int)p.x()-r/2,(int)p.y()-r/2,r,r);
        }
    }
    public Point3D frameLocation(geo_location p) {
        double x = (((p.x() - this.xMin) / (this.xMax - this.xMin)) * (this.getWidth() * 0.75) + this.getWidth() * 0.1);
        double y = (((p.y() - this.yMin) / (this.yMax - this.yMin)) * (this.getHeight() * 0.75) + this.getHeight() * 0.15);
        return new Point3D(x, y, 0);
    }
     public void setRange(double xMin,double xMax,double ymin,double ymax) {
         this.xMax = xMax;
         this.xMin = xMin;
         this.yMin = ymin;
         this.yMax = ymax;
     }
     public void setPokemons(List<CL_Pokemon> poke) {
        this.pokemons = poke;
     }
     public void setAgents(List<Point3D> ag){
        this.agents = ag;
     }


}

