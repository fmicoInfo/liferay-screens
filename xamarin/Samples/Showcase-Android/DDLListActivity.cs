using System.Collections;
using Android.App;
using Android.OS;
using Android.Views;
using Com.Liferay.Mobile.Screens.Base.List;
using Com.Liferay.Mobile.Screens.Ddl.List;

namespace ShowcaseAndroid
{
    [Activity]
    public class DDLListActivity : Activity, IBaseListListener
    {

        private DDLListScreenlet ddlListScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.DDLListView);

            ddlListScreenlet = (DDLListScreenlet)FindViewById(Resource.Id.ddl_list_screenlet);
            ddlListScreenlet.Listener = this;
	
        }

		public void Error(Java.Lang.Exception p0, string p1)
		{
			System.Diagnostics.Debug.WriteLine($"DDL List error: {p0}");
		}

		public void OnListItemSelected(Java.Lang.Object p0, View p1)
		{
			System.Diagnostics.Debug.WriteLine($"Item selected: {p0}");
		}

		public void OnListPageFailed(int p0, Java.Lang.Exception p1)
		{
			System.Diagnostics.Debug.WriteLine($"List page failed: {p1}");
		}

		public void OnListPageReceived(int p0, int p1, IList p2, int p3)
		{
			System.Diagnostics.Debug.WriteLine($"List page received");
		}
    }
}
