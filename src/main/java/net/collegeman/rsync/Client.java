package net.collegeman.rsync;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import net.collegeman.rsync.Rsync.Checksums;

public class Client {

	private Settings settings;
	
	// ssh connection string: [user@]host:dest
	private Pattern ssh = Pattern.compile("([a-z0-9\\-_]+@)?[a-z0-9\\-_]+:[a-z0-9\\-_ ]+", Pattern.CASE_INSENSITIVE);
	
	// http(s) connection string: http(s)://[user@]host[:port]/dest
	private Pattern http = Pattern.compile("https?://([a-z0-9\\-_]+@)?([a-z0-9\\-_]+\\.)+[a-z]{2,}(:[0-9]+)?/(a-z0-9\\-_ /?)+");
	
	private AtomicBoolean finished = new AtomicBoolean(false);
	
	public Client(Settings settings) {
		this.settings = settings;
		
		if (ssh.matcher(settings.getRemoteRoot()).matches())
			setupSSH();
		else if (http.matcher(settings.getRemoteRoot()).matches())
			setupHTTP();
		else
			setupLocal();
	}
	
	public synchronized void send(final File f) {
		new Thread() {
			@Override 
			public void run() {
				if (f.isDirectory()) 
					sendDirectory(f);
				else
					sendFile(f);
			}
		}.run();
	}
	
	private void sendDirectory(File dir) {
		if (dir.getName() != "." && dir.getName() != "..") {
			// TODO: send directory information
			
			for (File child : dir.listFiles()) {
				if (child.isDirectory())
					sendDirectory(child);
				else
					sendFile(child);
			}
		}
	}
	
	private void sendFile(File f) {
		Checksums checksums = Rsync.getChecksums(settings, f, 128);
	}
	
	public boolean isFinished() {
		return finished.get();
	}
	
	private void setupSSH() {
		
	}
	
	private void setupHTTP() {
		
	}
	
	private void setupLocal() {
		
	}
	
	public void shutdown() {
		
	}
	
}
