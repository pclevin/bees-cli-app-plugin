/*
 * Copyright 2010-2013, CloudBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudbees.sdk.commands.app.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("AccountRegionListResponse")
public class AccountRegionListResponse {
    private List<AccountRegionInfo> regions;

    public AccountRegionListResponse() {
        regions = new ArrayList<AccountRegionInfo>();
    }

    public List<AccountRegionInfo> getRegions() {
        return regions;
    }

    public void setAccountRegionInfos(List<AccountRegionInfo> regions) {
        this.regions = regions;
    }

    public void addAccountRegionInfo(AccountRegionInfo accountInfo) {
        this.regions.add(accountInfo);
    }
}