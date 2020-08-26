import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
/**
 * 백준 4179 : 불!
 * 미로에서 지훈이의 위치와 불이 붙은 위치를 감안해서 지훈이가 불에 탈출할 수 있는지의 여부, 그리고 얼마나 빨리 탈출 할 수 있는지 결정해야 한다.
 * 지훈이와 불은 매분마다 한칸 씩 수평 또는 수직(4방향) 이동
 * 지훈이는 미로의 가장자리에 접한 공간에서 탈출 가능
 * # : 벽	J : 지훈이	F : 불	. : 지나갈 수 있는 공간
 */
public class BOJ4179 {
	static int R;
	static int C;
	
	static char[][] miro;
	// 두 개의 bfs가 진행되야 해서 큐 두개 선언
	static Queue<Point> fire;
	static Queue<Point> jihun;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		miro = new char[R][C];
		
		fire = new LinkedList<Point>();
		jihun = new LinkedList<Point>();
		
		for(int i = 0; i < R; i++) {
			miro[i] = br.readLine().toCharArray();
			for(int j = 0; j < C; j++) {
				if(miro[i][j] == 'F')
					fire.add(new Point(i, j));
				else if(miro[i][j] == 'J')
					jihun.add(new Point(i, j));
			}
		}
		
		int ans = bfs();
		
		if(ans == -1)
			System.out.println("IMPOSSIBLE");
		else
			System.out.println(ans);
	}
	
	static int bfs() {
		int[] dr = {-1, 1, 0, 0};
		int[] dc = {0, 0, -1, 1};
		int cnt = 0;
		// 불을 먼저 번지게 한 후 지훈이가 움직여서 그 시간에 움직일 수 있는지 확인한다
		while(!jihun.isEmpty()) {
			cnt++;
			// 불을 먼저 번지게 한다
			int fireSize = fire.size();
			for(int i = 0; i < fireSize; i++) {
				Point f = fire.poll();
				
				for(int d = 0; d < 4; d++) {
					int nr = f.r + dr[d];
					int nc = f.c + dc[d];
					
					if(nr < 0 || nc < 0 || nr >= R || nc >= C || miro[nr][nc] == '#' || miro[nr][nc] == 'F')
						continue;
					
					miro[nr][nc] = 'F';
					fire.add(new Point(nr, nc));
				}
			}
			// 지훈이 이동
			int jihunSize = jihun.size();
			for(int i = 0; i < jihunSize; i++) {
				Point j = jihun.poll();
				
				for(int d = 0; d < 4; d++) {
					int nr = j.r + dr[d];
					int nc = j.c + dc[d];
					
					if(nr < 0 || nc < 0 || nr >= R || nc >= C) 
						return cnt;
					
					if(miro[nr][nc] == '#' || miro[nr][nc] == 'F' || miro[nr][nc] == 'J')
						continue;
					
					miro[nr][nc] = 'J';
					jihun.add(new Point(nr, nc));
				}
			}
		}
		
		return -1;
	}

	static class Point {
		int r;
		int c;
		
		public Point(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
}