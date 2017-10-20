/**
 * Yiğit Özkavcı
 * 2013400111
 * yigitozkavci8@gmail.com
 * Cmpe436-Assignment 2
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

class InputData {
	public int M;
	public int N;
	public int maxGenerations;
	public int[][] matrix;
	
	public InputData(int M, int N, int maxGenerations, int[][] matrix) {
		this.M = M;
		this.N = N;
		this.maxGenerations = maxGenerations;
		this.matrix = matrix;
	}
}

class MutableInt {
	public int value;
	
	public MutableInt(int value) {
		this.value = value;
	}
}

public class Main {
	public static void main(String[] args) throws FileNotFoundException, InvalidInputException {
		if(args.length < 3) {
			System.out.println("Not enough arguments.");
		} else if(args.length >= 3) {
			InputData data = prepareInputData(args);
			Utils.printMtx(data.matrix);
			spawnThreads(data);
		}
	}
	
	/**
	 * Parses and prepares input data received from user
	 * 
	 * @param args Command line arguments
	 * @return InputData A form of struct to keep safely-received input data together
	 * @throws FileNotFoundException Threw if user specifies an input file but file cannot be found
	 * @throws InvalidInputException Threw if user provides a malformed input
	 */
	private static InputData prepareInputData(String[] args) throws FileNotFoundException, InvalidInputException {
		int M = Integer.parseInt(args[0]);
		int N = Integer.parseInt(args[1]);
		int maxGenerations = Integer.parseInt(args[2]);
		Optional<String> input = args.length >= 4 ? Optional.of(args[3]) : Optional.empty();
		int[][] matrix = loadData(M, N, maxGenerations, input);
		return new InputData(M, N, maxGenerations, matrix);
	}
	
	/**
	 * Spawns the solver threads and waits until all of them die.
	 * 
	 * @param data Input data necessary for spawning logic
	 * @param mutex Mutex for protecting shared matrix input data
	 */
	private static void spawnThreads(InputData data) {
		CountingSemaphore barrier = new CountingSemaphore(0);
		CountingSemaphore barrier2 = new CountingSemaphore(1);
		CountingSemaphore mutex = new CountingSemaphore(1);
		List<Thread> ts = new ArrayList<>();
		MutableInt workCount = new MutableInt(0);
		for(int i = 1; i < data.M + 1; i++) {
			for(int j = 1; j < data.N + 1; j++) {
				Thread t = new Thread(new GolNode(i, j, data.matrix, data.maxGenerations, mutex, workCount, barrier, barrier2, data.M * data.N));
				t.start();
				ts.add(t);
			}
		}
		
		for(Thread t1 : ts) {
			try {
				t1.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}; 
		};
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
		int[][] mx = new int[M + 2][N + 2];
		if(input.isPresent()) {
			String filename = input.get();
			Scanner in = new Scanner(new FileReader(filename));
			for(int i = 1; i < M + 1; i++) {
				for(int j = 1; j < N + 1; j++) {
					int val = in.nextInt();
					if(val != 0 && val != 1) throw new InvalidInputException();
					mx[i][j] = val;
				}
			}
			in.close();
		} else {
			Random rand = new Random();
			for(int i = 1; i < M + 1; i++) {
				for(int j = 1; j < N + 1; j++) {
					mx[i][j] = rand.nextInt(2);
				}
			}
		}
		return mx;
	}
}