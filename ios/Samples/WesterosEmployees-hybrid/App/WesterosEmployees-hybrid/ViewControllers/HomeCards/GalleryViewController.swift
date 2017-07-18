/**
* Copyright (c) 2000-present Liferay, Inc. All rights reserved.
*
* This library is free software; you can redistribute it and/or modify it under
* the terms of the GNU Lesser General Public License as published by the Free
* Software Foundation; either version 2.1 of the License, or (at your option)
* any later version.
*
* This library is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
* details.
*/
import UIKit
import LiferayScreens

class GalleryViewController: CardViewController, PortletDisplayScreenletDelegate,
		CardDeckDataSource, CardDeckDelegate, ImageGalleryScreenletDelegate {

	var selectedImageEntry: String?
    var imageGalleryScreenlet: ImageGalleryScreenlet?

	var uploadImageViewController: UploadImageViewController? {
		didSet {
			addChildViewController(uploadImageViewController!)
			uploadImageViewController?.onImageSelected = self.onImageSelected
		}
	}

	var loaded: Bool = false

	
	//MARK: Outlets

    @IBOutlet weak var portletDisplayScreenlet: PortletDisplayScreenlet!
	

	@IBOutlet weak var cardDeck: CardDeckView? {
		didSet {
			cardDeck?.delegate = self
			cardDeck?.dataSource = self
		}
	}


	//MARK: CardViewController

	override func pageWillDisappear() {
		hideUploadCard()
	}

    func loadPortletScreenlet() {
        let portletConfiguration = PortletConfiguration.Builder(portletUrl: "/web/guest/gallery").addCss(localFile: "gallery").addJs(localFile: "gallery").load()
        portletDisplayScreenlet.configuration = portletConfiguration
        portletDisplayScreenlet.load()
        portletDisplayScreenlet.delegate = self
    }
    
    //MARK: CardViewController
    override func pageWillAppear() {
        if !loaded {
            loadPortletScreenlet()
            loaded = true
        }
    }


	//MARK: UIViewController

	override func viewDidLoad() {
		super.viewDidLoad()
		
        uploadImageViewController = UploadImageViewController()
        uploadImageViewController?.onImageSelected = onImageSelected
	}

	//MARK: Init methods

	convenience init() {
		self.init(nibName: "GalleryViewController", bundle: nil)
        loadImageGalleryScreenlet()
	}
    
    func loadImageGalleryScreenlet() {
        imageGalleryScreenlet = ImageGalleryScreenlet(frame: .zero, themeName: "westeros")
        imageGalleryScreenlet?.delegate = self
        imageGalleryScreenlet?.presentingViewController = self
        
        imageGalleryScreenlet?.repositoryId = LiferayServerContext.groupId
        imageGalleryScreenlet?.folderId =
            LiferayServerContext.longPropertyForKey("galleryFolderId")
        
    }


	//MARK: Private methods

	func hideUploadCard() {
		if let uploadCard = cardDeck?.cards[safe: 0] {
			if uploadCard.pageCount > 1 {
				uploadCard.removePageAtIndex(1)
				uploadCard.moveLeft()
			}
			uploadCard.changeToState(.minimized)
		}
	}

    //MARK: CardDeckDataSource

	func numberOfCardsIn(_ cardDeck: CardDeckView) -> Int {
		return 1
	}

	func cardDeck(_ cardDeck: CardDeckView, titleForCard position: CardPosition) -> String? {
		return "Upload image"
	}

	func cardDeck(_ cardDeck: CardDeckView, controllerForCard position: CardPosition) -> CardViewController? {
		return uploadImageViewController
	}
    
    //MARK: Private methods
    
    func onImageSelected(_ image: UIImage) {
        let title = "westeros-\(UUID().uuidString).png"
        let imageUpload = ImageEntryUpload(image: image, title: title)
        self.imageGalleryScreenlet?.showDetailUploadView(imageUpload)
    }


	//MARK: CardDeckDelegate

	func cardDeck(_ cardDeck: CardDeckView, customizeCard card: CardView, atIndex index: Int) {
		if let firstCardDeck = self.cardView?.superview {
			card.normalHeight = firstCardDeck.frame.height * 0.4
		}
	}

	func cardDeck(_ cardDeck: CardDeckView, colorForCardIndex index: Int) -> UIColor? {
		return DefaultResources.OddColorBackground
	}

	func cardDeck(_ cardDeck: CardDeckView, colorForButtonIndex index: Int) -> UIColor? {
		return DefaultResources.EvenColorBackground
	}

	func cardDeck(_ cardDeck: CardDeckView, buttonImageForCardIndex index: Int) -> UIImage? {
		return UIImage(named: "icon_DOWN_W")
	}
    
    //MARK: ImageGalleryScreenletDelegate
    
    func screenlet(_ screenlet: ImageGalleryScreenlet, onImageUploadDetailViewCreated detailUploadView: ImageUploadDetailViewBase) -> Bool {
        self.cardDeck?.cards[safe: 0]?.addPage(detailUploadView)
        self.cardDeck?.cards[safe: 0]?.moveRight()
        return true
    }
    
    func screenlet(_ screenlet: ImageGalleryScreenlet, onImageUploadStart image: ImageEntryUpload) {
        hideUploadCard()
    }
    
    func screenlet(_ screenlet: ImageGalleryScreenlet, onImageUploaded image: ImageEntry) {
        loadPortletScreenlet()
    }

    
    //MARK: PortletScreenletDelegate
    func screenlet(_ screenlet: PortletDisplayScreenlet,
                   onScriptMessageHandler key: String,
                   onScriptMessageBody body: Any) {
        selectedImageEntry = body as? String
        cardView?.moveRight()
    }
}
