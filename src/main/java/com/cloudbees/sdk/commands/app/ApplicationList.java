package com.cloudbees.sdk.commands.app;

import com.cloudbees.api.ApplicationListResponse;
import com.cloudbees.api.BeesClient;
import com.cloudbees.sdk.cli.BeesCommand;
import com.cloudbees.sdk.cli.CLICommand;
import com.cloudbees.sdk.commands.Command;
import com.cloudbees.sdk.utils.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Fabian Donze
 */
@BeesCommand(group="Application", pattern = "app:li.*", description = "List applications")
@CLICommand("app:list")
public class ApplicationList extends Command {
    private String account;

    public ApplicationList() {
    }

    @Override
    protected boolean preParseCommandLine() {
        // add the Options
        addOption( "a", "account", true, "Account Name" );

        return true;
    }

    @Override
    protected boolean postParseCommandLine() {
        return true;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    protected String getAccount() throws IOException {
        return account;
    }

    @Override
    protected boolean execute() throws Exception {
        BeesClient client = getBeesClient();
        ApplicationListResponse res = client.applicationList(getAccount());

        if (isTextOutput()) {
            System.out.println("Application                Status    URL                           Instance(s)");
            System.out.println();
            List<String> list = new ArrayList<String>();
            for (com.cloudbees.api.ApplicationInfo applicationInfo: res.getApplications()) {
                String msg = s(applicationInfo.getId(), 26)+ " " + s(applicationInfo.getStatus(), 10) + s(applicationInfo.getUrls()[0], 38);
                Map<String, String> settings = applicationInfo.getSettings();
                if (settings != null) {
                    msg += " " + settings.get("clusterSize");
                }
                list.add(msg);
            }
            Collections.sort(list);
            for (String app: list)
                System.out.println(app);
        } else {
            printOutput(res, ApplicationInfo.class, ApplicationListResponse.class);
        }

        return true;
    }

    private String s(String str, int length) {
        return Helper.getPaddedString(str, length);
    }

}
