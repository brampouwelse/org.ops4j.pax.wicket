package org.ops4j.pax.wicket.samples.shiro.internal;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class AccesDeniedPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public AccesDeniedPage() {
		add(new FeedbackPanel("feedback"));
		add(new BookmarkablePageLink<Void>("loginLink", LoginPage.class));
	}
	
}
