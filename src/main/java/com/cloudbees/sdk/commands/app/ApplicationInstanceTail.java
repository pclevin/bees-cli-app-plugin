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

import com.cloudbees.sdk.cli.BeesCommand;
import com.cloudbees.sdk.cli.CLICommand;

/**
 * @author Fabian Donze
 */
@BeesCommand(group="Application", description = "Tail application instance log")
@CLICommand("app:instance:tail")
public class ApplicationInstanceTail extends ApplicationInstanceBase {

    public ApplicationInstanceTail() {
        super();
    }

    @Override
    protected boolean execute() throws Exception {
        String instanceId = getInstanceId();

        AppClient client = getBeesClient(AppClient.class);
        client.applicationInstanceTailLog(instanceId, "server", System.out);

        return true;
    }

}
