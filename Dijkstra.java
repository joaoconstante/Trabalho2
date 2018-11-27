package aprendendo;
import java.util.*;

public class Dijkstra {
    /** aqui será definida as constantes*/
    public static final int INF=Integer.MAX_VALUE; //infinity
    public static final int NUM_VERTICES=8;
    /**Agora iremos definir as cidades.*/
    public static final int HNL=0;
    public static final int SFO=1;
    public static final int LAX=2;
    public static final int ORD=3;
    public static final int DFW=4;
    public static final int LGA=5;
    public static final int PVD=6;
    public static final int MIA=7;
    public static final int nonexistent=8;
    /**inicialização e termino das vértices*/
    public static final int FIRST_VERTEX=HNL;
    public static final int LAST_VERTEX=MIA;
    /**lista de nomes das cidades, para saída*/
    private String[] name={"HNL","SFO","LAX","ORD","DFW","LGA","PVD","MIA"};
    /**agora a matriz de distância inicial ("peso")*/
    private int weight[][]=
    {
    /** HNL    SFO     LAX     ORD     DFW     LGA     PVD     MIA */
        {0,     INF,    2555,   INF,    INF,    INF,    INF,    INF }, //HNL
        {INF,   0,      337,    1843,   INF,    INF,    INF,    INF }, //SFO
        {2555,  337,    0,      1743,   1233,   INF,    INF,    INF }, //LAX
        {INF,   1843,   1743,   0,      802,    INF,    849,    INF }, //ORD
        {INF,   INF,    1233,   802,    0,      1387,   INF,    1120}, //DFW
        {INF,   INF,    INF,    INF,    1387,   0,      142,    1099}, //LGA
        {INF,   INF,    INF,    849,    INF,    142,    0,      1205}, //PVD
        {INF,   INF,    INF,    INF,    1120,   1099,   1205,   0   }, //MIA
    };
    /** Algoritmo de Dijkstra começa aqui */
    int[] distance=new int[NUM_VERTICES];
    boolean[] tight=new boolean[NUM_VERTICES];
    int[] predecessor=new int[NUM_VERTICES];
    private int minimalNontight(){
        int j,u;
        for(j=FIRST_VERTEX; j<LAST_VERTEX; j++){
            if(!tight[j])
                break;
        }
        assert(j<=LAST_VERTEX);
        u=j; /** u agora é o primeiro vértice com estimativa não atual, mas talvez não o mínimo. */
        for(j++; j<=LAST_VERTEX; j++)
            if(!tight[j] && distance[j]<distance[u])
                u=j;
        return u;
    }
    private boolean successor(int u, int z){
        return ((weight[u][z]!=INF) && u!=z);
    }
    /**agora vai inicializar essas matrizes*/
    public void dijkstra(int s){
        int z,u;
        int i;
        distance[s]=0;
        for(z=FIRST_VERTEX; z<=LAST_VERTEX; z++){
            if(z!=s) distance[z]=INF;
            tight[z]=false;
            predecessor[z]=nonexistent;
        }
        for(i=0; i<NUM_VERTICES; i++){
            u=minimalNontight();
            tight[u]=true;
            if(distance[u]==INF)
                continue;
            for(z=FIRST_VERTEX; z<=LAST_VERTEX; z++)
                if(successor(u,z) && !tight[z] && (distance[u]+weight[u][z]<distance[z])){
                    distance[z]=distance[u]+weight[u][z]; 
                    predecessor[z]=u;
                }
        }      
    }
    public void printShortestPath(int origin, int destination){
        assert(origin!=nonexistent && destination!=nonexistent);
        dijkstra(origin);
        System.out.println(" O caminho mais curto de "+name[origin]+" á "+name[destination]+" é:\n");
        Stack<Integer> st=new Stack<Integer>();
        for(int v=destination; v!=origin; v=predecessor[v])
            if(v==nonexistent){
                System.out.println("non-existent (because the graph is not connected).");
                return;
            }else{
                st.push(v);
            }
        st.push(origin);
        while(!st.empty())
            System.out.print(name[st.pop()]+" -> ");
        System.out.println(" Fim da Execução");
    }
    public Dijkstra(){ return; }
    public static void main(String[] unused){
        new Dijkstra().printShortestPath(LAX,MIA);
    }
}
