/*
 * Created on 11/11/2005
 *
 */
package main.proofTree;


/**
 * Describes a proof tree branch
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class BranchId {
	

	private String name;
	
	public static final BranchId ROOT = new BranchId("");

	public static final int LEFT = 0;

	public static final int RIGHT = 1;
	

	private BranchId(String name) {
		this.name = name;
	}

	public BranchId(BranchId bid, int position) {
		this.name = bid.toString()+ ((position==LEFT)? "l":"r");
	}

	public String toString() {
		return name;
	}
}
