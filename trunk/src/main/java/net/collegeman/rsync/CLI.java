package net.collegeman.rsync;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class CLI {
	
	private static Options options = new Options();
	
	static {
		options.addOption("?", "help", false, "prints this message");
	}

	public static void main(String[] args) {
		try {
		
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		} catch (RsyncHelpException e) {
			help();
		} catch (RsyncException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void help() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("rsync", options);
	}
	
}
