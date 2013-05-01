package gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class RedirectURLBackingBean {

	private String access_token;
	
	public static final String NOME_PROJETO = "MusicBook";
	
	public RedirectURLBackingBean() {
		init();
	}
	
	public void init() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String error = req.getParameter("error_reason");
		if (error != null) {
			try {
				// you may want to pass this error to
				// the error redirect url
				String error_desc = req.getParameter("error_description");
				((HttpServletResponse) FacesContext.getCurrentInstance()
						.getExternalContext().getResponse())
						.sendRedirect("http://local_host:8080/"+NOME_PROJETO+"/faces/ERROR_URI.xhtml");
			} catch (Exception e) {
			}
		}

		String code = req.getParameter("code");
		if (code != null) {
			int ret = retrieveToken(code);
		} else {
			// Redirect or tell the user about the error
		}
	}

	private int retrieveToken(String code) {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				"https://graph.facebook.com/oauth/access_token");

		try {

			String[][] parameters = {
					{ "client_id", "129729313885038" },
					{ "client_secret", "863cde2df7632e06eb9a5057548b2d2a" },
					{ "redirect_uri",
							"http://localhost:8080/"+NOME_PROJETO+"/faces/login.xhtml" },
					{ "code", code } };

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

			for (int i = 0; i < parameters.length; i++) {
				nameValuePairs.add(new BasicNameValuePair(parameters[i][0],
						parameters[i][1]));
			}

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse resp = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(resp
					.getEntity().getContent()));

			String message = "";
			String lineData;
			while ((lineData = rd.readLine()) != null) {
				message += lineData;
			}

			String token = null;

			// Add more safety traps
			String[] params = message.split("&");
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					if (params[i].contains("access_token")) {
						String[] B = params[i].split("=");
						if (B != null) {
							token = B[1];
						}
						break;
					}
				}
			} else {
				// Let the user know about the error.
				return 0;
			}

			access_token = token;

			return 1;

		} catch (Exception e) {
			return 0;
		}

	}

	public String getAccessToken() {
		return access_token;
	}
 }