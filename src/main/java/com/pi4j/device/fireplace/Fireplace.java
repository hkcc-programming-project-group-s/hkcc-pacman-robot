package com.pi4j.device.fireplace;

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: GameDevice Abstractions
 * FILENAME      :  Fireplace.java  
 * 
 * This file is part of the Pi4J project. More information about 
 * this project can be found here:  http://www.pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2015 Pi4J
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.pi4j.device.ObserveableDevice;

import java.util.concurrent.TimeUnit;

public interface Fireplace extends ObserveableDevice {

    public FireplaceState getState();

    public void setState(FireplaceState state) throws FireplacePilotLightException;

    public boolean isState(FireplaceState state);

    public boolean isOn();

    public boolean isOff();

    public boolean isPilotLightOn();

    public boolean isPilotLightOff();

    public void on() throws FireplacePilotLightException;

    public void on(long timeoutDelay, TimeUnit timeoutUnit) throws FireplacePilotLightException;

    public void off();

    public void setTimeout(long delay, TimeUnit unit);

    public void cancelTimeout();

    public long getTimeoutDelay();

    public TimeUnit getTimeoutUnit();

    void addListener(FireplaceStateChangeListener... listener);

    void removeListener(FireplaceStateChangeListener... listener);

    void addListener(FireplacePilotLightListener... listener);

    void removeListener(FireplacePilotLightListener... listener);

    void addListener(FireplaceTimeoutListener... listener);

    void removeListener(FireplaceTimeoutListener... listener);

    public void shutdown();
}
