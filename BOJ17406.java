import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * 백준 17406 : 배열 돌리기4
 * 크기가 N x M 크기인 배열이 있을 때, 회전 연산을 수행 할 수 있다
 * 회전 연산은 (r, c, s)로 이루어져 있으며 가장 왼쪽 위가 (r - s, c - s), 가장 오른쪽 아래 칸이 (r + s, c + s)인 정사각형을
 * 시계 방향으로 한칸씩 돌린다는 의미이다.
 * 연산이 주어졌을 때, 회전 연산을 모두 한번 씩 사용하고, 순서는 임의로 정해도 될 때,
 * 각 행의 합들 중 최소값이 배열의 값이라고 할 때, 배열의 최소 값을 구하는 문제
 */
public class BOJ17406 {
	static int N;
	static int M;
	static int K;
	// 배열과 명령어를 저장하고 순열을 저장하기 위한 변수 선언
	static int[] select;
	static int[][] arr;
	static int[][] command;
	
	static int min = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 배열 정보와 연산의 횟수 입력
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		// 배열 정보를 입력
		arr = new int[N + 1][M + 1];
		for(int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j = 1; j <= M; j++)
				arr[i][j] = Integer.parseInt(st.nextToken());
		}
		// 명령어를 입력 받아 배열에 저장
		select = new int[K];
		command = new int[K][3];
		for(int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			command[i][0] = Integer.parseInt(st.nextToken());
			command[i][1] = Integer.parseInt(st.nextToken());
			command[i][2] = Integer.parseInt(st.nextToken());
		}
		// 순열을 통해 명령어를 구성하여 확인한다
		permutation(0, 0);
		
		System.out.println(min);
		br.close();
	}
	// 기존 배열을 유지하기 위한 배열 카피 메서드
	public static int[][] copyArr() {
		int[][] copy = new int[N + 1][M + 1];
		// 배열을 카피한다, for each를 통해 값만 빼온다
		int r = 0;
		for(int[] i : arr) {
			int c = 0;
			for(int n : i) {
				copy[r][c] = n;
				c++;
			}
			r++;
		}
		
		return copy;
	}
	// 주어진 배열에서 행의 합들 중 최소값을 찾는다
	public static void findMin(int[][] temp ) {
		for(int i = 1; i <= N; i++) {
			int sum = 0;
			for(int j = 1; j <= M; j++)
				sum += temp[i][j];

			min = Math.min(min, sum);
		}
	}
	// 순열을 통해 명령어의 조합을 구성
	public static void permutation(int cnt, int isVisited) {
		// 순열이 완성 되었을 때
		if(cnt == K) {
			// 배열을 값만 빼서 카피 해온 후 명령 수행
			int[][] temp = copyArr();
			for(int i = 0; i < K; i++)
				turnArr(temp, command[select[i]][0], command[select[i]][1], command[select[i]][2]);
			findMin(temp);
			
			return;
		}
		
		for(int i = 0; i < K; i++) {
			if((isVisited & (1 << i)) > 0)
				continue;
			
			select[cnt] = i;
			permutation(cnt + 1, isVisited | (1 << i));
		}
	}
	// 배열을 돌리는 연산
	public static void turnArr(int[][] arr, int R, int C, int S) {
		// 배열 돌리는 진행 순서
		int[] dr = {0, 1, 0, -1};
		int[] dc = {1, 0, -1, 0};
		// 정사각형 내부 전체를 돌려야 하기 때문에 s를 증가시켜 가며 배열을 돌린다
		for(int s = 1; s <= S; s++) {
			// 왼쪽 위부터 시작
			int row = R - s;
			int col = C - s;
			int dir = 0;
			// 현재 값과 전 값을 저장하기 위한 변수
			int before = 0;
			int now = 0;
			// 회전 진행
			do {
				// 현재 값을 저장하고 현재 값에는 전값을 저장하며 배열을 돌려나간다.
				now = arr[row][col];
				arr[row][col] = before;
				before = now;
				// 다음 위치를 찾는다
				int nr = row + dr[dir];
				int nc = col + dc[dir];
				// 다음 위치가 사각형에서 벗어나면 진행 방향을 바꾼다
				if(nr < (R - s) || nc < (C - s) || nr > (R + s) || nc > (C + s)) {
					dir++;
					nr = row + dr[dir];
					nc = col + dc[dir];
				}
				// 다음 위치를 현재 위치로 조정
				row = nr;
				col = nc;
			} while(row != (R - s) || col != (C - s));
			// 마지막으로 현재 위치에 전 값을 저장함으로서 회전 완료
			arr[row][col] = before;
		}
	}
}
