using System;

using UIKit;
using LiferayScreens;
using Foundation;

namespace ShowcaseiOS.ViewController
{
    public partial class CommentAddViewController : UIViewController, ICommentAddScreenletDelegate
    {
        public CommentAddViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.commentAddScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";
            this.commentAddScreenlet.ClassPK = 57177;
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }

		[Export("screenlet:onAddCommentError:")]
        public virtual void OnAddCommentError(CommentAddScreenlet screenlet, NSError error)
        {
			System.Diagnostics.Debug.WriteLine("Comment Add error");
		}

		[Export("screenlet:onCommentAdded:")]
        public virtual void OnCommentAdded(CommentAddScreenlet screenlet, Comment comment)
        {
			System.Diagnostics.Debug.WriteLine("Comment Add added");
		}

		[Export("screenlet:onCommentUpdated:")]
        public virtual void OnCommentUpdated(CommentAddScreenlet screenlet, Comment comment)
        {
			System.Diagnostics.Debug.WriteLine("Comment Add updated");
		}

		[Export("screenlet:onUpdateCommentError:")]
        public virtual void OnUpdateCommentError(CommentAddScreenlet screenlet, NSError error)
        {
			System.Diagnostics.Debug.WriteLine("Comment Add Updated error");

		}

    }
}

