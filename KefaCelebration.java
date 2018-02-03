import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n, m;
		int[] cat;
		UnGraph g;
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		cat = new int[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) 
			cat[i] = Integer.parseInt(st.nextToken());
		g = new UnGraph(n);
		for (int i = 0; i < n - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			g.addEdge(x-1, y-1);
		}
		
		DFS dfs = new DFS(m, cat, g);
		System.out.println(dfs.ans());
	}
}

class DFS {
	private int m, ans;
	private int[] cat;
	private boolean[] marked;
	private UnGraph g;
	
	public DFS(int m, int[] cat, UnGraph g) {
		this.m = m;
		this.cat = cat;
		this.g = g;
		marked = new boolean[g.V()];
		ans = 0;
		marked[0] = true;
		dfs(0, cat[0]);
	}
	
	int ans() {
		return ans;
	}
	
	// solution using dfs with extra consecutive cats variable
	void dfs(int v, int cats) {
		if (cats > m) return;
		boolean isChild = true;
		
		for (int i : g.adj(v)) {
			if (!marked[i]) {
				marked[i] = true;
				isChild = false;
				if (cat[i] == 1) dfs(i, cats + 1);
				else			 dfs(i, 0);
			}
		}
		
		if (isChild) ans++;
	}
}

class UnGraph {
	private int v;
	private LinkedList<Integer>[] adj;
	
	@SuppressWarnings("unchecked")
	public UnGraph(int v) {
		this.v = v;
		adj = (LinkedList<Integer>[]) new LinkedList[v];
		for (int i = 0; i < v; i++) {
			adj[i] = new LinkedList<>();
		}
	}
	
	int V() {
		return v;
	}
	
	void addEdge(int v, int w) {
		adj[v].add(w);
		adj[w].add(v);
	}
	
	Iterable<Integer> adj(int v) {
		return adj[v];
	}
}
