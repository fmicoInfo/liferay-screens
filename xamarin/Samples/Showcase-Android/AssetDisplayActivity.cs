using Android.App;
using Android.OS;
using Com.Liferay.Mobile.Screens.Asset;
using Com.Liferay.Mobile.Screens.Asset.Display;

namespace ShowcaseAndroid
{
    [Activity]
    public class AssetDisplayActivity : Activity, IAssetDisplayListener
    {

        private AssetDisplayScreenlet assetDisplayScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.AssetDisplayView);

            assetDisplayScreenlet = (AssetDisplayScreenlet)FindViewById(Resource.Id.asset_display_screenlet);
            assetDisplayScreenlet.SetListener(this);
        }

		public void Error(Java.Lang.Exception p0, string p1)
		{
			System.Diagnostics.Debug.WriteLine($"Asset display error: {p0}");
		}

		public void OnRetrieveAssetSuccess(AssetEntry p0)
		{
			System.Diagnostics.Debug.WriteLine($"Asset List succes: {p0}");
		}

    }
}
