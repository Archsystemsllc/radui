package com.archsystemsinc.pqrs.executer;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

/**
 * Created by mkandaswamy on 2/18/2016.
 */
public class IMapUITestsRunner {

    private static Logger logger = Logger.getLogger(IMapUITestsRunner.class);

    @Before
    public void setUp() throws Exception{
    }

    @Test
    public void invokeTestExecuter(){
        logger.info("invokeTestExecuter(String testCaseID)--->");
        String testCaseID = "TC0001";
        try {
            IMapUIExecuter iMapUIExecuter = new IMapUIExecuter();
            iMapUIExecuter.executeCreateApplication(testCaseID);
        } catch (Exception exception_) {
            Assert.fail("TEST CASE EXECUTION FAILED:" + testCaseID + ". ERROR MESSAGE::" + exception_.getMessage());
        }
        logger.info("invokeTestExecuter(String testCaseID)<---");
    }

}
