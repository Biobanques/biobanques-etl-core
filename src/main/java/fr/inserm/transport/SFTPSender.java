package fr.inserm.transport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;

import fr.inserm.bean.IssueBean;
import fr.inserm.bean.IssueBean.Categorie;
import fr.inserm.bean.PropertiesBean;
import fr.inserm.tools.StringFileTools;

/**
 * classe d evoi en sftp des fichiers
 * 
 * @author nicolas
 * 
 */
public class SFTPSender {
	private static final Logger LOGGER = Logger.getLogger(SFTPSender.class);
	private final int portFTP;
	private final String ftpHost;
	private final String ftpUserName;
	private final String ftpPassword;
	private final String workingDirectory;
	private final String exportFolder;
	private final String sentFolder;
	private String proxyHost = "";
	private int proxyPort;
	private String proxyUserName = "";
	private String proxyUserPassword = "";

	/**
	 * init du sender avec les proprietes d envoi sur sftp.
	 * 
	 * @param appLoader
	 */
	public SFTPSender(PropertiesBean appLoader) {
		exportFolder = appLoader.getFolderExport();
		sentFolder = appLoader.getFolderExportSent();
		ftpHost = appLoader.getServer();
		ftpUserName = appLoader.getUsername();
		ftpPassword = appLoader.getPasswordFTP();
		workingDirectory = appLoader.getDirectory();
		portFTP = appLoader.getPortFTP();

		try {
			String proxy = appLoader.getProxyHost();
			if (proxy != null && !proxy.equals("null") && !proxy.equals("")) {

				proxyHost = appLoader.getProxyHost();
				proxyPort = appLoader.getProxyPort();
				if (!appLoader.getProxyUserName().equals(null)
						&& !appLoader.getProxyUserName().equals("")) {
					proxyUserName = appLoader.getProxyUserName();
					proxyUserPassword = appLoader.getProxyUserPassword();
				}
			}
		} catch (Exception e) {
			LOGGER.info("Pas de propriete proxyHost sur ce profil : "
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * Establish SFTP Session
	 * 
	 * @return
	 * @throws Exception
	 */
	public ChannelSftp createSession() throws Exception {
		JSch jsch = new JSch();
		Session session = null;
		Channel channel = null;
		ChannelSftp c = null;
		try {
			final SSLContext sc = SSLContext.getInstance("SSL");
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] certs,
						String authType) {
				}

				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] certs,
						String authType) {
				}
			} };
			sc.init(null, trustAllCerts, new java.security.SecureRandom());

			// portFTP = 22;
			session = jsch.getSession(this.ftpUserName, this.ftpHost,
					this.portFTP);
			session.setPassword(ftpPassword);

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			if (!this.proxyHost.equals(null) && !this.proxyHost.equals("")) {

				// ProxyHTTP proxy = new ProxyHTTP("de2538.ispfr.net", 443);
				ProxyHTTP proxy = new ProxyHTTP(proxyHost, proxyPort);

				if (!this.proxyUserName.equals(null)
						&& !this.proxyUserName.equals("")) {
					proxy.setUserPasswd(proxyUserName, proxyUserPassword);
				}

				session.setProxy(proxy);
			}

			LOGGER.info("Trying to connect to" + ftpHost + ":" + portFTP);

			session.connect();

			LOGGER.info("Connected to" + ftpHost + ":" + portFTP);
			channel = session.openChannel("sftp");
			channel.connect();
			c = (ChannelSftp) channel;
			return c;
		} catch (Exception e) {
			LOGGER.error("unable to connect : " + e.getLocalizedMessage());
			e.printStackTrace();
			throw e;
		}
	}

	// Destroy SFTP Session
	public void destroySession(ChannelSftp c) throws Exception {
		try {
			c.quit();
		} catch (Exception exc) {
			System.err.println("Unable to disconnect from SFTP server. "
					+ exc.toString());
			throw exc;
		}
	}

	/**
	 * push des fichiers sur serveur return liste des anomalies.
	 * 
	 * @return issues list
	 */
	public List<IssueBean> pushFilesOnSent() {
		List<IssueBean> issues = new ArrayList<IssueBean>();
		String path = exportFolder;
		if (path == null) {
			issues.add(new IssueBean(IssueBean.Severity.error,
					"path d export is null", Categorie.transportSFTP));
			return issues;
		}
		if (path.isEmpty()) {
			issues.add(new IssueBean(IssueBean.Severity.error,
					"path d export is empty", Categorie.transportSFTP));
			return issues;
		}
		File folder = new File(path);
		if (!folder.isDirectory()) {
			if (path.isEmpty()) {
				issues.add(new IssueBean(IssueBean.Severity.error,
						"path d export n est pas un repertoire:<" + path + ">",
						Categorie.transportSFTP));
				return issues;
			}
		}
		if (!folder.exists()) {
			issues.add(new IssueBean(IssueBean.Severity.error,
					"folder d export n existe pas:<" + path + ">",
					Categorie.transportSFTP));
			return issues;
		}
		// list files and folder
		File[] files = folder.listFiles();
		if (files != null) {
			if (files.length > 0) {
				try {
					ChannelSftp c = createSession();
					c.cd(workingDirectory);
					try {
						int fileCount = 0;
						for (File f : files) {
							if (f.isFile()) {
								fileCount++;
								FileInputStream fis = new FileInputStream(f);
								try {
									c.put(fis, f.getName());
								} finally {
									fis.close();
								}
								// move des fichiers envoyes dans le repertoire
								// d envoi
								StringFileTools.moveFile(f, sentFolder);
							}
						}
						if (fileCount == 0) {
							issues.add(new IssueBean(IssueBean.Severity.error,
									"aucun fichier trouvé dans le dossier  dexport:"
											+ path, Categorie.transportSFTP));
						}
					} catch (Exception e) {
						issues.add(new IssueBean(IssueBean.Severity.error,
								"exception in put file:" + e.getMessage(),
								Categorie.transportSFTP));
					}
					try {
						destroySession(c);
					} catch (Exception exc) {
						issues.add(new IssueBean(IssueBean.Severity.error,
								" Unable to disconnect exception:"
										+ exc.getMessage(),
								Categorie.transportSFTP));
					}
				} catch (Exception e) {
					LOGGER.error("unable to connect :"
							+ e.getLocalizedMessage());
					issues.add(new IssueBean(IssueBean.Severity.error,
							" Unable to connect with u=" + ftpUserName + ",h="
									+ ftpHost + ",wdir=" + workingDirectory
									+ " exception:" + e.getMessage(),
							Categorie.transportSFTP));
				}
			} else {
				issues.add(new IssueBean(IssueBean.Severity.error,
						"aucun fichier a envoyer par sftp, size=0",
						Categorie.transportSFTP));
			}
		} else {
			issues.add(new IssueBean(IssueBean.Severity.error,
					"files is null on folder:" + path, Categorie.transportSFTP));
		}
		return issues;
	}

	private static void doTunnelHandshake(Socket tunnel, String host, int port)
			throws IOException {
		OutputStream out = tunnel.getOutputStream();
		String msg = "CONNECT " + host + ":" + port + " HTTP/1.0\n"
				+ "User-Agent: "
				+ sun.net.www.protocol.http.HttpURLConnection.userAgent
				+ "\r\n\r\n";
		byte b[];
		try {

			b = msg.getBytes("ASCII7");
		} catch (UnsupportedEncodingException ignored) {
			/*
			 * If ASCII7 isn't there, something serious is wrong, but Paranoia
			 * Is Good (tm)
			 */
			b = msg.getBytes();
		}
		out.write(b);
		out.flush();

		/*
		 * We need to store the reply so we can create a detailed error message
		 * to the user.
		 */
		byte reply[] = new byte[200];
		int replyLen = 0;
		int newlinesSeen = 0;
		boolean headerDone = false; /* Done on first newline */

		InputStream in = tunnel.getInputStream();
		boolean error = false;

		while (newlinesSeen < 2) {
			int i = in.read();
			if (i < 0) {
				throw new IOException("Unexpected EOF from proxy");
			}
			if (i == '\n') {
				headerDone = true;
				++newlinesSeen;
			} else if (i != '\r') {
				newlinesSeen = 0;
				if (!headerDone && replyLen < reply.length) {
					reply[replyLen++] = (byte) i;
				}
			}
		}

		/*
		 * Converting the byte array to a string is slightly wasteful in the
		 * case where the connection was successful, but it's insignificant
		 * compared to the network overhead.
		 */
		String replyStr;
		try {
			replyStr = new String(reply, 0, replyLen, "ASCII7");
		} catch (UnsupportedEncodingException ignored) {
			replyStr = new String(reply, 0, replyLen);
		}

		System.out.println(replyStr);

		/* We asked for HTTP/1.0, so we should get that back */
		if (!replyStr.startsWith("HTTP/1.0 200")) {
			throw new IOException("Unable to tunnel for " + host + ":" + port
					+ ".  Proxy returns \"" + replyStr + "\"");
		}

		/* tunneling Handshake was successful! */
	}

}