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
		//printTree(huffTree.root);
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
		System.out.println("Original message: " +  originalMessage);
	}
	
	private void makeFrequencyTable(String inputString)
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
	
	private void displayFrequencyTable()
	{
		
		for (int i=0; i<huffArray.size(); i++)
		{
			System.out.print(huffArray.get(i).character + "   |" + huffArray.get(i).weight+"\n");
		}
	}
	
	private void addToQueue() 
	{
		theQueue = new PriorityQ(huffArray.size());
		System.out.println("_______________");
		System.out.println();
		
		
		for (int i=0; i<huffArray.size(); i++)
		{
			
			theQueue.insert(new HuffTree(huffArray.get(i).character, huffArray.get(i).weight));
		}
		
		/*while (theQueue.getSize()>0)
		{
			System.out.println(theQueue.remove().getWeight() );
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
			System.out.println(temp3.getWeight());
					
		}
		
		huffTree = hufflist.remove();
	}
	/*public void printTree(HuffNode root)
	{
		if (root.isLeaf())
		{
			System.out.print(root.character + " " + root.weight + "\n");
			return;
		}
		printTree(root.leftChild);
		printTree(root.rightChild);
		
		
		
	}*/
	
	private void makeCodeTable(HuffNode huffNode, String bc)
	{		
		
		if (huffNode.isLeaf())
		{
			codeTable[(int) huffNode.character-1] = bc;
			//System.out.println(huffNode.character + " "+bc);
			return;
		}
		
		makeCodeTable(huffNode.leftChild, bc+='0');
		bc = bc.substring(0, bc.length()-1);
		
		makeCodeTable(huffNode.rightChild, bc+='1');
		
		//hint, this will be a recursive method
	}
	
	private void displayCodeTable()
	{	
		for (int i=0; i<codeTable.length; i++)
		{
			if (codeTable[i]!=null)
			System.out.println((char) (i+1) + "   |"+codeTable[i]);
		}
	}
	
	private void encode()                   
	{		
		//use the code table to encode originalMessage. Save result in the encodedMessage field
		
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
		//System.out.print((int) 'd');
		//System.out.println(codeTable[99]);
		String s = "";
		
		for (int i=0; i<encodedMessage.length(); i++)
		{
			s+=encodedMessage.charAt(i);
			
			for (int j=1; j<codeTable.length; j++)
			{
				if (s.equals(codeTable[j-1]))
				{
					
					decodedMessage+=(char) j;
					s="";
				}
				
			}
		}
		
	}
	
	public void displayDecodedMessage() {
		System.out.println("Decoded message: " + decodedMessage);
	}	
}
