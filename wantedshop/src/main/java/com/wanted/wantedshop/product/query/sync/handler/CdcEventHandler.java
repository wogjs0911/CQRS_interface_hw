package com.wanted.wantedshop.product.query.sync.handler;

import com.wanted.wantedshop.product.query.sync.CdcEvent;

public interface CdcEventHandler {
    boolean canHandle(CdcEvent event);
    void handle(CdcEvent event);
}