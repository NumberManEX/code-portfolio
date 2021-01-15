package cs405prog3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Truffelsearch {
	private static int[] pathfinder(int[][] field){
		int l = field[0].length;
		int[][] backtrace= new int[l][l];
		int[][] sums= new int[l+1][l];
		//find the best
		for (int i=1;i<l+1;i++) {
			for (int j=0;j<l;j++) {
				int maxsum=sums[i-1][j]+field[i-1][j];
				int bkey =j;
				if(j!=0) {
					if(sums[i-1][j-1]>sums[i-1][j]) {
						maxsum=sums[i-1][j-1]+field[i-1][j];
						bkey =j-1;
					}
				}
				//middle check is default
				if (j!=l-1) {
					if(sums[i-1][j+1]>sums[i-1][j]) {
						maxsum=sums[i-1][j+1]+field[i-1][j];
						bkey =j+1;
					}
				}
				sums[i][j]=maxsum;
				backtrace[i-1][j]=bkey;
			}
		}
		//find an optimal value:
		int max =sums[l][0];
		int key=0;
		for (int i=1;i<l;i++) {
			if(max <sums[l][i]) {
				max=sums[l][i];
				key=i;
			}
		}
		//follow the back trace of the optimal value to get the best path
		int[] op= new int[l];
		op[l-1]=key;
		for (int i=l-2;0<i;i--) {
			op[i]=backtrace[i+1][key];
			key=backtrace[i+1][key];
		}
		op[0]=key;
		return op;
	}
	
	public static void main(String[] args) {
		//take  input here
				File text = new File(args[0]);
				//get input
		        Scanner scnr=new Scanner(System.in);
				try {
					scnr = new Scanner(text);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
		        int lnum = 1;
		        String line = scnr.nextLine();
		        while(scnr.hasNextLine()){
		            line = scnr.nextLine();
		            lnum++;
		            }
		        Scanner cscnr= new Scanner(line);
		        int ele=0;
		        int elecount=0;
		        while(cscnr.hasNextInt()){
		            ele = cscnr.nextInt();
		            elecount++;
		            }
		        int[][] A = new int[lnum][elecount];
		        
		        try {
					scnr = new Scanner(text);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		        for (int i=0;i<lnum;i++) {
		        	for (int j=0;j<elecount;j++) {
		        		A[i][j]=scnr.nextInt();
		        	}
		        }
		        //get and return the solution
		        int[] path = pathfinder(A);
		        int sum =0;
		        for (int i=0;i<lnum;i++) {
		        	System.out.println("["+(i+1)+","+(path[i]+1)+"]---"+A[i][path[i]]);
		        	sum+=A[i][path[i]];
		        }
		        System.out.println("\t"+sum+" truffles");
		        
	}
}
