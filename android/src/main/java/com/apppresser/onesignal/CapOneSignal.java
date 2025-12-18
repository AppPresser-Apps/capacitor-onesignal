package com.apppresser.onesignal;

import com.getcapacitor.Logger;

public class CapOneSignal {

    public String echo(String value) {
        Logger.info("Echo", value);
        return value;
    }
}
