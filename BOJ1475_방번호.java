import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 백준 1475 : 방 번호
 * 다솜이는 은진이의 옆집에 새로 이사왔다. 다솜이는 자기 방 번호를 예쁜 플라스틱 숫자로 문에 붙이려고 한다.
 * 다솜이의 옆집에서는 플라스틱 숫자를 한 세트로 판다. 한 세트에는 0번부터 9번까지 숫자가 하나씩 들어있다.
 * 다솜이의 방 번호가 주어졌을 때, 필요한 세트의 개수의 최솟값을 출력하시오.(6은 9를 뒤집어서 이용할 수 있고, 9는 6을 뒤집어서 이용할 수 있다.)
 */
public class BOJ1475_방번호 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();
		int[] num = new int[10];
		// 숫자 개수 새기
		for(int i = 0; i < input.length(); i++)
			num[input.charAt(i) - '0']++;
		// 6과 9의 값을 더한 값이 홀수이면 1을 더해서 나누고 짝수이면 그대로 2를 나눠서 저장
		int ans = (num[6] + num[9]);
		if(ans % 2 != 0)
			ans++;
		ans /= 2;
		// 모든 숫자를 탐색하며 가장 큰 경우를 찾는다
		for(int i = 0; i <= 9; i++) {
			if(i == 6 || i == 9)
				continue;
			
			ans = Math.max(ans, num[i]);
		}
		
		System.out.println(ans);
	}

}
