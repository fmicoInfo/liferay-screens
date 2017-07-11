package com.liferay.mobile.screens.viewsets.defaultviews.portlet;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Víctor Galán Grande
 */

public class ScreensNativeWebView extends WebViewClient implements ScreensWebView  {

	private ScreensWebView.Listener listener;
	private WebView webView;

	public ScreensNativeWebView(Context context) {
		webView = new WebView(context);
		webView.setWebViewClient(this);
	}

	@Override
	public WebView getView() {
		return webView;
	}

	@Override
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	@Override
	public void onAttached() {

	}

	@Override
	public void onDetached() {

	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);

		if (listener != null) {
			listener.onPageStarted();
		}
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);

		if (listener != null) {
			listener.onPageFinished(url);
		}
	}
}