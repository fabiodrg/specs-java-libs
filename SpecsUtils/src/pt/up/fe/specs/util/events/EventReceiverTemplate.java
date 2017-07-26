/**
 * Copyright 2012 SPeCS Research Group.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package pt.up.fe.specs.util.events;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a class that can receive and respond to events.
 * 
 * @author Joao Bispo
 * 
 */
public abstract class EventReceiverTemplate implements EventReceiver {

    protected abstract ActionsMap getActionsMap();

    @Override
    public void acceptEvent(Event event) {
	if (getActionsMap() == null) {
	    return;
	}

	getActionsMap().performAction(event);
    }

    @Override
    public Collection<EventId> getSupportedEvents() {
	if (getActionsMap() == null) {
	    return Collections.emptyList();
	}

	return getActionsMap().getSupportedEvents();
    }
}
