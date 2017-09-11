using System;
using UIKit;
using Foundation;
using LiferayScreens;
using System.Diagnostics;

namespace ShowcaseiOS.ViewController
{
    public partial class WebcontentListViewController : UIViewController, IWebContentListScreenletDelegate
    {
        public WebcontentListViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            this.webContentListScreenlet.FolderId = 0;
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }

		[Export("screenlet:onWebContentListError:")]
        public virtual void OnWebContentListError(WebContentListScreenlet screenlet, NSError error){
            Debug.WriteLine($"Webcontent error: {screenlet}");
		}

		[Export("screenlet:onWebContentListResponse:")]
        public virtual void OnWebContentListResponse(WebContentListScreenlet screenlet, WebContent[] contents){
			Debug.WriteLine($"Webcontent response: {screenlet}");
		}

		[Export("screenlet:onWebContentSelected:")]
        public virtual void OnWebContentSelected(WebContentListScreenlet screenlet, WebContent content){
			Debug.WriteLine($"Webcontent selected: {screenlet}");
		}
    }
}

