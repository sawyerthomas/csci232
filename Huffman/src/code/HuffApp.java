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
		printTree(huffTree.root);
		//when the following method is implemented, remove the "//", so it executes
		makeCodeTable(huffTree.root, "");  						
		encode();
		displayEncodedMessage();
		displayCodeTable();
		decode();
		displayDecodedMessage();		
	}
	
	private void readInput() {
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
		//System.out.println("Original message: " +  originalMessage);
	}
	
	private void makeFrequencyTable(String inputString)
	{	
		//ArrayList<HuffNode> huffList = new ArrayList<HuffNode>();
		
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
	
	private void displayFrequencyTable()
	{
		
		/*for (int i=0; i<huffList.size(); i++)
		{
			System.out.print(huffList.get(i).character + "   |" + huffList.get(i).weight+"\n");
		}*/
	}
	
	private void addToQueue() 
	{
		theQueue = new PriorityQ(huffArray.size());
		
		for (int i=0; i<huffArray.size(); i++)
		{
			
			theQueue.insert(new HuffTree(huffArray.get(i).character, huffArray.get(i).weight));
		}
		
		/*while (theQueue.getSize()>0)
		{
			System.out.println(theQueue.remove().getWeight());
		}*/
	}
	
	private void buildTree(PriorityQ hufflist) 
	{
		//pull items from the priority queue and combine them to form 
		//a HuffTree. Save the results to the huffTree field
		while (hufflist.getSize()>1)
		{
			HuffTree temp1 = hufflist.remove();
			HuffTree temp2 = hufflist.remove();
			int w = temp1.getWeight() + temp2.getWeight();
			HuffTree temp3 = new HuffTree(w, temp1, temp2);
			hufflist.insert(temp3);
					
		}
		
		huffTree = hufflist.remove();
	}
	public void printTree(HuffNode root)
	{
		if (root.isLeaf())
		{
			System.out.print(root.character + " " + root.weight + "\n");
			return;
		}
		printTree(root.leftChild);
		printTree(root.rightChild);
		
		
		
	}
	
	private void makeCodeTable(HuffNode huffNode, String bc)
	{		
		if (huffNode.isLeaf())
		{
			codeTable[(int) huffNode.character-1] = bc;
			System.out.println(huffNode.character + " "+bc);
			return;
		}
		makeCodeTable(huffNode.rightChild, bc+='1');
		makeCodeTable(huffNode.leftChild, bc+='0');
		//hint, this will be a recursive method
	}
	
	private void displayCodeTable()
	{	
		for (int i=0; i<codeTable.length; i++)
		{
			if (codeTable[i]!=null)
			System.out.println(i + codeTable[i]);
		}
	}
	
	private void encode()                   
	{		
		//use the code table to encode originalMessage. Save result in the encodedMessage field
	}

	private void displayEncodedMessage() {
		System.out.println("\nEncoded message: " + encodedMessage);		
	}

	private void decode()
	{
		//decode the message and store the result in the decodedMessage field
	}
	
	public void displayDecodedMessage() {
		System.out.println("Decoded message: " + decodedMessage);
	}	
}
