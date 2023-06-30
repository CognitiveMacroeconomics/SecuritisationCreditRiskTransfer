


/**
 * 
 * @author Oluwasegun Bewaji
 *
 * This is a utility class used to collect pairs or tuples of integer points/co-ordinates used for
 * defining the keys in the transition matrix tables
 * 
 * example Pair<-1,-1> will be used as a coordinate key to define the point in for instance a 
 * table such as below
 *   -------------------------------------------
 *   -------------------------------------------
 *   --------|  -1   ||   0   ||   1   |--------
 *   |  -1   |   1   ||   2   ||   3   |--------
 *   |   0   |   4   ||   5   ||   6   |--------
 *   |   1   |   7   ||   8   ||   9   |--------
 *   -------------------------------------------
 *   -------------------------------------------
 *   
 *   The class may be extended to a 3 dimensional table by adding a z-coordinate
 */
public class Pair {
	
	public static int PIAR_ID = 0;
	int pairID;
	int xCord;//x coordinate
	int yCord;//y coordinate
	int[] prop;//stores a count of the properties
	int size;
	
	
	/**
	 * 2 coordinate pair constructor
	 * @param x
	 * @param y
	 */
	public Pair(int x, int y){
		PIAR_ID++;
		pairID = PIAR_ID;
		xCord = x;
		yCord = y;
		prop[0] = xCord;
		prop[1] = yCord;
		setSize();
	}
	
	
	
	/**
	 * sets the pair size, i.e. number of coordinate points
	 * @return
	 */
	private void setSize(){
		size = this.prop.length;
	}
	
	/**
	 * gets the pair size, i.e. number of coordinate points
	 * @return
	 */
	public int getSize(){
		return this.size;
	}
	
	/**
	 * gets the x coordinate
	 * @return
	 */
	public int getXCord(){
		return this.xCord;
	}
	
	/**
	 * gets the y coordinate
	 * @return
	 */
	public int getYCord(){
		return this.yCord;
	}
	
	
	/**
	 * Returns the Pair ID
	 * @return
	 */
	public int getPairID(){
		return this.pairID;
	}
	
	/**
	 * method used to compare 2 Pairs. If identical then it returns true otherwise false
	 * 
	 * only implements a 2 coordinate pair. Would need to be extended to include 3 coordinates
	 * 
	 * @param pr
	 * @return
	 */
	public boolean compareTo(Pair pr){
		boolean equal = false;
		if(this.getSize() == pr.getSize()){
			if((this.getXCord() == pr.getXCord()) && (this.getYCord() == pr.getYCord())){
				equal = true;
			}else{
				equal = false;
			}
		}
		return equal;
	}

}
