package controlleur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ThreadControl {

	public boolean threadControl(Process process) {
		try {
			InputStream is1 = process.getInputStream();
			InputStream is2 = process.getErrorStream();
			OutputStream is3 = process.getOutputStream();

			new Thread() {
				public void run() {
					BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
					try {
						while (br1.readLine() != null) {
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							is1.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();

			new Thread() {
				public void run() {
					BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
					
					try {
						while (br2.readLine() != null) {
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							is2.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();		
			
			try {
				process.waitFor();
				process.destroy();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			process.getInputStream().close();
			process.getOutputStream().close();
			process.getErrorStream().close();

		} catch (IOException e) {
			e.printStackTrace();

		}
		return true;
	}

}
