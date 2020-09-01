import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 백준 1753 : 최단 경로
 * 방향그래프가 주어지면 주어진 시작점에서 다른 모든 정점까지의 최단 경로를 구하는 프로그램
 * 정점의 개수가 최대 20,000개 이므로 행령을 쓰면 메모리 초과가 날 것임
 * 즉, 인접 리스트를 이용하여 그래프를 만들어야 함
 */
public class BOJ1753 {
	// 정점과 간선의 개수
	static int V;
	static int E;
	// 시작 정점에서의 거리와 방문 체크를 위한 변수
	static int[] dist;
	static boolean[] isVisited;
	// 간 정점에서 연결된 정점을 관리하기 위한 리스트
	static List<ArrayList<Edge>> edge;
	// 무한대 값을 설정
	static final int INF = 987654321;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 정점과 간선의 개수를 입력 받음
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		// 정점의 개수만큼 거리와 방문체크 변수 초기화
		dist = new int[V + 1];
		isVisited = new boolean[V + 1];
		// 각 정점에 연결된 간선들을 관리하기 위해 리스트 초기화
		edge = new ArrayList<ArrayList<Edge>>();
		for(int i = 0; i <= V; i++)
			edge.add(new ArrayList<Edge>());
		// 시작 정점 읽어오기
		int start = Integer.parseInt(br.readLine());
		// 간선 정보 읽어 오기
		for(int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			edge.get(u).add(new Edge(v, w));
		}
		// 모든 정점과의 거리를 무한대 값으로 초기화 후 시작 정점만 0으로 초기화
		Arrays.fill(dist, INF);
		dist[start] = 0;
		// 최소 거리에 있는 정점을 빠르게 가져오기 위한 우선순위 큐
		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();
		pq.add(new Vertex(start, dist[start]));
		
		Vertex current = null;
		
		while(!pq.isEmpty()) {
			// 가장 가까운 거리에 있는 정점 가져오기
			current = pq.poll();
			// 방문 한 정점이면 continue
			if(isVisited[current.vertex])
				continue;
			// 방문 체크
			isVisited[current.vertex] = true;
			// 현재 정점에서 연결된 간선들 만을 체크 함
			List<Edge> list = edge.get(current.vertex);
			for(Edge e : list) {
				if(!isVisited[e.v] && dist[e.v] > current.totalDistance + e.w) {
					dist[e.v] = current.totalDistance + e.w;
					pq.add(new Vertex(e.v, dist[e.v]));
				}
			}
		}
		
		StringBuilder sb = new StringBuilder("");
		for(int i = 1; i <= V; i++) {
			if(dist[i] == INF)
				sb.append("INF\n");
			else
				sb.append(dist[i]).append("\n");
		}
		System.out.println(sb.toString());
	}
	// 우선순위 큐를 활용하여 가장 가까운 거리에 있는 정점을 가져오기 위해 만든 클래스
	static class Vertex implements Comparable<Vertex> {
		int vertex;
		int totalDistance;
		
		public Vertex(int vertex, int totalDistance) {
			this.vertex = vertex;
			this.totalDistance = totalDistance;
		}
		// 시작 정점에서 현재 정점까지의 거리를 기준으로 오름차순 정렬
		@Override
		public int compareTo(Vertex o) {
			// TODO Auto-generated method stub
			return this.totalDistance - o.totalDistance;
		}
		
	}
	// 간선을 인접리스트를 이용하기 위한 클래스
	static class Edge {
		int v;
		int w;
		
		public Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}
		
	}
}
