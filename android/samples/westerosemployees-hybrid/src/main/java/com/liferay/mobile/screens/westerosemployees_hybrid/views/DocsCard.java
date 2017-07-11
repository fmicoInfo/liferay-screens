package com.liferay.mobile.screens.westerosemployees_hybrid.views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.ViewPropertyAnimator;

import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.portlet.PortletConfiguration;
import com.liferay.mobile.screens.portlet.PortletDisplayListener;
import com.liferay.mobile.screens.portlet.PortletDisplayScreenlet;
import com.liferay.mobile.screens.portlet.util.InjectableScript;
import com.liferay.mobile.screens.westerosemployees_hybrid.R;
import com.liferay.mobile.screens.westerosemployees_hybrid.utils.CardState;

/**
 * @author Víctor Galán Grande
 */
public class DocsCard extends CommentsRatingsCard implements PortletDisplayListener {

	PortletDisplayScreenlet portletDisplayScreenlet;
	private boolean loaded;

	public DocsCard(Context context) {
		super(context);
	}

	public DocsCard(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DocsCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DocsCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public ViewPropertyAnimator setState(CardState state) {
		if (!loaded && state.equals(CardState.NORMAL)) {
			loaded = true;
			PortletConfiguration configuration = new PortletConfiguration.Builder("/web/guest/documents").addRawCss(R.raw.docs_portlet_css).addRawJs(R.raw.docs_portlet_js).load();

            portletDisplayScreenlet.setPortletConfiguration(configuration);
            portletDisplayScreenlet.load();
            portletDisplayScreenlet.setListener(this);

		}

		return super.setState(state);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		portletDisplayScreenlet = (PortletDisplayScreenlet) findViewById(R.id.portlet_docs);
	}

	@Override
	public void goLeft() {
		super.goLeft();
	}

	@Override
	public void error(Exception e, String userAction) {

	}

	@Override
	public void onRetrievePortletSuccess(String url) {

	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {

	}

	@Override
	public void onScriptMessageHandler(String namespace, final String body) {
		if("doc-item".equals(namespace)) {
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
                    PortletConfiguration configuration = new PortletConfiguration.Builder("/web/guest/detail?id=" + body).addRawCss(R.raw.detail_css).addRawJs(R.raw.detail_js).load();

                    PortletDisplayScreenlet portletDisplayScreenlet = (PortletDisplayScreenlet) findViewById(R.id.portlet_doc_item);
                    portletDisplayScreenlet.setPortletConfiguration(configuration);
                    portletDisplayScreenlet.load();

                    cardListener.moveCardRight(DocsCard.this);
				}
			});
		}
	}

	@Override
	public InjectableScript cssForPortlet(String portlet) {
		return null;
	}

	@Override
	public InjectableScript jsForPortlet(String portlet) {
		return null;
	}
}