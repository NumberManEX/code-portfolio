import java.util.Scanner;

public class Mbot {
	public static int numcups; 
	public static int numrocks;
	public static boolean bonusturn=false;
	public static boolean cancontinue=false;
	public static int continuecup=-1;
	
	public static int[] makeboard(){
		int[] board= new int[2*(numcups+1)];
		for(int i=1;i<numcups+1;i++) board[i]=numrocks;
		for(int i=numcups+2;i<2*(numcups+1);i++) board[i]=numrocks;
		return board;
	}
	
	public static int[] moverocks(int[] board,int mcup,int player) {
		int skipcup=0;
		if (player==1){
			skipcup=0;
		}else {skipcup=numcups+1;}
		int carry=board[mcup];
		board[mcup]=0;
		int i=mcup+1;
		if (i==2*(numcups+1)) {i=0;}
		for(;carry!=0;i++){
			if (i==skipcup) i++;
			board[i]++;
			carry--;
			if (i==(2*(numcups)+1)) i=-1;
		}
		if (i==0) {i=2*(numcups+1);}
		if (board[i-1]>1&&(i-1!=(numcups+1))&&(i-1!=0)) {
			cancontinue=true;
			continuecup=i-1;
		}
		if (board[i-1]==1&&(i-1!=(numcups+1))&&(i-1!=0)) {
			if (player==1&&i-1>numcups+1){
				board[0]+=board[i-1-(numcups+1)];
				board[i-1-(numcups+1)]=0;
				}
			if (player==0&&i-1<numcups+1){
				board[numcups+1]+=board[i-1+(numcups+1)];
				board[i-1+(numcups+1)]=0;
				}
		}
		if(i-1==0||i-1==numcups+1) bonusturn=true; //bonus turn
		return board;
	}
	
	public static boolean nomoves(int[] board) {
		for(int i=1;i<numcups+1;i++){ 
			if(board[i]!=0) return false;
			}
		for(int i=numcups+2;i<2*(numcups+1);i++){ 
			if(board[i]!=0) return false;
			}
		return true;
	}
	public static int decidemove(int[] board) {
		int[] testboard=board;
		int bestmove=numcups;
		int bestscore=0;
		for (int i=numcups;i>0;i--) {
			testboard=moverocks(testboard,i,0);
			while (cancontinue){
				cancontinue=false;
				testboard=moverocks(testboard,continuecup,0);
			}
			if (bonusturn) {
				testboard[numcups+1]+=4;
				bonusturn=false;
			}
			if (testboard[numcups+1]>bestscore){
				bestmove=i;
				bestscore=testboard[numcups+1];
			}
			testboard=board;
		}
		return bestmove;
	}
	/*use for loop of ifs with a break if bonus, turn do auto continue. 
	 * use the ifs to max out points and min seeds on own side*/
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("How many cups are on each side?");
		numcups= scan.nextInt();
		System.out.println("How rocks are in each cup?");
		numrocks= scan.nextInt();
		int[] board=makeboard();
		int mcup;//cup to move
		boolean end=false;
		System.out.println("If I am to go first imput 0, if not imput 1.");
		int firstturn= scan.nextInt();
		if (firstturn==1) {
			System.out.println("what was their move?");
			mcup= scan.nextInt();
			board=moverocks(board,mcup+numcups+1,1);
		}
		while (!end){
			bonusturn=true;//to start mbot turn
			//mbot makes its move here
			System.out.println("please make the following moves:");
			while (bonusturn){
				bonusturn=false;
				mcup=decidemove(board);
				System.out.print(mcup+", ");
				board=moverocks(board,mcup,0);
			}
			System.out.println("thank you opperator.");
			bonusturn=true;//to start player turn
			while (bonusturn){
				bonusturn=false;
				System.out.println("what was their move?");
				mcup= scan.nextInt();
				board=moverocks(board,mcup+numcups+1,1);
				if (cancontinue) {
					cancontinue=false;
					System.out.println("did they continue? (yes:0 no:1");
					int a=scan.nextInt();
					if(a==0) board=moverocks(board,continuecup,1);
				}
			}
			end=nomoves(board);
		}
	}
}
