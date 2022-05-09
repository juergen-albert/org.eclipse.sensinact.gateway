/*********************************************************************
* Copyright (c) 2021 Kentyou and others
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.sensinact.gateway.generic.test.tb.moke;

import org.eclipse.sensinact.gateway.generic.ExtModelInstance;
import org.eclipse.sensinact.gateway.generic.ExtResourceConfig;
import org.eclipse.sensinact.gateway.generic.ExtResourceImpl;
import org.eclipse.sensinact.gateway.generic.ExtServiceImpl;
import org.eclipse.sensinact.gateway.generic.annotation.Act;
import org.json.JSONObject;

/**
 *
 */
public class MokeAction extends ExtResourceImpl {
    /**
     * @param resourceConfig
     * @param service
     */
    protected MokeAction(ExtModelInstance<?> snaModelInstance, ExtResourceConfig resourceConfig, ExtServiceImpl service) {
        super(snaModelInstance, resourceConfig, service);
    }

    @Act
    JSONObject act(String number, String message) {

        JSONObject jsonObject = new JSONObject().put("message", number + " called : " + message);

        return jsonObject;
    }

    @Act
    public JSONObject act() {
        JSONObject jsonObject = new JSONObject().put("message", "empty call");

        return jsonObject;
    }
}
