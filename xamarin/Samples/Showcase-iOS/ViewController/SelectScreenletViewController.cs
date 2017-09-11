using System;
using UIKit;
using ShowcaseiOS.SelectScreenlets;

namespace ShowcaseiOS.ViewController
{
    public partial class SelectScreenletViewController : UITableViewController
    {
        protected string[] screenlets = 
        { 
            "DDLFormScreenlet", 
            "UserPortraitScreenlet",
            "WebContentScreenlet",
            "AssetListScreenlet",
            "AssetDisplayScreenlet",
            "PdfDisplayScreenlet",
            "AudioDisplayScreenlet",
            "VideoDisplayScreenlet",
            "CommentAddScreenlet",
            "CommentListScreenlet",
			"WebContentListScreenlet",
        };

        protected string[] viewControllers = 
        { 
            "DDLFormViewController",
            "UserPortraitViewController",
            "WebContentViewController",
            "AssetListViewController",
            "AssetDisplayViewController",
            "PdfDisplayViewController",
            "AudioDisplayViewController",
            "VideoDisplayViewController",
            "CommentAddViewController",
            "CommentListViewController",
            "WebcontentListViewController",
        };

        public SelectScreenletViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

            TableView.Source = new ScreenletsTableSource(screenlets, viewControllers, this);
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }
    }
}

