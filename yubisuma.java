import java.util.Random;
import java.util.Scanner;

public class yubisuma {
	static int cnt = 0;

	static int maxFing(int n, int f[]) { //今ある指の最大値
		int max = 0;
		int i = 0;
		for (i = 0; i < n; i++) {
			max += f[i];
		}
		return max;
	}

	static int preventionFool(int fin, int ans, int sum, int f[], int i) { //バカ防止
		Random rand = new Random();
		if (ans == 0) //答えが0のとき0を出す
			fin = 0;
		if (ans < fin) //答えが上げる指より小さいとき
			switch (fin) {
			case 1:
				fin = 0;
			case 2:
				fin = rand.nextInt(2);
			}

		if (ans == sum + f[i]) //全PLの指を合わせた最大値の時最大を出す
			fin = f[i];
		else if (ans > sum) //他PLの指の最大値より答えが大きい時に0を出さない調整
			fin = rand.nextInt(f[i]) + 1;

		return fin;
	}

	static void judg(int eo, int ans, int f[], int i) {
		if (eo == ans) {
			System.out.println("あたり！");
			f[i]--;
			if (f[i] == 0) {
				yubisuma.cnt++;
			}
		} else
			System.out.println("残念！");
	}

	static int selectFing(int fin, int f[]) {
		Scanner stdIn = new Scanner(System.in);
		do {
			System.out.print("上げる指:");
			fin = stdIn.nextInt();
			if (f[0] < fin)
				System.out.println("腕増えた？");
		} while (f[0] < fin);

		return fin;
	}

	static int selectCpDisplay(int f[],int n){
		Random rand = new Random();
		int fin = 0;
		switch (f[n]) {
		case 2:
			fin = rand.nextInt(3);
			System.out.println("(" + n + " '∀')<(@" + f[n] + ")" + fin);
			break;
		case 1:
			fin = rand.nextInt(2);
			System.out.println("(" + n + " '∀')<(@" + f[n] + ")" + fin);
			break;
		case 0:
			break;
		}
		return fin;
	}

	public static void main(String[] args) {
		Random rand = new Random();
		Scanner stdIn = new Scanner(System.in);
		System.out.println("指スマしまーす。\n上げたい指の数を0,1,2で表現してね。");

		int n = 0;
		do {
		System.out.print("何人でやる？");
		n = stdIn.nextInt();
		if(n <= 1)
			System.out.println("2人以上でお願いします。");
		}while(n <= 1);

		int max = 0;
		int eo = 0;
		int i, j = 0;
		int fin = 0;
		int ans = 0;

		int[] f = new int[n]; //人数分の配列
		for (i = 0; i < n; i++)//n人の指の数入れる
			f[i] = 2;

		do {
			max = maxFing(n, f);

			if (f[0] != 0) {
				do {
					System.out.print("指スマ？(" + max + "まで):");
					ans = stdIn.nextInt();
					if (ans > 2 * n) // スタート時の最大値より多いときに再入力(実際に言い間違えることがあるのでそのターンの最大値以上でははじかない)
						System.out.println("※さすがに多い！");
				} while (ans > 2 * n);
				fin = selectFing(fin, f);
				System.out.println("\n(you'v')<(@" + f[0] + ")" + fin + "(" + ans + "!)");

				eo += fin;

				for (i = 1; i < n; i++) {
					eo += selectCpDisplay(f,i);
				}
				System.out.println("合計 : " + eo);

				judg(eo, ans, f, 0);

				if (yubisuma.cnt == n - 1)
					break;
				System.out.println();
			}
			eo = 0;
			max = 0;


			for (i = 1; i < n; i++) { //CP親

				max = maxFing(n, f);

				if (f[i] == 0) //上がったCPの親阻止
					continue;

				System.out.println("(" + i + " '∀')<(@" + f[i] + ")指スマ...(～" + max + ")");

				if (f[0] != 0) { //PL上がった後の選択阻止
					fin = selectFing(fin, f);
					eo += fin;
					System.out.println("\n(you'v')<(@" + f[0] + ")" + fin);
				}

				do { //CPの答え
					ans = rand.nextInt(2 * n + 1);
				} while (ans > max); //バカ防止

				switch (f[i]) {
				case 2:
					fin = rand.nextInt(3);
					fin = preventionFool(fin, ans, (max -= f[i]), f, i);
					break;
				case 1:
					fin = rand.nextInt(2);
					fin = preventionFool(fin, ans, (max -= f[i]), f, i);
					break;
				default:
					break;
				}

				System.out.println("(" + i + " '∀')<(@" + f[i] + ")" + fin + "(" + ans + "!)");
				eo += fin;

				for (j = 1; j < n; j++) {
					if (j == i) //親CPの選択阻止
						continue;
					eo += selectCpDisplay(f,j);
				}

				System.out.println("合計 : " + eo);
				judg(eo, ans, f, i);
				System.out.println();

				eo = 0;
				max = 0;

			if(yubisuma.cnt == n - 1)
				break;
			}

		} while (yubisuma.cnt != n - 1);
		System.out.println("勝った人！");
		for (i = 1; i < n; i++) {
			if (f[i] == 0)
				System.out.println("(" + i + " '∀')<ﾔｯﾀｰ");
		}
		if (f[0] == 0) {
			System.out.println("(you'v')");
			System.out.println("勝利の言葉を(0:雑魚乙、1:ヤッター、2:GG！)");
			int win = stdIn.nextInt();
			switch(win) {
			case 0:
				for(i = 1;i < n;i++) {
					if(f[i] != 0)
						System.out.print("(" + i + "`皿´)");
				}
				break;
			case 1:
				for(i = 1;i < n;i++) {
					if(f[i] != 0)
						System.out.print("(" + i + " ＴnT)");
				}
				break;
			case 2:
				for(i = 1;i < n;i++) {
					if(f[i] != 0)
						System.out.print("(" + i + "´'v')ﾊ\"\"");
				}
				break;
			}
		}
	}
}
