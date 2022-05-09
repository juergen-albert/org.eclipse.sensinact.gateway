/*********************************************************************
* Copyright (c) 2021 Kentyou and others
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.sensinact.gateway.generic.stream;

import org.eclipse.sensinact.gateway.generic.Task;

/**
 * Extended {@link Task} dedicated to STREAM typed tasks
 *
 * @author <a href="mailto:christophe.munilla@cea.fr">Christophe Munilla</a>
 */
public interface StreamTask extends Task {
    public static final Task.RequestType REQUEST_TYPE = Task.RequestType.STREAM;

    /**
     * Returns the payload of the frame command to send as
     * a bytes array
     *
     * @return the payload of the frame command to send as
     * a bytes array
     */
    byte[] getPayloadBytesArray();
}
