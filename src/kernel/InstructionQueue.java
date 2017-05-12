package kernel;

import java.util.ArrayList;

import util.FileReaderUtil;

public class InstructionQueue {
	public FileReaderUtil fru = null;
	private String fileName;
	
	public InstructionQueue(String fileName) {
		// fru = new FileReaderUtil(fileName);
		this.fileName = fileName;
	}
	
	public void close() {
		if (fru != null) fru.close();
	}
	
	public boolean reload() {
		
		ArrayList<String> instrs = new ArrayList<String>();
		FileReaderUtil.readFileToList(fileName, instrs);
		
		
		return true;
	}
	
	public void mount(AbstractHandler ah) {
		
	}
}
