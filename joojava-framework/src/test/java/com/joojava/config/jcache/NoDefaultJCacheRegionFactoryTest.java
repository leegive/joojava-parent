package com.joojava.config.jcache;

import com.joojava.test.LogbackRecorder;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;

public class NoDefaultJCacheRegionFactoryTest {

    private NoDefaultJcacheRegionFactory factory;

    @Before
    public void setup() {
        LogbackRecorder recorder = LogbackRecorder.forName("org.jboss.logging").reset().capture("ALL");
        factory = new NoDefaultJcacheRegionFactory();
        recorder.release();
    }

    @Test
    public void testNoDefaultJcacheRegionFactory() {
        Throwable caught = catchThrowable(() -> factory.createCache("krypton", null, null));
        assertThat(caught).isInstanceOf(IllegalStateException.class);
        assertThat(caught.getMessage()).isEqualTo(NoDefaultJcacheRegionFactory.EXCEPTION_MESSAGE + " krypton");
    }

}
