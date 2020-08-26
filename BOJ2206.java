import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
/**
 * 백준 2206 : 벽 부수고 이동하기
 * 맵에서 0은 이동 가능, 1은 벽으로 이동 불가
 * (1, 1)에서 (N, M)으로 이동하는데 최단 경로로 이동하고 싶음, 시작하는 칸과 끝나는 칸도 포함해서 센다.
 * 만약 이동하는 도중 벽을 만나면 한 개의 벽을 부수고 이동 가능
 * 맵이 주어 졌을 때 최단 경로를 구해 내는 프로그램 작성
 */

public class BOJ2206 {
	static int N;
	static int M;
	// 맵을 저장할 배열과 방문 체크를 할 배열 선언
	// 방문 체크 시 벽을 몇 개 깨고 왔는지 확인하기 위해 3차원으로 선언
	static char[][] map;
	static boolean[][][] isVisited;
	// 사방향 이동을 위한 배열 선언
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		// map과 방문 체크 배열 초기화 및 입력 받음
		map = new char[N][M];
		isVisited = new boolean[N][M][2];
		for(int r = 0; r < N; r++)
			map[r] = br.readLine().toCharArray();
		// bfs로 탐색을 위해 queue를 선언
		// (1, 1) ~ (N, M) 대신 (0, 0) ~ (N - 1, M - 1) 까지로 대체하여 탐색
		Queue<Point> q = new LinkedList<Point>();
		q.add(new Point(0, 0, 0));
		isVisited[0][0][0] = true;
		// 경로 수 체크를 위한 변수와 도착 지점에 도착하였는가 여부를 확인하기 위한 변수 선언
		int cnt = 0;
		boolean flag = false;
		// bfs 진행
		loop:while(!q.isEmpty()) {
			// 경로 수 체크를 위해 현재 큐 사이즈 만큼 진행하고 경로 수 더해줌
			int size = q.size();
			cnt++;
			
			for(int i = 0; i < size; i++) {
				Point now = q.poll();
				// 현재 방문 위치가 마지막 위치일 경우 bfs 진행 중단
				if(now.r == N - 1 && now.c == M - 1) {
					flag = true;
					break loop;
				}
				// 4방향 탐색을 하며 탐색 진행
				for(int d = 0; d < 4; d++) {
					int nr = now.r + dr[d];
					int nc = now.c + dc[d];
					// 방문 했거나 범위에서 벗어 났을 경우 continue
					// 방문 확인은 3차원으로 벽을 몇개 깨고 왔는지 여부까지 체크
					if(nr < 0 || nc < 0 || nr >= N || nc >= M || isVisited[nr][nc][now.wallCnt])
						continue;
					// 벽일 때 벽을 깰 수 있다면 깨고 진행 없다면 continue
					if(map[nr][nc] == '1') {
						if(now.wallCnt < 1) {
							isVisited[nr][nc][now.wallCnt + 1] = true;
							q.add(new Point(nr, nc, now.wallCnt + 1));
						}
						continue;
					}
					// 다음으로 탐색 가능하면 탐색을 진행함
					isVisited[nr][nc][now.wallCnt] = true;
					q.add(new Point(nr, nc, now.wallCnt));
				}
			}
			
		}
		// 도착하지 못한 경우 -1로 초기화
		if(!flag)
			cnt = -1;
		// 결과 출력
		System.out.println(cnt);
	}
}
// 좌표와 현재 벽을 몇개 깼는지를 저장하기 위한 class 선언
class Point {
	int r;
	int c;
	int wallCnt;
	
	public Point(int r, int c, int wallCnt) {
		this.r = r;
		this.c = c;
		this.wallCnt = wallCnt;
	}
}