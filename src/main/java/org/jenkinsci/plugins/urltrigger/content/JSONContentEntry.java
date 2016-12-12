package org.jenkinsci.plugins.urltrigger.content;

import org.kohsuke.stapler.DataBoundConstructor;

import java.io.Serializable;

/**
 * @author Gregory Boissinot
 */
public class JSONContentEntry implements Serializable {

    private String jsonPath;

    @DataBoundConstructor
    public JSONContentEntry(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    @SuppressWarnings("unused")
    public String getJsonPath() {
        return jsonPath;
    }
}
