package com.liferay.mobile.screens.westerosemployees_hybrid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.portlet.PortletConfiguration;
import com.liferay.mobile.screens.portlet.PortletDisplayListener;
import com.liferay.mobile.screens.portlet.PortletDisplayScreenlet;
import com.liferay.mobile.screens.portlet.util.InjectableScript;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.westerosemployees_hybrid.R;

import java.util.List;


public class UserActivity extends WesterosActivity implements View.OnClickListener, BaseListListener<AssetEntry>, PortletDisplayListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user);

		bindViews();
	}

	private void bindViews() {
		TextView userNameTextView = (TextView) findViewById(R.id.liferay_username);
		userNameTextView.setOnClickListener(this);

		UserPortraitScreenlet userPortraitScreenlet = (UserPortraitScreenlet) findViewById(R.id.userscreenlet_home);
		userPortraitScreenlet.setOnClickListener(this);
		userPortraitScreenlet.loadLoggedUserPortrait();

        loadLastChanges();

		userNameTextView.setText(SessionContext.getCurrentUser().getFullName());
    }

    private void loadLastChanges() {
        PortletDisplayScreenlet portletDisplayScreenlet = (PortletDisplayScreenlet) findViewById(R.id.portlet_last_changes);
        PortletConfiguration configuration = new PortletConfiguration.Builder("/web/guest/lastchanges").addRawCss(R.raw.last_changes_portlet_css).addRawJs(R.raw.last_changes_portlet_js).load();

        portletDisplayScreenlet.setPortletConfiguration(configuration);
        portletDisplayScreenlet.load();

        portletDisplayScreenlet.setListener(this);
    }

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		startActivity(new Intent(this, UserProfileActivity.class));
	}

	@Override
	public void onListPageFailed(int startRow, Exception e) {

	}

	@Override
	public void onListPageReceived(int startRow, int endRow, List<AssetEntry> entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(AssetEntry element, View view) {
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
		if("last-changes-item".equals(namespace)) {
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(UserActivity.this, ModalDetailActivity.class);
					intent.putExtra("id", body);
					startActivity(intent);
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