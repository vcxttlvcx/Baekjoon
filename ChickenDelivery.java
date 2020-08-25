import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/**
 * 백준 15686번 치킨 배달
 * 치킨집을 M개 선택했을 때, 집들과의 거리가 최소가 되는 값 구하기
 *
 */
public class ChickenDelivery {
	static int N;
	static int M;
	
	static int[] select;
	static List<Point> chicken;
	static List<Point> house;
	
	static int ans;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		M = sc.nextInt();
		
		select = new int[M];
		chicken = new ArrayList<Point>();
		house = new ArrayList<Point>();
		
		ans = 4 * N * N * M;
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				int n = sc.nextInt();
//				입력을 받아 치킨집과 집의 좌표를 저장
				if(n == 2)
					chicken.add(new Point(i, j));
				else if(n == 1)
					house.add(new Point(i, j));
			}
		}
		
		solve(0, 0);
		
		System.out.println(ans);
		sc.close();
	}
//	조합을 통해 치킨집을 선택하고 모든 집들과의 거리를 구한다
	public static void solve(int idx, int cnt) {
		if(cnt == M) {
			int sum = 0;
			
			for(int i = 0; i < house.size(); i++) {
				Point h = house.get(i);
				int min = N * 2;
				
				for(int j = 0; j < M; j++) {
					Point c = chicken.get(select[j]);
					
					int distance = Math.abs(h.r - c.r) + Math.abs(h.c - c.c);
					min = distance < min ? distance : min;
				}
				sum += min;
			}
			System.out.println(Arrays.toString(select) + " : " + sum);
			
			ans = sum < ans ? sum : ans;
			
			return;
		}
//		치킨집을 선택했을 때와 선택하지 않았을 때로 나누어 진행
		if(idx < chicken.size()) {
			select[cnt] = idx;
			solve(idx + 1, cnt + 1);
			solve(idx + 1, cnt);
		}
	}
//	좌표를 저장하기 위한 클래스
	static class Point {
		int r;
		int c;
		
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
}

