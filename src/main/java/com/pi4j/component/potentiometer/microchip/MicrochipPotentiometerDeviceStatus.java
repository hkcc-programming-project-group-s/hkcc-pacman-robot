package com.pi4j.component.potentiometer.microchip;


/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: Device Abstractions
 * FILENAME      :  MicrochipPotentiometerDeviceStatus.java  
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

/**
 * The device-status concerning the channel this
 * instance of MCP45XX_MCP46XX_Potentiometer is configured for.
 *
 * @author <a href="http://raspelikan.blogspot.co.at">Raspelikan</a>
 * @see com.pi4j.component.potentiometer.microchip.impl.MicrochipPotentiometerBase
 */
public interface MicrochipPotentiometerDeviceStatus {

    /**
     * @return Whether the device is writing to EEPROM at the moment
     */
    public boolean isEepromWriteActive();

    /**
     * @return Whether EEPROM is write-protected
     */
    public boolean isEepromWriteProtected();

    /**
     * @return The channel the wiper-lock-active status is for
     */
    public MicrochipPotentiometerChannel getWiperLockChannel();

    /**
     * @return Whether the wiper's lock is active
     */
    public boolean isWiperLockActive();
}
