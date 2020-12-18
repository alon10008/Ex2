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
     - 
