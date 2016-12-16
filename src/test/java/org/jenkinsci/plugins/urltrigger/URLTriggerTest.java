package org.jenkinsci.plugins.urltrigger;

import hudson.model.Node;
import org.jenkinsci.lib.xtrigger.XTriggerLog;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

public class URLTriggerTest {
    @Rule public JenkinsRule j = new JenkinsRule();

    @Test
    public void testWorkflowJobSuccess() throws Exception {
        URLTriggerEntry entry = new URLTriggerEntry();
        entry.setUrl("");

        URLTrigger trigger = new URLTrigger("* * * * *", Arrays.asList(entry), false, "testTrigger", true);
        URLTrigger triggerSpy = Mockito.spy(trigger);
        doReturn(true).when(triggerSpy).checkIfModified(any(Node.class),any(XTriggerLog.class));
        assertTrue(triggerSpy.checkIfModified(null, null));

        j.jenkins.setNumExecutors(1);
        j.createOnlineSlave();
        WorkflowJob p = j.jenkins.createProject(WorkflowJob.class, "p");
        p.setDefinition(new CpsFlowDefinition(
                "node { echo 'hello world' }"));


        p.addTrigger(triggerSpy);
        triggerSpy.start(p, true);
        triggerSpy.run();

        Thread.sleep(60000);

        assertTrue(!p.getBuilds().isEmpty());
        j.assertLogContains("hello world", p.getFirstBuild());

    }

    @Test
    public void testWorkflowJobNoPollingNode() throws Exception {
        URLTriggerEntry entry = new URLTriggerEntry();
        entry.setUrl("");

        URLTrigger trigger = new URLTrigger("* * * * *", Arrays.asList(entry), false, "testTrigger", false);
        URLTrigger triggerSpy = Mockito.spy(trigger);
        doReturn(true).when(triggerSpy).checkIfModified(any(Node.class),any(XTriggerLog.class));
        assertTrue(triggerSpy.checkIfModified(null, null));

        j.createOnlineSlave();
        WorkflowJob p = j.jenkins.createProject(WorkflowJob.class, "p");
        p.setDefinition(new CpsFlowDefinition(
                "node { echo 'hello world' }"));


        p.addTrigger(triggerSpy);
        triggerSpy.start(p, true);
        triggerSpy.run();

        Thread.sleep(60000);

        assertTrue(!p.getBuilds().isEmpty());
        j.assertLogContains("hello world", p.getFirstBuild());

    }
}