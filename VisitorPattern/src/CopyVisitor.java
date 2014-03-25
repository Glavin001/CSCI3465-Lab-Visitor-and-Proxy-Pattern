/**
 * @author glavin
 *
 */

import java.io.*;
import java.util.ArrayList;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyVisitor implements FileSystemVisitor
{

	private ArrayList<String> rootPath = new ArrayList<String>();
	private String destPath = "";
	
	private Path pathFromParts(ArrayList<String> parts, String filename)
	{
		
		String listString = "";
		for (String s : parts)
		{
		    listString += s + "/";
		}
		listString += filename;
		return Paths.get(listString);
	}
	
	public CopyVisitor(String destPath)
	{
		this.destPath = destPath;
	}
	
	public void visitFileNode(FileNode node)
	{
		//for (int i = 0; i < rootPath.size(); i++) System.out.print("/"+rootPath.get(i));
		//System.out.println(node.getFile().getName());
		
		// Copy file
		Path p = pathFromParts(rootPath, node.getFile().getName());
		//System.out.println(p);
		
		Path d = Paths.get(destPath, p.toString());
		System.out.println(d);
		
		try {
			Files.copy(p, d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	public void visitDirectoryNode(DirectoryNode node)
	{
		//for (int i = 0; i < rootPath.size(); i++) System.out.print("/"+rootPath.get(i));
		//System.out.println(node.getDirectory().getName());
		rootPath.add(node.getDirectory().getName());
		// Copy directory
		Path p = pathFromParts(rootPath, "/");
		//System.out.println(p);

		Path d = Paths.get(destPath, p.toString());
		System.out.println(d);
		try {
			Files.copy(p, d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		// Recurse thru children
		for (FileSystemNode c : node.getChildren())
			c.accept(this);
		rootPath.remove(rootPath.size()-1);
	}

}
