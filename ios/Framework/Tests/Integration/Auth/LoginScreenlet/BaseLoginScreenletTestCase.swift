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
import XCTest

@objc class TestLoginScreenletDelegate: NSObject, LoginScreenletDelegate {
	var onCompleted: ((AnyObject) -> Void)?
	var onCredentialsStored: (() -> Void)?

	var credentialsSavedCalled = false
	var credentialsLoadedCalled = false

	var strongLoop: TestLoginScreenletDelegate?

	init(completed: ((AnyObject) -> Void)? = nil) {
		self.onCompleted = completed

		super.init()

		self.strongLoop = self
	}

	func screenlet(_ screenlet: BaseScreenlet,
			onLoginResponseUserAttributes attributes: [String:AnyObject]) {
		onCompleted?(attributes as AnyObject)
		self.strongLoop = nil
	}

	func screenlet(_ screenlet: BaseScreenlet,
			onLoginError error: NSError) {
		onCompleted?(error)
		self.strongLoop = nil
	}

	func screenlet(_ screenlet: BaseScreenlet,
			onCredentialsSavedUserAttributes attributes: [String:AnyObject]) {
		credentialsSavedCalled = true
		onCredentialsStored?()
	}

	func screenlet(_ screenlet: LoginScreenlet,
			onCredentialsLoadedUserAttributes attributes: [String:AnyObject]) {
		credentialsLoadedCalled = true
	}

}

class BaseLoginScreenletTestCase: IntegrationTestCase {

	var screenlet: LoginScreenlet?

	override func setUp() {
		super.setUp()

		screenlet = LoginScreenlet(frame: CGRect.zero)
		screenlet!.themeName = "test"
		screenlet!.loadScreenletView()
	}

}
