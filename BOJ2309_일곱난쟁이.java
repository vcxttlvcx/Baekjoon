import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 백준 2309 : 일곱난쟁이
 * 왕비를 피해 일곱 난쟁이들과 함께 평화롭게 생활하고 있던 백설공주에게 위기가 찾아왔다. 일과를 마치고 돌아온 난쟁이가 일곱 명이 아닌 아홉 명이었던 것이다.
 * 아홉 명의 난쟁이는 모두 자신이 "백설 공주와 일곱 난쟁이"의 주인공이라고 주장했다. 뛰어난 수학적 직관력을 가지고 있던 백설공주는, 다행스럽게도 일곱 난쟁이의 키의 합이 100이 됨을 기억해 냈다.
 * 아홉 난쟁이의 키가 주어졌을 때, 백설공주를 도와 일곱 난쟁이를 찾는 프로그램을 작성하시오.
 */
public class BOJ2309_일곱난쟁이 {
	static int[] height;

	static int[] answer;
	
	static boolean flag;
	
	public static void combination(int cnt, int idx, int visited) {
		if(cnt == 7) {
			int aIdx = 0;
			int sum = 0;
			
			for(int i = 0; i < 9; i++) {
				if((visited & (1 << i)) == 0)
					continue;
				
				sum += height[i];
				answer[aIdx++] = height[i];
			}
			// 이미 키의 합이 100이 되는 경우를 구했다면 종료하기 위해 변수를 true로 바꾼다
			if(sum == 100)
				flag = true;
			
			return;
		}
		
		for(int i = idx; i < 9; i++) {
			// 이미 키의 합이 100이 되는 난쟁이 그룹을 찾았다면 종료
			if(flag)
				return;
			combination(cnt + 1, i + 1, visited | (1 << i));
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		height = new int[9];
		answer = new int[7];
		
		flag = false;
		
		for(int i = 0; i < 9; i++)
			height[i] = Integer.parseInt(br.readLine());
		// 조합으로 7명의 난쟁이를 선택해서 키의 합을 구한다
		combination(0, 0, 0);
		
		Arrays.sort(answer);
		for(int i = 0; i < 7; i++)
			System.out.println(answer[i]);
	}

}
