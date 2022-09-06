package org.eclipse.sensinact.prototype.notification;

import java.util.Map;
import java.util.Objects;

/**
 * Lifecycle notifications are sent to indicate the creation or deletion of a provider/service/resource
 * 
 * Topic name is
 * 
 * LIFECYCLE/&lt;provider&gt;[/&lt;service&gt;[/&lt;resource&gt;]]
 */
public class LifecycleNotification extends AbstractResourceNotification {
	
	public Status status;
	
	public Object initialValue;
	
	public Map<String, Object> initialMetadata;
	
	@Override
	public String getTopic() {
		Objects.requireNonNull(status);
		Objects.requireNonNull(provider);
		int ordinal = status.ordinal();
		if(ordinal >= Status.SERVICE_CREATED.ordinal()) {
			Objects.requireNonNull(service);
		}
		if(ordinal >= Status.RESOURCE_CREATED.ordinal()) {
			Objects.requireNonNull(resource);
		}
		
		return String.format("LIFECYCLE/%s", String.format(status.template, provider, service, resource));
	}

	public enum Status {
		/**
		 * Provider created,
		 * <ul> 
		 * <li>{@link LifecycleNotification#service} will be null</li>
		 * <li>{@link LifecycleNotification#resource} will be null</li>
		 * <li>{@link LifecycleNotification#initialMetadata} will be null</li>
		 * <li>{@link LifecycleNotification#initialValue} will be a List of String service names for initial services</li>
		 * </ul>
		 */
		PROVIDER_CREATED("%s"), 
		
		/**
		 * Provider deleted,
		 * <ul> 
		 * <li>{@link LifecycleNotification#service} will be null</li>
		 * <li>{@link LifecycleNotification#resource} will be null</li>
		 * <li>{@link LifecycleNotification#initialMetadata} will be null</li>
		 * <li>{@link LifecycleNotification#initialValue} will be null</li>
		 * </ul>
		 */
		PROVIDER_DELETED("%s"), 
		
		/**
		 * Service created,
		 * <ul> 
		 * <li>{@link LifecycleNotification#resource} will be null</li>
		 * <li>{@link LifecycleNotification#initialMetadata} will be null</li>
		 * <li>{@link LifecycleNotification#initialValue} will be a List of String service names for initial resources</li>
		 * </ul>
		 */
		SERVICE_CREATED("%s/%s"), 
		
		/**
		 * Service deleted,
		 * <ul> 
		 * <li>{@link LifecycleNotification#resource} will be null</li>
		 * <li>{@link LifecycleNotification#initialMetadata} will be null</li>
		 * <li>{@link LifecycleNotification#initialValue} will be null</li>
		 * </ul>
		 */
		SERVICE_DELETED("%s/%s"), 
		
		/**
		 * Resource created,
		 * <ul> 
		 * <li>{@link LifecycleNotification#initialMetadata} will be the initial metadata</li>
		 * <li>{@link LifecycleNotification#initialValue} will be the initial value</li>
		 * </ul>
		 */
		RESOURCE_CREATED("%s/%s/%s"), 
		
		/**
		 * Resource created,
		 * <ul> 
		 * <li>{@link LifecycleNotification#initialMetadata} will be the initial metadata</li>
		 * <li>{@link LifecycleNotification#initialValue} will be the initial value</li>
		 * </ul>
		 */
		RESOURCE_DELETED("%s/%s/%s");
		
		private final String template;
		
		private Status(String template) {
			this.template = template;
		}
	}
}
