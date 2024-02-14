package Burkhardt_Project_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Project03 {
	// the main method is the entry point of the program
	public static void main(String[] args) throws Exception {		// create a new Scanner object to read input from the console
		Scanner in = new Scanner(System.in);
		Boolean run = true;
		while (run) {
			System.out.print("Enter a binary tree: ");
			// read a line of input from the console and store it in the expression variable
			String expression = in.nextLine();
			//Testing String expression = "(53(28(11**)(41**))(83(67**)*))";
			// create a new binary tree from the input expression
			BinaryTree tree = parseExpression(expression);
			// display the binary tree
			displayTree(tree);
			// calculate the height of the binary tree
			int treeHeight = getTreeHeight(tree);
			// print the height of the binary tree
			System.out.println("\nThe height of the binary tree is " + treeHeight);
			isBalanced(tree);
			while (run){
				System.out.println("Do you want to continue? Y/N");
				String userInput = in.nextLine();
				if (userInput.equalsIgnoreCase("N")) {
					run = false;
					break;
				}else if (userInput.equalsIgnoreCase("Y")) {
					run = true;
					break;
				}else {
					continue;
				}
			}
		}
	}
	
	// parse an input expression and return a binary tree
	private static BinaryTree parseExpression(String expression) {
		int data = 0; // initialize a variable to store the data of the current node
		int binaryTreeCount = 0; // initialize a variable to count the number of binary trees in the expression
		BinaryTree leftChild = null; // initialize a variable to store the left child of the current node
		BinaryTree rightChild = null; // initialize a variable to store the right child of the current node
		for (int i = 0; i < expression.length(); i++) { // iterate over each character in the expression
			char ch = expression.charAt(i); // get the current character
			if (ch == '(' || ch == '*') { // if the current character is an opening parenthesis or a multiplication operator
				if (ch == '(') { // if the current character is an opening parenthesis
					if (binaryTreeCount == 1) { // if this is the second binary tree in the expression
						// extract the left subexpression from the input string
						String leftExpression = extractExpression(expression, i);
						// parse the left subexpression to create a binary tree
						leftChild = parseExpression(leftExpression);
						// update the index to the end of the left subexpression
						i += leftExpression.length() - 1;
					} else if (binaryTreeCount == 2) { // if this is the third binary tree in the expression
						// extract the right subexpression from the input string
						String rightExpression = extractExpression(expression, i);
						// parse the right subexpression to create a binary tree
						rightChild = parseExpression(rightExpression);
						// update the index to the end of the right subexpression
						i += rightExpression.length() - 1;
					}
				}

				binaryTreeCount++; // increment the binary tree count
			}

			if (Character.isDigit(ch)) { // if the current character is a digit
				data *= 10; // shift the current data value to the left by one digit
				data += ch - '0'; // add the current digit to the data value
			}
		}

		return new BinaryTree(data, leftChild, rightChild); // create a new binary tree with the current data, left child, and right child
	}

	// extract a subexpression from the input string
	private static String extractExpression(String expression, int start) {
		int parenthesesCount = 0; // initialize a variable to count the number of parentheses in the subexpression
		int i = start; // initialize a variable to the start index of the subexpression
		for (; i < expression.length(); i++) { // iterate over each character in the expression
			char ch = expression.charAt(i); // get the current character

			if (ch == '(') { // if the current character is an opening parenthesis
				parenthesesCount++; // increment the parentheses count
			} else if (ch == ')') { // if the current character is a closing parenthesis
				parenthesesCount--; // decrement the parentheses count
			}

			if (parenthesesCount == 0) // if the parentheses count is 0, we have reached the end of the subexpression
				break;
		}

		return expression.substring(start, i + 1); // return the subexpression as a string
	}
	
	// display the binary tree in a tree-like structure
	private static void displayTree(BinaryTree tree) {
		displayTree(tree, 0); // call the recursive display method with the current tree and a level of 0
	}

	// recursive method to display the binary tree
	private static void displayTree(BinaryTree tree, int level) {
		if (tree == null) { // if the current node is null, return
			return;
		}

		for (int i = 0; i < level; i++) { // print leading spaces to indicate the level of the current node
			System.out.print("  ");
		}

		System.out.println(tree.getData()); // print the data of the current node
		displayTree(tree.getLeft(), level + 1); // recursively display the left child
		displayTree(tree.getRight(), level + 1); // recursively display the right child
	}
	
	// calculate the height of the binary tree
	public static int getTreeHeight(BinaryTree tree) {
		return getTreeHeight(tree, -1); // call the recursive height method with the current tree and a level of 1
	}
	
	// recursive method to calculate the height of the binary tree
	private static int getTreeHeight(BinaryTree tree, int level) {
		if (tree == null) { // if the current node is null, return the current level
			return level;}
		return Math.max( // return the maximum of the left and right subtree heights plus the current level
				getTreeHeight(tree.getLeft(), level + 1),
				getTreeHeight(tree.getRight(), level + 1)
				);
	}
	private static boolean isBalanced(BinaryTree tree){
	
		int leftHeight = getTreeHeight(tree.getLeft());
		int rightHeight = getTreeHeight(tree.getRight());
	
	
		if (leftHeight == rightHeight) {
			System.out.println("The tree is balanced");
			return true;
		}else if (leftHeight > rightHeight){
			System.out.println("Tree is inbalanced left branch is larger.");
			return false;
		}else{
			System.out.println("Tree is inbalanced right branch is larger.");
			return false;
		}
	}

}
// class to represent a binary tree node
class BinaryTree {
	private int data; // the data stored in the node
	private BinaryTree left; // the left child of the node
	private BinaryTree right; // the right child of the node

	// constructor to create a new binary tree node
	public BinaryTree(int data, BinaryTree left, BinaryTree right){
		this.data = data; // set the data of the node
		this.left = left; // set the left child of the node
		this.right = right; // set the right child of the node
	}

	// constructor to create a new binary search tree from an ArrayList of integers
	public BinaryTree(ArrayList<Integer> list) {
		if (list.size() > 0) {

			// sort the list of integers
			Collections.sort(list);

			// calculate the middle index of the list
			int mid = list.size() / 2;

			// set the data of the root node to the middle value of the list
			data = list.get(mid);

			// if the left sublist is not empty, create a new binary tree from the left sublist
			if (mid > 0) {
				left = new BinaryTree(new ArrayList<Integer>(list.subList(0, mid)));
			}

			// if the right sublist is not empty, create a new binary tree from the right sublist
			if (mid < list.size() - 1) {
				right = new BinaryTree(new ArrayList<Integer>(list.subList(mid + 1, list.size())));
			}
		}
	}

	// getter method to retrieve the data stored in the node
	public int getData() {
		return data;
	}

	// getter method to retrieve the left child of the node
	public BinaryTree getLeft() {
		return left;
	}
	
	// getter method to retrieve the right child of the node
	public BinaryTree getRight() {
		return right;
	}
}