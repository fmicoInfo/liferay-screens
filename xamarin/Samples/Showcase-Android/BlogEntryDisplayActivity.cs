using System;
using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Asset;
using Com.Liferay.Mobile.Screens.Asset.Display;
using Com.Liferay.Mobile.Screens.Blogs;
using Java.Lang;

namespace ShowcaseAndroid
{
    [Activity]
    public class BlogEntryDisplayActivity : Activity, IAssetDisplayListener
    {

        private BlogsEntryDisplayScreenlet blogsEntryDisplayScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.BlogEntryDisplayView);

            blogsEntryDisplayScreenlet = (BlogsEntryDisplayScreenlet)FindViewById(Resource.Id.blog_entry_screenlet);
            blogsEntryDisplayScreenlet.Listener = this;

        }

		public void Error(Java.Lang.Exception p0, string p1)
		{
			System.Diagnostics.Debug.WriteLine($"Blogs entry error: {p0}");
		}

		public void OnRetrieveAssetSuccess(AssetEntry p0)
		{
			Toast.MakeText(this, "Success {p0}", ToastLength.Short).Show();
		}

    }
}
