import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
/**
 * 백준 14442 : 벽 부수고 이동하기2
 * 맵에서 0은 이동 가능, 1은 벽으로 이동 불가
 * (1, 1)에서 (N, M)으로 이동하는데 최단 경로로 이동하고 싶음, 시작하는 칸과 끝나는 칸도 포함해서 센다.
 * 만약 이동하는 도중 벽을 만나면 K개까지 벽을 부수고 이동 가능
 * 맵이 주어 졌을 때 최단 경로를 구해 내는 프로그램 작성
 */
public class BOJ14442 {
	static int N;
	static int M;
	static int K;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 맵의 크기 정보와 부술 수 있는 최대 벽의 개수 입력 받음
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		// 4방향 탐색을 위한 배열 선언
		int[] dr = {-1, 1, 0, 0};
		int[] dc = {0, 0, -1, 1};
		// 맵과 방문 체크를 위한 배열 초기화
		int[][] map = new int[N + 1][M + 1];
		boolean[][][] isVisited = new boolean[N + 1][M + 1][K + 1];
		// 맵의 정보를 입력 받음
		for(int i = 1; i <= N; i++) {
			char[] c = br.readLine().toCharArray();
			for(int j = 1; j <= M; j++)
				map[i][j] = c[j - 1] - '0';
		}
		
		// bfs 탐색을 위한 queue 생성
		Queue<Point> q = new LinkedList<Point>();
		q.add(new Point(1, 1, 1, 0));
		isVisited[1][1][0] = true;
		// 경로 수를 새기 위한 변수와 도착 여부 확인을 위한 변수 선언
		int cnt = 0;
		boolean flag = false;
		// bfs 진행
		loop:while(!q.isEmpty()) {
			// 경로 수를 새기 위해 q 사이즈 만큼 진행하고 경로 수 ++
			int size = q.size();
			cnt++;
			// 다음 경로로 진행
			for(int i = 0; i < size; i++) {
				// 현재 위치를 큐에서 꺼낸다
				Point now = q.poll();
				// 도착지점에 도착 했으면 bfs 종료
				if(now.r == N && now.c == M) {
					flag = true;
					break loop;
				}
				// 4방향 탐색 진행
				for(int d = 0; d < 4; d++) {
					int nr = now.r + dr[d];
					int nc = now.c + dc[d];
					// 다음 위치가 범위에 벗어나거나 방문했다면 continue
					if(nr < 1 || nc < 1 || nr > N || nc > M || isVisited[nr][nc][now.wallCnt])
						continue;
					// 다음 위치가 벽일 때
					if(map[nr][nc] == 1) {
						// 벽을 깨고 이동할 수 있다면 이동, 없다면 continue
						if(now.wallCnt < K) {
							isVisited[nr][nc][now.wallCnt] = true;
							q.add(new Point(nr, nc, now.cnt + 1, now.wallCnt + 1));
						}
						continue;
					}
					// 벽이 아니므로 이동
					isVisited[nr][nc][now.wallCnt] = true;
					q.add(new Point(nr, nc, now.cnt + 1, now.wallCnt));
				}
			}
		}
		if(!flag)
			cnt = -1;
		System.out.println(cnt);
	}
	
	public static int distance(Point p) {
		return Math.abs(p.r - N) + Math.abs(p.c - M);
	}
	
	static class Point {
		int r;
		int c;
		int cnt;
		int wallCnt;
		
		public Point(int r, int c, int cnt, int wallCnt) {
			super();
			this.r = r;
			this.c = c;
			this.cnt = cnt;
			this.wallCnt = wallCnt;
		}

		@Override
		public String toString() {
			return "Point [r=" + r + ", c=" + c + ", cnt=" + cnt + ", wallCnt=" + wallCnt + "]";
		}
	}
}
