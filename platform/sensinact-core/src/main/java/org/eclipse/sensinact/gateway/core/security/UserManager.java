/*
 * Copyright (c) 2017 CEA.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    CEA - initial API and implementation
 */
package org.eclipse.sensinact.gateway.core.security;

import org.eclipse.sensinact.gateway.datastore.api.DataStoreException;

/**
 * 
 *
 * @author <a href="mailto:christophe.munilla@cea.fr">Christophe Munilla</a>
 */
public interface UserManager {
	public static final long ANONYMOUS_ID = 0L;
	public static final String ANONYMOUS_PKEY = "anonymous";

	/**
	 * Returns true if the String login passed as parameter already exists in the
	 * system as one of an registered user; otherwise returns false
	 * 
	 * @param login
	 *            the String login
	 * 
	 * @return
	 *         <ul>
	 *         <li>true if the login already exists</li>
	 *         <li>false otherwise</li>
	 *         </ul>
	 * @throws DataStoreException
	 */
	boolean loginExists(String login) throws SecuredAccessException, DataStoreException;

	/**
	 * Returns true if the String account endpoint passed as parameter with the
	 * specified type already exists in the system as one of an registered user;
	 * otherwise returns false
	 * 
	 * @param accouype
	 *            the String endpoint of the account
	 * 
	 * @return
	 *         <ul>
	 *         <li>true if the account with the specified type already exists</li>
	 *         <li>false otherwise</li>
	 *         </ul>
	 * @throws DAOException
	 * @throws SecuredAccessException
	 * @throws DataStoreException
	 */
	boolean accountExists(String account) throws SecuredAccessException, DataStoreException;

	/**
	 * Retrieves and returns the {@link User} whose Strings login and password are
	 * passed as parameter
	 * 
	 * @param login
	 *            the String login of the {@link User} to be returned
	 * @param password
	 *            the String password of the {@link User} to be returned
	 * 
	 * @return the {@link User} for the specified login and password
	 * @throws DataStoreException
	 */
	User getUser(String login, String password) throws SecuredAccessException, DataStoreException;

	/**
	 * Asks for the creation of a new user and returns the {@link UserUpdater}
	 * holding the creation. The validation of the {@link UserUpdater} by the
	 * appropriate String token will result in the affective user creation.
	 * 
	 * @param login
	 *            the String login of the user to be created
	 * @param password
	 *            the String password of the user to be created
	 * @param account
	 *            the String account endpoint used to validate the request
	 * 
	 * @return the {@link UserUpdater} holding the user creation
	 */
	UserUpdater createUser(String login, String password, String account) throws SecuredAccessException;

	/**
	 * Asks for the update of the password of the user whose account endpoint is
	 * passed as parameter and returns the {@link UserUpdater} holding the update.
	 * The validation of the {@link UserUpdater} by the appropriate String token
	 * will result in the effective password update.
	 * 
	 * @param account
	 *            the String account endpoint of the user for who to update the
	 *            password
	 * @param newPassword
	 *            the new String password to be to defined
	 * 
	 * @return the {@link UserUpdater} holding the password update
	 */
	UserUpdater updateUserPassword(String account, String newPassword) throws SecuredAccessException;

	/**
	 * @param publicKey
	 * @return
	 * @throws DataStoreException
	 */
	User getUserFromPublicKey(String publicKey) throws SecuredAccessException, DataStoreException;

	/**
	 * @param account
	 * @return
	 * @throws SecuredAccessException
	 * @throws DataStoreException
	 */
	User getUserFromAccount(String account) throws SecuredAccessException, DataStoreException;
}
