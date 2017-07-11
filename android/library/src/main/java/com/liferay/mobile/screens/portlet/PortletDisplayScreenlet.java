/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.portlet;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.dlfile.display.audio.AudioDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.image.ImageDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.pdf.PdfDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.video.VideoDisplayScreenlet;
import com.liferay.mobile.screens.portlet.interactor.PortletDisplayInteractor;
import com.liferay.mobile.screens.portlet.util.CssScript;
import com.liferay.mobile.screens.portlet.util.InjectableScript;
import com.liferay.mobile.screens.portlet.util.JsScript;
import com.liferay.mobile.screens.portlet.view.PortletDisplayViewModel;
import com.liferay.mobile.screens.util.AssetReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author Sarai Díaz García
 */
public class PortletDisplayScreenlet extends BaseScreenlet<PortletDisplayViewModel, PortletDisplayInteractor>
	implements PortletDisplayListener {

	private boolean autoLoad;
	private PortletDisplayListener listener;
	private HashMap<String, Integer> layouts = new HashMap<>();
	private int imageLayout = R.layout.image_display_default;
	private int videoLayout = R.layout.video_display_default;
	private int audioLayout = R.layout.audio_display_default;
	private int pdfLayout = R.layout.pdf_display_default;
	private PortletConfiguration portletConfiguration;

	public PortletDisplayScreenlet(Context context) {
		super(context);
	}

	public PortletDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PortletDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public PortletDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	/**
	 * Starts the operation and check if the Portlet URL it's correct and then loads it.
	 * If the URL is not correct, the operation fails.
	 */
	public void load() {
		getViewModel().showStartOperation(DEFAULT_ACTION);
		if (portletConfiguration != null) {

			String finalUrl = buildPortletUrl(portletConfiguration.getPortletUrl());
			String body = buildBody();

			JavascriptInjector javascriptInjector = new JavascriptInjector(getContext());

			javascriptInjector.addJsFile(R.raw.screens);

			if (!portletConfiguration.isThemeEnabled()) {
				getViewModel().setTheme(false);
			}

			for (InjectableScript script : portletConfiguration.getScripts()) {
				javascriptInjector.addJs(script.getContent());
			}

			getViewModel().showFinishOperation(finalUrl, body, javascriptInjector.generateInjectableJs());
		} else {
			getViewModel().showFailedOperation(DEFAULT_ACTION, new MalformedURLException());
		}
	}

	public String buildPortletUrl(String url) {
		try {
			url = URLEncoder.encode(url, "UTF-8");
			return String.format("%s/c/portal/login?redirect=%s", LiferayServerContext.getServer(), url);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public String buildBody() {
		Authentication authentication = SessionContext.getAuthentication();

		if (authentication instanceof BasicAuthentication) {
			BasicAuthentication basicAuth = (BasicAuthentication) authentication;

			String username = basicAuth.getUsername();
			String password = basicAuth.getPassword();

			return String.format("login=%s&password=%s", username, password);
		}

		return "";
	}

	public void loadAsset() {
		performUserAction();
	}

	/**
	 * Checks if there is a session created and if exists {@link #portletConfiguration} and
	 * {@link #portletConfiguration} attributes.
	 * Then calls {@link #load()} method.
	 */
	protected void autoLoad() {
		if (SessionContext.isLoggedIn() && portletConfiguration != null && portletConfiguration.getPortletUrl() != null) {
			if (portletConfiguration.getPortletUrl().contains("documents")) {
				loadAsset();
			} else {
				load();
			}
		}
	}

	@Override
	protected void onScreenletAttached() {
		if (autoLoad) {
			autoLoad();
		}
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {

		AssetDisplayScreenlet assetDisplayScreenlet = new AssetDisplayScreenlet(getContext());
		assetDisplayScreenlet.render(R.layout.asset_display_default);
		getDLFileScreenletLayouts();

		assetDisplayScreenlet.setLayouts(layouts);
		assetDisplayScreenlet.load(assetEntry);

		getViewModel().showFinishOperation(assetDisplayScreenlet);

		if (listener != null) {
			listener.onRetrieveAssetSuccess(assetEntry);
		}
	}

	@Override
	public void onScriptMessageHandler(String namespace, String body) {
		if (namespace.startsWith("screensInternal")) {
			handleInternal(namespace, body);
		} else {
            if (listener != null) {
                listener.onScriptMessageHandler(namespace, body);
            }
		}
	}

	@Override
	public InjectableScript cssForPortlet(String portlet) {
        if (listener != null){
            return listener.cssForPortlet(portlet);
        }
        return null;
	}

	@Override
	public InjectableScript jsForPortlet(String portlet) {
        if (listener != null){
            return getListener().jsForPortlet(portlet);
        }
        return null;
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {

		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.PortletDisplayScreenlet, 0, 0);

		autoLoad = typedArray.getBoolean(R.styleable.PortletDisplayScreenlet_autoLoad, true);

		int layoutId = typedArray.getResourceId(R.styleable.PortletDisplayScreenlet_layoutId, getDefaultLayoutId());

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override
	protected PortletDisplayInteractor createInteractor(String actionName) {
		return new PortletDisplayInteractor();
	}

	@Override
	protected void onUserAction(String userActionName, PortletDisplayInteractor interactor, Object... args) {
		interactor.start(portletConfiguration.getPortletUrl());
	}

	@Override
	public void error(Exception e, String userAction) {
		getViewModel().showFailedOperation(userAction, e);

		if (listener != null) {
			listener.error(e, userAction);
		}
	}

	@Override
	public void onRetrievePortletSuccess(String url) {
		if (listener != null) {
			listener.onRetrievePortletSuccess(url);
		}

		getViewModel().showFinishOperation(url);
	}

	public boolean isAutoLoad() {
		return autoLoad;
	}

	public void setAutoLoad(boolean autoLoad) {
		this.autoLoad = autoLoad;
	}

	public PortletDisplayListener getListener() {
		return listener;
	}

	public void setListener(PortletDisplayListener listener) {
		this.listener = listener;
	}

	public void setPortletConfiguration(PortletConfiguration portletConfiguration) {
		this.portletConfiguration = portletConfiguration;
	}

	public void setImageLayout(@IdRes int imageLayout) {
		this.imageLayout = imageLayout;
	}

	public void setVideoLayout(int videoLayout) {
		this.videoLayout = videoLayout;
	}

	public void setAudioLayout(int audioLayout) {
		this.audioLayout = audioLayout;
	}

	public void setPdfLayout(int pdfLayout) {
		this.pdfLayout = pdfLayout;
	}

	private void getDLFileScreenletLayouts() {
		layouts.put(ImageDisplayScreenlet.class.getName(), imageLayout);
		layouts.put(VideoDisplayScreenlet.class.getName(), videoLayout);
		layouts.put(AudioDisplayScreenlet.class.getName(), audioLayout);
		layouts.put(PdfDisplayScreenlet.class.getName(), pdfLayout);
	}

	private void handleInternal(String namespace, String body) {
		if (namespace.endsWith("listPortlets")) {
			String[] portlets = body.split(",");
//
//			for (String portlet : portlets) {
//				InjectableScript js = listener.jsForPortlet(portlet);
//				InjectableScript css = listener.cssForPortlet(portlet);
//
//				String fileName = getLayoutTheme() + "_" + portlet;
//
//				if (js == null) {
//					js = new JsScript(new AssetReader(getContext()).read(fileName));
//				}
//
//				if (css == null) {
//					css = new CssScript(new AssetReader(getContext()).read(fileName));
//				}
//
//				JavascriptInjector javascriptInjector = new JavascriptInjector(getContext());
//
//				if (!"".equals(js.getContent())) {
//					javascriptInjector.addJs(js.getContent(), true);
//				}
//
//				if (!"".equals(css.getContent())) {
//					javascriptInjector.addCss(css.getContent());
//				}
//
//				getViewModel().injectJavascript(javascriptInjector.generateInjectableJs());
//			}
		}
	}
}
