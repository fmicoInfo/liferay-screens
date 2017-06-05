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
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.dlfile.display.audio.AudioDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.image.ImageDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.pdf.PdfDisplayScreenlet;
import com.liferay.mobile.screens.dlfile.display.video.VideoDisplayScreenlet;
import com.liferay.mobile.screens.portlet.interactor.PortletDisplayInteractor;
import com.liferay.mobile.screens.portlet.view.PortletDisplayViewModel;
import com.liferay.mobile.screens.util.AssetReader;
import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * @author Sarai Díaz García
 */

public class PortletDisplayScreenlet extends BaseScreenlet<PortletDisplayViewModel, PortletDisplayInteractor>
	implements PortletDisplayListener {

	private boolean autoLoad;
	private String url;
	private PortletDisplayListener listener;
	private int jsFile;
	private int cssFile;
	private boolean hasBiggerPagination;
	private HashMap<String, Integer> layouts = new HashMap<>();
	private int imageLayout = R.layout.image_display_default;
	private int videoLayout = R.layout.video_display_default;
	private int audioLayout = R.layout.audio_display_default;
	private int pdfLayout = R.layout.pdf_display_default;

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
		if (url != null) {

			String pureJs = AssetReader.read(getContext(), jsFile);
			String js = "";
			if (pureJs != null) {
				js = pureJs.replace("\t", "").replace("\n", "");
			}

			String pureCss = AssetReader.read(getContext(), cssFile);
			String css = "";
			if (pureCss != null) {
				css = pureCss.replace("\t", "").replace("\n", "");
			}

			getViewModel().showFinishOperation(url, js, css);
			getViewModel().setBiggerPagination(hasBiggerPagination);
		} else {
			getViewModel().showFailedOperation(DEFAULT_ACTION, new MalformedURLException());
		}
	}

	public void loadAsset() {
		performUserAction();
	}

	/**
	 * Checks if there is a session created and if exists {@link #url} attribute.
	 * Then calls {@link #load()} method.
	 */
	protected void autoLoad() {
		if (SessionContext.isLoggedIn() && url != null) {
			if (url.contains("documents")) {
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

		getDLFileScreenletLayouts();

		assetDisplayScreenlet.setLayouts(layouts);
		assetDisplayScreenlet.load(assetEntry);

		if (listener != null) {
			listener.onRetrieveAssetSuccess(assetEntry);
		}
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.WebContentDisplayScreenlet, 0, 0);

		autoLoad = typedArray.getBoolean(R.styleable.PortletDisplayScreenlet_autoLoad, true);

		url = typedArray.getString(R.styleable.PortletDisplayScreenlet_url);

		hasBiggerPagination = typedArray.getBoolean(R.styleable.PortletDisplayScreenlet_biggerPagination, true);

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
		interactor.start(url);
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PortletDisplayListener getListener() {
		return listener;
	}

	public void setListener(PortletDisplayListener listener) {
		this.listener = listener;
	}

	public void setJs(int jsFile) {
		this.jsFile = jsFile;
	}

	public void setCss(int cssFile) {
		this.cssFile = cssFile;
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
}