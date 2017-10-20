/**
 * @author yigitozkavci
 * 
 * Yiğit Özkavcı
 * 2013400111
 * yigitozkavci8@gmail.com
 * CMPE436-Assignment 2
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, InvalidInputException {
		int[][] data;
		if(args.length < 3) {
			System.out.println("Not enough arguments.");
		} else if(args.length >= 3) {
			int M = Integer.parseInt(args[0]);
			int N = Integer.parseInt(args[1]);
			int maxGenerations = Integer.parseInt(args[2]);
			Optional<String> input = args.length >= 4 ? Optional.of(args[3]) : Optional.empty();
			data = loadData(M, N, maxGenerations, input);
			GOL g = new GOL(M, N, data, maxGenerations);
			g.run((Void) -> System.out.println("---------"));
		}
	}
	
	/**
	 * Given necessary inputs, loads the data int a 2d array
	 * 
	 * @param M 						Width of the matrix to return
	 * @param N 						Height of the matrix to return
	 * @param maxGenerations			Max generation information that's taken as input argument to program
	 * @param input					If this optional exists, input is read from file. If not, we assign it randomly
	 * @return						A 2d array to be used in Game of Life algorithm
	 * @throws FileNotFoundException Threw if input file argument is present and file is not found
	 * @throws InvalidInputException Threw if input file is present but input data is corrupted
	 */
	private static int[][] loadData(int M, int N, int maxGenerations, Optional<String> input) throws FileNotFoundException, InvalidInputException {
		int[][] mx = new int[M][N];
		if(input.isPresent()) {
			String filename = input.get();
			Scanner in = new Scanner(new FileReader(filename));
			for(int i = 0; i < M; i++) {
				for(int j = 0; j < N; j++) {
					int val = in.nextInt();
					if(val != 0 && val != 1) throw new InvalidInputException();
					mx[i][j] = val;
				}
			}
			in.close();
		} else {
			Random rand = new Random();
			for(int i = 0; i < M; i++) {
				for(int j = 0; j < N; j++) {
					mx[i][j] = rand.nextInt(2);
				}
			}
		}
		return mx;
	}
}