import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * 백준 2605 : 줄 세우기
 * 점심시간이 되면 반 학생 모두가 한 줄로 줄을 서서 급식을 탄다. 그런데 매일 같이 앞자리에 앉은 학생들이 앞에 줄을 서 먼저 점심을 먹고, 뒷자리에 앉은 학생들은 뒤에 줄을 서 늦게 점심을 먹게 된다.
 * 어떻게 하면 이러한 상황을 바꾸어 볼 수 있을까 고민하던 중 선생님이 한 가지 방법을 내 놓았다. 그 방법은 다음과 같다.
 * 학생들이 한 줄로 줄을 선 후, 첫 번째 학생부터 차례로 번호를 뽑는다. 첫 번째로 줄을 선 학생은 무조건 0번 번호를 받아 제일 앞에 줄을 선다.
 * 두 번째로 줄을 선 학생은 0번 또는 1번 둘 중 하나의 번호를 뽑는다. 0번을 뽑으면 그 자리에 그대로 있고, 1번을 뽑으면 바로 앞의 학생 앞으로 가서 줄을 선다.
 * 세 번째로 줄을 선 학생은 0,1 또는 2 중 하나의 번호를 뽑는다. 그리고 뽑은 번호만큼 앞자리로 가서 줄을 선다.
 * 마지막에 줄을 선 학생까지 이와 같은 방식으로 뽑은 번호만큼 앞으로 가서 줄을 서게 된다. 각자 뽑은 번호는 자신이 처음에 선 순서보다는 작은 수이다.
 * 예를 들어 5명의 학생이 줄을 서고, 첫 번째로 줄을 선 학생부터 다섯 번째로 줄을 선 학생까지 차례로 0, 1, 1, 3, 2번의 번호를 뽑았다고 하자, 첫 번째 학생부터 다섯 번째 학생까지 1부터 5로 표시하면 학생들이 줄을 선 순서는 다음과 같이 된다.
 * 첫 번째 학생이 번호를 뽑은 후 : 1
 * 두 번째 학생이 번호를 뽑은 후 : 2 1
 * 세 번째 학생이 번호를 뽑은 후 : 2 3 1
 * 네 번째 학생이 번호를 뽑은 후 : 4 2 3 1
 * 다섯 번째 학생이 번호를 뽑은 후 : 4 2 5 3 1
 * 따라서 최종적으로 학생들이 줄을 선 순서는 4, 2, 5, 3, 1이 된다.
 * 줄을 선 학생들이 차례로 뽑은 번호가 주어질 때 학생들이 최종적으로 줄을 선 순서를 출력하는 프로그램을 작성하시오.
 */
public class BOJ2605_줄세우기 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 삽입을 여러번 해야 하므로 linkedlist가 유리
		LinkedList<Integer> line = new LinkedList<Integer>();
		
		for(int i = 1; i <= N; i++) {
			int num = Integer.parseInt(st.nextToken());
			// 번호만큼 뒤에서 부터 앞으로 이동하므로 최대 사이즈에서 뽑은 번호만큼 뺀 자리에 수를 넣는다
			line.add(line.size() - num, i);
		}
		
		for(int i = 0; i < line.size(); i++)
			System.out.print(line.get(i) + " ");
		System.out.println();
	}

}
