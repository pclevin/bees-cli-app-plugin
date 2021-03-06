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

import com.cloudbees.api.ServiceSubscriptionInvokeInfo;
import com.cloudbees.api.StaxClient;
import com.cloudbees.sdk.cli.BeesCommand;
import com.cloudbees.sdk.cli.CLICommand;
import com.cloudbees.sdk.commands.services.ServiceBase;
import com.cloudbees.sdk.utils.Helper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Fabian Donze
 */
@BeesCommand(group="Application", description = "Validate a router SSL certificate")
@CLICommand("app:cert:validate")
public class ApplicationSSLCertValidate extends ServiceBase {
    private String certificate;
    private String privateKey;

    public ApplicationSSLCertValidate() {
    }

    public String getCertificate() throws IOException {
        if (certificate == null) certificate = Helper.promptFor("SSL certificate file name: ", true);
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getPrivateKey() throws IOException {
        if (privateKey == null) privateKey = Helper.promptFor("SSL private key file name: ", true);
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    protected boolean preParseCommandLine() {
        if(super.preParseCommandLine()) {
            addOption( "cert", "certificate", true, "SSL certificate file" );
            addOption( "pk", "privateKey", true, "SSL private key file" );

            return true;
        }
        return false;
    }

    @Override
    protected String getUsageMessage() {
        return "";
    }

    protected boolean execute() throws Exception {
        Map<String, String> settings = getSettings();
        settings.put("cert", "file://" + getCertificate());
        if (!new File(getCertificate()).exists()) {
            throw new IllegalArgumentException("Certificate file not found: " + getCertificate());
        }
        settings.put("key", "file://" + getPrivateKey());
        if (!new File(getPrivateKey()).exists()) {
            throw new IllegalArgumentException("Private key file not found: " + getPrivateKey());
        }
        StaxClient client = getBeesClient(StaxClient.class);
        ServiceSubscriptionInvokeInfo invokeInfo = client.serviceSubscriptionInvoke("cb-app", getAccount(), "validate_ssl_cert", settings);
        System.out.println(invokeInfo.getMessage());
        return true;
    }
}
