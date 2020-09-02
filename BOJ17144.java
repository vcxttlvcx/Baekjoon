import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 17144 : 미세먼지 안녕!
 * 미세먼지를 제거하기 위해 공기청정기를 설치하려고 한다
 * 집을 크기가 RxC인 격자판으로 나타냈고, 1x1 크기의 칸으로 나눴다
 * 공기청정기는 항상 1열에 설치되어 있고 두칸을 차지한다
 * 공기청정기가 설치 되지 않은 칸에는 미세먼지가 있다
 * 1초 동안 아래 적힌 일이 순서대로 일어난다
 * 1. 미세먼지가 확산된다. 확산은 미세먼지가 있는 모든 칸에서 동시에 일어난다
 * 		(r, c)에 있는 미세먼지는 인접한 네 방향으로 확산
 * 		인접한 방향에 공기청정기가 있거나, 칸이 없으면 그 방향은 확산이 일어나지 않는다
 * 		확산되는 양은 미세먼지양/5이고 소수점은 버린다
 * 		(r, c)에 남은 미세먼지 양은 (원래 있던 양 - (원래 있던 양/5)*(확산된 방향의 개수))
 * 2. 공기 청정기가 작동
 * 		공기 청정기에서는 바람이 나온다
 * 		위쪽 공기청정기의 바람은 반시계방향으로 순환, 아래쪽 공기청정기는 시계방향으로 순환
 * 		바람이 불면 미세먼지가 바람의 방향대로 모두 한 칸씩 이동
 * 		공기청정기에서 부는 바람은 미세먼지가 없는 바람이고, 공기청정기로 들어간 바람은 모두 정화된다
 * 
 * 방의 정보가 주어졌을 때, T초가 지난 후 방에 남아있는 미세먼지의 양을 구하는 프로그램
 */
public class BOJ17144 {
	// 방의 크기와 몇초 동안 진행 할 것인지에 대한 변수
	static int R;
	static int C;
	static int T;
	// 미세먼지와 공기청정기 정보가 담겨 있는 방정보
	static int[][] room;
	
	static int up;		// 위쪽 공기청정기 Row index
	static int down;	// 아래쪽 공기청정기 Row index
	// 미세먼지 확산은 위한 4방향 진행 배열
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	// 결과 값 저장을 위한 변수
	static int answer;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 방의 크기 정보와 시간정보 입력
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		// 방의 정보 입력
		room = new int[R][C];
		for(int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < C; j++) {
				room[i][j] = Integer.parseInt(st.nextToken());
				
				if (room[i][j] == -1)	// 위에서 부터 내려오니 -1이 늦게 나오면 아래쪽 공기청정기
					down = i;
			}
		}
		// 아래쪽 공기청정기 인덱스 -1이 위쪽 공기청정기
		up = down - 1;
		// 공기청정기 작동 시작
		answer = 0;
		solve();
		
		System.out.println(answer);
		br.close();
	}
	
	public static void spread() {
		// 미세먼지가 퍼진 위치와 그 값을 저장하기 위한 큐
		Queue<Dust> dust = new LinkedList<Dust>();
		// 미세먼지가 있는 곳을 찾아 확산을 진행
		for(int r = 0; r < R; r++) {
			for(int c = 0; c < C; c++) {
				if(room[r][c] <= 0)
					continue;
				// 미세먼지가 있는 위치일 경우
				int cnt = 0;	// 미세먼지가 몇칸 퍼져나갔는지 세기 위한 변수
				for(int d = 0; d < 4; d++) {
					int nr = r + dr[d];
					int nc = c + dc[d];
					// 확산될 칸이 범위를 벗어났거나 공기청정기가 있는 위치라면 스킵
					if(nr < 0 || nc < 0 || nr >= R || nc >= C || room[nr][nc] == -1)
						continue;
					// 미세먼지 확산
					cnt++;
					dust.offer(new Dust(nr, nc, room[r][c] / 5));
				}
				// 현재 위치의 미세먼지를 확산한 만큼 뺘주고 방을 0으로 초기화
				dust.offer(new Dust(r, c, (room[r][c] - (room[r][c] / 5) * cnt)));
				room[r][c] = 0;
			}
		}
		// 확산 후의 미세먼지 값을 방에 다 입력해준다
		while(!dust.isEmpty()) {
			Dust now = dust.poll();
			room[now.r][now.c] += now.amount; 
		}
	}
	
	public static void runAir(int air) {
		// 0 ~ 3 시계 방향, 4 ~ 7 반시게방향
		int[] moveR = {0, -1, 0, 1, 0, 1, 0, -1};
		int[] moveC = {1, 0, -1, 0, 1, 0, -1, 0};
		// 공기청정기의 위치
		int r = air;
		int c = 0;
		// 전 값을 저장하기 위한 변수 선언
		int before = 0;
		int now = 0;
		// 시계 방향으로 돌것이지 반시계 방향으로 돌것인지를 결정
		int d = 0;
		if(air == up)
			d = 0;
		else
			d = 4;
		// 시작 지점이 공기청정기 위치이므로 do while문으로 진행
		do {
			int nr = r + moveR[d];
			int nc = c + moveC[d];
			// 범위를 벗어나면 진행 방향을 바꾼다
			if(nr < 0 || nc < 0 || nr >= R || nc >= C) {
				d++;
				
				nr = r + moveR[d];
				nc = c + moveC[d];
			}
			// 미세먼지를 한칸씩 멀어 낸다
			now = room[nr][nc];
			room[nr][nc] = before;
			before = now;
			
			r = nr;
			c = nc;
		} while(r != up || c != 0);
		// 반복문을 돌고나면 공기청정기 위치에 있는 값이 바뀌므로 다시 -1로 돌려줌
		room[air][0] = -1;
	}
	
	public static void solve() {
		// 주어진 시간 만큼 미세먼지 확산 및 공기청정기 작동
		for(int i = 0; i < T; i++) {
			spread();		// 미세먼지 확산
			runAir(up);		// 위쪽 공기청정기 작동
			runAir(down);	// 아래쪽 공기청정기 작동
		}
		// 남아 있는 미세먼지의 양을 구한다
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				if(room[i][j] > 0)
					answer += room[i][j];
			}
		}
	}
	// 미세먼지를 관리하기 위한 클래스
	static class Dust {
		int r;
		int c;
		int amount;
		
		public Dust(int r, int c, int amount) {
			super();
			this.r = r;
			this.c = c;
			this.amount = amount;
		}
	}
}
