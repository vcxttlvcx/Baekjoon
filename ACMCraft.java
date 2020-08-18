import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
/**
 * 백준 1005번 : ACM Craft
 * 특정 건물을 지을 때까지 걸리는 최소 시간을 알아내는 프로그램을 작성하자
 * 위상 정렬 문제
 */
// 제출 시 클래스 이름을 Main으로 변경
public class ACMCraft {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
//		테스트 케이스의 수를 입력 받는다
		int T = sc.nextInt();
//		테스트 케이스 실행
		for(int test_case = 0; test_case < T; test_case++) {
//			건물의 개수 N과 건물간의 건설 순서규칙의 총 개수 K 입력 받음
			int N = sc.nextInt();
			int K = sc.nextInt();
//			각 건물당 건설에 걸리는 시간
			int[] building = new int[N + 1];
			for(int i = 1; i <= N; i++)
				building[i] = sc.nextInt();
//			건설 순서 규칙을 입력 받기 위한 이중 ArrayList 선언 및 초기화
			ArrayList<ArrayList<Integer>> path = new ArrayList<ArrayList<Integer>>();
			for(int i = 0; i <= N; i++)
				path.add(new ArrayList<Integer>());
//			각 건물을 짓기 위해 몇 개의 건물이 지어져야 되는지를 저장할 변수 선언 및 건물 순서 규칙 입력 받기
			int[] pre = new int[N + 1];
			for(int i = 0; i < K; i++) {
				int X = sc.nextInt();
				int Y = sc.nextInt();
//				X를 지어야 Y를 지을 수 있음
				path.get(X).add(Y);
				pre[Y]++;
			}
//			건설해야할 건물의 번호
			int target = sc.nextInt();
//			각 건물이 건설 되기 시작하는 시간을 저장할 배열 선언
			int[] time = new int[N + 1];
//			현재 지을 수 있는 건물을 저장할 큐 선언
			Queue<Integer> q = new LinkedList<Integer>();
//			처음에는 아무것도 안지어도 지을 수 있는 건물을 먼저 큐에 삽입
			for(int i = 1; i <= N; i++)
				if(pre[i] == 0)
					q.add(i);
//			목표 건물을 지을 수 있을 때까지 큐 탐색
			while(pre[target] > 0) {
				int now = q.poll();
//				현재 건물을 지어야 지을 수 있는 건물을 방문하여 건설 시간 초기화
				for(int next : path.get(now)) {
//					다음 지을 건물이 이미 가지고 있는 시작시간과 현재 건물을 다 지었을 때 시간을 비교하여 큰 값으로 시간 초기화
					time[next] = Math.max(time[next], time[now] + building[now]);
					pre[next]--;
//					다음 지을 건물을 지을 수 있다면 큐에 추가
					if(pre[next] == 0)
						q.add(next);
				}
			}
//			목표 건물의 시작 시간과 건설 시간을 더하여 결과 출력
			System.out.println(time[target] + building[target]);
		}
		sc.close();
	}
}
