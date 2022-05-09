/*********************************************************************
* Copyright (c) 2021 Kentyou and others
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.sensinact.gateway.core.method;

import org.json.JSONObject;

/**
 * Extended {@link AccessMethodResponseBuilder} dedicated to
 * {@link SubscribeMethod} execution
 * 
 * @author <a href="mailto:christophe.munilla@cea.fr">Christophe Munilla</a>
 */
@SuppressWarnings("serial")
public class SubscribeResponseBuilder extends AccessMethodResponseBuilder<JSONObject, SubscribeResponse> {
	/**
	 * @param uri
	 * @param parameters
	 */
	public SubscribeResponseBuilder(String uri, Object[] parameters) {
		super(uri, parameters);
	}

	/**
	 * @inheritDoc
	 *
	 * @see AccessMethodResponseBuilder#
	 *      createAccessMethodResponse(org.eclipse.sensinact.gateway.core.model.message.SnaMessage.Status)
	 */
	@Override
	public SubscribeResponse createAccessMethodResponse(AccessMethodResponse.Status status) {
		return new SubscribeResponse(super.getPath(), status);
	}

	/**
	 * @inheritDoc
	 *
	 * @see org.eclipse.sensinact.gateway.core.method.AccessMethodResponseBuilder#getComponentType()
	 */
	@Override
	public Class<JSONObject> getComponentType() {
		return JSONObject.class;
	}
}
