import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 백준 1010 : 다리 놓기
 * 조합 구하는 문제
 * 재귀로 풀면 메모리 초과 나기 때문에 dp를 이용해서 풀이해야 됨
 * nCr = n-1Cr-1 + n-1Cr 음 이용해서 문제 풀기
 */
public class BOJ1010 {
	public static int[][] dp;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			String[] input = br.readLine().split(" ");
			
			int N = Integer.parseInt(input[0]);
			int M = Integer.parseInt(input[1]);
			
			dp = new int[31][31];
			
			int ans = combination(M, N);
			
			System.out.println(ans);
		}
	}
	// dp를 적용한 제귀로 조합을 구한다
	public static int combination(int n, int r) {
		// n과 r이 같거나 r이 0이면 1을 리턴
		if(n == r || r == 0)
			return 1;
		// 이미 값을 찾은 적이 있다면 그 값을 리턴
		if(dp[n][r] > 0)
			return dp[n][r];
		// 재귀 호출로 조합 값 구하기
		dp[n][r] = combination(n - 1, r - 1) + combination(n - 1, r);
		return dp[n][r];
	}
}
