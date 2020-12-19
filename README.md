# Ex2
## api

  - NodeData
  
    - Class variables:
  
      ```java
       static int count = 0;
       int key, tag;
       double weight;
       HashMap<Integer,EdgeData> neigh;
       GeoLocation gl;
       String info;
       ```
       The HashMap neigh contain all the neighbors of this vertice.
       
  - EdgeData
    
    - Class variables:
      
      ```java
      int srcKey, dstKey, tag;
      double weight;
      String info;
      ```
       - 
  
  - DWGraph_DS
  
    - Class variables:
    
      ```java
      HashMap<Integer, NodeData> nodes;
      HashMap<Double,EdgeData> edges;
      int mc;
      ```
       Have two HashMap's: "nodes" contain all the graph vertices, and "edges" contain all the graph edges.
       
 - DWGraph_Algo
   
   - Class variables:
   
       ```java
       DWGraph_DS dwg;
       int component;
       static int counter;
       ```
      component is used in isConnected function.
       
   - Functios:
     
     - isConnected: based on tarjan algorithm.
     
     - shortestPathDist: based on dijkstra algorithm.
     
     - shortestPath: based on dijkstra algorithm.
     
     - DFS: used for traversing the graph for isConnected function.
     
     - initJson: init dwg from json String.

## gameClient

  - frame
    
    - Class variables:
    
      ```java
      double xMin,xMax,yMin,yMax;
      directed_weighted_graph graph;
      Collection<CL_Pokemon> pokemons;
      List<Point3D> agents;
      ```
      xMin,xMax,yMin,yMax represent the edges of the vertices.
      agents is List of Point3D that represent the locations of the agents.
      
    - Functions:
    
      - init: initiate the class variable.
      - frameLocation: convert the vertices coordinates to pixel coordinates.
  
  - Ex2
    
    This class run the game.
    While the game is running, the algorithm check for each agent which is the closest pokemon by DWGraph_Algo.shortestPathDist.
    Then use DWGraph_algo.shortestPath to determine the path to that pokemon.
    The algorithm use the move() function every fifth iteration, and constantly when agent is on edge that contain pokemon.
    
    - Class variable
      ```java
      static int lvl;
      static long id;
      game_service game;
      double xMin,xMax,yMin,yMax;
      List<CL_Pokemon> pokemons;
      List<CL_Agent> agents = new ArrayList<>();
      directed_weighted_graph graph;
      Frame frame = new Frame();
      ```
      
  
  ### util
    
    - line: represent simple line y=mx+n. Used to find out on which edges the pokemons are.
      
    
  
      
    
