/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.portlet.view;

import android.view.View;
import com.liferay.mobile.screens.base.view.BaseViewModel;

/**
 * @author Sarai Díaz García
 */
public interface PortletDisplayViewModel extends BaseViewModel {

	/**
	 * Called when portlet screenlet is ready to be displayed.
	 *
	 * @param url portlet url.
	 * @param injectedJs custom Javascript to use in the portlet.
	 */
	void showFinishOperation(String url, String injectedJs, String injectedCss);

	/**
	 * Called when asset child screenlet is ready to be displayed.
	 *
	 * @param view screenlet or custom view.
	 */
	void showFinishOperation(View view);

	/**
	 * Call if the portlet has pagination and we want to increase its size.
	 *
	 * @param biggerPagination true if we want to increase, false otherwise.
	 */
	void setBiggerPagination(boolean biggerPagination);
}