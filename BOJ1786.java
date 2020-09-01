import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 백준 1786 : 찾기
 * 문자열 T 안에 문자열 P가 몇번 등장하고, 등장 시 첫번 째 인덱스들을 출력
 * KMP 알고리즘 문제
 */
public class BOJ1786 {
	static String T;
	static String P;
	
	static int[] fail;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = br.readLine();
		P = br.readLine();
		// 실패 함수 만들기
		fail = new int[P.length()];
		makeFail();
		
		List<Integer> list = new ArrayList<Integer>();
		int j = 0;
		for(int i = 0; i < T.length(); i++) {
			// 일치 하지 않을 경우 실패 함수를 이용해 앞에가 맞는 부분이 오게 중간 단계를 건너뛴다
			while(j > 0 && T.charAt(i) != P.charAt(j))
				j = fail[j - 1];
			// 패턴과 타킷이 일치하는 경우
			if(T.charAt(i) == P.charAt(j)) {
				// 패턴이 완벽히 일치하는 경우를 찾았을 경우 리스트에 삽입 및 j 초기화
				if(j == P.length() - 1) {
					list.add(i - (P.length() - 1) + 1);
					j = fail[j];
				} else {	// 찾을 문자열이 남은 경우 j 증가
					j++;
				}
			}
		}
		StringBuilder sb = new StringBuilder("");
		sb.append(list.size()).append("\n");
		for(int i = 0; i < list.size(); i++)
			sb.append(list.get(i)).append("\n");
		System.out.println(sb.toString());
	}

	public static void makeFail() {
		int len = P.length();
		char[] pattern = P.toCharArray();
		
		int j = 0;
		fail[0] = 0;
		for(int i = 1; i < len; i++) {
			// j가 0보다 크고, 일치 하지 않을 경우 j를 fail을 이용해 당긴다
			while(j > 0 && pattern[i] != pattern[j])
				j = fail[j - 1];
			// 패턴이 일치 한다면 fail에 j + 1을 기록하고 j를 증가
			if(pattern[i] == pattern[j])
				fail[i] = ++j;
		}
	}
}
