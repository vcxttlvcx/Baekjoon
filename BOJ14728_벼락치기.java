import java.util.Scanner;

/**
 * 백준 14728 : 벼락치기
 * ChAOS(Chung-ang Algorithm Organization and Study) 회장이 되어 일이 많아진 준석이는 시험기간에도 일 때문에 공부를 하지 못하다가 시험 전 날이 되어버리고 말았다.
 * 다행히도 친절하신 교수님께서 아래와 같은 힌트를 시험 전에 공지해 주셨다. 내용은 아래와 같다.
 * 여러 단원을 융합한 문제는 출제하지 않는다.
 * 한 단원에 한 문제를 출제한다. 단, 그 단원에 모든 내용을 알고 있어야 풀 수 있는 문제를 낼 것이다.
 * 이런 두가지 힌트와 함께 각 단원 별 배점을 적어 놓으셨다. 어떤 단원의 문제를 맞추기 위해서는 그 단원의 예상 공부 시간만큼, 혹은 그보다 더 많이 공부하면 맞출 수 있다고 가정하자.
 * 이때, ChAOS 회장 일로 인해 힘든 준석이를 위하여 남은 시간 동안 공부해서 얻을 수 있는 최대 점수를 구하는 프로그램을 만들어 주도록 하자.
 */
public class BOJ14728_벼락치기 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		int T = sc.nextInt();
		
		int[][] score = new int[N + 1][2];
		
		for(int i = 1; i <= N; i++) {
			score[i][0] = sc.nextInt();
			score[i][1] = sc.nextInt();
		}
		// 0-1 knapsack 1차원으로 구현
		int[] dp = new int[T + 1];
		// 모든 아이템에 대해서 반복
		for(int i = 1; i <= N; i++) {
			// 최대 무개부터 현재 무개까지 고려해서 최적의 값 찾기
			for(int w = T; w >= score[i][0]; w--) {
				dp[w] = Math.max(dp[w], score[i][1] + dp[w - score[i][0]]);
			}
		}
		
		System.out.println(dp[T]);
		
		sc.close();
	}

}
