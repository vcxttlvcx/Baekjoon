import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 17472 : 다리 만들기 2
 * 섬으로 이루어진 나라가 있고, 모든 섬을 다리로 연결하려고 한다. 이 나라의 지도는 N×M 크기의 이차원 격자로 나타낼 수 있고, 격자의 각 칸은 땅이거나 바다이다.
 * 섬은 연결된 땅이 상하좌우로 붙어있는 덩어리를 말하고, 아래 그림은 네 개의 섬으로 이루어진 나라이다. 색칠되어있는 칸은 땅이다.
 * 다리는 바다에만 건설할 수 있고, 다리의 길이는 다리가 격자에서 차지하는 칸의 수이다. 다리를 연결해서 모든 섬을 연결하려고 한다.
 * 섬 A에서 다리를 통해 섬 B로 갈 수 있을 때, 섬 A와 B를 연결되었다고 한다. 다리의 양 끝은 섬과 인접한 바다 위에 있어야 하고, 한 다리의 방향이 중간에 바뀌면 안된다. 또, 다리의 길이는 2 이상이어야 한다.
 * 다리의 방향이 중간에 바뀌면 안되기 때문에, 다리의 방향은 가로 또는 세로가 될 수 밖에 없다. 방향이 가로인 다리는 다리의 양 끝이 가로 방향으로 섬과 인접해야 하고, 방향이 세로인 다리는 다리의 양 끝이 세로 방향으로 섬과 인접해야 한다.
 * 섬 A와 B를 연결하는 다리가 중간에 섬 C와 인접한 바다를 지나가는 경우에 섬 C는 A, B와 연결되어있는 것이 아니다.
 * 나라의 정보가 주어졌을 때, 모든 섬을 연결하는 다리 길이의 최솟값을 구해보자.
 */
public class BOJ17472_다리만들기2 {
	static int N;
	static int M;
	
	static int[][] sea;
	static int[][] bridge;
	
	static int numIsland;
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		// 바다의 정보를 읽어옴
		sea = new int[N][M];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++)
				sea[i][j] = Integer.parseInt(st.nextToken());
		}
		// 섬을 찾아 라벨을 붙이고 섬의 총 개수를 구한다
		numIsland = 0;
		int label = 1;
		boolean[][] visited = new boolean[N][M];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(visited[i][j])
					continue;
				// 방문하지 않았고 1인 곳을 섬으로 찾아 라벨을 붙인다
				if(sea[i][j] == 1) {
					makeLabel(i, j, label++, visited);
					numIsland++;
				}
			}
		}
		// 다리를 놓은 간선 정보를 배열에 저장
		bridge = new int[numIsland + 1][numIsland + 1];
		lenBridge();
		// 프림 알고리즘을 적용해 최소 비용 찾기
		int min = 0;
		int minVertex = 0;
		int[] minEdge = new int[numIsland + 1];
		Arrays.fill(minEdge, Integer.MAX_VALUE);
		
		int answer = 0;
		
		minEdge[1] = 0;
		boolean[] selected = new boolean[numIsland + 1];
		// 프림 알고리즘 시작
		for(int i = 1; i <= numIsland; i++) {
			min = Integer.MAX_VALUE;
			minVertex = 0;
			// 현재 갈 수 있게 표시 해둔 다리 중 가장 짧은 것을 선택
			for(int v = 1; v <= numIsland; v++) {
				if(!selected[v] && minEdge[v] < min) {
					minVertex = v;
					min = minEdge[v];
				}
			}
			// 섬 방문 체크 및 최소 비용 더하기
			selected[minVertex] = true;
			answer += min;
			// 선택 된 정점을 포함하여 다른 섬에 갈 수 있는 최소거리 업데이트
			for(int v = 1; v <= numIsland; v++) {
				if(!selected[v] && bridge[minVertex][v] != 0 && bridge[minVertex][v] < minEdge[v])
					minEdge[v] = bridge[minVertex][v];
			}
		}
		// minEdge 중 하나 라도 업데이트가 안되어 있으면 모두 연결하는 방법이 없음
		for(int i = 1; i <= numIsland; i++) {
			if(minEdge[i] == Integer.MAX_VALUE) {
				answer = -1;
				break;
			}
		}
		
		System.out.println(answer);
		br.close();
	}
	// 다리를 놓을 수 있는지 찾고 있다면 최소 다리 비용을 배열에 입력
	static void canBridge(int row, int col, int dir) {
		int curLabel = sea[row][col];
		
		int cnt = -1;
		// 같은 방향으로 계속 진행하며 섬이 있는지 확인
		do {
			row += dr[dir];
			col += dc[dir];
			
			if(row < 0 || col < 0 || row >= N || col >= M || sea[row][col] == curLabel)
				return;
			cnt++;
		} while(sea[row][col] == 0);
		// 다리의 길이가 2보다 작으면 리턴
		if(cnt < 2)
			return;
		// 다리가 놓여지는 방법이 없었다면 현재 값으로 업데이트
		if(bridge[curLabel][sea[row][col]] == 0)
			bridge[curLabel][sea[row][col]] = cnt;
		// 다리가 놓여지는 방법 중 가장 짧은 경우를 선택
		else
			bridge[curLabel][sea[row][col]] = Math.min(bridge[curLabel][sea[row][col]], cnt);
	}
	// 섬을 찾아 다리를 놓는다
	static void lenBridge() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(sea[i][j] == 0)
					continue;
				
				for(int dir = 0; dir < 4; dir++)
					canBridge(i, j, dir);
			}
		}
		
	}
	// 섬에 라벨을 붙여 섬을 구분하여 준다
	static void makeLabel(int row, int col, int label, boolean[][] visited) {
		Queue<int[]> q = new LinkedList<int[]>();
		visited[row][col] = true;
		sea[row][col] = label;
		q.offer(new int[] {row, col});
		// 큐를 통해 탐색하여 4방향으로 이어진 경우 같은 섬으로 체크 해준다
		while(!q.isEmpty()) {
			int r = q.peek()[0];
			int c = q.peek()[1];
			q.poll();
			
			for(int i = 0; i < 4; i++) {
				int nr = r + dr[i];
				int nc = c + dc[i];
				
				if(nr < 0 || nc < 0 || nr >= N || nc >= M || visited[nr][nc] || sea[nr][nc] == 0)
					continue;
				
				visited[nr][nc] = true;
				sea[nr][nc] = label;
				q.offer(new int[] {nr, nc});
			}
		}
	}
}
