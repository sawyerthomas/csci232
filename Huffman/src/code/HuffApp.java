/*
 * Sawyer Thomas
 * Edward Farley
 * 
 * csci232 Lab1: HuffMan Coding
 */


package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;
import java.io.FileReader;;


public class HuffApp {
	private int[]freqTable;
	private final static int ASCII_TABLE_SIZE = 128;
	private String originalMessage = "";
	private PriorityQ theQueue;
	private HuffTree huffTree;
	private String encodedMessage = "";
	private String[] codeTable;
	private String decodedMessage = "";
	ArrayList<HuffNode> huffArray = new ArrayList<HuffNode>();
	

	public static void main(String[] args) {
		new HuffApp();	
		
	}
		
	
	public HuffApp() {
		codeTable = new String[ASCII_TABLE_SIZE];  
		readInput();
		displayOriginalMessage();
		makeFrequencyTable(originalMessage);
		displayFrequencyTable();
		addToQueue();
		buildTree(theQueue);
		
		makeCodeTable(huffTree.root, "");  						
		encode();
		displayEncodedMessage();
		displayCodeTable();
		decode();
		displayDecodedMessage();		
	}
	
	private void readInput() //this method takes in a file called input.txt and saves it as a string
	{
		BufferedReader reader = null;

		try {
		    File file = new File("input.txt");
		    reader = new BufferedReader(new FileReader(file));

		    String line;
		    while ((line = reader.readLine()) != null) {
		        originalMessage+= line;
		        System.out.println(originalMessage);
		    }

		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}
	
	private void displayOriginalMessage() {
		System.out.println("Original message: " +  originalMessage);
	}
	
	private void makeFrequencyTable(String inputString)/*iterates through the string, then iterates through arraylist.
	if the character in the string is already represented in the list, increment frequency. else create a new object in the list*/
	{	
		
		
		for(int i=0; i<inputString.length(); i++)
		{
			boolean flag = false;
			for (int j=0; j<huffArray.size(); j++)
			{
				if(inputString.charAt(i)==huffArray.get(j).character)
				{
					flag=true;
					huffArray.get(j).weight++;
				}
			}
			if (flag==false)
				huffArray.add(new HuffNode(inputString.charAt(i), 1));
		}
		
	}
	
	private void displayFrequencyTable()/*iterates through the arraylist, mostly just formatting*/
	{
		System.out.println("Frequency Table: ");
		System.out.println("char |val");
		
		for (int i=0; i<huffArray.size(); i++)
		{
			
			System.out.print(huffArray.get(i).character + "    |" + huffArray.get(i).weight+"\n");
		}
	}
	
	private void addToQueue() //takes huffNodes, turns them into hufftrees, adds them to the queue
	{
		theQueue = new PriorityQ(huffArray.size());
		
		
		for (int i=0; i<huffArray.size(); i++)
		{
			
			theQueue.insert(new HuffTree(huffArray.get(i).character, huffArray.get(i).weight));
		}
		
		
	}
	
	private void buildTree(PriorityQ hufflist) 
	{
		//pull items from the priority queue and combine them to form 
		//a HuffTree. Save the results to the huffTree field
		while (hufflist.getSize()>1)//iteratively, removes two trees, combines them with a parent node, inserts them back into tree
		{
			HuffTree temp1 = hufflist.remove();
			HuffTree temp2 = hufflist.remove();
			int w = temp1.getWeight() + temp2.getWeight();
			HuffTree temp3 = new HuffTree(w, temp1, temp2);
			hufflist.insert(temp3);
			
					
		}
		
		huffTree = hufflist.remove();
	}

	
	
	private void makeCodeTable(HuffNode huffNode, String bc)/*casts the character to its acii value inserts it into the array*/
	{		
		
		if (huffNode.isLeaf())
		{
			codeTable[(int) huffNode.character-1] = bc;
			
			return;
		}
		
		makeCodeTable(huffNode.leftChild, bc+='0');
		bc = bc.substring(0, bc.length()-1);
		
		makeCodeTable(huffNode.rightChild, bc+='1');
		
		
	}
	
	private void displayCodeTable()/*casts index to a charcter, formatting*/
	{
		System.out.println("Code Table: ");
		for (int i=0; i<codeTable.length; i++)
		{
			if (codeTable[i]!=null)
			System.out.println((char) (i+1) + "   |"+codeTable[i]);
		}
	}
	
	private void encode()/*reference the code table with the ascii index of the character in the string*/                   
	{		
		
		
		for (int i=0; i<originalMessage.length(); i++)
		{
			encodedMessage+=codeTable[(int) originalMessage.charAt(i)-1];
		}
	}

	private void displayEncodedMessage() {
		System.out.println("\nEncoded message: " + encodedMessage);		
	}

	private void decode()
	{
		
		String s = "";
		
		for (int i=0; i<encodedMessage.length(); i++)
		{
			s+=encodedMessage.charAt(i);//iteratively add code character to the string
			
			for (int j=1; j<codeTable.length; j++)
			{
				if (s.equals(codeTable[j-1]))
				{
					
					decodedMessage+=(char) j;
					s="";//empty the temp string if a code has been found
				}
				
			}
		}
		
	}
	
	public void displayDecodedMessage() {
		System.out.println("Decoded message: " + decodedMessage);
	}	
}
