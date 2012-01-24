package org.ops4j.pax.wicket.samples.shiro.internal;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.shiro.component.LoginPanel;

public class LoginPage extends WebPage {
	
	public LoginPage() {
		super();
		add(new LoginPanel("login", true));
	}
	
	public LoginPage(PageParameters pageParameters){
		super(pageParameters);
		add(new LoginPanel("login", true));
	}
}
