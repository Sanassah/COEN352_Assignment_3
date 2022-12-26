import java.io.File;
import java.io.FileNotFoundException;  
import java.util.Scanner; 
import java.util.StringTokenizer;

class Graph implements GraphInterface {
    public String [] courses;
    protected static int def_size = 50;
    public Graph() {
        courses = new String [def_size];
    }
    private int[][] matrix;             
    private int numEdge;                   
    public int[] Mark;                
    public int[] Count;

    public Graph(int n) {                 // Constructor
        Init(n);
    }

    public void Init(int n) {
        Mark = new int[n];
        Count = new int[n];
        matrix = new int[n][n];
        numEdge = 0;
    }

    public int n() { return Mark.length; } 

    public int e() { return numEdge; }     

    public int first(int v) { 
        for (int i=0; i<Mark.length; i++)
            if (matrix[v][i] != 0) return i;
        return Mark.length;  
    }

    public int next(int v, int w) { 
        for (int i=w+1; i<Mark.length; i++)
            if (matrix[v][i] != 0)
                return i;
        return Mark.length;  
    }

    public boolean isEdge(int i, int j) 
    { return matrix[i][j] != 0; }
    
    public void setEdge(int i, int j, int wt) {
        assert wt!=0 : "Cannot set weight to 0";
        if (matrix[i][j] == 0) numEdge++;
        matrix[i][j] = wt;
    }

    public void delEdge(int i, int j) {
        if (matrix[i][j] != 0) {
            matrix[i][j] = 0;
            numEdge--;
        }
    }

    public int weight(int i, int j) { 
        if (i == j) return 0;
        if (matrix[i][j] == 0) return Integer.MAX_VALUE;
        return matrix[i][j];
    }
    
    public void setMark(int v, int val) { Mark[v] = val; }
    public int getMark(int v) { return Mark[v]; }

    public int incrCount(int w) {return ++Count[w];}

    static final int UNVISITED = 0;
    static final int VISITED = 1;

    public int n = 44; // Number of vertices in the graph
    public Graph createGraph() throws FileNotFoundException {
        File file = new File("src/coen_course.gph");  //file path
        Scanner myReader = new Scanner(file);
        String lineOfText = myReader.nextLine();
        Graph graph = new Graph();

        graph.Init(n);
        for (int i=0; i<n; i++)
            graph.setMark(i, UNVISITED);

        while (myReader.hasNextLine()) {
            int token1 = 0;
            int token2 = 0;
            if (lineOfText.contains("#"))
                lineOfText = myReader.nextLine();
            else {
                StringTokenizer lineTokens = new StringTokenizer(lineOfText, " ");

                token1 = Integer.parseInt(lineTokens.nextToken());
                token2 = Integer.parseInt(lineTokens.nextToken());

                graph.setEdge(token1, token2, 1);
                lineOfText = myReader.nextLine();
            }
            graph.courses[token1] = String.valueOf(token1);
            graph.courses[token2] = String.valueOf(token2);
        }
    return graph;
    }
    public void clear() {
        for(int i = 0; i < this.n(); i++)
        {
            this.setMark(i, UNVISITED);
        }
    }
    public String getPrerequisitePath(String courseCode) {
        this.clear(); 
        ADTStack <String> stack = new AStack <String> (50);
        ADTStack <String> output = new AStack <String> (50);
        String toReturn = "";
        stack.push(courseCode);
        output.push(courseCode);

        while(stack.length() > 0)
        {
            String v = stack.pop();

            String [] prereq = this.getPrerequisiteHelp(v);

            if (prereq != null) {
                for(int i = 0; i < prereq.length; i++)
                {
                    output.push(prereq[i]);
                    stack.push(prereq[i]);
                }
            }
        }
        while(output.length() > 0)
        {
            toReturn += output.pop();
            toReturn += " ";
        }
        return toReturn;
    }

    public String [] getPrerequisiteHelp(String courseCode) {
        int count = 0;
        int indexInput = -1;
        String[] prereq = new String [this.n() - 1];

        for(int i = 0; i < this.n(); i++) {
            if (courses[i] != null) {
                if (courses[i].equals(courseCode))
                    indexInput = i;
            }
        }
        if(indexInput == -1)
            return null;

        for(int k = 0; k < this.n(); k++) {
            if(matrix[k][indexInput] != 0)
            {
                this.setMark(k, VISITED);
                prereq[count] = courses[k];
                count++;
            }
        }

        String [] str = new String[count];
        for(int j = 0; j < count; j++) {
            str[j] = prereq[j];
            //System.out.println(prereq[j]);
        }
        return str;
    }
    public boolean isPrerequisite(String sourceCourse, String destinationCourse ){
        this.clear();
        ADTQueue <String> queue = new AQueue <String> (50);
        String str = destinationCourse;
        queue.enqueue(str);

        while(queue.length() > 0) {
            str = queue.dequeue();
            String [] prereq = this.getPrerequisiteHelp(str);
            if (prereq != null) {
                for(int i = 0; i < prereq.length; i++) {
                    if(prereq[i].equals(sourceCourse))
                        return true;
                    queue.enqueue(prereq[i]);
                }
            }
        }
        return false;
    }
}
