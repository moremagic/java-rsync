package net.collegeman.rsync;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class Settings {

	private String localRoot;
	
	private String remoteRoot;
	
	public Settings() {}
	
	public Settings(String[] args, Options options) {
		try {
			CommandLineParser parser = new PosixParser();
			CommandLine cmd = parser.parse(options, args);
			
			if (cmd.hasOption("help") || cmd.hasOption("?"))
				throw new RsyncHelpException();
			
			if (!cmd.hasOption("src") && !cmd.hasOption("dest"))
				throw new RsyncHelpException();
			
		} catch (ParseException e) {
			throw new IllegalArgumentException("Failed to parse command line arguments", e);
		}
	}

	public String getLocalRoot() {
		return localRoot;
	}

	public void setLocalRoot(String localRoot) {
		this.localRoot = localRoot;
	}

	public String getRemoteRoot() {
		return remoteRoot;
	}

	public void setRemoteRoot(String remoteRoot) {
		this.remoteRoot = remoteRoot;
	}
	
}
