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

package com.cloudbees.sdk.commands.app;

import com.cloudbees.api.ApplicationDeleteResponse;
import com.cloudbees.api.BeesClient;
import com.cloudbees.sdk.cli.BeesCommand;
import com.cloudbees.sdk.cli.CLICommand;
import com.cloudbees.sdk.utils.Helper;

/**
 * @author Fabian Donze
 */
@BeesCommand(group="Application", description = "Delete an application")
@CLICommand("app:delete")
public class ApplicationDelete extends ApplicationBase {
    private Boolean force;


    public ApplicationDelete() {
    }

    public void setForce(Boolean force) {
        this.force = force;
    }

    @Override
    protected boolean preParseCommandLine() {
        if (super.preParseCommandLine()) {
            addOption( "f", "force", false, "force delete without prompting" );
            return true;
        }

        return false;
    }

    @Override
    protected boolean execute() throws Exception {
        String appid = getAppId();

        if (force == null || !force.booleanValue()) {
            if (!Helper.promptMatches("Are you sure you want to delete this application [" + appid + "]: (y/n) ", "[yY].*")) {
                return true;
            }
        }

        AppClient client = getAppClient(appid);
        ApplicationDeleteResponse res = client.applicationDelete(appid);

        if (isTextOutput()) {
            if(res.isDeleted())
                System.out.println("application deleted - " + appid);
            else
                System.out.println("application could not be deleted");
        } else
            printOutput(res, ApplicationDeleteResponse.class);

        return true;
    }

}
