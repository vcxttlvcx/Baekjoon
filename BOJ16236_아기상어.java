import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 백준 16236 : 아기 상어
 * N×N 크기의 공간에 물고기 M마리와 아기 상어 1마리가 있다. 공간은 1×1 크기의 정사각형 칸으로 나누어져 있다. 한 칸에는 물고기가 최대 1마리 존재한다.
 * 아기 상어와 물고기는 모두 크기를 가지고 있고, 이 크기는 자연수이다. 가장 처음에 아기 상어의 크기는 2이고, 아기 상어는 1초에 상하좌우로 인접한 한 칸씩 이동한다.
 * 아기 상어는 자신의 크기보다 큰 물고기가 있는 칸은 지나갈 수 없고, 나머지 칸은 모두 지나갈 수 있다. 아기 상어는 자신의 크기보다 작은 물고기만 먹을 수 있다. 따라서, 크기가 같은 물고기는 먹을 수 없지만, 그 물고기가 있는 칸은 지나갈 수 있다.
 * 아기 상어가 어디로 이동할지 결정하는 방법은 아래와 같다.
 * 		더 이상 먹을 수 있는 물고기가 공간에 없다면 아기 상어는 엄마 상어에게 도움을 요청한다.
 * 		먹을 수 있는 물고기가 1마리라면, 그 물고기를 먹으러 간다.
 * 		먹을 수 있는 물고기가 1마리보다 많다면, 거리가 가장 가까운 물고기를 먹으러 간다.
 * 			거리는 아기 상어가 있는 칸에서 물고기가 있는 칸으로 이동할 때, 지나야하는 칸의 개수의 최솟값이다.
 * 			거리가 가까운 물고기가 많다면, 가장 위에 있는 물고기, 그러한 물고기가 여러마리라면, 가장 왼쪽에 있는 물고기를 먹는다.
 * 아기 상어의 이동은 1초 걸리고, 물고기를 먹는데 걸리는 시간은 없다고 가정한다. 즉, 아기 상어가 먹을 수 있는 물고기가 있는 칸으로 이동했다면, 이동과 동시에 물고기를 먹는다. 물고기를 먹으면, 그 칸은 빈 칸이 된다.
 * 아기 상어는 자신의 크기와 같은 수의 물고기를 먹을 때 마다 크기가 1 증가한다. 예를 들어, 크기가 2인 아기 상어는 물고기를 2마리 먹으면 크기가 3이 된다.
 * 공간의 상태가 주어졌을 때, 아기 상어가 몇 초 동안 엄마 상어에게 도움을 요청하지 않고 물고기를 잡아먹을 수 있는지 구하는 프로그램을 작성하시오.
 */
public class BOJ16236_아기상어 {
	static int N;
	static int M;

	static int[][] map;
	// 상어 위치를 bfs로 탐색, 가장 위부터 위가 같으면 왼쪽을 먼저 탐색하기 위해 우선순위 큐 사용
	static PriorityQueue<Fish> shark;
	// 가장 위에 있는 물고기 먼저 방문, 높이가 같다면 왼쪽 먼저 방문
	static int[] dr = { -1, 0, 0, 1 };
	static int[] dc = { 0, -1, 1, 0 };

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		N = Integer.parseInt(br.readLine());
		M = 0;

		map = new int[N][N];
		shark = new PriorityQueue<Fish>();
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());

			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				// 상어 위치 찾고 그 위치를 0으로 바꿔줌
				if (map[i][j] == 9) {
					shark.offer(new Fish(i, j, 2, 0));
					map[i][j] = 0;
				} else if (map[i][j] != 0)	// 물고기 총  마리 수 카운트
					M++;
			}
		}

		int answer = 0;
		int T = 0;
		// 상어가 이동 시 방문 위치 체크
		boolean[][] visited = new boolean[N][N];
		loop: while (!shark.isEmpty() && M > 0) {
			// size 이용해서 하면 큐 하나로 하면 우선순위 큐 이기 때문에 먼거리의 위치가 먼저 나올 수 있기에 큐 하나 더 추가
			PriorityQueue<Fish> temp = new PriorityQueue<Fish>();
			// 현재 이동 한 위치로 초기화
			while (!shark.isEmpty())
				temp.offer(shark.poll());
			// 같은 거리 이동한 위치의 상어에 대해 반복
			while (!temp.isEmpty()) {
				Fish current = temp.poll();
				// 현재 위치에 있는 물고기를 먹을 수 있다면 먹는다
				if (map[current.r][current.c] != 0 && map[current.r][current.c] < current.size) {
					current.eat();
					// 현재 위치 부터 다시 거리 측정을 위해 큐를 초기화한 후 현재 위치만 삽입
					shark.clear();
					shark.offer(current);
					// 물고기 총 마리 수 감소
					M--;
					// 현재 위치의 물고기를 먹었기에 0으로 초기화
					map[current.r][current.c] = 0;
					// 방문 체크 역시 false로 다 초기화
					for(int i = 0; i < N; i++)
						Arrays.fill(visited[i], false);
					// 물고기를 잡아 먹었을 때의 시간으로 답을 구해줘야 함으로 답을 물고기 먹은 시간으로 초기화
					answer = T;
					// 현재 거리 이동을 다 생략하고 다시 거리 증가 하도록 이동
					continue loop;
				}
				// 4방향 이동을 하며 물고기 위치 탐색
				for (int d = 0; d < 4; d++) {
					int nr = current.r + dr[d];
					int nc = current.c + dc[d];

					if (nr < 0 || nc < 0 || nr >= N || nc >= N || visited[nr][nc] || map[nr][nc] > current.size)
						continue;

					visited[nr][nc] = true;
					shark.add(new Fish(nr, nc, current.size, current.cnt));
				}
			}
			T++;
		}

		// 결과 출력
		System.out.println(answer);
		br.close();
	}
	// 상어의 위치를 저장하기 위한 클래스
	// 위부터 탐색하며 같은 경우 왼쪽 부터 탐색 되도록 comparable 구현
	public static class Fish implements Comparable<Fish> {
		int r;
		int c;
		int size;
		int cnt;

		public Fish(int r, int c, int size, int cnt) {
			super();
			this.r = r;
			this.c = c;
			this.size = size;
			this.cnt = cnt;
		}
		// 물고기를 먹었을 때 cnt를 증가시키고 cnt가 size와 같아지면 사이즈를 증가 시킨다
		public void eat() {
			cnt++;

			if (cnt == size) {
				size++;
				cnt = 0;
			}
		}

		@Override
		public int compareTo(Fish o) {
			if (this.r == o.r)
				return this.c - o.c;
			return this.r - o.r;
		}
	}
}
