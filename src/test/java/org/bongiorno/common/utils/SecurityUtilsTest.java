package org.bongiorno.common.utils;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author cbongiorno
 * @version 7/1/12 11:54 AM
 */
public class SecurityUtilsTest{


    @Test
    public void testSHA256() throws Exception {
        // Independently generated hash of "Christian"
        String expected256 = "67e0082893e848c8706431c32dea6c3ca86c488ae24ee6b0661489cb8b3bb78a";

        String testHash = SecurityUtils.hashToSHA256("Christian");
        assertEquals(expected256, testHash);
        assertEquals(testHash.length(), 64);


    }

    @Test
    public void testMD5() throws Exception {
        // Independently generated hash of "Christian"
        String expectedMD5 = "db6017bd1f27118d44083a172a82409f";

        String testHash = SecurityUtils.hashToMD5("Christian");
        assertEquals(expectedMD5, testHash);
        assertEquals(testHash.length(), 32);
    }
    @Test
    public void testSHA1() throws Exception {
        String expectedSha1String = "a94a8fe5ccb19ba61c4c0873d391e987982fbbd3";

        String testHash = SecurityUtils.hashToSHA1("test");
        assertEquals(expectedSha1String, testHash.toString());
    }
}
