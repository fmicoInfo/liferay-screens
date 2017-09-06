using System;
using Android.App;
using Android.OS;
using Com.Liferay.Mobile.Screens.Comment;
using Com.Liferay.Mobile.Screens.Comment.Display;
using Java.Lang;

namespace ShowcaseAndroid
{
    [Activity]
    public class CommentDisplayActivity : Activity, ICommentDisplayListener
    {

        private CommentDisplayScreenlet commentDisplayScreenlet;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.CommentDisplayView);

            commentDisplayScreenlet = (CommentDisplayScreenlet)FindViewById(Resource.Id.comment_display_screenlet);
        }

		public void Error(Java.Lang.Exception p0, string p1)
		{
			System.Diagnostics.Debug.WriteLine($"Comment display error: {p0}");
		}

		public void OnDeleteCommentSuccess(CommentEntry p0)
		{
			System.Diagnostics.Debug.WriteLine($"Comment display success: {p0}");
		}

		public void OnLoadCommentSuccess(CommentEntry p0)
		{
			System.Diagnostics.Debug.WriteLine($"Comment display load: {p0}");
		}

		public void OnUpdateCommentSuccess(CommentEntry p0)
		{
			System.Diagnostics.Debug.WriteLine($"Comment display update: {p0}");
		}
    }
}
