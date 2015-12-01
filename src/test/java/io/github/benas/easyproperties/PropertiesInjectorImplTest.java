/*
 *   The MIT License
 *
 *    Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    in the Software without restriction, including without limitation the rights
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *    copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in
 *    all copies or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *    THE SOFTWARE.
 */

package io.github.benas.easyproperties;

import io.github.benas.easyproperties.api.PropertiesInjector;
import org.junit.Before;
import org.junit.Test;

import static io.github.benas.easyproperties.impl.PropertiesInjectorBuilder.aNewPropertiesInjector;
import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesInjectorImplTest {

    private Bean bean;

    @Before
    public void setUp() throws Exception {
        System.setProperty("threshold", "30");
        PropertiesInjector propertiesInjector = aNewPropertiesInjector()
                .registerAnnotationProcessor(MyCustomAnnotation.class, new MyCustomAnnotationProcessor())
                .build();
        bean = new Bean();
        propertiesInjector.injectProperties(bean);
    }

    @Test
    public void testSystemPropertyInjection() throws Exception {
        assertThat(bean.getUserHome()).isEqualTo(System.getProperty("user.home"));
    }

    @Test
    public void testSystemPropertyDefaultValueInjection() throws Exception {
        assertThat(bean.getValue()).isEqualTo("default");
    }

    @Test
    public void testSystemPropertyInjectionWithTypeConversion() throws Exception {
        assertThat(bean.getThreshold()).isEqualTo(30);
    }

    @Test
    public void testCustomAnnotationProcessor() throws Exception {
        assertThat(bean.getCustom()).isEqualTo("foo");
    }
}
