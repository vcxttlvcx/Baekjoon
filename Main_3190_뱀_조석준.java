import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * BOJ 3190 : 뱀
 * 'Dummy' 라는 도스게임이 있다. 이 게임에는 뱀이 나와서 기어다니는데, 사과를 먹으면 뱀 길이가 늘어난다. 뱀이 이리저리 기어다니다가 벽 또는 자기자신의 몸과 부딪히면 게임이 끝난다.
 * 게임은 NxN 정사각 보드위에서 진행되고, 몇몇 칸에는 사과가 놓여져 있다. 보드의 상하좌우 끝에 벽이 있다. 게임이 시작할때 뱀은 맨위 맨좌측에 위치하고 뱀의 길이는 1 이다. 뱀은 처음에 오른쪽을 향한다.
 * 뱀은 매 초마다 이동을 하는데 다음과 같은 규칙을 따른다.
 * 		먼저 뱀은 몸길이를 늘려 머리를 다음칸에 위치시킨다.
 * 		만약 이동한 칸에 사과가 있다면, 그 칸에 있던 사과가 없어지고 꼬리는 움직이지 않는다.
 * 		만약 이동한 칸에 사과가 없다면, 몸길이를 줄여서 꼬리가 위치한 칸을 비워준다. 즉, 몸길이는 변하지 않는다.
 * 사과의 위치와 뱀의 이동경로가 주어질 때 이 게임이 몇 초에 끝나는지 계산하라.
 */
public class Main_3190_뱀_조석준 {
	static int[] dr = { 0, 1, 0, -1 };
	static int[] dc = { 1, 0, -1, 0 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 보드의 크기 입력 및 보드 초기화
		int N = Integer.parseInt(br.readLine());
		int[][] board = new int[N + 1][N + 1];
		// 사과의 개수 입력 및 사과의 위치를 보드에 기록
		int K = Integer.parseInt(br.readLine());
		for (int i = 0; i < K; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			board[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = 3;
		}
		// 방향 변환 횟수와 방향 변환 시간, 방향 입력
		int L = Integer.parseInt(br.readLine());
		int[] turnTime = new int[L];
		char[] turnDir = new char[L];
		for (int i = 0; i < L; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			turnTime[i] = Integer.parseInt(st.nextToken());
			turnDir[i] = st.nextToken().charAt(0);
		}
		// 앞 뒤로 다 넣을 수 있는 deque를 이용해 뱀을 표현
		Deque<int[]> snake = new LinkedList<int[]>();
		snake.add(new int[] { 1, 1 });
		board[1][1] = 1;
		int dir = 0; // 뱀의 진행 방향
		int x = 0; // 뱀의 방향 전환 일어나는 시기 알기 위한 index
		int time = 0;
		// 뱀 이동 시작
		while (true) {
			time++;
			// 뱀의 다음 머리 위치
			int nr = snake.getFirst()[0] + dr[dir];
			int nc = snake.getFirst()[1] + dc[dir];

			if (nr < 1 || nr > N || nc < 1 || nc > N || board[nr][nc] == 1)
				break;
			// 머리 위치는 이동
			snake.addFirst(new int[] { nr, nc });
			// 만약 머리 이동한 위치가 사과가 아니라면 맨 뒤에 있는 꼬리를 제거
			if (board[nr][nc] != 3) {
				int[] last = snake.pollLast();
				board[last[0]][last[1]] = 0;
			}
			board[nr][nc] = 1;
			// 이동이 끝난 후 뱀의 방향을 바꿔야 하는지 확인
			if (x < L && time == turnTime[x]) {
				if (turnDir[x] == 'D')
					dir = (dir + 1) % 4;
				else {
					dir = (dir - 1) < 0 ? 3 : dir - 1;
				}
				x++;
			}
		}
		// 결과 출력
		System.out.println(time);

		br.close();
	}

}
