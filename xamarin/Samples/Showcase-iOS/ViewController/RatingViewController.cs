using System;
using UIKit;
using LiferayScreens;
using Foundation;

namespace ShowcaseiOS.ViewController
{
    public partial class RatingViewController : UIViewController, IRatingScreenletDelegate
    {
        public RatingViewController(IntPtr handle) : base(handle) { }

        public override void ViewDidLoad()
        {
            base.ViewDidLoad();

			this.ratingScreenlet.ClassName = "com.liferay.document.library.kernel.model.DLFileEntry";
			this.ratingScreenlet.ClassPK = 57177;
		}

        public override void DidReceiveMemoryWarning()
        {
            base.DidReceiveMemoryWarning();
        }

		[Export("screenlet:onRatingDeleted:")]
        public virtual void OnRatingDeleted(RatingScreenlet screenlet, RatingEntry rating){
            System.Diagnostics.Debug.WriteLine($"Rating delete: {screenlet}");
		}

		[Export("screenlet:onRatingError:")]
        public virtual void OnRatingError(RatingScreenlet screenlet, NSError error){
			System.Diagnostics.Debug.WriteLine($"Rating error: {error.Description}");
		}

		[Export("screenlet:onRatingRetrieve:")]
        public virtual void OnRatingRetrieve(RatingScreenlet screenlet, RatingEntry rating){
            System.Diagnostics.Debug.WriteLine($"Rating retrive: {screenlet}");
		}

		[Export("screenlet:onRatingUpdated:")]
        public virtual void OnRatingUpdated(RatingScreenlet screenlet, RatingEntry rating){
            System.Diagnostics.Debug.WriteLine($"Rating updated: {screenlet}");
		}
    }
}

