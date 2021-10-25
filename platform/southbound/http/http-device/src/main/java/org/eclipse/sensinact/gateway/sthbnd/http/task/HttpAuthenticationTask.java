/*
* Copyright (c) 2020 Kentyou.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
*    Kentyou - initial API and implementation
 */
package org.eclipse.sensinact.gateway.sthbnd.http.task;

import org.eclipse.sensinact.gateway.common.bundle.Mediator;
import org.eclipse.sensinact.gateway.common.execution.Executable;
import org.eclipse.sensinact.gateway.core.method.AccessMethod;
import org.eclipse.sensinact.gateway.protocol.http.client.Request;
import org.eclipse.sensinact.gateway.sthbnd.http.HttpProtocolStackEndpoint;
import org.eclipse.sensinact.gateway.sthbnd.http.HttpResponse;
import org.eclipse.sensinact.gateway.util.CastUtils;

/**
 * Extended {@link HttpTask} dedicated to discovery process
 *
 * @author <a href="mailto:christophe.munilla@cea.fr">Christophe Munilla</a>
 */
public class HttpAuthenticationTask<RESPONSE extends HttpResponse, REQUEST extends Request<RESPONSE>> extends HttpDiscoveryTask<RESPONSE, REQUEST> {
    private static final String AUTHENTICATION_HEADER_KEY = "X-Auth-Token";

    private String authenticationHeaderKey;
    private Executable<Object, String> tokenExtractor;

    /**
     * Constructor
     *
     * @param mediator    the {@link Mediator} allowing to interact with
     *                    the OSGi host environment
     * @param transmitter the {@link HttpProtocolStackEndpoint} transmitting
     *                    the requests build by the HttpDiscoveryTask to instantiate
     * @param requestType the extended {@link HttpRequest} type handled
     *                    by this HttpDiscoveryConnectionConfiguration
     */
    public HttpAuthenticationTask(Mediator mediator, HttpProtocolStackEndpoint transmitter, Class<REQUEST> requestType) {
        super(mediator, transmitter, requestType);
    }

    /**
     * @inheritDoc
     * @see HttpTask#isDirect()
     */
    public boolean isDirect() {
        return true;
    }

    /**
     * Defines the {@link Executable} used to extract the
     * authentication token from this task's result object.
     * If no extractor is defined the result object is cast
     * into String and used as is.
     *
     * @param tokenExtractor the {@link Executable} in charge
     *                       of extracting the string authentication token
     */
    public void registerTokenExtractor(Executable<Object, String> tokenExtractor) {
        this.tokenExtractor = tokenExtractor;
    }

    /**
     * Defines the authentication header key to use
     * when registering it as a permanent header into the
     * {@link HttpProtocolStackEndpoint}
     *
     * @param authenticationHeaderKey the authentication header
     *                                key to be used
     */
    public void setAuthenticationHeaderKey(String authenticationHeaderKey) {
        this.authenticationHeaderKey = authenticationHeaderKey;
    }

    /**
     * Registers the String token passed as parameter as value
     * of an authentication permanent header
     *
     * @param token the String token to be registered
     */
    private void registerAuthenticationHeader(String token) {
        if (token == null || token.length() == 0) {
            return;
        }
        String key = null;
        if ((key = this.authenticationHeaderKey) == null) {
            key = HttpAuthenticationTask.AUTHENTICATION_HEADER_KEY;
        }
        ((HttpProtocolStackEndpoint) super.transmitter).registerPermanentHeader(key, token);
    }

    /**
     * @inheritDoc
     * @see TaskImpl#
     * setResult(java.lang.Object, long)
     */
    public void setResult(Object result, long timestamp) {
        super.setResult(result, timestamp);
        if (super.result != null && super.result == AccessMethod.EMPTY) {
            return;
        }
        String value = null;
        try {
            value = this.tokenExtractor == null ? CastUtils.cast(String.class, result) : this.tokenExtractor.execute(result);
        } catch (Exception e) {
            this.mediator.error(e);
        }
        this.registerAuthenticationHeader(value);
    }
}
