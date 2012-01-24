package org.ops4j.pax.wicket.samples.shiro.internal;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;
import org.wicketstuff.shiro.page.LogoutPage;

@ShiroSecurityConstraint(constraint=ShiroConstraint.IsAuthenticated)
public class SecuredPage extends WebPage {

	public SecuredPage() {
		Subject subject = SecurityUtils.getSubject();
		add(new Label("name", (String) subject.getPrincipal()));
		PageParameters pageParameters = new PageParameters();
		pageParameters.add("logout", ""); // Dummy because there is no no arg constructor for LogoutPage
		add(new BookmarkablePageLink<Void>("logoutLink", LogoutPage.class, pageParameters));
		
		if (subject.isPermitted("war:start")){
			add(new Label("startWar", "You can start a war"));
			BookmarkablePageLink<Void> warPageLink = new BookmarkablePageLink<Void>("startWarLink", WarPage.class);
			if (!subject.hasRole("president")){
				warPageLink.setVisible(false);
			}
			add(warPageLink);
		}else{
			add(new Label("startWar", "You can't start a war"));
		}
		
		if (subject.isPermitted("war:end")){
			add(new Label("endWar", "You can end a war"));
		}else{
			add(new Label("endWar", "You can't end a war"));
		}
		
		if (subject.isPermitted("war:watch")){
			add(new Label("watchWar", "You can watch a war"));
		}else{
			add(new Label("watchWar", "You can't watch a war"));
		}
	}
	
}
