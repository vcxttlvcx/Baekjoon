import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 15961 : 회전 초밥
 * 초밥을 회전 순서대로 k개 연속 먹고 쿠폰 번호에 써져 있는 것 까지 먹을 때 
 * 가장 다양하게 먹을 수 있는 경우 몇 종류를 먹을 수 있는지 구하는 프로그램
 */
public class BOJ15961 {
	static int N;	// 회전 초밥 벨트에 놓인 접시의 수
	static int d;	// 초밥의 가짓수
	static int k;	// 연속해서 먹는 접시의 수
	static int c;	// 쿠폰 번호
	// 회전 초밥 정보
	static int[] sushi;
	// 지금 먹은 초밥 번호 기억 용 배열
	static int[] select;
	
	static int answer;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		answer = 0;
		
		N = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		// 회전 초밥 정보 입력 받기
		sushi = new int[N];
		for(int i = 0; i < N; i++)
			sushi[i] = Integer.parseInt(br.readLine());
		// 초밥 번호를 기억하기 위한 배열을 선언하고 쿠폰에 적힌 초밥은 먹었다고 가정
		select = new int[d + 1];
		select[c]++;
		// 몇 종류의 초밥을 먹었는지 확인하기 위한 변수
		int cnt = 1;
		// 0 ~ k - 1 번 까지 초밥을 먹는다
		for(int i = 0; i < k; i++) {
			int value = sushi[i];
			// 먹어본적 없는 종류의 초밥일 경우 cnt를 증가 시킨다
			if(select[value] == 0)
				cnt++;
			// 같은 종류를 몇개 먹었는지 저장해 둔다
			select[value]++;
		}
		// k개의 초밥을 먹었을 때 가장 먼저 먹은 초밥과 마지막에 먹은 초밥의 위치의 인덱스를 저장한다
		int frontIdx = 0;
		int tailIdx = k - 1;
		// N -1 , 0, 1 ~ 이 되는 경우도 고려해야 하기에 do ~ while을 사용하고
		// 인덱스를 증가 시킬 때, % 연산을 해준다
		do {
			// 지금 까지 먹은 종류의 개수가 최대인지 확인하여 초기화
			answer = Math.max(answer, cnt);
			// 가장 먼저 먹었던 초밥과 다음 먹을 초밥의 번호를 가지고 온다
			int frontValue = sushi[frontIdx];
			int tailValue = sushi[(tailIdx + 1) % N];
			// 가정 먼저 먹었던 초밥의 종류를 하나 감소 시키고,
			// 이 때 먹었던 적이 없게 되면 cnt를 감소 시킨다
			if(--select[frontValue] == 0)
				cnt--;
			// 다음 먹을 초밥을 먹고, 먹기 전에 먹어본 적 없는 종류였을 경우 cnt를 증가 시킨다
			if(select[tailValue]++ == 0)
				cnt++;
			// 인덱스들을 하나 씩 증가 시킨다
			frontIdx = (frontIdx + 1) % N;
			tailIdx = (tailIdx + 1) % N;
		} while(frontIdx != 0);
		
		// 결과 출력
		System.out.println(answer);
		br.close();
	}

}
