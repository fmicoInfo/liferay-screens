using System;
using UIKit;
using LiferayScreens;
using Foundation;

namespace ShowcaseiOS.ViewController
{
    public partial class CommentListViewController : UIViewController, ICommentListScreenletDelegate
    {
        public CommentListViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            commentListScreenlet.ClassPK = 54473;
            commentListScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";
            commentListScreenlet.Editable = true;
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }

		[Export("screenlet:onCommentDelete:onError:")]
        public virtual void OnCommentDelete(CommentListScreenlet screenlet, Comment comment, NSError error){
			System.Diagnostics.Debug.WriteLine("Comment delete");
		}

		[Export("screenlet:onCommentListError:")]
        public virtual void OnCommentListError(CommentListScreenlet screenlet, NSError error){
			System.Diagnostics.Debug.WriteLine("Comment List error");
		}

		[Export("screenlet:onCommentUpdate:onError:")]
        public virtual void OnCommentUpdate(CommentListScreenlet screenlet, Comment comment, NSError error){
			System.Diagnostics.Debug.WriteLine("Comment update");
		}

		[Export("screenlet:onDeletedComment:")]
        public virtual void OnDeletedComment(CommentListScreenlet screenlet, Comment comment){
			System.Diagnostics.Debug.WriteLine("Comment Add error");
		}

		[Export("screenlet:onListResponseComments:")]
        public virtual void OnListResponseComments(CommentListScreenlet screenlet, Comment[] comments){
			System.Diagnostics.Debug.WriteLine("Comment response");
		}

		[Export("screenlet:onSelectedComment:")]
        public virtual void OnSelectedComment(CommentListScreenlet screenlet, Comment comment){
			System.Diagnostics.Debug.WriteLine("Comment selected");
		}

		[Export("screenlet:onUpdatedComment:")]
        public virtual void OnUpdatedComment(CommentListScreenlet screenlet, Comment comment){
			System.Diagnostics.Debug.WriteLine("Comment updated");
		}
    }
}

