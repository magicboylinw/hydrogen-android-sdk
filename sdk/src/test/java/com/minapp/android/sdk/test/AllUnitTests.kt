package com.minapp.android.sdk.test

import junit.framework.TestSuite
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    RecordTest::class, WhereTest::class
)
class AllUnitTests