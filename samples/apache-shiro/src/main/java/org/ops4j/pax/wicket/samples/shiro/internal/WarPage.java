package org.ops4j.pax.wicket.samples.shiro.internal;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

@ShiroSecurityConstraint(constraint=ShiroConstraint.HasRole, value="president")
public class WarPage extends WebPage {

	public WarPage() {
		
	}
	
}
