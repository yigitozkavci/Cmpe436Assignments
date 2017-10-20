/**
 * @author yigitozkavci
 * 
 * Yiğit Özkavcı
 * 2013400111
 * yigitozkavci8@gmail.com
 * CMPE436-Assignment 1
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Main class basically handles impure IO logic. For pure matrix operations, see @Matrix
 * 
 * @author yigitozkavci
 */
public class Main {
	public static void main(String[] args) throws InvalidInputException {
		if(args.length != 3) throw new InvalidInputException();
		String firstFile, secondFile, resultFile;
		firstFile = args[0];
		secondFile = args[1];
		resultFile = args[2];
		try {
			Matrix m1 = readMatrix(firstFile);
			Matrix m2 = readMatrix(secondFile);
			Matrix result = m1.multiplyBy(m2);
			writeMatrix(resultFile, result);
			System.out.println(result.toString());
		} catch(DimensionMismatchException e) {
			System.out.println(e.getMessage());
		} catch(FileNotFoundException e) {
			System.out.println("One of the files are not found: " + e.getMessage());
		} catch(IOException e) {
			System.out.println("Error on writing to result file: " + e.getMessage());
		} catch(NoSuchElementException e) {
			System.out.println("Element sizes and actual elements in input files don't match");
		}
	}
	
	/**
	 * Read a matrix given a filename
	 * 
	 * @param filename
	 * @return A new matrix
	 * @throws FileNotFoundException
	 */
	public static Matrix readMatrix(String filename) throws FileNotFoundException {
		Scanner in = new Scanner(new FileReader(filename));
		int rowN, colN;
		rowN = in.nextInt();
		colN = in.nextInt();
		int[][] vx = new int[rowN][colN];
		for(int i = 0; i < rowN; i++) {
			for(int j = 0; j < colN; j++) {
				vx[i][j] = in.nextInt();
			}
		}
		in.close();
		return new Matrix(vx);
	}
	
	/**
	 * Write a matrix given a filename and, well, a matrix
	 * 
	 * @param filename File to write matrix to
	 * @param m Matrix to be written
	 * @throws IOException Threw if there is an error while writing the matrix
	 */
	public static void writeMatrix(String filename, Matrix m) throws IOException {
		FileWriter fw = new FileWriter(filename);
		fw.write(m.toString());
		fw.close();
	}
}
