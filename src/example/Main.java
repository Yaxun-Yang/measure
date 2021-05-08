package example;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gr.spinellis.ckjm.MetricsFilter;

public class Main {

	public static void main(String[] args) {
		// WMC, DIT, NOC, CBO, RFC, LCOM, Ce, and NPM.
		String path = Main.class.getResource(".").getPath();
		
		String wmc = path + "wmc";
		String dit = path + "dit";
		String noc = path + "noc";
		String cbo = path + "cbo";
		String rfc = path + "rfc";
		String lcom= path + "lcom";
		
		String[] arguments = getArguments(lcom);
		MetricsFilter.main(arguments);
	}

	private static String[] getArguments(String... args) {
		List<String> fileList = new ArrayList<>();
		for(String path : args){
			File file = new File(path);
			if(file.isDirectory()){
				for(String f : file.list()){
					fileList.add(path + File.separator + f);
				}
			}else if(file.isFile()){
				fileList.add(path);
			}
		}
		String[] results = fileList.toArray(new String[fileList.size()]);
		return results;
	}
}
