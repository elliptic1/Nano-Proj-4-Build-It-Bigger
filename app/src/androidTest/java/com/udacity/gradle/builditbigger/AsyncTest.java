package com.udacity.gradle.builditbigger;

import android.support.v4.util.Pair;
import android.test.InstrumentationTestCase;

import org.junit.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by todd on 3/14/16.
 */
public class AsyncTest extends InstrumentationTestCase {

    CountDownLatch countDownLatch;

    class MockAsyncTask extends EndpointsAsyncTask {
        @Override
        protected void onPostExecute(String result) {
            countDownLatch.countDown();
            Assert.assertTrue(result.length() > 0);
        }
    }

    public void testAsync() throws Throwable {
        countDownLatch = new CountDownLatch(1);
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                new MockAsyncTask().execute(new Pair<>(getInstrumentation().getContext(), "a"));
            }
        });
        countDownLatch.await(30, TimeUnit.SECONDS);
    }
}
