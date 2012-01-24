package org.ops4j.pax.wicket.samples.shiro.internal;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class AccesDeniedPage extends WebPage {

	public AccesDeniedPage() {
		add(new BookmarkablePageLink<Void>("loginLink", LoginPage.class));
	}
	
}
