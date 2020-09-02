import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 17471 : 게리맨더링
 * N개의 선거구를 각각이 연결되어 있는 2개의 선거구로 나눌 때, 두 선거구에 포함된 인구의 차이를 최소로 하는 프로그램 작성
 */
public class BOJ17471 {
	static int N;
	
	static int[] population;
	static boolean[] isSelected;
	
	static boolean[][] edge;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		population = new int[N + 1];
		isSelected = new boolean[N + 1];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) 
			population[i] = Integer.parseInt(st.nextToken());
		
		edge = new boolean[N + 1][N + 1];
		for(int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			
			int size = Integer.parseInt(st.nextToken());
			for(int j = 0; j < size; j++)
				edge[i][Integer.parseInt(st.nextToken())] = true;
		}
		
		System.out.println(solve());
	}
	
	public static boolean isConnected(ArrayList<Integer> group) {
		boolean flag = false;
		
		if(group.size() == 0)
			return false;
		// 연결 되어 있는 정점들을 보관할 리스트
		ArrayList<Integer> connect = new ArrayList<Integer>();
		connect.add(group.get(0));	// 가장 앞쪽 정점을 기준으로 연결 시작
		// 연결 안되어 있는 정점을 관리하기 위한 큐
		// 지금 연결되어 있는 정점 말고 나중에 연결된 정점을 통해 연결 될 수 있으므로 큐를 통해 추후 계속 확인
		Queue<Integer> q = new LinkedList<Integer>();
		for(int i = 1; i < group.size(); i++)
			q.offer(group.get(i));
		// 연결 된 정점에서 다른 정점을 연결할 수 있는지 확인
		// connect에 정점이 추가되면 사이즈가 늘어나서 늘어난 만큼 더 탐색함
		for(int i = 0; i < connect.size(); i++) {
			// 현재 큐에 들어 있는 정점들만 탐색하여 연결 가능성 확인
			int size = q.size();
			for(int j = 0; j < size; j++) {
				int v = q.poll();
				// 연결된 간선이 있다면 connect에 추가 없다면 큐에 추가해서 다른 정점에서 탐색 해봄
				if(edge[connect.get(i)][v])
					connect.add(v);
				else
					q.add(v);
			}
		}
		// 큐 사이즈가 0이면 모든 정점들이 연결 되어 있는 것이므로 true 설정
		if(q.size() == 0)
			flag = true;
		
		return flag;
	}
	
	public static int solve() {
		int min = Integer.MAX_VALUE;
		// 선택된 부분집합들이 방문 했었는지 여부 확인
		boolean[] visited = new boolean[1 << N];
		// 부분 집합을 이용하는 문제이므로 비트마스킹을 이용해 풀이
		for(int i = 1; i < (1 << N); i++) {
			ArrayList<Integer> group1 = new ArrayList<Integer>();
			ArrayList<Integer> group2 = new ArrayList<Integer>();
			// 이미 사용해본 부분집합일 경우 패스
			if(visited[i])
				continue;
			// 두 부분집합을 나누기 위한 변수
			int a = 0;
			int b = 0;
			
			for(int j = 0; j < N; j++) {
				// 현재 부분집합에 속할 경우 group1
				if((i & (1 << j)) > 0) {
					group1.add(j + 1);
					a = a | (1 << j);
				} else {	// 현재 부분집합에 속하지 않을 경우 group2
					group2.add(j + 1);
					b = b | (1 << j);
				}
			}
			// N이 6일 때, {1, 2, 3} 집합을 group1로 선택하는 경우와 {4, 5, 6}으로 선택하는 경우는 같은 것이므로
			// {1, 2, 3}이 선택 되었을 때 두 부분집합 모두 방문 한것으로 체크하여 반복 횟수를 줄인다
			visited[a] = visited[b] = true;
			// 두 그룹 모두가 연결 되어 있을 경우
			if(isConnected(group1) && isConnected(group2)) {
				int diff = 0;
				// 두 그룹간의 인구 수 차이를 계산
				for(int j = 0; j < group1.size(); j++)
					diff += population[group1.get(j)];
				for(int j = 0; j < group2.size(); j++)
					diff -= population[group2.get(j)];
				// 현재 찾은 값과 비교하여 최소값 갱신
				min = Math.min(min, Math.abs(diff));
			}
		}
		// 만약 최소값이 한번도 갱신 되지 않았을 경우 두 그룹으로 나눌 수 없는 것
		if(min == Integer.MAX_VALUE)
			min = -1;
		
		return min;
	}
}
