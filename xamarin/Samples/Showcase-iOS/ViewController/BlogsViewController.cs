using System;
using UIKit;
using Foundation;
using LiferayScreens;

namespace ShowcaseiOS.ViewController
{
    public partial class BlogsViewController : UIViewController, IBlogsEntryDisplayScreenletDelegate
    {
        public BlogsViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.blogsEntryDisplayScreenlet.ClassPK = 57366;
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }

		[Export("screenlet:onBlogEntryError:")]
        public virtual void OnBlogEntryError(BlogsEntryDisplayScreenlet screenlet, NSError error){
            System.Diagnostics.Debug.WriteLine("Blog entry error");
		}

		[Export("screenlet:onBlogEntryResponse:")]
        public virtual void OnBlogEntryResponse(BlogsEntryDisplayScreenlet screenlet, BlogsEntry blogEntry){
			System.Diagnostics.Debug.WriteLine("Blog entry response");
		}

    }
}

