/*********************************************************************
* Copyright (c) 2022 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   Kentyou - initial implementation
**********************************************************************/
package org.eclipse.sensinact.prototype.command.impl;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sensinact.model.core.Metadata;
import org.eclipse.sensinact.model.core.Provider;
import org.eclipse.sensinact.model.core.Service;
import org.eclipse.sensinact.prototype.command.SensinactProvider;
import org.eclipse.sensinact.prototype.command.SensinactResource;
import org.eclipse.sensinact.prototype.command.SensinactService;
import org.eclipse.sensinact.prototype.command.TimedValue;
import org.eclipse.sensinact.prototype.model.ResourceType;
import org.eclipse.sensinact.prototype.model.ValueType;
import org.eclipse.sensinact.prototype.model.nexus.impl.ModelNexus;
import org.eclipse.sensinact.prototype.notification.NotificationAccumulator;
import org.osgi.util.promise.Promise;
import org.osgi.util.promise.PromiseFactory;

public class SensinactResourceImpl extends CommandScopedImpl implements SensinactResource {

    private final String name;
    private final SensinactService service;
    private final Class<?> type;
    private final ModelNexus modelNexus;
    private final PromiseFactory promiseFactory;

    public SensinactResourceImpl(AtomicBoolean active, SensinactService service, String name, Class<?> type,
            NotificationAccumulator accumulator, ModelNexus nexusImpl, PromiseFactory promiseFactory) {
        super(active);
        this.service = service;
        this.name = name;
        this.type = type;
        this.modelNexus = nexusImpl;
        this.promiseFactory = promiseFactory;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public ValueType getValueType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResourceType getResourceType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Class<?>> getArguments() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isExclusivelyOwned() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAutoDelete() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Promise<Void> setValue(Object value, Instant timestamp) {
        checkValid();

        SensinactService service = getService();
        SensinactProvider provider = service.getProvider();

        modelNexus.handleDataUpdate(provider.getModelName(), provider.getName(), service.getName(), getName(), getType(),
                value, timestamp);
        return promiseFactory.resolved(null);
    }

    @Override
    public Promise<TimedValue<?>> getValue() {
        checkValid();

        final SensinactProvider snProvider = service.getProvider();
        final Provider provider = modelNexus.getProvider(snProvider.getModelName(), snProvider.getName());
        if (provider == null) {
            return null;
        }

        final EStructuralFeature svcFeature = provider.eClass().getEStructuralFeature(service.getName());
        if (svcFeature == null) {
            return null;
        }
        final Service svc = (Service) provider.eGet(svcFeature);

        final EStructuralFeature rcFeature = svc.eClass().getEStructuralFeature(name);
        if (rcFeature == null) {
            // No value
            return promiseFactory.resolved(new TimedValueImpl<Object>(null, null));
        }

        // Get the resource metadata
        final Metadata metadata = svc.getMetadata().get(rcFeature);
        final Instant timestamp;
        if (metadata != null) {
            timestamp = metadata.getTimestamp();
        } else {
            timestamp = null;
        }

        return promiseFactory.resolved(new TimedValueImpl<Object>(svc.eGet(rcFeature), timestamp));
    }

    @Override
    public SensinactService getService() {
        return service;
    }

    @Override
    public Promise<Void> setMetadataValue(String name, Object value, Instant timestamp) {
        checkValid();

        final SensinactProvider provider = service.getProvider();
        try {
            modelNexus.setResourceMetadata(provider.getModelName(), provider.getName(), service.getName(), this.name,
                    name, value, timestamp);
            return promiseFactory.resolved(null);
        } catch (Throwable t) {
            return promiseFactory.failed(t);
        }
    }

    @Override
    public Promise<Object> getMetadataValue(String name) {
        checkValid();

        final SensinactProvider provider = service.getProvider();
        final Map<String, Object> resourceMetadata = modelNexus.getResourceMetadata(provider.getModelName(),
                provider.getName(), service.getName(), name);
        if (resourceMetadata == null) {
            return promiseFactory.failed(new IllegalArgumentException("Resource not found"));
        } else {
            return promiseFactory.resolved(resourceMetadata.get(name));
        }
    }

    @Override
    public Promise<Map<String, Object>> getMetadataValues() {
        checkValid();

        final SensinactProvider provider = service.getProvider();
        final Map<String, Object> resourceMetadata = modelNexus.getResourceMetadata(provider.getModelName(),
                provider.getName(), service.getName(), name);
        if (resourceMetadata == null) {
            return promiseFactory.failed(new IllegalArgumentException("Resource not found"));
        } else {
            return promiseFactory.resolved(resourceMetadata);
        }
    }

}
