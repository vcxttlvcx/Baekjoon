import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 백준 1074 : Z
 * 배열을 Z 자 형태로 방문
 *
 */
public class Z {
	static int N;
	static int r;
	static int c;
	static int cnt;
	static int ans;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		
		N = Integer.parseInt(input[0]);
		r = Integer.parseInt(input[1]);
		c = Integer.parseInt(input[2]);
		
		cnt = 0;
		ans = -1;
		int size = (int)Math.pow(2, N);
		
		solve(size, 0, 0);
		
		System.out.println(ans);
		br.close();
	}
	// size가 2일 때만 방문하고, 이 이상일경우 2로 나눈 사이즈만큼 재귀 호출하여 4부분을 방문하여 준다
	public static void solve(int size, int row, int col) {
		if(ans > -1)
			return;
		if(size == 2) {
			if(row == r && col == c)
				ans = cnt;
			else 
				cnt++;
			
			if(row == r && col + 1 == c)
				ans = cnt;
			else 
				cnt++;
			
			if(row + 1 == r && col == c)
				ans = cnt;
			else 
				cnt++;
			
			if(row + 1 == r && col + 1 == c)
				ans = cnt;
			else 
				cnt++;
			
			return;
		}
		size /= 2;
		solve(size, row, col);
		solve(size, row, col + size);
		solve(size, row + size, col);
		solve(size, row + size, col + size);
	}
}
