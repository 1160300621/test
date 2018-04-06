package P1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class MagicSquare {
	private static BufferedReader br;

	public static boolean isLegalMagicSquare(String fileName) {
		String handle = null;
		int row = 0;
		int[] column = new int[500];
		String path = "C:\\Users\\51780\\eclipse-workspace\\lab1_1160300621\\src\\P1\\txt\\" + fileName;
		File file = new File(path);
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			System.out.println(fileName + ":没有找到文件	false");
			return false;
		}
		br = new BufferedReader(reader);
		int[][] a = new int[500][500];
		try {
			String s = null;
			while ((s = br.readLine()) != null) {
				String number[] = s.split("\t");
				column[row] = number.length;
				for (int i = 0; i < number.length; i++) {
					handle = number[i];
					a[row][i] = Integer.valueOf(number[i]);
					if (a[row][i] <= 0) {
						System.out.println(fileName + ":矩阵包含不是正整数的数	false");
						return false;
					}
				}
				row++;
			}
			if (row != column[0]) {
				System.out.println(fileName + ":矩阵不是方阵	false");
				return false;
			}
			for (int i = 1; i < row; i++) {
				if (column[i] != column[0]) {
					System.out.println(fileName + ":不是一个矩阵	false");
					return false;
				}
			}
			int sum = 0;
			for (int i = 0; i < row; i++)
				sum += a[0][i];
			for (int i = 0; i < row; i++) {
				int temp = 0;
				for (int j = 0; j < row; j++) {
					temp += a[i][j];
				}
				if (temp != sum) {
					System.out.println(fileName + ":和不相等	false");
					return false;
				}
			}
			for (int i = 0; i < row; i++) {
				int temp = 0;
				for (int j = 0; j < row; j++) {
					temp += a[j][i];
				}
				if (sum != temp) {
					System.out.println(fileName + ":和不相等	false");
					return false;
				}
			}
			int temp1 = 0, temp2 = 0;
			for (int i = 0; i < row; i++) {
				temp1 += a[i][i];
				temp2 += a[i][row - i - 1];
			}
			if (sum != temp1 || sum != temp2) {
				System.out.println(fileName + ":和不相等	false");
				return false;
			}
		} catch (IOException e) {
			System.out.println(fileName + ":出现输入输出异常	false");
			return false;
		} catch (NumberFormatException e) {
			for (int i = 0; i < handle.length(); i++) {
				if (handle.charAt(i) >= '1' && handle.charAt(i) <= '9' || handle.charAt(i) == '.') {
					boolean flag = false;
					if (handle.charAt(i) == '.') {
						if (!flag)
							flag = true;
						else {
							System.out.println(fileName + ":不是以\\t分割	false");
							return false;
						}
					}
				} else {
					System.out.println(fileName + ":不是以\\t分割	false");
					return false;
				}
			}
			System.out.println(fileName + ":矩阵包含不是正整数的数	false");
			return false;
		}
		return true;
	}


	public static boolean generateMagicSquare(int n) {
		try {
		int magic[][] = new int[n][n];
		int row = 0, col = n / 2, i, j, square = n * n;
		for (i = 1; i <= square; i++) {
			magic[row][col] = i;
			if (i % n == 0)
				row++;
			else {
				if (row == 0)
					row = n - 1;
				else
					row--;
				if (col == (n - 1))
					col = 0;
				else
					col++;
			}
		}
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++)
				System.out.print(magic[i][j] + "\t");
			System.out.println();
		}
		try {
			File file=new File("C:\\Users\\51780\\eclipse-workspace\\lab1_1160300621\\src\\P1\\txt\\6.txt");
			FileOutputStream out = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out)); 
            for (i=0;i<n;i++)
            {
            	for (j=0;j<n;j++)
            	{
            		bw.write(magic[i][j]+"\t");
            	}
            	bw.newLine();
            }
            bw.close();
		}
		catch (IOException e){
			return false;
		}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("6.txt:矩阵生成过程中数组越界，生成矩阵失败  false");
			return false;
		}
		catch(NegativeArraySizeException e) {
			System.out.println("6.txt:输入的n是负数，生成矩阵失败 false");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		for (int i = 1; i <= 5; i++) {
			String fileName = new String(i + ".txt");
			boolean judge;
			judge = isLegalMagicSquare(fileName);
			if (judge)
				System.out.println(fileName + ":是一个MagicSqure	true");
		}
		Scanner in = new Scanner(System.in);
		System.out.println("请输入正整数n生成6.txt");
		int n = in.nextInt();
		boolean flag=generateMagicSquare(n);
		boolean judge = isLegalMagicSquare("6.txt");
		if (judge&&flag)
			System.out.println("\n6.txt:是一个MagicSqure	true");
		in.close();
	}

}
