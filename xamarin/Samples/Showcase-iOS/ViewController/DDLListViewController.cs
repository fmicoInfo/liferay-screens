using System;
using UIKit;
using Foundation;
using LiferayScreens;
using System.Diagnostics;

namespace ShowcaseiOS.ViewController
{
    public partial class DDLListViewController : UIViewController, IDDLListScreenletDelegate
    {
        public DDLListViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

			this.ddlListScreenlet.RecordSetId = 33280;
            this.ddlListScreenlet.LabelFields = "title";
            this.ddlListScreenlet.Delegate = this;
        }

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }

        [Export("screenlet:onDDLListError:")]
        public virtual void OnDDLListError(DDLListScreenlet screenlet, NSError error){
			Debug.WriteLine($"DDL list error: {error}");
		}

		[Export("screenlet:onDDLListResponseRecords:")]
        public virtual void OnDDLListResponseRecords(DDLListScreenlet screenlet, DDLRecord[] records){
            Debug.WriteLine($"DDL response records: {screenlet}");
		}

		[Export("screenlet:onDDLSelectedRecord:")]
        public virtual void OnDDLSelectedRecord(DDLListScreenlet screenlet, DDLRecord record){
            Debug.WriteLine($"DDL select record: {screenlet}");
		}
    }
}

