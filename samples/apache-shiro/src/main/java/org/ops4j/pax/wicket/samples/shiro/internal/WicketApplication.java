package org.ops4j.pax.wicket.samples.shiro.internal;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.shiro.annotation.AnnotationsShiroAuthorizationStrategy;
import org.wicketstuff.shiro.authz.ShiroUnauthorizedComponentListener;

public class WicketApplication extends WebApplication {

	@Override
	protected void init() {
		super.init();

		// Enable Shiro security
		AnnotationsShiroAuthorizationStrategy authz = new AnnotationsShiroAuthorizationStrategy();
		getSecuritySettings().setAuthorizationStrategy(authz);
		getSecuritySettings().setUnauthorizedComponentInstantiationListener(
				new ShiroUnauthorizedComponentListener(LoginPage.class, AccesDeniedPage.class, authz));

		mountPage("login", LoginPage.class);
		mountPage("secure", SecuredPage.class);
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}
}
