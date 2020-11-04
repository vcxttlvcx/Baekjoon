import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 백준 2239 : 스도쿠
 */
public class BOJ2239_스도쿠 {

	private static int[][] sudoku;
	private static List<int[]> list;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		sudoku = new int[9][9];
		list = new ArrayList<int[]>();
		for (int i = 0; i < 9; i++) {
			String input = br.readLine();
			for(int j = 0; j < 9; j++) {
				sudoku[i][j] = input.charAt(j) - '0';
				if(sudoku[i][j] == 0)
					list.add(new int[] {i, j});
			}
		}

		dfs(0);
		
		br.readLine();
	}
	
	public static void dfs(int idx) {
		if(idx == list.size()) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					System.out.print(sudoku[i][j]);
				}
				System.out.println();
			}
			System.exit(0);
			return;
		}
		
		for(int i = 1; i <= 9; i++) {
			int r = list.get(idx)[0];
			int c = list.get(idx)[1];
			
			if(check(r, c, i)) {
				sudoku[r][c] = i;
				dfs(idx + 1);
				sudoku[r][c] = 0;
			}
		}
	}

	public static boolean check(int r, int c, int num) {
		// 가로 세로 확인
		for (int i = 0; i < 9; i++)
			if (sudoku[r][i] == num || sudoku[i][c] == num)
				return false;
		
		int startR = r / 3 * 3;
		int startC = c / 3 * 3;

		for (int i = startR; i < startR + 3; i++) {
			for (int j = startC; j < startC + 3; j++)
				if (sudoku[i][j] == num)
					return false;
		}

		return true;
	}

}
