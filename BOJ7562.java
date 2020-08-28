import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 7562 : 나이트의 이동 
 * 체스말 나이트가 이동하는 규칙을 이용하여 시작점에서 종료점 까지 이동하는데 걸리는 최소 회수를 구하는 프로그램
 */
public class BOJ7562 {
	static int I;

	static boolean[][] isVisited;
	
	static int[] dr = { -2, -2, -1, 1, 2, 2, 1, -1 };
	static int[] dc = { -1, 1, 2, 2, 1, -1, -2, -2 };
	
	static int answer;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			answer = Integer.MAX_VALUE;
			
			I = Integer.parseInt(br.readLine());
			isVisited = new boolean[I][I];
			// 나이트의 시작 위치
			StringTokenizer st = new StringTokenizer(br.readLine());
			Point start = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			// 나이트가 이동하고자 하는 목표 위치
			st = new StringTokenizer(br.readLine());
			Point end = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			// bfs 탐색을 위한 queue 선언
			Queue<Point> q = new LinkedList<Point>();
			// 처음 위치 방문 체크 및 큐에 삽입
			isVisited[start.r][start.c] = true;
			q.add(start);
			// 이동 회수를 새기 위한 변수
			int cnt = 0;
			// bfs로 탐색하며 말 이동
			loop:while(!q.isEmpty()) {
				int size = q.size();
				
				for(int i = 0; i < size; i++) {
					Point now = q.poll();
					
					if(now.r == end.r && now.c == end.c)
						break loop;
					
					for(int d = 0; d < 8; d++) {
						int nr = now.r + dr[d];
						int nc = now.c + dc[d];
						
						if(nr < 0 || nc < 0 || nr >= I || nc >= I || isVisited[nr][nc])
							continue;
						
						isVisited[nr][nc] = true;
						q.add(new Point(nr, nc));
					}
				}
				cnt++;
			}
			
			System.out.println(cnt);
		}
		
		br.close();
	}

	static class Point {
		int r;
		int c;

		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
}
