import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1600_말이되고픈원숭이_조석준 {
	static int[] hourseDr = { -1, -2, -2, -1, 1, 2, 2, 1 };
	static int[] hourseDc = { -2, -1, 1, 2, 2, 1, -1, -2 };

	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 말 처럼 움직일 수 있는 회수
		int K = Integer.parseInt(br.readLine());
		// 격자판의 크기 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		int C = Integer.parseInt(st.nextToken());
		int R = Integer.parseInt(st.nextToken());
		// 격자판 정보 입력
		int[][] board = new int[R + 1][C + 1];
		for (int i = 1; i <= R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= C; j++)
				board[i][j] = Integer.parseInt(st.nextToken());
		}

		boolean[][][] visited = new boolean[R + 1][C + 1][K + 1];
		visited[1][1][0] = true;

		PriorityQueue<Monkey> pq = new PriorityQueue<Monkey>(new Comparator<Monkey>() {

			@Override
			public int compare(Monkey o1, Monkey o2) {
				return o1.cnt - o2.cnt;
			}
		});

		pq.add(new Monkey(1, 1, 0, 0));
		int ans = -1;
		while (!pq.isEmpty()) {
			Monkey now = pq.poll();

			if (now.r == R && now.c == C) {
				ans = now.cnt;
				break;
			}
			// 말 처럼 이동
			if (now.kCnt < K) {
				for (int i = 0; i < 8; i++) {
					int nr = now.r + hourseDr[i];
					int nc = now.c + hourseDc[i];

					if (nr < 1 || nr > R || nc < 1 || nc > C || board[nr][nc] == 1)
						continue;

					if (visited[nr][nc][now.kCnt + 1])
						continue;

					visited[nr][nc][now.kCnt + 1] = true;
					pq.add(new Monkey(nr, nc, now.kCnt + 1, now.cnt + 1));
				}
			}
			// 상하좌우 이동
			for (int i = 0; i < 4; i++) {
				int nr = now.r + dr[i];
				int nc = now.c + dc[i];

				if (nr < 1 || nr > R || nc < 1 || nc > C || board[nr][nc] == 1 || visited[nr][nc][now.kCnt])
					continue;

				visited[nr][nc][now.kCnt] = true;
				pq.add(new Monkey(nr, nc, now.kCnt, now.cnt + 1));
			}
		}

		System.out.println(ans);
	}

	static class Monkey {
		int r;
		int c;
		int kCnt;
		int cnt;

		public Monkey(int r, int c, int kCnt, int cnt) {
			super();
			this.r = r;
			this.c = c;
			this.kCnt = kCnt;
			this.cnt = cnt;
		}

	}
}
