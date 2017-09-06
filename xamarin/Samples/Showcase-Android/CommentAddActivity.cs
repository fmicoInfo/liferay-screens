using System;
using Android.App;
using Android.OS;
using Android.Widget;
using Com.Liferay.Mobile.Screens.Comment;
using Com.Liferay.Mobile.Screens.Comment.Add;
using Java.Lang;

namespace ShowcaseAndroid
{
    [Activity(Label = "CommentAddActivity")]
    public class CommentAddActivity : Activity, ICommentAddListener
    {

        private CommentAddScreenlet commentAddScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.CommentAddView);

            commentAddScreenlet = (CommentAddScreenlet) FindViewById(Resource.Id.comment_add_screenlet);
            commentAddScreenlet.Listener = this;

        }


		public void Error(Java.Lang.Exception p0, string p1)
		{
			System.Diagnostics.Debug.WriteLine($"Comment add error: {p0}");
		}

		public void OnAddCommentSuccess(CommentEntry p0)
		{
            Toast.MakeText(this, "Success {p0}", ToastLength.Short).Show();
        }

    }
}
