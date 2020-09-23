import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 백준 1463 : 1로 만들기
 * 정수 X에 사용할 수 있는 연산은 다음과 같이 세 가지 이다.
 * 		1. X가 3으로 나누어 떨어지면, 3으로 나눈다.
 * 		2. X가 2로 나누어 떨어지면, 2로 나눈다.
 * 		3. 1을 뺀다.
 * 정수 N이 주어졌을 때, 위와 같은 연산 세 개를 적절히 사용해서 1을 만들려고 한다. 연산을 사용하는 횟수의 최솟값을 출력하시오.
 */
public class BOJ1463_1로만들기 {
	static int[] dp;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		dp = new int[N + 1];
		Arrays.fill(dp, Integer.MAX_VALUE);
		
		dp[1] = 0;
		if(N >= 2)
			dp[2] = 1;		
		
		// 현재 찾은 값과 각 경우를 수행한 경우와 내려 갔을 때의 경우를 합하여 최소값을 찾는다
		for(int i = 3; i <= N; i++) {
			if(i % 3 == 0)
				dp[i] = Math.min(dp[i], 1 + dp[i / 3]);
			if(i % 2 == 0)
				dp[i] = Math.min(dp[i], 1 + dp[i / 2]);
			dp[i] = Math.min(dp[i], 1 + dp[i - 1]);
		}
		
		System.out.println(dp[N]);
	}

}
